package com.conference.platform.room.controller;

import com.conference.platform.room.dto.controller.CreateRoomRequestDto;
import com.conference.platform.room.dto.controller.RoomResponseDto;
import com.conference.platform.room.dto.controller.UpdateRoomRequestDto;
import com.conference.platform.room.service.RoomService;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/rooms")
public class RoomController {

  private final RoomService roomService;

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public RoomResponseDto create(@Valid @RequestBody CreateRoomRequestDto roomDto) {
    return roomService.createNewRoom(roomDto);
  }

  @PutMapping("/{roomCode}")
  public RoomResponseDto update(@Valid @RequestBody UpdateRoomRequestDto roomDto, @PathVariable String roomCode) {
    return roomService.updateRoom(roomDto, roomCode);
  }

  @GetMapping("/{roomCode}")
  public RoomResponseDto findByRoomCode(@PathVariable String roomCode) {
    return roomService.findByRoomCode(roomCode);
  }

  @GetMapping
  public List<RoomResponseDto> findAll() {
    return roomService.findAll();
  }

}
