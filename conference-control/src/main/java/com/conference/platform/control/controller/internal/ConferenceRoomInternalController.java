package com.conference.platform.control.controller.internal;

import com.conference.platform.control.dto.controller.CapacityCheckRequestDto;
import com.conference.platform.control.dto.controller.CapacityCheckResponseDto;
import com.conference.platform.control.dto.controller.ConferenceAvailabilityResponseDto;
import com.conference.platform.control.dto.controller.ConferenceResponseDto;
import com.conference.platform.control.dto.controller.CreateConferenceRequestDto;
import com.conference.platform.control.dto.controller.RoomOccupationRequestDto;
import com.conference.platform.control.dto.controller.RoomOccupationResponseDto;
import com.conference.platform.control.dto.controller.UpdateConferenceRequestDto;
import com.conference.platform.control.service.ConferenceRoomService;
import com.conference.platform.control.service.ConferenceService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/conferences/rooms")
public class ConferenceRoomInternalController {

  private final ConferenceRoomService conferenceRoomService;

  @GetMapping("/{roomCode}/reservation")
  public RoomOccupationResponseDto roomHasUpcomingConference(String roomCode) {
    return conferenceRoomService.roomHasUpcomingConference(roomCode);
  }

  @GetMapping("/{roomCode}/new-capacity")
  public CapacityCheckResponseDto cancelConference(String roomCode, @RequestParam(required = true) Integer newCapacity) {
    return conferenceRoomService.conferenceWillExceedCapacity(roomCode, newCapacity);
  }

}
