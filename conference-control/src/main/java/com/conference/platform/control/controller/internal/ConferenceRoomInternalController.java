package com.conference.platform.control.controller.internal;

import com.conference.platform.control.dto.controller.CapacityCheckResponseDto;
import com.conference.platform.control.dto.controller.RoomOccupationResponseDto;
import com.conference.platform.control.service.ConferenceRoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/conferences/rooms")
public class ConferenceRoomInternalController {

  private final ConferenceRoomService conferenceRoomService;

  @GetMapping("/{roomCode}/reservation-check")
  public RoomOccupationResponseDto roomHasUpcomingConference(@PathVariable String roomCode) {
    return conferenceRoomService.roomHasUpcomingConference(roomCode);
  }

  @GetMapping("/{roomCode}/new-capacity-check")
  public CapacityCheckResponseDto cancelConference(@PathVariable String roomCode, @RequestParam(required = true) Integer newCapacity) {
    return conferenceRoomService.conferenceWillExceedCapacity(roomCode, newCapacity);
  }

}
