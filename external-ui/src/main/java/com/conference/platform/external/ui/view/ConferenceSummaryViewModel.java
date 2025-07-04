package com.conference.platform.external.ui.view;

import java.time.LocalDateTime;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public final class ConferenceSummaryViewModel {
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
