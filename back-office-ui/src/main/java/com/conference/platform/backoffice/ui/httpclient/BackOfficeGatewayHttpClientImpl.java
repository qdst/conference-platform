package com.conference.platform.backoffice.ui.httpclient;

import com.conference.platform.backoffice.ui.httpclient.dto.CreateRoomRequestDto;
import com.conference.platform.backoffice.ui.httpclient.dto.RoomResponseDto;
import com.conference.platform.backoffice.ui.httpclient.dto.UpdateRoomRequestDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class BackOfficeGatewayHttpClientImpl implements BackOfficeGatewayHttpClient {

  private final String existingRoomPath;
  private final String createRoomPath;

  private final RestTemplate restTemplate;

  public BackOfficeGatewayHttpClientImpl(
      @Value("${backoffice.gateway.rest.client.paths.room.find}") String existingRoomPath,
      @Value("${backoffice.gateway.rest.client.paths.room.new}") String newRoomPath,
      RestTemplate restTemplate) {
    this.restTemplate = restTemplate;
    this.existingRoomPath = existingRoomPath;
    this.createRoomPath = newRoomPath;
  }

  @Override
  public RoomResponseDto createRoom(CreateRoomRequestDto requestDto) {
    ResponseEntity<RoomResponseDto> responseEntity =
        restTemplate.postForEntity(createRoomPath, requestDto, RoomResponseDto.class);
    return responseEntity.getBody();
  }

  @Override
  public RoomResponseDto updateRoom(UpdateRoomRequestDto requestDto, String roomCode) {
    HttpEntity<UpdateRoomRequestDto> requestEntity = new HttpEntity<>(requestDto);
    return restTemplate.exchange(existingRoomPath, HttpMethod.PUT, requestEntity, RoomResponseDto.class, roomCode).getBody();
  }

  @Override
  public RoomResponseDto getRoom(String roomCode) {
    return restTemplate.getForObject(existingRoomPath, RoomResponseDto.class, roomCode);
  }

}
