package com.conference.platform.feedback.dto.controller;

import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class FeedbackResponseDto {
  private String title;
  private String text;
  private String author;
  private LocalDateTime createdAt;
}
