package com.conference.platform.room.mapper;

import com.conference.platform.room.dto.CreateRoomRequestDto;
import com.conference.platform.room.dto.RoomResponseDto;
import com.conference.platform.room.dto.UpdateRoomRequestDto;
import com.conference.platform.room.model.entity.Room;

public class RoomMapper {

  public static Room toModel(CreateRoomRequestDto roomDto, String roomCode) {
    var room = new Room();
    room.setRoomCode(roomCode);
    room.setName(roomDto.getName());
    room.setCapacity(roomDto.getCapacity());
    room.setRoomStatus(roomDto.getRoomStatus());
    room.setLocation(LocationMapper.toModel(roomDto.getLocationDto()));
    room.getLocation().setRoom(room);
    return room;
  }

  public static RoomResponseDto toResponseDto(Room room) {
    return RoomResponseDto.builder()
        .roomCode(room.getRoomCode())
        .name(room.getName())
        .locationDto(LocationMapper.toDto(room.getLocation()))
        .roomStatus(room.getRoomStatus())
        .capacity(room.getCapacity())
        .build();
  }

  public static void updateRoom(Room room, UpdateRoomRequestDto requestDto) {
    room.setName(requestDto.getName());
    room.setRoomStatus(requestDto.getRoomStatus());
    room.setCapacity(requestDto.getCapacity());
  }
}
