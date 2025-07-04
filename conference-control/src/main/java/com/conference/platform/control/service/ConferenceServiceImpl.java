package com.conference.platform.control.service;

import com.conference.platform.control.dto.controller.ConferenceAvailabilityResponseDto;
import com.conference.platform.control.dto.controller.ConferenceSummaryResponseDto;
import com.conference.platform.control.dto.controller.CreateConferenceRequestDto;
import com.conference.platform.control.dto.controller.ConferenceResponseDto;
import com.conference.platform.control.dto.controller.UpdateConferenceRequestDto;
import com.conference.platform.control.dto.httpclient.RoomResponseDto;
import com.conference.platform.control.error.ConferenceException;
import com.conference.platform.control.error.InvalidConferenceInputException;
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
  @Transactional(readOnly = true)
  public ConferenceResponseDto getConference(String conferenceCode) {
    var conference = conferenceRepository.getByConferenceCode(conferenceCode);
    return ConferenceMapper.toResponseDto(conference);
  }

  @Transactional(readOnly = true)
  @Override
  public ConferenceSummaryResponseDto getConferenceSummary(String conferenceCode) {
    var conference = conferenceRepository.getByConferenceCode(conferenceCode);
    var room = roomService.findByRoomCode(conference.getRoomCode());
    return ConferenceMapper.toSummaryResponseDto(conference, room.getLocationDto());
  }

  @Override
  public ConferenceResponseDto createConference(CreateConferenceRequestDto requestDto) {
    var roomCode = requestDto.getRoomCode();
    var startTime = requestDto.getStartTime();
    var endTime = requestDto.getEndTime();

    var roomDto = roomService.findByRoomCode(roomCode);

    verifyCorrectInputStartTimeAndEndTime(startTime, endTime);
    verifyRoomAvailability(roomDto, startTime, endTime);

    var roomCapacity = roomDto.getCapacity();

    var conferenceCode = identificationCodeService.generateConferenceCode();
    var newConference = ConferenceMapper.toNewModel(requestDto, conferenceCode, roomCapacity);

    var createdConference = conferenceRepository.save(newConference);
    return ConferenceMapper.toResponseDto(createdConference);
  }

  private static void verifyCorrectInputStartTimeAndEndTime(LocalDateTime startTime, LocalDateTime endTime) {
    if (startTime.isAfter(endTime) || startTime.isEqual(endTime)) {
      throw new InvalidConferenceInputException("Conference start time must be after end time.");
    }
  }

  private void verifyRoomAvailability(RoomResponseDto roomDto, LocalDateTime startTime, LocalDateTime endTime) {
    verifyRoomAvailability(roomDto, startTime, endTime, null);
  }

  private void verifyRoomAvailability(RoomResponseDto roomDto, LocalDateTime startTime, LocalDateTime endTime, String conferenceCode) {
    var hasOverlappingConferences =
        conferenceRepository.existsOverlapping(roomDto.getRoomCode(), startTime, endTime, conferenceCode);

    if (hasOverlappingConferences) {
      throw new ConferenceException("The room has overlapping conferences. Room code: " + roomDto.getRoomCode());
    }

    if (roomDto.getRoomStatus() != RoomStatus.AVAILABLE) {
      throw new ConferenceException("The room has status other than AVAILABLE. Room status: " + roomDto.getRoomCode());
    }
  }

  @Override
  public ConferenceResponseDto cancelConference(String conferenceCode) {
    var conference = conferenceRepository.getByConferenceCode(conferenceCode);

    if(conference.getStatus() != ConferenceStatus.SCHEDULED) {
      throw new ConferenceException(
          "Conference can be canceled only in the status SCHEDULED. The current conference status: "
          + conference.getStatus());
    }
    verifyConferenceHasCorrectStartAndEndTime(conference.getStartTime(), conference.getEndTime(), "CANCELLED");

    conference.setStatus(ConferenceStatus.CANCELLED);

    participantRepository.cancelAllRegistrationsForConference(conferenceCode);
    var canceledConference = conferenceRepository.save(conference);
    return ConferenceMapper.toResponseDto(canceledConference);
  }

  private void verifyConferenceHasCorrectStartAndEndTime(LocalDateTime startTime, LocalDateTime endTime, String action) {
    var currentTime = dateTimeService.getCurrentDateTime();

    if (currentTime.isAfter(startTime) && currentTime.isBefore(endTime)) {
      throw new ConferenceException("The conference cannot be '%s' during the progress".formatted(action));
    } else if (currentTime.isAfter(startTime) && currentTime.isAfter(endTime)) {
      throw new ConferenceException("The conference cannot be '%s' when it's completed".formatted(action));
    }
  }

  @Override
  public ConferenceResponseDto updateConference(String conferenceCode, UpdateConferenceRequestDto requestDto) {
    var conference = conferenceRepository.getByConferenceCode(conferenceCode);
    if (conference.getStatus() != ConferenceStatus.SCHEDULED) {
      throw new ConferenceException(
          "Conference can be updated only in the status SCHEDULED. The current conference status: "
          + conference.getStatus());
    }
    verifyConferenceHasCorrectStartAndEndTime(conference.getStartTime(), conference.getEndTime(), "UPDATED");

    var startTimeForUpdate = requestDto.getStartTime();
    var endTimeForUpdate = requestDto.getEndTime();
    var roomCodeForUpdate = requestDto.getRoomCode();

    verifyCorrectInputStartTimeAndEndTime(startTimeForUpdate, endTimeForUpdate);

    var roomDto = roomService.findByRoomCode(roomCodeForUpdate);
    verifyRoomAvailability(roomDto, startTimeForUpdate, endTimeForUpdate,  conferenceCode);

    var maxCapacity = roomDto.getCapacity();
    var activeRegistrations = participantRepository.countAllActiveConferenceParticipant(conferenceCode);
    validateRoomCapacity(maxCapacity, activeRegistrations);

    conference.setEndTime(endTimeForUpdate);
    conference.setStartTime(endTimeForUpdate);
    conference.setRoomCode(roomCodeForUpdate);
    var updatedConference = conferenceRepository.save(conference);

    return ConferenceMapper.toResponseDto(updatedConference);
  }

  private void validateRoomCapacity(int maxCapacity, int activeRegistrations) {
    if(Utils.hasNoCapacity(maxCapacity, activeRegistrations)) {
      throw new ConferenceException("Room has no capacity.");
    }
  }

  @Override
  @Transactional(readOnly = true)
  public ConferenceAvailabilityResponseDto checkAvailability(String conferenceCode) {
    var conference = conferenceRepository.getByConferenceCode(conferenceCode);
    if(conference.getStatus() != ConferenceStatus.SCHEDULED) {
      throw new  ConferenceException("The conference must be in status SCHEDULED. The current conference status: " + conference.getStatus());
    }

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
  public List<ConferenceSummaryResponseDto> findAvailableConferences(LocalDateTime startTime, LocalDateTime endTime) {
    verifyCorrectInputStartTimeAndEndTime(startTime, endTime);
    return conferenceRepository.findAllWithinTimeRange(startTime, endTime).parallelStream()
        .filter(conference -> conference.getTotalCapacity() > conference.getActiveParticipantsCount())
        .map(this::createSummaryResponseDto)
        .toList();
  }

  private ConferenceSummaryResponseDto createSummaryResponseDto(Conference conference) {
    var room = roomService.findByRoomCode(conference.getRoomCode());
    return ConferenceMapper.toSummaryResponseDto(conference, room.getLocationDto());
  }

}
