package com.conference.platform.feedback.controller;

import com.conference.platform.feedback.dto.controller.FeedbackResponseDto;
import com.conference.platform.feedback.service.FeedbackService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/conference-feedback")
public class FeedbackInternalController {

  private final FeedbackService feedbackService;

  @GetMapping("/conferences/{conferenceCode}/feedback")
  public List<FeedbackResponseDto> getFeedback(@PathVariable String conferenceCode) {
    return feedbackService.getFeedbacksForConference(conferenceCode);
  }

}
