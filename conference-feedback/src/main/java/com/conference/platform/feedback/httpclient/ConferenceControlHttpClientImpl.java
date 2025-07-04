package com.conference.platform.feedback.httpclient;

import com.conference.platform.feedback.dto.httpclient.ParticipantResponseDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class ConferenceControlHttpClientImpl implements ConferenceControlHttpClient {

  private final String participantPath;
  private final RestTemplate restTemplate;

  public ConferenceControlHttpClientImpl(
      @Value("${conference.control.rest.client.paths.participant.find}") String participantPath,
      RestTemplate restTemplate) {
    this.restTemplate = restTemplate;
    this.participantPath = participantPath;
  }

  @Override
  public ParticipantResponseDto getParticipantByRegistrationCode(String registrationCode) {
    return restTemplate.getForObject(participantPath, ParticipantResponseDto.class, registrationCode);
  }
}
