package com.conference.platform.feedback.model.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import java.time.LocalDateTime;

@Entity
public class Feedback {
  @Id
  private Long id;
  private String feedbackUuid;
  private String participantUsername;
  private String conferenceNumber;
  private String content;
  private LocalDateTime submittedAt;
}
