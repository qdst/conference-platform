package com.conference.platform.external.ui.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/main")
public class MainPageController {

  @GetMapping
  public String home() {
    return "main";
  }

  @GetMapping("/register-participant")
  public String registerParticipant(@RequestParam String conferenceCode) {
    return "redirect:/conferences/" + conferenceCode + "/participant";
  }

  @GetMapping("/feedback")
  public String feedback() {
    return "feedback";
  }
}
