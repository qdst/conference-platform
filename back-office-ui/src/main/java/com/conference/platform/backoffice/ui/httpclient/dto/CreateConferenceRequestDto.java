package com.conference.platform.backoffice.ui.httpclient.dto;

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
public class CreateConferenceRequestDto {

  private String title;
  private LocalDateTime startTime;
  private LocalDateTime endTime;
  private String roomCode;
}
