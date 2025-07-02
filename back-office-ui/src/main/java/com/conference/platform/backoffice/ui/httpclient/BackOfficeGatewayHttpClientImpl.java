package com.conference.platform.backoffice.ui.httpclient;

import com.conference.platform.backoffice.ui.httpclient.dto.ConferenceResponseDto;
import com.conference.platform.backoffice.ui.httpclient.dto.CreateConferenceRequestDto;
import com.conference.platform.backoffice.ui.httpclient.dto.UpdateConferenceRequestDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class BackOfficeGatewayHttpClientImpl implements BackOfficeGatewayHttpClient {

  private final String oneConferencePath;
  private final String newConferencePath;

  private final RestTemplate restTemplate;

  public BackOfficeGatewayHttpClientImpl(
      @Value("${backoffice.gateway.rest.client.paths.conference.one}") String oneConferencePath,
      @Value("${backoffice.gateway.rest.client.paths.conference.new}") String newConferencePath,
      RestTemplate restTemplate) {
    this.restTemplate = restTemplate;
    this.oneConferencePath = oneConferencePath;
    this.newConferencePath = newConferencePath;
  }

  @Override
  public ConferenceResponseDto createConference(CreateConferenceRequestDto requestDto) {
    ResponseEntity<ConferenceResponseDto> responseEntity =
        restTemplate.postForEntity(newConferencePath, requestDto, ConferenceResponseDto.class);
    return responseEntity.getBody();
  }

  @Override
  public ConferenceResponseDto cancelConference(String conferenceCode) {
    ResponseEntity<ConferenceResponseDto> responseEntity =
        restTemplate.postForEntity(oneConferencePath, null, ConferenceResponseDto.class, conferenceCode);
    return responseEntity.getBody();
  }

  @Override
  public ConferenceResponseDto updateConference(UpdateConferenceRequestDto requestDto, String conferenceCode) {
    HttpEntity<UpdateConferenceRequestDto> requestEntity = new HttpEntity<>(requestDto);
    return restTemplate.exchange(oneConferencePath, HttpMethod.PUT, requestEntity, ConferenceResponseDto.class, conferenceCode).getBody();
  }

  @Override
  public ConferenceResponseDto getConference(String conferenceCode) {
    return restTemplate.getForObject(oneConferencePath, ConferenceResponseDto.class, conferenceCode);
  }

}
