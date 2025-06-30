package com.conference.platform.control.service;

import com.conference.platform.control.dto.controller.ConferenceAvailabilityResponseDto;
import com.conference.platform.control.dto.controller.CreateConferenceRequestDto;
import com.conference.platform.control.dto.controller.ConferenceResponseDto;
import com.conference.platform.control.dto.controller.UpdateConferenceRequestDto;
import com.conference.platform.control.dto.httpclient.RoomResponseDto;
import com.conference.platform.control.mapper.ConferenceMapper;
import com.conference.platform.control.model.ConferenceStatus;
import com.conference.platform.control.model.RoomStatus;
import com.conference.platform.control.model.entity.Conference;
import com.conference.platform.control.repository.ConferenceRepository;
import com.conference.platform.control.repository.ParticipantRepository;
import com.conference.platform.control.util.Utils;
import java.time.LocalDateTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class ConferenceServiceImpl implements ConferenceService {

  private final ConferenceRepository conferenceRepository;
  private final RoomService roomService;
  private final ParticipantRepository participantRepository;
  private final IdentificationCodeService identificationCodeService;
  private final DateTimeService dateTimeService;

  @Override
  public ConferenceResponseDto createConference(CreateConferenceRequestDto requestDto) {
    var roomCode = requestDto.getRoomCode();
    var startTime = requestDto.getStartTime();
    var endTime = requestDto.getEndTime();

    var roomDto = roomService.findByRoomCode(roomCode);

    verifyCorrectStartTimeAndEndTime(startTime, endTime);
    verifyRoomAvailability(roomDto, startTime, endTime);

    var roomCapacity = roomDto.getCapacity();

    var conferenceCode = identificationCodeService.generateConferenceCode();
    var newConference = ConferenceMapper.toNewModel(requestDto, conferenceCode, roomCapacity);

    var createdConference = conferenceRepository.save(newConference);
    return ConferenceMapper.toDto(createdConference);
  }

  private static void verifyCorrectStartTimeAndEndTime(LocalDateTime startTime, LocalDateTime endTime) {
    if (startTime.isAfter(endTime) || startTime.isEqual(endTime)) {
      throw null;
    }
  }

  private void verifyRoomAvailability(RoomResponseDto roomDto, LocalDateTime startTime, LocalDateTime endTime) {
    verifyRoomAvailability(roomDto, startTime, endTime, null);
  }

  private void verifyRoomAvailability(RoomResponseDto roomDto, LocalDateTime startTime, LocalDateTime endTime, String conferenceCode) {
    var hasOverlappingConferences =
        conferenceRepository.existsOverlapping(roomDto.getRoomCode(), startTime, endTime, conferenceCode);

    if (hasOverlappingConferences) {
      throw null;
    }

    if (roomDto.getRoomStatus() != RoomStatus.AVAILABLE) {
      throw null;
    }
  }

  @Override
  public void cancelConference(String conferenceCode) {
    var conference = conferenceRepository.getByCode(conferenceCode);

    verifyConferenceState(conference);

    conference.setStatus(ConferenceStatus.CANCELLED);

    participantRepository.cancelAllRegistrationsForConference(conferenceCode);
    conferenceRepository.save(conference);
  }

  private void verifyConferenceState(Conference conference) {
    if (conference.getStatus() != ConferenceStatus.SCHEDULED) {
      throw null;
    }

    var startTime = conference.getStartTime();
    var endTime = conference.getEndTime();
    var currentTime = dateTimeService.getCurrentDateTime();

    if (currentTime.isAfter(startTime) && currentTime.isBefore(endTime)) {
      throw null; // Conference in progress
    } else if (currentTime.isAfter(startTime) && currentTime.isAfter(endTime)) {
      throw null; // Conference is completed
    }
  }

  @Override
  public ConferenceResponseDto updateConference(String conferenceCode, UpdateConferenceRequestDto requestDto) {
    var conference = conferenceRepository.getByCode(conferenceCode);
    verifyConferenceState(conference);

    var startTimeForUpdate = requestDto.getStartTime();
    var endTimeForUpdate = requestDto.getEndTime();
    var roomCodeForUpdate = requestDto.getRoomCode();

    verifyCorrectStartTimeAndEndTime(startTimeForUpdate, endTimeForUpdate);

    var roomDto = roomService.findByRoomCode(roomCodeForUpdate);
    verifyRoomAvailability(roomDto, startTimeForUpdate, endTimeForUpdate,  conferenceCode);

    var maxCapacity = roomDto.getCapacity();
    var activeRegistrations = participantRepository.countAllActiveConferenceParticipant(conferenceCode);
    validateRoomCapacity(maxCapacity, activeRegistrations);

    conference.setEndTime(endTimeForUpdate);
    conference.setStartTime(endTimeForUpdate);
    conference.setRoomCode(roomCodeForUpdate);
    var updatedConference = conferenceRepository.save(conference);

    return ConferenceMapper.toDto(updatedConference);
  }

  private void validateRoomCapacity(int maxCapacity, int activeRegistrations) {
    if(Utils.hasNoCapacity(maxCapacity, activeRegistrations)) {
      throw null;
    }
  }

  @Override
  @Transactional(readOnly = true)
  public ConferenceAvailabilityResponseDto checkAvailability(String conferenceCode) {
    var conference = conferenceRepository.getByCode(conferenceCode);
    verifyConferenceState(conference);

    var activeRegistrations = participantRepository.countAllActiveConferenceParticipant(conferenceCode);
    var maxCapacity = conference.getTotalCapacity();

    var availableCapacity = Utils.computeAvailableCapacity(maxCapacity, activeRegistrations);
    var isConferenceAvailable = Utils.hasFreeCapacity(maxCapacity, activeRegistrations);

    return ConferenceAvailabilityResponseDto.builder()
        .remainingCapacity(availableCapacity)
        .isConferenceAvailable(isConferenceAvailable)
        .build();
  }

  @Override
  @Transactional(readOnly = true)
  public List<ConferenceResponseDto> findAvailableConferences(LocalDateTime startTime, LocalDateTime endTime) {
    verifyCorrectStartTimeAndEndTime(startTime, endTime);
    return conferenceRepository.findAllWithinTimeRange(startTime, endTime).stream()
        .map(ConferenceMapper::toDto)
        .toList();
  }

}
