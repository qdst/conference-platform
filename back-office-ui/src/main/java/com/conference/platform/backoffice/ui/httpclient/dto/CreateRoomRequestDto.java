package com.conference.platform.backoffice.ui.httpclient.dto;

import com.conference.platform.backoffice.ui.view.RoomStatus;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CreateRoomRequestDto {

  private String name;
  private LocationDto locationDto;
  private RoomStatus roomStatus;
  private Integer capacity;
}
