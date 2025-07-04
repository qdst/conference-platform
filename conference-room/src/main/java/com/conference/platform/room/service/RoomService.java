package com.conference.platform.room.service;

import com.conference.platform.room.dto.controller.CreateRoomRequestDto;
import com.conference.platform.room.dto.controller.RoomResponseDto;
import com.conference.platform.room.dto.controller.UpdateRoomRequestDto;
import java.util.List;

public interface RoomService {

  RoomResponseDto createNewRoom(CreateRoomRequestDto createRoomRequestDto);
  RoomResponseDto updateRoom(UpdateRoomRequestDto updateRoomRequestDto, String roomCode);
  RoomResponseDto findByRoomCode(String roomCode);
  List<RoomResponseDto> findAll();
}
