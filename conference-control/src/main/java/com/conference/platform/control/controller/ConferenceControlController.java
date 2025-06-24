package com.conference.platform.control.controller;

import com.conference.platform.control.dto.ConferenceAvailabilityResponseDto;
import com.conference.platform.control.dto.ConferenceRequestDto;
import com.conference.platform.control.dto.ConferenceResponseDto;
import com.conference.platform.control.service.ConferenceService;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/conferences")
public class ConferenceControlController {

  private final ConferenceService conferenceService;

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public ConferenceResponseDto createConference(@RequestBody ConferenceRequestDto requestDto) {
    return conferenceService.createConference(requestDto);

  }

  @ResponseStatus(HttpStatus.NO_CONTENT)
  @DeleteMapping("/{conferenceCode}")
  public void cancelConference(@PathVariable String conferenceCode) {
    conferenceService.cancelConference(conferenceCode);
  }

  @PutMapping("/{conferenceCode}")
  public ConferenceResponseDto updateConference(
      @PathVariable String conferenceCode,
      @RequestBody ConferenceRequestDto requestDto) {
    return conferenceService.updateConference(conferenceCode, requestDto);
  }

  @GetMapping("/availability/{conferenceCode}")
  public ConferenceAvailabilityResponseDto checkAvailability(@PathVariable String conferenceCode) {
    return conferenceService.checkAvailability(conferenceCode);
  }

}
