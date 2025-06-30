package com.conference.platform.room.service;

import com.conference.platform.room.dto.CreateRoomRequestDto;
import com.conference.platform.room.dto.RoomResponseDto;
import com.conference.platform.room.dto.UpdateRoomRequestDto;
import java.util.List;

public interface RoomService {

  RoomResponseDto createNewRoom(CreateRoomRequestDto createRoomRequestDto);
  RoomResponseDto updateRoom(UpdateRoomRequestDto updateRoomRequestDto, String roomCode);
  RoomResponseDto findByRoomCode(String roomCode);
  List<RoomResponseDto> findAll();
}
