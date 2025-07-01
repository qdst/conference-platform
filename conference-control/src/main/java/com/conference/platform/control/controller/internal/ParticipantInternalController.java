package com.conference.platform.control.controller.internal;

import com.conference.platform.control.dto.controller.ParticipantResponseDto;
import com.conference.platform.control.service.ParticipantService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/conferences/participants")
public class ParticipantInternalController {

  private final ParticipantService participantService;

  @GetMapping("/{registrationCode}")
  public ParticipantResponseDto cancelRegistration(@PathVariable String registrationCode) {
    return participantService.getByParticipantCode(registrationCode);
  }
}
