package com.conference.platform.backoffice.ui.service;

import com.conference.platform.backoffice.ui.httpclient.ConferenceHttpClient;
import com.conference.platform.backoffice.ui.httpclient.dto.ConferenceResponseDto;
import com.conference.platform.backoffice.ui.httpclient.dto.CreateConferenceRequestDto;
import com.conference.platform.backoffice.ui.httpclient.dto.UpdateConferenceRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ConferenceServiceImpl implements ConferenceService {

  private final ConferenceHttpClient conferenceHttpClient;

  @Override
  public ConferenceResponseDto getConference(String conferenceCode) {
    return conferenceHttpClient.getConference(conferenceCode);
  }

  @Override
  public ConferenceResponseDto cancelConference(String conferenceCode) {
    return conferenceHttpClient.cancelConference(conferenceCode);
  }

  @Override
  public ConferenceResponseDto createConference(CreateConferenceRequestDto requestDto) {
    return conferenceHttpClient.createConference(requestDto);
  }

  @Override
  public ConferenceResponseDto updateConference(UpdateConferenceRequestDto requestDto, String conferenceCode) {
    return conferenceHttpClient.updateConference(requestDto, conferenceCode);
  }
}
