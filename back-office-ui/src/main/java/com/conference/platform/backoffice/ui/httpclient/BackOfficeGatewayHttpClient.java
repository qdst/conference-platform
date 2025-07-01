package com.conference.platform.backoffice.ui.httpclient;

import com.conference.platform.backoffice.ui.httpclient.dto.CreateRoomRequestDto;
import com.conference.platform.backoffice.ui.httpclient.dto.RoomResponseDto;
import com.conference.platform.backoffice.ui.httpclient.dto.UpdateRoomRequestDto;

public interface BackOfficeGatewayHttpClient {

  RoomResponseDto createRoom(CreateRoomRequestDto requestDto);

  RoomResponseDto updateRoom(UpdateRoomRequestDto requestDto, String roomCode);

  RoomResponseDto getRoom(String roomCode);
}
