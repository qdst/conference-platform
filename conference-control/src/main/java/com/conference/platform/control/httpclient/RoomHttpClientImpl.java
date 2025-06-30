package com.conference.platform.control.httpclient;

import com.conference.platform.control.dto.httpclient.RoomResponseDto;
import java.util.Collections;
import java.util.List;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class RoomHttpClientImpl implements RoomHttpClient {

  private final String findOneRoomPath;

  private final RestTemplate restTemplate;

  public RoomHttpClientImpl(
      @Value("${conference.room.rest.client.paths.findone}") String findOneRoomPath,
      RestTemplate restTemplate) {
    this.restTemplate = restTemplate;
    this.findOneRoomPath = findOneRoomPath;
  }

  @Override
  public RoomResponseDto findByRoomCode(String roomCode) {
    return restTemplate.getForObject(findOneRoomPath, RoomResponseDto.class, roomCode);
  }

}
