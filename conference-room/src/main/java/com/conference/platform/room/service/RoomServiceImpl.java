package com.conference.platform.room.service;

import com.conference.platform.room.dto.CreateRoomRequestDto;
import com.conference.platform.room.dto.RoomResponseDto;
import com.conference.platform.room.dto.UpdateRoomRequestDto;
import com.conference.platform.room.mapper.RoomMapper;
import com.conference.platform.room.repository.RoomRepository;
import de.huxhorn.sulky.ulid.ULID;
import java.util.List;
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
    // TODO: validate for upcoming conferences (e.g. capacity, status)

    var roomForUpdate = roomRepository.findByRoomCode(roomCode).orElseThrow();
    RoomMapper.updateRoom(roomForUpdate, updateRoomRequestDto);
    var updatedRoom = roomRepository.save(roomForUpdate);
    return RoomMapper.toResponseDto(updatedRoom);
  }

  @Override
  public RoomResponseDto findByRoomCode(String roomCode) {
    return roomRepository.findByRoomCode(roomCode)
        .map(RoomMapper::toResponseDto).orElseThrow();
  }

  @Override
  public List<RoomResponseDto> findAll() {
    return roomRepository.findAll().stream()
        .map(RoomMapper::toResponseDto)
        .toList();
  }
}
