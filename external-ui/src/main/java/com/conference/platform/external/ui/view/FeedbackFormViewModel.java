package com.conference.platform.external.ui.view;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class FeedbackFormViewModel {
  @NotBlank
  private String registrationCode;
  @NotBlank
  private String title;
  @NotBlank
  private String text;
}
