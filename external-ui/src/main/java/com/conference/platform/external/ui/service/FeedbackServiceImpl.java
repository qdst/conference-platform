package com.conference.platform.external.ui.service;

import com.conference.platform.external.ui.dto.httpclient.FeedbackRequestDto;
import com.conference.platform.external.ui.dto.httpclient.FeedbackResponseDto;
import com.conference.platform.external.ui.httpclient.ConferenceGatewayHttpClientImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FeedbackServiceImpl implements FeedbackService {

  private final ConferenceGatewayHttpClientImpl gatewayHttpClient;

  @Override
  public FeedbackResponseDto leaveFeedback(FeedbackRequestDto requestDto, String registrationCode) {
    return gatewayHttpClient.leaveFeedback(requestDto, registrationCode);
  }
}
