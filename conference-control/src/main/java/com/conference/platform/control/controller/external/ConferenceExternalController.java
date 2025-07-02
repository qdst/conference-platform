package com.conference.platform.control.controller.external;

import com.conference.platform.control.dto.controller.ConferenceSummaryResponseDto;
import com.conference.platform.control.dto.controller.ParticipantRegistrationRequestDto;
import com.conference.platform.control.dto.controller.ParticipantRegistrationResponseDto;
import com.conference.platform.control.service.ConferenceService;
import com.conference.platform.control.service.ParticipantService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/external/v1/conferences")
public class ConferenceExternalController {

  private final ConferenceService conferenceService;
  private final ParticipantService participantService;

  @GetMapping("/search")
  public List<ConferenceSummaryResponseDto> findAvailable(
      @RequestParam
      @NotNull(message = "startTime is required")
      @DateTimeFormat(pattern = "dd-MM-yyyy'T'HH:mm")
      LocalDateTime startTime,

      @RequestParam("endTime")
      @NotNull(message = "endTime is required")
      @DateTimeFormat(pattern = "dd-MM-yyyy'T'HH:mm")
      LocalDateTime endTime) {
    return conferenceService.findAvailableConferences(startTime, endTime);
  }

  @PostMapping("/{conferenceCode}/participant")
  public ParticipantRegistrationResponseDto registerParticipant(@PathVariable String conferenceCode,
      @Valid @RequestBody ParticipantRegistrationRequestDto requestDto) {
    return participantService.registerParticipant(conferenceCode, requestDto);
  }

}
