package com.conference.platform.control.service;

import com.conference.platform.control.dto.controller.ParticipantRegistrationRequestDto;
import com.conference.platform.control.dto.controller.ParticipantRegistrationResponseDto;

public interface ParticipantService {

  ParticipantRegistrationResponseDto registerParticipant(
      String conferenceCode, ParticipantRegistrationRequestDto requestDto);

  void cancelRegistration(String participantCode);

}
