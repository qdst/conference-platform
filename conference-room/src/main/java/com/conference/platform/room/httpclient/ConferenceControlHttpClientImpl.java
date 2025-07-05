package com.conference.platform.room.httpclient;

import com.conference.platform.room.dto.httpclient.CapacityCheckResponseDto;
import com.conference.platform.room.dto.httpclient.RoomOccupationResponseDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class ConferenceControlHttpClientImpl implements ConferenceControlHttpClient {

  private final String checkRoomUpcomingReservation;
  private final String checkRoomNewCapacityPath;
  private final RestTemplate restTemplate;

  public ConferenceControlHttpClientImpl(
      @Value("${conference.control.rest.client.paths.conferences.rooms.new-capacity}") String checkRoomNewCapacityPath,
      @Value("${conference.control.rest.client.paths.conferences.rooms.reservation}") String checkRoomUpcomingReservation,
      RestTemplate restTemplate) {
    this.restTemplate = restTemplate;
    this.checkRoomNewCapacityPath = checkRoomNewCapacityPath;
    this.checkRoomUpcomingReservation = checkRoomUpcomingReservation;
  }

  @Override
  public RoomOccupationResponseDto roomHasUpcomingConference(String roomCode) {
    return restTemplate.getForObject(checkRoomUpcomingReservation,  RoomOccupationResponseDto.class, roomCode);
  }

  @Override
  public CapacityCheckResponseDto conferenceWillExceedCapacity(String roomCode, Integer newCapacity) {
    return restTemplate.getForObject(checkRoomNewCapacityPath, CapacityCheckResponseDto.class, roomCode, newCapacity);
  }

}
