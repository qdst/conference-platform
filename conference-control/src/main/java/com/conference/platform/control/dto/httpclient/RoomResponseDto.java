package com.conference.platform.control.dto.httpclient;

import com.conference.platform.control.model.RoomStatus;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class RoomResponseDto {
    private String roomCode;
    private String name;
    private Integer capacity;
    private RoomStatus roomStatus;
    private LocationDto locationDto;
  }
