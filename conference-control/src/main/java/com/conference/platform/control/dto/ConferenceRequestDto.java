package com.conference.platform.control.dto;

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
public class ConferenceRequestDto {
  private String title;
  private LocalDateTime scheduledDateTime;
  private String conferenceRoom;
  private ConferenceStatus status;
}
