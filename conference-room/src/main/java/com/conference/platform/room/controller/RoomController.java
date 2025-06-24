package com.conference.platform.room.controller;

import com.conference.platform.room.dto.CreateRoomRequestDto;
import com.conference.platform.room.dto.RoomResponseDto;
import com.conference.platform.room.dto.UpdateRoomRequestDto;
import com.conference.platform.room.service.RoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/conference-rooms")
public class RoomController {

  private final RoomService roomService;

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public RoomResponseDto create(@RequestBody CreateRoomRequestDto roomDto) {
    return roomService.createNewRoom(roomDto);
  }

  @PatchMapping("/{roomCode}")
  public RoomResponseDto update(@RequestBody UpdateRoomRequestDto roomDto,  @PathVariable String roomCode) {
    return roomService.updateRoom(roomDto, roomCode);
  }

  @PutMapping("/{roomCode}")
  public RoomResponseDto findByRoomCode(@PathVariable String roomCode) {
    return roomService.findByRoomCode(roomCode);
  }

}
