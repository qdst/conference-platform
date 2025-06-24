package com.conference.platform.room.model.entity;

import com.conference.platform.room.model.RoomStatus;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.SequenceGenerator;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Room {

  @Id
  @SequenceGenerator(
      name = "room_seq",
      sequenceName = "room_t_id_seq",
      allocationSize = 1
  )
  @GeneratedValue(
      strategy = GenerationType.SEQUENCE,
      generator = "room_seq"
  )
  private Long id;
  private String roomCode;
  private String name;
  private Integer capacity;

  @OneToOne(mappedBy = "room", cascade = CascadeType.PERSIST, orphanRemoval = true)
  private Location location;

  @Enumerated(EnumType.STRING)
  private RoomStatus roomStatus;

}
