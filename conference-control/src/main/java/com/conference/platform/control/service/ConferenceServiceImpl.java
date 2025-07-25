package com.conference.platform.control.service;

import com.conference.platform.control.dto.controller.ConferenceAvailabilityResponseDto;
import com.conference.platform.control.dto.controller.ConferenceResponseDto;
import com.conference.platform.control.dto.controller.ConferenceSummaryResponseDto;
import com.conference.platform.control.dto.controller.CreateConferenceRequestDto;
import com.conference.platform.control.dto.controller.UpdateConferenceRequestDto;
import com.conference.platform.control.dto.httpclient.RoomResponseDto;
import com.conference.platform.control.error.ConferenceConflictStateException;
import com.conference.platform.control.error.ConferenceInvalidInputException;
import com.conference.platform.control.error.RoomConflictStateException;
import com.conference.platform.control.httpclient.RoomHttpClient;
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

  private final static String OPERATION_CANCELLED = "CANCELLED";
  private final static String OPERATION_UPDATED = "UPDATED";

  private final ConferenceRepository conferenceRepository;
  private final RoomHttpClient roomHttpClient;
  private final ParticipantRepository participantRepository;
  private final IdentificationCodeService identificationCodeService;
  private final DateTimeService dateTimeService;

  @Override
  @Transactional(readOnly = true)
  public ConferenceResponseDto getConference(String conferenceCode) {
    var conference = conferenceRepository.getByConferenceCode(conferenceCode);
    return ConferenceMapper.toResponseDto(conference);
  }

  @Override
  @Transactional(readOnly = true)
  public ConferenceSummaryResponseDto getConferenceSummary(String conferenceCode) {
    var conference = conferenceRepository.getByConferenceCode(conferenceCode);
    var room = roomHttpClient.findByRoomCode(conference.getRoomCode());
    return ConferenceMapper.toSummaryResponseDto(conference, room.getLocationDto());
  }

  @Override
  public ConferenceResponseDto createConference(CreateConferenceRequestDto requestDto) {
    var roomCode = requestDto.getRoomCode();
    var startTime = requestDto.getStartTime();
    var endTime = requestDto.getEndTime();

    var roomDto = roomHttpClient.findByRoomCode(roomCode);

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
      throw new ConferenceInvalidInputException("Conference start time must be after end time.");
    }
  }

  private void verifyRoomAvailability(RoomResponseDto roomDto, LocalDateTime startTime, LocalDateTime endTime) {
    verifyRoomAvailability(roomDto, startTime, endTime, null);
  }

  private void verifyRoomAvailability(RoomResponseDto roomDto, LocalDateTime conferenceNewStartTime, LocalDateTime conferenceNewEndTime,
      String conferenceCode) {

    if (roomDto.getRoomStatus() != RoomStatus.AVAILABLE) {
      throw new RoomConflictStateException(
          "The room has status other than AVAILABLE. Room status: " + roomDto.getRoomCode());
    }

    var hasOverlappingConferences =
        conferenceRepository.existsOverlapping(roomDto.getRoomCode(), conferenceNewStartTime, conferenceNewEndTime, conferenceCode);

    if (hasOverlappingConferences) {
      throw new RoomConflictStateException("The room has overlapping conferences. Room code: " + roomDto.getRoomCode());
    }

  }

  @Override
  public ConferenceResponseDto cancelConference(String conferenceCode) {
    var conference = conferenceRepository.getByConferenceCode(conferenceCode);

    if (conference.getStatus() != ConferenceStatus.SCHEDULED) {
      throw new ConferenceConflictStateException(
          "Conference can be canceled only in the status SCHEDULED. The current conference status: "
          + conference.getStatus());
    }
    verifyConferenceHasCorrectStartAndEndTime(conference.getStartTime(), conference.getEndTime(), OPERATION_CANCELLED);

    conference.setStatus(ConferenceStatus.CANCELLED);

    participantRepository.cancelAllRegistrationsForConference(conferenceCode);
    var canceledConference = conferenceRepository.save(conference);
    return ConferenceMapper.toResponseDto(canceledConference);
  }

  private void verifyConferenceHasCorrectStartAndEndTime(LocalDateTime startTime, LocalDateTime endTime,
      String action) {
    var currentTime = dateTimeService.getCurrentDateTime();

    if (currentTime.isAfter(startTime) && currentTime.isBefore(endTime)) {
      throw new ConferenceConflictStateException("The conference cannot be '%s' during the progress".formatted(action));
    } else if (currentTime.isAfter(startTime) && currentTime.isAfter(endTime)) {
      throw new ConferenceConflictStateException("The conference cannot be '%s' when it's completed".formatted(action));
    }
  }

  @Override
  public ConferenceResponseDto updateConference(String conferenceCode, UpdateConferenceRequestDto requestDto) {
    var conference = conferenceRepository.getByConferenceCode(conferenceCode);
    if (conference.getStatus() != ConferenceStatus.SCHEDULED) {
      throw new ConferenceConflictStateException(
          "Conference can be updated only in the status SCHEDULED. The current conference status: "
          + conference.getStatus());
    }
    verifyConferenceHasCorrectStartAndEndTime(conference.getStartTime(), conference.getEndTime(), OPERATION_UPDATED);
    var conferenceNewStartTime = requestDto.getStartTime();
    var conferenceNewEndTime = requestDto.getEndTime();

    verifyCorrectInputStartTimeAndEndTime(conferenceNewStartTime, conferenceNewEndTime);

    var newRoomCode = requestDto.getRoomCode();

    if (!newRoomCode.equals(conference.getRoomCode())) {

      var newRoomDto = roomHttpClient.findByRoomCode(newRoomCode);
      verifyConferenceRoomCanBeChanged(conferenceCode, conferenceNewStartTime, conferenceNewEndTime, newRoomDto);
      conference.setRoomCode(newRoomCode);
      conference.setTotalCapacity(newRoomDto.getCapacity());
    }

    conference.setStartTime(conferenceNewStartTime);
    conference.setEndTime(conferenceNewEndTime);

    var updatedConference = conferenceRepository.save(conference);

    return ConferenceMapper.toResponseDto(updatedConference);
  }

  private void verifyConferenceRoomCanBeChanged(
      String conferenceCode,
      LocalDateTime conferenceNewStartTime,
      LocalDateTime conferenceNewEndTime,
      RoomResponseDto roomDto) {
    verifyRoomAvailability(roomDto, conferenceNewStartTime, conferenceNewEndTime, conferenceCode);

    var maxCapacity = roomDto.getCapacity();
    var activeParticipantRegistrations = participantRepository.countAllActiveConferenceParticipant(conferenceCode);
    validateRoomCapacity(maxCapacity, activeParticipantRegistrations);
  }

  private void validateRoomCapacity(int maxCapacity, int activeParticipantRegistrations) {
    if (Utils.hasNoCapacity(maxCapacity, activeParticipantRegistrations)) {
      throw new RoomConflictStateException("Room won't have enough capacity during this period.");
    }
  }

  @Override
  @Transactional(readOnly = true)
  public ConferenceAvailabilityResponseDto checkAvailability(String conferenceCode) {
    var conference = conferenceRepository.getByConferenceCode(conferenceCode);

    if (conference.getStatus() != ConferenceStatus.SCHEDULED) {
      throw new ConferenceConflictStateException(
          "The conference must be in status SCHEDULED. The current conference status: " + conference.getStatus());
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
    var room = roomHttpClient.findByRoomCode(conference.getRoomCode());
    return ConferenceMapper.toSummaryResponseDto(conference, room.getLocationDto());
  }

}
