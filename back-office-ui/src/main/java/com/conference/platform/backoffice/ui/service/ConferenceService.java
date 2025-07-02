package com.conference.platform.backoffice.ui.service;

import com.conference.platform.backoffice.ui.httpclient.dto.ConferenceResponseDto;
import com.conference.platform.backoffice.ui.httpclient.dto.CreateConferenceRequestDto;
import com.conference.platform.backoffice.ui.httpclient.dto.UpdateConferenceRequestDto;

public interface ConferenceService {

  ConferenceResponseDto getConference(String conferenceCode);

  ConferenceResponseDto cancelConference(String conferenceCode);

  ConferenceResponseDto createConference(CreateConferenceRequestDto requestDto);

  ConferenceResponseDto updateConference(UpdateConferenceRequestDto requestDto, String conferenceCode);
}
