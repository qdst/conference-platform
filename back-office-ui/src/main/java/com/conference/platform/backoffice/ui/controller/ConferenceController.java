package com.conference.platform.backoffice.ui.controller;

import com.conference.platform.backoffice.ui.httpclient.dto.CreateConferenceRequestDto;
import com.conference.platform.backoffice.ui.httpclient.dto.UpdateConferenceRequestDto;
import com.conference.platform.backoffice.ui.mapper.ConferenceMapper;
import com.conference.platform.backoffice.ui.service.ConferenceService;
import com.conference.platform.backoffice.ui.view.ConferenceViewModel;
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
public class ConferenceController {

  private final ConferenceService conferenceService;

  @GetMapping("/new")
  public String newConferenceForm(Model model) {
    model.addAttribute("conference", new ConferenceViewModel());
    return "new-conference";
  }

  @GetMapping("/{code}")
  public String editConference(
      @PathVariable String code,
      @ModelAttribute("conference") ConferenceViewModel conferenceViewModel,
      Model model) {

    if (conferenceViewModel == null || conferenceViewModel.getRoomCode() == null) {
      var conferenceResponseDto = conferenceService.getConference(code);
      var viewModel = ConferenceMapper.toViewModel(conferenceResponseDto);
      model.addAttribute("conference", viewModel);
    }
    return "edit-conference";
  }

  @PostMapping("/{code}")
  public String updateConference(
      @PathVariable String code,
      @ModelAttribute("conference") ConferenceViewModel conference,
      RedirectAttributes redirectAttributes) {

    UpdateConferenceRequestDto updateDto = ConferenceMapper.toUpdateDto(conference);
    var updatedDto = conferenceService.updateConference(updateDto, code);
    var updatedVm = ConferenceMapper.toViewModel(updatedDto);

    redirectAttributes.addFlashAttribute("conference", updatedVm);
    return "redirect:/conferences/" + code;
  }

  @PostMapping
  public String createConference(
      @ModelAttribute ConferenceViewModel conference,
      RedirectAttributes redirectAttributes) {

    CreateConferenceRequestDto createDto = ConferenceMapper.toCreateDto(conference);
    var createdDto = conferenceService.createConference(createDto);
    var createdVm = ConferenceMapper.toViewModel(createdDto);

    redirectAttributes.addFlashAttribute("conference", createdVm);
    return "redirect:/conferences/" + createdDto.getCode();
  }

  @PostMapping("/{conferenceCode}/cancel")
  public String cancelConference(
      @PathVariable String conferenceCode,
      RedirectAttributes redirectAttributes) {

    var conferenceResponseDto = conferenceService.cancelConference(conferenceCode);
    var canceledConferenceModel = ConferenceMapper.toViewModel(conferenceResponseDto);
    redirectAttributes.addFlashAttribute("conference", canceledConferenceModel);
    return "redirect:/conferences/" + conferenceCode;
  }

}
