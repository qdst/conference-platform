package com.conference.platform.feedback.mapper;

import com.conference.platform.feedback.dto.controller.FeedbackRequestDto;
import com.conference.platform.feedback.dto.controller.FeedbackResponseDto;
import com.conference.platform.feedback.model.entity.Feedback;

public final class FeedbackMapper {

  private FeedbackMapper() {
  }

  public static Feedback toModel(FeedbackRequestDto requestDto) {
    var feedback = new Feedback();
    feedback.setTitle(requestDto.getTitle());
    feedback.setText(requestDto.getText());
    return feedback;
  }

  public static FeedbackResponseDto toDto(Feedback feedback) {
    return FeedbackResponseDto.builder()
        .text(feedback.getText())
        .author(feedback.getAuthor())
        .title(feedback.getTitle())
        .createdAt(feedback.getCreatedAt())
        .build();
  }
}
