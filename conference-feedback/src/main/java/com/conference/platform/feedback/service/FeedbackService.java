package com.conference.platform.feedback.service;

import com.conference.platform.feedback.dto.controller.FeedbackRequestDto;
import com.conference.platform.feedback.dto.controller.FeedbackResponseDto;
import java.util.List;

public interface FeedbackService {

  FeedbackResponseDto createFeedback(String registrationCode, FeedbackRequestDto requestDto);

  List<FeedbackResponseDto> getFeedbacksForConference(String conferenceCode);
}
