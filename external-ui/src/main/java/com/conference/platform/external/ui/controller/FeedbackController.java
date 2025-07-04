package com.conference.platform.external.ui.controller;

import com.conference.platform.external.ui.mapper.FeedbackMapper;
import com.conference.platform.external.ui.service.FeedbackService;
import com.conference.platform.external.ui.view.FeedbackFormViewModel;
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
@RequestMapping("/feedback")
public class FeedbackController {

  private final FeedbackService feedbackService;

  @GetMapping
  public String feedbackForm(Model model) {
    model.addAttribute("feedbackForm", new FeedbackFormViewModel());
    return "feedback";
  }

  @PostMapping
  public String submitFeedback(
      @Valid @ModelAttribute("feedbackForm")
      FeedbackFormViewModel feedbackFormViewModel,
      RedirectAttributes redirectAttributes) {

    var requestDto = FeedbackMapper.toRequestDto(feedbackFormViewModel);
    var responseDto = feedbackService.leaveFeedback(requestDto, feedbackFormViewModel.getRegistrationCode());
    var responseViewModel = FeedbackMapper.toViewModel(responseDto);
    redirectAttributes.addFlashAttribute("feedbackResponse", responseViewModel);
    return "redirect:/feedback/success";
  }

  @GetMapping("/success")
  public String feedbackSuccess() {
    return "feedback";
  }

}
