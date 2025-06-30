package com.conference.platform.control.service;

import com.conference.platform.control.dto.httpclient.RoomResponseDto;

public interface RoomService {

  RoomResponseDto findByRoomCode(String roomCode);
}
