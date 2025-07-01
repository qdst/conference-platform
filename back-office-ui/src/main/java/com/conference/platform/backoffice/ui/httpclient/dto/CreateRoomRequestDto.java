package com.conference.platform.backoffice.ui.httpclient.dto;

import com.conference.platform.backoffice.ui.view.RoomStatus;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
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

  @NotBlank
  private String name;
  @NotNull
  private LocationDto locationDto;
  @NotNull
  private RoomStatus roomStatus;
  @NotNull
  @Min(1)
  private Integer capacity;
}
