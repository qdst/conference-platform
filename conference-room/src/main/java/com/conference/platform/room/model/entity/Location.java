package com.conference.platform.room.model.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.SequenceGenerator;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Setter
@Getter
@NoArgsConstructor
public class Location {

  @Id
  @SequenceGenerator(
      name = "location_seq",
      sequenceName = "location_t_id_seq",
      allocationSize = 1
  )
  @GeneratedValue(
      strategy = GenerationType.SEQUENCE,
      generator = "location_seq"
  )  private Long id;
  @OneToOne
  @JoinColumn(name = "room_id", nullable = false, unique = true)
  private Room room;
  private String addressLine1;
  private String addressLine2;
  private String city;
}
