package com.conference.platform.room.mapper;

import com.conference.platform.room.dto.controller.LocationDto;
import com.conference.platform.room.model.entity.Location;

public class LocationMapper {

  public static Location toModel(LocationDto locationDto) {
    var location = new Location();
    location.setAddressLine1(locationDto.getAddressLine1());
    location.setAddressLine2(locationDto.getAddressLine2());
    location.setCity(locationDto.getCity());
    return location;
  }

  public static LocationDto toDto(Location location) {
    return LocationDto.builder()
        .addressLine1(location.getAddressLine1())
        .addressLine2(location.getAddressLine2())
        .city(location.getCity())
        .build();
  }

}
