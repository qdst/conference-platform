package com.conference.platform.control.service;

import com.conference.platform.control.dto.httpclient.RoomResponseDto;
import com.conference.platform.control.httpclient.RoomHttpClient;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class RoomServiceImpl implements RoomService {

  private final RoomHttpClient roomHttpClient;

  @Override
  public RoomResponseDto findByRoomCode(String roomCode) {
    return roomHttpClient.findByRoomCode(roomCode);
  }
}
