package com.conference.platform.backoffice.ui.mapper;

import com.conference.platform.backoffice.ui.httpclient.dto.CreateRoomRequestDto;
import com.conference.platform.backoffice.ui.httpclient.dto.LocationDto;
import com.conference.platform.backoffice.ui.httpclient.dto.RoomResponseDto;
import com.conference.platform.backoffice.ui.httpclient.dto.UpdateRoomRequestDto;
import com.conference.platform.backoffice.ui.view.RoomViewModel;

public class RoomMapper {

  public static UpdateRoomRequestDto toUpdateDto(RoomViewModel roomViewModel) {
    return UpdateRoomRequestDto.builder()
        .name(roomViewModel.getName())
        .roomStatus(roomViewModel.getRoomStatus())
        .capacity(roomViewModel.getCapacity())
        .build();
  }

  public static CreateRoomRequestDto toCreateDto(RoomViewModel roomViewModel) {
    var locationDto  = LocationDto.builder()
        .city(roomViewModel.getCity())
        .addressLine1(roomViewModel.getAddressLine1())
        .addressLine2(roomViewModel.getAddressLine2())
        .build();

    return CreateRoomRequestDto.builder()
        .name(roomViewModel.getName())
        .roomStatus(roomViewModel.getRoomStatus())
        .capacity(roomViewModel.getCapacity())
        .locationDto(locationDto)
        .build();
  }

  public static RoomViewModel toViewModel(RoomResponseDto responseDto) {
    RoomViewModel roomViewModel = new RoomViewModel();
    roomViewModel.setRoomStatus(responseDto.getRoomStatus());
    roomViewModel.setCapacity(responseDto.getCapacity());
    roomViewModel.setName(responseDto.getName());
    roomViewModel.setRoomCode(responseDto.getRoomCode());
    var locationDto = responseDto.getLocationDto();
    roomViewModel.setCity(locationDto.getCity());
    roomViewModel.setAddressLine1(locationDto.getAddressLine1());
    roomViewModel.setAddressLine2(locationDto.getAddressLine2());
    return roomViewModel;
  }
}
