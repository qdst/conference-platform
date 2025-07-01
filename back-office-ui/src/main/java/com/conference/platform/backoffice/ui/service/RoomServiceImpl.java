package com.conference.platform.backoffice.ui.service;

import com.conference.platform.backoffice.ui.httpclient.BackOfficeGatewayHttpClient;
import com.conference.platform.backoffice.ui.httpclient.dto.CreateRoomRequestDto;
import com.conference.platform.backoffice.ui.httpclient.dto.RoomResponseDto;
import com.conference.platform.backoffice.ui.httpclient.dto.UpdateRoomRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RoomServiceImpl implements RoomService {

  private final BackOfficeGatewayHttpClient gatewayHttpClient;

  @Override
  public RoomResponseDto updateRoom(UpdateRoomRequestDto requestDto, String roomCode) {
    return gatewayHttpClient.updateRoom(requestDto, roomCode);
  }

  @Override
  public RoomResponseDto createRoom(CreateRoomRequestDto createRoomRequestDto) {
    return gatewayHttpClient.createRoom(createRoomRequestDto);
  }

  @Override
  public RoomResponseDto getRoom(String roomCode) {
    return gatewayHttpClient.getRoom(roomCode);
  }

}
