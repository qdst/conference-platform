package com.conference.platform.control.service;

import com.conference.platform.control.dto.controller.ParticipantRegistrationRequestDto;
import com.conference.platform.control.dto.controller.ParticipantResponseDto;

public interface ParticipantService {

  ParticipantResponseDto registerParticipant(
      String conferenceCode, ParticipantRegistrationRequestDto requestDto);

  ParticipantResponseDto cancelRegistration(String registrationCode);

  ParticipantResponseDto getByParticipantCode(String registrationCode);

}
