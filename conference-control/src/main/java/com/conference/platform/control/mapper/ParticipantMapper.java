package com.conference.platform.control.mapper;

import com.conference.platform.control.dto.controller.ParticipantRegistrationRequestDto;
import com.conference.platform.control.dto.controller.ParticipantRegistrationResponseDto;
import com.conference.platform.control.model.ParticipantStatus;
import com.conference.platform.control.model.entity.Participant;

public final class ParticipantMapper {

  public static Participant toModel(ParticipantRegistrationRequestDto requestDto) {
    var participant = new Participant();
    participant.setFirstName(requestDto.getFirstName());
    participant.setLastName(requestDto.getLastName());
    participant.setEmail(requestDto.getEmail());
    participant.setDateOfBirth(requestDto.getDateOfBirth());
    participant.setGender(requestDto.getGender());
    participant.setStatus(ParticipantStatus.REGISTERED);
    return participant;
  }

  public static ParticipantRegistrationResponseDto toDto(Participant participant, String conferenceCode) {
    return ParticipantRegistrationResponseDto.builder()
        .firstName(participant.getFirstName())
        .lastName(participant.getLastName())
        .email(participant.getEmail())
        .registrationCode(participant.getRegistrationCode())
        .dateOfBirth(participant.getDateOfBirth())
        .gender(participant.getGender())
        .conferenceCode(conferenceCode)
        .build();
  }
}
