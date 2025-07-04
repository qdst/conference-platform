package com.conference.platform.external.ui.controller;

import com.conference.platform.external.ui.mapper.ParticipantMapper;
import com.conference.platform.external.ui.service.ParticipantService;
import com.conference.platform.external.ui.view.CancelParticipantRegistrationViewModel;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequiredArgsConstructor
@RequestMapping("/participants")
public class ParticipantController {

  private final ParticipantService participantService;

  @GetMapping("/cancel")
  public String showCancelForm(Model model) {
    model.addAttribute("cancelForm", new CancelParticipantRegistrationViewModel());
    return "cancel-participant-registration";
  }

  @PostMapping("/cancel")
  public String processCancellationRequest(
      @Valid @ModelAttribute("cancelForm") CancelParticipantRegistrationViewModel cancelForm,
      RedirectAttributes redirectAttributes) {

    var cancelledParticipantDto =
        participantService.cancelParticipantRegistration(cancelForm.getRegistrationCode());

    var cancelledRegistrationViewModel = ParticipantMapper.toViewModel(cancelledParticipantDto);

    redirectAttributes.addFlashAttribute("participant", cancelledRegistrationViewModel);
    return "redirect:/participants/" + cancelledRegistrationViewModel.getRegistrationCode() + "/cancelled";
  }

  @GetMapping("/{registrationCode}/cancelled")
  public String renderCancellationConfirmation() {
    return "cancel-participant-registration";
  }
}
