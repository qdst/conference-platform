package com.conference.platform.external.ui.dto.httpclient;

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
public class ConferenceSummaryResponseDto {
  private String code;
  private String title;
  private LocalDateTime startTime;
  private LocalDateTime endTime;
  private Integer totalCapacity;
  private Integer registeredParticipants;
  private String addressLine1;
  private String addressLine2;
  private String city;
}
