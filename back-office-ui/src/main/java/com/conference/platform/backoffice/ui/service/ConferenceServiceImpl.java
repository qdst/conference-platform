package com.conference.platform.backoffice.ui.service;

import com.conference.platform.backoffice.ui.httpclient.BackOfficeGatewayHttpClient;
import com.conference.platform.backoffice.ui.httpclient.dto.ConferenceResponseDto;
import com.conference.platform.backoffice.ui.httpclient.dto.CreateConferenceRequestDto;
import com.conference.platform.backoffice.ui.httpclient.dto.UpdateConferenceRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ConferenceServiceImpl implements ConferenceService {

  private final BackOfficeGatewayHttpClient backOfficeGatewayHttpClient;

  @Override
  public ConferenceResponseDto getConference(String conferenceCode) {
    return backOfficeGatewayHttpClient.getConference(conferenceCode);
  }

  @Override
  public ConferenceResponseDto cancelConference(String conferenceCode) {

    return backOfficeGatewayHttpClient.cancelConference(conferenceCode);
  }

  @Override
  public ConferenceResponseDto createConference(CreateConferenceRequestDto requestDto) {
    return backOfficeGatewayHttpClient.createConference(requestDto);
  }

  @Override
  public ConferenceResponseDto updateConference(UpdateConferenceRequestDto requestDto, String conferenceCode) {
    return backOfficeGatewayHttpClient.updateConference(requestDto, conferenceCode);
  }
}
