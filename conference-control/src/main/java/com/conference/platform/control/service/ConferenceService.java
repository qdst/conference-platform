package com.conference.platform.control.service;

import com.conference.platform.control.dto.controller.ConferenceAvailabilityResponseDto;
import com.conference.platform.control.dto.controller.CreateConferenceRequestDto;
import com.conference.platform.control.dto.controller.ConferenceResponseDto;
import com.conference.platform.control.dto.controller.UpdateConferenceRequestDto;
import java.time.LocalDateTime;
import java.util.List;

public interface ConferenceService {

  ConferenceResponseDto createConference(CreateConferenceRequestDto requestDto);
  void cancelConference(String conferenceCode);
  ConferenceResponseDto updateConference(String conferenceCode, UpdateConferenceRequestDto requestDto);
  ConferenceAvailabilityResponseDto checkAvailability(String conferenceCode);
  List<ConferenceResponseDto> findAvailableConferences(LocalDateTime startTime, LocalDateTime endTime);
}
