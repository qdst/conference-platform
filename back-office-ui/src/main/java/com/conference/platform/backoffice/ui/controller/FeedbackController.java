package com.conference.platform.backoffice.ui.controller;

import com.conference.platform.backoffice.ui.mapper.FeedbackMapper;
import com.conference.platform.backoffice.ui.service.FeedbackService;
import com.conference.platform.backoffice.ui.view.FeedbackResponseViewModel;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/conference-feedback/{conferenceCode}/feedback")
public class FeedbackController {


  private final FeedbackService feedbackService;

  @GetMapping
  public String getConferenceFeedback(@PathVariable String conferenceCode,  Model model) {

    var feedbackDtos = feedbackService.getConferenceFeedback(conferenceCode);
    List<FeedbackResponseViewModel> feedbackViewModel = feedbackDtos.stream()
        .map(FeedbackMapper::toResponseViewModel)
        .toList();

    model.addAttribute("conferenceCode", conferenceCode);
    model.addAttribute("feedbacks", feedbackViewModel);
    return "conference-feedback";
  }
}
