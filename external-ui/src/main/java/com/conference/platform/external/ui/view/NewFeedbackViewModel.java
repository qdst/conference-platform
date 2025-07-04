package com.conference.platform.external.ui.view;

import java.time.LocalDateTime;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class NewFeedbackViewModel {
  private String title;
  private String text;
  private String author;
  private LocalDateTime createdAt;
}
