package com.conference.platform.backoffice.ui.httpclient;

import com.conference.platform.backoffice.ui.httpclient.dto.ConferenceResponseDto;
import com.conference.platform.backoffice.ui.httpclient.dto.CreateConferenceRequestDto;
import com.conference.platform.backoffice.ui.httpclient.dto.UpdateConferenceRequestDto;

public interface BackOfficeGatewayHttpClient {

  ConferenceResponseDto createConference(CreateConferenceRequestDto requestDto);

  ConferenceResponseDto cancelConference(String conferenceCode);

  ConferenceResponseDto updateConference(UpdateConferenceRequestDto requestDto, String conferenceCode);

  ConferenceResponseDto getConference(String roomCode);
}
