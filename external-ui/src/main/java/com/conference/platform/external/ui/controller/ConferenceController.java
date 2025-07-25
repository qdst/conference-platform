package com.conference.platform.external.ui.controller;

import com.conference.platform.external.ui.mapper.ConferenceSummaryMapper;
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
@RequestMapping("/conferences/")
public class ConferenceController {

  private final ConferenceService conferenceService;

  @GetMapping("/search")
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
      var foundConferenceDtos =
          conferenceService.searchAvailableConferences(criteriaViewModel.getStartTime(),
              criteriaViewModel.getEndTime());
      var summaryConferencesModels = foundConferenceDtos.stream()
          .map(ConferenceSummaryMapper::toViewModel)
          .toList();

      model.addAttribute("conferences", summaryConferencesModels);
      return "conference-search-result";
    }
  }
}
