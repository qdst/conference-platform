package com.conference.platform.control.model.entity;

import com.conference.platform.control.model.ConferenceStatus;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.SequenceGenerator;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Conference {

  @Id
  @SequenceGenerator(
      name = "conference_seq",
      sequenceName = "c_control.conference_t_id_seq",
      allocationSize = 1
  )
  @GeneratedValue(
      strategy = GenerationType.SEQUENCE,
      generator = "conference_seq"
  )
  @Setter(AccessLevel.NONE)
  private Long id;

  private String code;

  private String title;

  private LocalDateTime startTime;

  private LocalDateTime endTime;

  private String roomCode;

  private Integer totalCapacity;

  @Enumerated(EnumType.STRING)
  private ConferenceStatus status;

  @Setter(AccessLevel.NONE)
  @OneToMany(
      mappedBy = "conference",
      cascade = CascadeType.ALL,
      orphanRemoval = true,
      fetch = FetchType.LAZY)
  private final List<Participant> participants = new ArrayList<>();

  public void addParticipant(Participant participant) {
    participants.add(participant);
    participant.setConference(this);
  }

}
