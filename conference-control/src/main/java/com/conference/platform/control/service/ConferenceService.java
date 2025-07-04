package com.conference.platform.control.service;

import com.conference.platform.control.dto.controller.ConferenceAvailabilityResponseDto;
import com.conference.platform.control.dto.controller.ConferenceSummaryResponseDto;
import com.conference.platform.control.dto.controller.CreateConferenceRequestDto;
import com.conference.platform.control.dto.controller.ConferenceResponseDto;
import com.conference.platform.control.dto.controller.UpdateConferenceRequestDto;
import java.time.LocalDateTime;
import java.util.List;

public interface ConferenceService {

  ConferenceResponseDto getConference(String conferenceCode);
  ConferenceSummaryResponseDto getConferenceSummary(String conferenceCode);
  ConferenceResponseDto createConference(CreateConferenceRequestDto requestDto);
  ConferenceResponseDto cancelConference(String conferenceCode);
  ConferenceResponseDto updateConference(String conferenceCode, UpdateConferenceRequestDto requestDto);
  ConferenceAvailabilityResponseDto checkAvailability(String conferenceCode);
  List<ConferenceSummaryResponseDto> findAvailableConferences(LocalDateTime startTime, LocalDateTime endTime);
}
