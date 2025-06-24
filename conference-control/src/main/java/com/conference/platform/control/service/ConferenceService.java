package com.conference.platform.control.service;

import com.conference.platform.control.dto.ConferenceAvailabilityResponseDto;
import com.conference.platform.control.dto.ConferenceResponseDto;
import com.conference.platform.control.dto.ConferenceRequestDto;

public interface ConferenceService {

  ConferenceResponseDto createConference(ConferenceRequestDto requestDto);
  void cancelConference(String conferenceCode);
  ConferenceResponseDto updateConference(String conferenceCode, ConferenceRequestDto requestDto);
  ConferenceAvailabilityResponseDto checkAvailability(String conferenceCode);
}
