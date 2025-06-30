package com.conference.platform.control.dto.controller;

import com.conference.platform.control.model.ConferenceStatus;
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
public class ConferenceResponseDto {
  private String code;
  private String title;
  private LocalDateTime startTime;
  private LocalDateTime endTime;
  private String roomCode;
  private Integer totalCapacity;
  private ConferenceStatus status;
}
