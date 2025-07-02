package com.conference.platform.external.ui.controller;

import com.conference.platform.external.ui.service.ConferenceService;
import com.conference.platform.external.ui.view.SearchCriteriaViewModel;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

@Validated
@Controller
@RequiredArgsConstructor
@RequestMapping("/participants/")
public class ParticipantController {

  private final ConferenceService conferenceService;

  @GetMapping("/re")
  public String searchPage(Model model) {
    model.addAttribute("criteria", new SearchCriteriaViewModel());
    model.addAttribute("conferences", List.of());
    return "conference-search-result";
  }

  @GetMapping("/search/results")
  public String searchConferences(
      @Valid @ModelAttribute("criteria") SearchCriteriaViewModel criteriaViewModel,
      BindingResult bindingResult, Model model) {

    if (bindingResult.hasErrors()) {
      model.addAttribute("conferences", List.of());
      return "conference-search-result";

    } else {
      var foundConferences =
          conferenceService.searchAvailableConferences(criteriaViewModel.getStartTime(), criteriaViewModel.getEndTime());
      model.addAttribute("conferences", foundConferences);
      return "conference-search-result";
    }
  }

}
