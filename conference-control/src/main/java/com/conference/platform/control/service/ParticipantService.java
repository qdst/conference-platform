package com.conference.platform.control.service;

import com.conference.platform.control.dto.controller.ParticipantRegistrationRequestDto;
import com.conference.platform.control.dto.controller.ParticipantRegistrationResponseDto;
import com.conference.platform.control.dto.controller.ParticipantResponseDto;

public interface ParticipantService {

  ParticipantRegistrationResponseDto registerParticipant(
      String conferenceCode, ParticipantRegistrationRequestDto requestDto);

  void cancelRegistration(String registrationCode);

  ParticipantResponseDto getByParticipantCode(String registrationCode);

}
