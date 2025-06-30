package com.conference.platform.control.model.entity;

import com.conference.platform.control.model.Gender;
import com.conference.platform.control.model.ParticipantStatus;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;
import java.time.LocalDate;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
public class Participant {

  @Id
  @SequenceGenerator(
      name = "participant_seq",
      sequenceName = "c_control.participant_t_id_seq",
      allocationSize = 1
  )
  @GeneratedValue(
      strategy = GenerationType.SEQUENCE,
      generator = "participant_seq"
  )
  @Setter(AccessLevel.NONE)
  private Long id;

  @ManyToOne
  @JoinColumn(name = "conference_id", nullable = false)
  private Conference conference;

  private String registrationCode;

  @Enumerated(EnumType.STRING)
  private ParticipantStatus status;

  private String firstName;

  private String lastName;

  @Enumerated(EnumType.STRING)
  private Gender gender;

  private String email;

  private LocalDate dateOfBirth;
}
