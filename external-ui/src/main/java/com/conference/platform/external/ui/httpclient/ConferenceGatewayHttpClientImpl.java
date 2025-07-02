package com.conference.platform.external.ui.httpclient;

import com.conference.platform.external.ui.dto.httpclient.ConferenceSummaryResponseDto;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class ConferenceGatewayHttpClientImpl implements ConferenceGatewayHttpClient {

  private final static ParameterizedTypeReference<List<ConferenceSummaryResponseDto>> RESPONSE_PARAMETERIZED_TYPE_REFERENCE =
      new ParameterizedTypeReference<>() {
      };
  private final String conferenceSearchPath;

  private final RestTemplate restTemplate;

  public ConferenceGatewayHttpClientImpl(
      @Value("${conference.gateway.rest.client.paths.conference.search}") String conferenceSearchPath,
      RestTemplate restTemplate) {
    this.restTemplate = restTemplate;
    this.conferenceSearchPath = conferenceSearchPath;
  }

  @Override
  public List<ConferenceSummaryResponseDto> searchAvailableConferences(LocalDateTime startTime, LocalDateTime endTime) {
    var requestParameters = Map.of("startTime", startTime, "endTime", endTime);
    return restTemplate.exchange(
            conferenceSearchPath,
            HttpMethod.GET,
            null,
            RESPONSE_PARAMETERIZED_TYPE_REFERENCE,
            requestParameters)
        .getBody();
  }
//
//  @Override
//  public ConferenceResponseDto cancelConference(String conferenceCode) {
//    ResponseEntity<ConferenceResponseDto> responseEntity =
//        restTemplate.postForEntity(oneConferencePath, null, ConferenceResponseDto.class, conferenceCode);
//    return responseEntity.getBody();
//  }
//
//  @Override
//  public ConferenceResponseDto updateConference(UpdateConferenceRequestDto requestDto, String conferenceCode) {
//    HttpEntity<UpdateConferenceRequestDto> requestEntity = new HttpEntity<>(requestDto);
//    return restTemplate.exchange(oneConferencePath, HttpMethod.PUT, requestEntity, ConferenceResponseDto.class,
//        conferenceCode).getBody();
//  }
//
//  @Override
//  public ConferenceResponseDto getConference(String conferenceCode) {
//    return restTemplate.getForObject(oneConferencePath, ConferenceResponseDto.class, conferenceCode);
//  }

}
