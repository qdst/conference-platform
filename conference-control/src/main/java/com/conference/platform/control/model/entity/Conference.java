package com.conference.platform.control.model.entity;

import com.conference.platform.control.model.ConferenceStatus;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.SequenceGenerator;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Conference {

  @Id
  @SequenceGenerator(
      name = "conference_seq",
      sequenceName = "conference_t_id_seq",
      allocationSize = 1
  )
  @GeneratedValue(
      strategy = GenerationType.SEQUENCE,
      generator = "conference_seq"
  )
  private Long id;
  private String conferenceCode;
  private String title;
  private LocalDateTime scheduledDateTime;
  private String conferenceRoom;
  @Enumerated(EnumType.STRING)
  private ConferenceStatus status;
  @OneToMany(mappedBy = "conference", cascade = CascadeType.ALL, orphanRemoval = true)
  private List<Participant> participants = new ArrayList<>();
}
