package com.conference.platform.control.service;

import com.conference.platform.control.repository.ConferenceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ConferenceServiceImpl {
  private ConferenceRepository conferenceRepository;
}
