package com.conference.platform.backoffice.ui.service;

import com.conference.platform.backoffice.ui.httpclient.FeedbackHttpClient;
import com.conference.platform.backoffice.ui.httpclient.dto.FeedbackResponseDto;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FeedbackServiceImpl implements FeedbackService {

  private final FeedbackHttpClient feedbackHttpClient;

  @Override
  public List<FeedbackResponseDto> getConferenceFeedback(String conferenceCode) {
    return feedbackHttpClient.getConferenceFeedback(conferenceCode);
  }
}
