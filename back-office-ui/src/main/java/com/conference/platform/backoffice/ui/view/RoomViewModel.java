package com.conference.platform.backoffice.ui.view;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class RoomViewModel {
  private String name;
  private String roomCode;
  private String addressLine1;
  private String addressLine2;
  private String city;
  private RoomStatus roomStatus;
  private int capacity;
}
