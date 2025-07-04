package com.conference.platform.external.ui.service;

import com.conference.platform.external.ui.dto.httpclient.ParticipantRegistrationRequestDto;
import com.conference.platform.external.ui.dto.httpclient.ParticipantResponseDto;

public interface ParticipantService {

  ParticipantResponseDto registerParticipant(ParticipantRegistrationRequestDto requestDto, String conferenceCode);

  ParticipantResponseDto cancelParticipantRegistration(String participantCode);
}
