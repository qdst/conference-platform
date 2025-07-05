package com.conference.platform.control.service;

import com.conference.platform.control.dto.controller.CapacityCheckResponseDto;
import com.conference.platform.control.dto.controller.RoomOccupationResponseDto;

public interface ConferenceRoomService {
  RoomOccupationResponseDto roomHasUpcomingConference(String roomCode);
  CapacityCheckResponseDto willCapacityBeExceeded(String roomCode, Integer newCapacity);
}
