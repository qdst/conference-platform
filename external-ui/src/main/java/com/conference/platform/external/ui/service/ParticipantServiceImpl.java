package com.conference.platform.external.ui.service;

import com.conference.platform.external.ui.dto.httpclient.ParticipantRegistrationRequestDto;
import com.conference.platform.external.ui.dto.httpclient.ParticipantResponseDto;
import com.conference.platform.external.ui.httpclient.ConferenceGatewayHttpClient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ParticipantServiceImpl implements ParticipantService {

  private final ConferenceGatewayHttpClient gatewayHttpClient;

  @Override
  public ParticipantResponseDto registerParticipant(ParticipantRegistrationRequestDto requestDto, String conferenceCode) {
    return gatewayHttpClient.registerParticipant(requestDto, conferenceCode);
  }

  @Override
  public ParticipantResponseDto cancelParticipantRegistration(String participantCode) {
    return gatewayHttpClient.cancelParticipantRegistration(participantCode);
  }


}
