package com.conference.platform.external.ui.view;

import java.time.LocalDate;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

@Getter
@Setter
@NoArgsConstructor
public class ParticipantViewModel {
  private String firstName;
  private String lastName;
  private Gender gender;
  private String email;
  @DateTimeFormat(pattern = "dd-MM-yyyy")
  private LocalDate dateOfBirth;
  private ParticipantStatus participantStatus;
  private String registrationCode;
  private String conferenceCode;
}
