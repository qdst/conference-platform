package com.conference.platform.external.ui.view;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CancelParticipantRegistrationViewModel {
  @NotBlank(message = "Registration code is required")
  private String registrationCode;
}
