package com.conference.platform.control.controller.external;

import com.conference.platform.control.service.ParticipantService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/external/v1/conferences/participants")
public class ParticipantExternalController {

  private final ParticipantService participantService;

  @ResponseStatus(HttpStatus.NO_CONTENT)
  @PatchMapping("/{registrationCode}")
  public void cancelRegistration(@PathVariable String registrationCode) {
    participantService.cancelRegistration(registrationCode);
  }
}
