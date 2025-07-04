package com.conference.platform.backoffice.ui.httpclient;

import com.conference.platform.backoffice.ui.httpclient.dto.FeedbackResponseDto;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class FeedbackHttpClientImpl implements FeedbackHttpClient {

  private final static ParameterizedTypeReference<List<FeedbackResponseDto>> FEEDBACK_REAPONSE_TYPE =
      new ParameterizedTypeReference<>() {
      };

  private final String conferenceFeedbackPath;
  private final RestTemplate restTemplate;

  public FeedbackHttpClientImpl(
      @Value("${backoffice.gateway.rest.client.paths.conference.feedback}") String conferenceFeedbackPath,
      RestTemplate restTemplate) {
    this.restTemplate = restTemplate;
    this.conferenceFeedbackPath = conferenceFeedbackPath;
  }

  @Override
  public List<FeedbackResponseDto> getConferenceFeedback(String conferenceCode) {
    var requestParameters = Map.of("conferenceCode", conferenceCode);
    return restTemplate.exchange(
            conferenceFeedbackPath,
            HttpMethod.GET,
            null,
            FEEDBACK_REAPONSE_TYPE,
            requestParameters)
        .getBody();
  }
}
