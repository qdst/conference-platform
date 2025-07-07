package com.conference.platform.control.controller.external;

import com.conference.platform.control.dto.controller.ParticipantResponseDto;
import com.conference.platform.control.service.ParticipantService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/external/v1/conferences/participants")
public class ParticipantExternalController {

  private final ParticipantService participantService;

  @PostMapping("/{registrationCode}/cancel")
  public ParticipantResponseDto cancelRegistration(@PathVariable String registrationCode) {
    return participantService.cancelRegistration(registrationCode);
  }
}
