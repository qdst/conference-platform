package com.conference.platform.control.dto.controller;

import com.conference.platform.control.model.Gender;
import com.conference.platform.control.model.ParticipantStatus;
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
public class ParticipantResponseDto {
  private String firstName;
  private String lastName;
  private String email;
  private LocalDate dateOfBirth;
  private Gender gender;
  private String conferenceCode;
  private String registrationCode;
  private ParticipantStatus participantStatus;
}
