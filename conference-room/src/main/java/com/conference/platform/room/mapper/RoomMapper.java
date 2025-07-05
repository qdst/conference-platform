package com.conference.platform.room.mapper;

import com.conference.platform.room.dto.controller.CreateRoomRequestDto;
import com.conference.platform.room.dto.controller.RoomResponseDto;
import com.conference.platform.room.dto.controller.UpdateRoomRequestDto;
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
    var roomNewName = requestDto.getName();
    if(roomNewName != null && !(room.getName().equals(roomNewName))) {
      room.setName(roomNewName);
    }
    var roomNewStatus = requestDto.getRoomStatus();
    if(roomNewStatus != null && !(room.getRoomStatus().equals(roomNewStatus))) {
      room.setRoomStatus(roomNewStatus);
    }
    var roomNewCapacity = requestDto.getCapacity();
    if(roomNewCapacity != null && !(room.getCapacity().equals(roomNewCapacity))) {
      room.setCapacity(requestDto.getCapacity());
    }
  }
}
