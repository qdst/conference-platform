package com.conference.platform.feedback.controller;

import com.conference.platform.feedback.dto.controller.FeedbackRequestDto;
import com.conference.platform.feedback.dto.controller.FeedbackResponseDto;
import com.conference.platform.feedback.service.FeedbackService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/external/v1/conference-feedback")
public class FeedbackExternalController {

  private final FeedbackService feedbackService;

  @PostMapping("/participants/{registrationCode}/feedback")
  public FeedbackResponseDto createFeedback(
      @PathVariable String registrationCode,
      @Valid @RequestBody FeedbackRequestDto feedbackRequestDto) {
    return feedbackService.createFeedback(registrationCode, feedbackRequestDto);
  }
}
