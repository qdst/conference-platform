package com.conference.platform.external.ui.controller;

import com.conference.platform.external.ui.mapper.ConferenceSummaryMapper;
import com.conference.platform.external.ui.mapper.ParticipantMapper;
import com.conference.platform.external.ui.service.ConferenceService;
import com.conference.platform.external.ui.service.ParticipantService;
import com.conference.platform.external.ui.view.ParticipantViewModel;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequiredArgsConstructor
@RequestMapping("/conferences")
public class ConferenceParticipantRegistrationController {

  private final ConferenceService conferenceService;
  private final ParticipantService participantService;

  @GetMapping("/{conferenceCode}/participant")
  public String showRegistrationForm(
      @PathVariable String conferenceCode,
      Model model) {

    var conferenceSummaryDto = conferenceService.getConferenceSummary(conferenceCode);
    var conferenceSummaryViewModel  = ConferenceSummaryMapper.toViewModel(conferenceSummaryDto);

    model.addAttribute("conference", conferenceSummaryViewModel);
    model.addAttribute("participant", new ParticipantViewModel());
    return "conference-participant-registration";
  }

  @GetMapping("/participant/{registrationCode}")
  public String showParticipantRegistration() {
    return "participant-registration";
  }

  @PostMapping("/{conferenceCode}/participant")
  public String registerParticipant(
      @PathVariable String conferenceCode,
      @Valid @ModelAttribute("participant") ParticipantViewModel participant,
      RedirectAttributes registrationAttributes) {

    var participantRegistrationRequestDto = ParticipantMapper.toDto(participant);
    var participantRegistrationDto =
        participantService.registerParticipant(participantRegistrationRequestDto, conferenceCode);
    var participantRegistrationViewModel = ParticipantMapper.toViewModel(participantRegistrationDto);
    registrationAttributes.addFlashAttribute("participant", participantRegistrationViewModel);

    return "redirect:/conferences/participant/" + participantRegistrationViewModel.getRegistrationCode();
  }

}
