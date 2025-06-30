package com.conference.platform.control.httpclient;

import com.conference.platform.control.dto.httpclient.RoomResponseDto;

public interface RoomHttpClient {
  RoomResponseDto findByRoomCode(String roomCode);
}
