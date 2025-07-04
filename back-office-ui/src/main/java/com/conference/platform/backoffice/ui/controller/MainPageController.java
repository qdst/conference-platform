package com.conference.platform.backoffice.ui.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/main")
public class MainPageController {


  @GetMapping
  public String mainPage() {
    return "main-page";
  }

  @GetMapping("/to-conference")
  public String goToConference(@RequestParam String conferenceCode) {
    return "redirect:/conferences/" + conferenceCode;
  }

  @GetMapping("/to-room")
  public String goToRoom(@RequestParam String roomCode) {
    return "redirect:/rooms/" + roomCode;
  }

  @GetMapping("/to-feedback")
  public String goToFeedback(@RequestParam String conferenceCode) {
    return "redirect:/conference-feedback/" + conferenceCode + "/feedback";
  }
}
