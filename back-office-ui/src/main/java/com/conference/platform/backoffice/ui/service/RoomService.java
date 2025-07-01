package com.conference.platform.backoffice.ui.service;

import com.conference.platform.backoffice.ui.httpclient.dto.CreateRoomRequestDto;
import com.conference.platform.backoffice.ui.httpclient.dto.RoomResponseDto;
import com.conference.platform.backoffice.ui.httpclient.dto.UpdateRoomRequestDto;

public interface RoomService {

  RoomResponseDto updateRoom(UpdateRoomRequestDto requestDto, String roomCode);

  RoomResponseDto createRoom(CreateRoomRequestDto createRoomRequestDto);

  RoomResponseDto getRoom(String roomCode);
}
