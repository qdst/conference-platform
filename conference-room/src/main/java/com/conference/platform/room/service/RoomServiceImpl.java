package com.conference.platform.room.service;

import com.conference.platform.room.dto.CreateRoomRequestDto;
import com.conference.platform.room.dto.RoomResponseDto;
import com.conference.platform.room.dto.UpdateRoomRequestDto;
import com.conference.platform.room.mapper.RoomMapper;
import com.conference.platform.room.repository.RoomRepository;
import de.huxhorn.sulky.ulid.ULID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RoomServiceImpl implements RoomService {

  private final RoomRepository roomRepository;
  private final ULID ulidGenerator = new ULID();

  @Override
  public RoomResponseDto createNewRoom(CreateRoomRequestDto roomDto) {
    var roomCode = ulidGenerator.nextULID();
    var room = RoomMapper.toModel(roomDto, roomCode);
    var cretedRoom = roomRepository.save(room);
    return RoomMapper.toResponseDto(cretedRoom);
  }

  @Override
  public RoomResponseDto updateRoom(UpdateRoomRequestDto updateRoomRequestDto, String roomCode) {
    var roomForUpdate = roomRepository.findByRoomCode(roomCode);
    RoomMapper.updateRoom(roomForUpdate, updateRoomRequestDto);
    var updatedRoom = roomRepository.save(roomForUpdate);
    return RoomMapper.toResponseDto(updatedRoom);
  }

  @Override
  public RoomResponseDto findByRoomCode(String roomCode) {
    var foundRoom = roomRepository.findByRoomCode(roomCode);
    return RoomMapper.toResponseDto(foundRoom);
  }
}
