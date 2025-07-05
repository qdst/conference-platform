package com.conference.platform.room.httpclient;

import com.conference.platform.room.dto.httpclient.CapacityCheckResponseDto;
import com.conference.platform.room.dto.httpclient.RoomOccupationResponseDto;

public interface ConferenceControlHttpClient {

  RoomOccupationResponseDto roomHasUpcomingConference(String roomCode);
  CapacityCheckResponseDto willCapacityBeExceeded(String roomCode, Integer newCapacity);
}
