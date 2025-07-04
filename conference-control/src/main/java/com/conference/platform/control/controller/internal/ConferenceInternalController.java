package com.conference.platform.control.controller.internal;

import com.conference.platform.control.dto.controller.ConferenceAvailabilityResponseDto;
import com.conference.platform.control.dto.controller.ConferenceResponseDto;
import com.conference.platform.control.dto.controller.CreateConferenceRequestDto;
import com.conference.platform.control.dto.controller.UpdateConferenceRequestDto;
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
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/conferences")
public class ConferenceInternalController {

  private final ConferenceService conferenceService;

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public ConferenceResponseDto createConference(@Valid @RequestBody CreateConferenceRequestDto requestDto) {
    return conferenceService.createConference(requestDto);
  }

  @GetMapping("/{conferenceCode}")
  public ConferenceResponseDto getConference(@PathVariable String conferenceCode) {
    return conferenceService.getConference(conferenceCode);
  }

  @PostMapping("/{conferenceCode}/cancel")
  public ConferenceResponseDto cancelConference(@PathVariable String conferenceCode) {
    return conferenceService.cancelConference(conferenceCode);
  }

  @PutMapping("/{conferenceCode}")
  public ConferenceResponseDto updateConference(
      @PathVariable String conferenceCode,
      @Valid @RequestBody UpdateConferenceRequestDto requestDto) {
    return conferenceService.updateConference(conferenceCode, requestDto);
  }

  @GetMapping("/{conferenceCode}/availability")
  public ConferenceAvailabilityResponseDto checkAvailability(@PathVariable String conferenceCode) {
    return conferenceService.checkAvailability(conferenceCode);
  }

}
