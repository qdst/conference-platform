package com.conference.platform.external.ui.httpclient;

import com.conference.platform.external.ui.dto.httpclient.ConferenceSummaryResponseDto;
import com.conference.platform.external.ui.dto.httpclient.FeedbackRequestDto;
import com.conference.platform.external.ui.dto.httpclient.FeedbackResponseDto;
import com.conference.platform.external.ui.dto.httpclient.ParticipantRegistrationRequestDto;
import com.conference.platform.external.ui.dto.httpclient.ParticipantResponseDto;
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

  private final static ParameterizedTypeReference<List<ConferenceSummaryResponseDto>> CONFERENCES_RESPONSE_TYPE =
      new ParameterizedTypeReference<>() {
      };

  private final String conferenceSearchPath;
  private final String cancelParticipantPath;
  private final String conferenceParticipantPath;
  private final String oneConferencePath;
  private final String leaveFeedbackByRegistration;

  private final RestTemplate restTemplate;

  public ConferenceGatewayHttpClientImpl(
      @Value("${conference.gateway.rest.client.paths.conference.one}") String oneConferencePath,
      @Value("${conference.gateway.rest.client.paths.conference.search}") String conferenceSearchPath,
      @Value("${conference.gateway.rest.client.paths.conference.participant}") String conferenceParticipantPath,
      @Value("${conference.gateway.rest.client.paths.conference.participant.cancel}") String cancelParticipantPath,
      @Value("${conference.gateway.rest.client.paths.conference-feedback.by-registration.leave}") String leaveFeedbackByRegistration,
      RestTemplate restTemplate) {
    this.restTemplate = restTemplate;
    this.conferenceSearchPath = conferenceSearchPath;
    this.conferenceParticipantPath = conferenceParticipantPath;
    this.cancelParticipantPath = cancelParticipantPath;
    this.oneConferencePath = oneConferencePath;
    this.leaveFeedbackByRegistration = leaveFeedbackByRegistration;
  }

  @Override
  public List<ConferenceSummaryResponseDto> searchAvailableConferences(LocalDateTime startTime, LocalDateTime endTime) {
    var requestParameters = Map.of("startTime", startTime, "endTime", endTime);
    return restTemplate.exchange(
            conferenceSearchPath,
            HttpMethod.GET,
            null,
            CONFERENCES_RESPONSE_TYPE,
            requestParameters)
        .getBody();
  }

  @Override
  public ConferenceSummaryResponseDto getConferenceSummary(String conferenceCode) {
    return restTemplate.getForObject(oneConferencePath, ConferenceSummaryResponseDto.class, conferenceCode);
  }

  @Override
  public ParticipantResponseDto registerParticipant(ParticipantRegistrationRequestDto requestDto, String conferenceCode) {
        var responseEntity = restTemplate.postForEntity(conferenceParticipantPath, requestDto, ParticipantResponseDto.class, conferenceCode);
    return responseEntity.getBody();
  }

  @Override
  public ParticipantResponseDto cancelParticipantRegistration(String participantRegistrationCode) {
    var responseEntity =
        restTemplate.postForEntity(cancelParticipantPath, null, ParticipantResponseDto.class, participantRegistrationCode);
    return responseEntity.getBody();
  }

  @Override
  public FeedbackResponseDto leaveFeedback(FeedbackRequestDto feedbackRequestDto, String registrationCode) {
    var responseEntity =
        restTemplate.postForEntity(leaveFeedbackByRegistration, feedbackRequestDto, FeedbackResponseDto.class, registrationCode);
    return responseEntity.getBody();
  }

}
