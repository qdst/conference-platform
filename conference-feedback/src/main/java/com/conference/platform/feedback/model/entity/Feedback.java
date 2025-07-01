package com.conference.platform.feedback.model.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@Entity
@NoArgsConstructor
@Table(name = "feedback", schema = "c_feedback")
public class Feedback {

  @Id
  @SequenceGenerator(
      name = "feedback_seq",
      sequenceName = "c_feedback.feedback_t_id_seq",
      allocationSize = 1
  )
  @GeneratedValue(
      strategy = GenerationType.SEQUENCE,
      generator = "feedback_seq"
  )
  @Setter(AccessLevel.NONE)  private Long id;
  private String author;
  private String conferenceCode;
  private String title;
  private String text;
  private LocalDateTime createdAt;
}
