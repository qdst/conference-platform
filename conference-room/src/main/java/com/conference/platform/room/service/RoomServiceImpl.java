package com.conference.platform.room.service;

import com.conference.platform.room.dto.controller.CreateRoomRequestDto;
import com.conference.platform.room.dto.controller.RoomResponseDto;
import com.conference.platform.room.dto.controller.UpdateRoomRequestDto;
import com.conference.platform.room.error.RoomException;
import com.conference.platform.room.httpclient.ConferenceControlHttpClient;
import com.conference.platform.room.httpclient.ConferenceControlHttpClientImpl;
import com.conference.platform.room.mapper.RoomMapper;
import com.conference.platform.room.model.RoomStatus;
import com.conference.platform.room.repository.RoomRepository;
import de.huxhorn.sulky.ulid.ULID;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RoomServiceImpl implements RoomService {

  private final ConferenceControlHttpClient conferenceControlHttpClient;
  private final RoomRepository roomRepository;
  private final ULID ulidGenerator = new ULID();
  private final ConferenceControlHttpClientImpl conferenceControlHttpClientImpl;

  @Override
  public RoomResponseDto createNewRoom(CreateRoomRequestDto roomDto) {
    var roomCode = ulidGenerator.nextULID();
    var room = RoomMapper.toModel(roomDto, roomCode);
    var cretedRoom = roomRepository.save(room);
    return RoomMapper.toResponseDto(cretedRoom);
  }

  @Override
  public RoomResponseDto updateRoom(UpdateRoomRequestDto updateRoomRequestDto, String roomCode) {

    if (updateRoomRequestDto.getRoomStatus() != RoomStatus.AVAILABLE) {
      verifyRoomReservation(roomCode);
    }
    var roomForUpdate = roomRepository.findByRoomCode(roomCode).orElseThrow();
    var newCapacity = updateRoomRequestDto.getCapacity();
    if (newCapacity < roomForUpdate.getCapacity()) {
      hasSufficientCapacityForConferences(roomCode, newCapacity);
    }

    RoomMapper.updateRoom(roomForUpdate, updateRoomRequestDto);
    var updatedRoom = roomRepository.save(roomForUpdate);
    return RoomMapper.toResponseDto(updatedRoom);
  }

  private void hasSufficientCapacityForConferences(String roomCode, Integer newCapacity) {
    var responseDto = conferenceControlHttpClient.conferenceWillExceedCapacity(roomCode, newCapacity);
    if (responseDto.getReservationExceedsNewCapacity()) {
      throw new RoomException("You cannot lower the capacity of room '%s', its capacity already has been reserved.".formatted(roomCode));
    }
  }

  private void verifyRoomReservation(String roomCode) {
    var responseDto = conferenceControlHttpClientImpl.roomHasUpcomingConference(roomCode);
    if (responseDto.getHasConferenceInTheFuture()) {
      throw new RoomException(
          ("Room '%s' has upcoming conferences. To change its status to anything other than AVAILABLE."
           + "Please cancel all future conferences first.").formatted(roomCode));
    }
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
