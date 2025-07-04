package com.conference.platform.external.ui.service;

import com.conference.platform.external.ui.dto.httpclient.FeedbackRequestDto;
import com.conference.platform.external.ui.dto.httpclient.FeedbackResponseDto;

public interface FeedbackService {

  FeedbackResponseDto leaveFeedback(FeedbackRequestDto requestDto, String registrationCode);
}
