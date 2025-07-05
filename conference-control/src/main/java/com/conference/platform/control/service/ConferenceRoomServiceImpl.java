package com.conference.platform.control.service;

import com.conference.platform.control.dto.controller.CapacityCheckResponseDto;
import com.conference.platform.control.dto.controller.RoomOccupationResponseDto;
import com.conference.platform.control.repository.ConferenceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ConferenceRoomServiceImpl implements ConferenceRoomService {

  private final ConferenceRepository conferenceRepository;
  private final DateTimeService dateTimeService;

  @Override
  public RoomOccupationResponseDto roomHasUpcomingConference(String roomCode) {
    var currentDateTime = dateTimeService.getCurrentDateTime();

    var isOccupied = conferenceRepository.roomHasUpcomingConference(roomCode, currentDateTime);

    return RoomOccupationResponseDto.builder()
        .hasConferenceInTheFuture(isOccupied)
        .build();
  }

  @Override
  public CapacityCheckResponseDto conferenceWillExceedCapacity(String roomCode, Integer newCapacity) {
    var currentDateTime = dateTimeService.getCurrentDateTime();

    var isOccupied = conferenceRepository.conferenceWillExceedCapacity(roomCode, currentDateTime, newCapacity);

    return CapacityCheckResponseDto.builder()
        .reservationExceedsNewCapacity(isOccupied)
        .build();
  }
}
