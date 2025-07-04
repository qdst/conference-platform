package com.conference.platform.external.ui.dto.httpclient;

import com.conference.platform.external.ui.view.Gender;
import java.time.LocalDate;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ParticipantRegistrationRequestDto {

  private String firstName;
  private String lastName;
  private String email;
  private LocalDate dateOfBirth;
  private Gender gender;
}
