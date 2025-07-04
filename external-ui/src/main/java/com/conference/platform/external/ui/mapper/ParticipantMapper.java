package com.conference.platform.external.ui.mapper;

import com.conference.platform.external.ui.dto.httpclient.ParticipantRegistrationRequestDto;
import com.conference.platform.external.ui.dto.httpclient.ParticipantResponseDto;
import com.conference.platform.external.ui.view.ParticipantViewModel;

public final class ParticipantMapper {

  private ParticipantMapper() {}

  public static ParticipantViewModel toViewModel(ParticipantResponseDto participantDto) {
    ParticipantViewModel participantViewModel = new ParticipantViewModel();
    participantViewModel.setFirstName(participantDto.getFirstName());
    participantViewModel.setLastName(participantDto.getLastName());
    participantViewModel.setEmail(participantDto.getEmail());
    participantViewModel.setGender(participantDto.getGender());
    participantViewModel.setParticipantStatus(participantDto.getParticipantStatus());
    participantViewModel.setDateOfBirth(participantDto.getDateOfBirth());
    participantViewModel.setRegistrationCode(participantDto.getRegistrationCode());
    participantViewModel.setConferenceCode(participantDto.getConferenceCode());
    return participantViewModel;
  }

  public static ParticipantRegistrationRequestDto toDto(ParticipantViewModel participantViewModel) {
    return ParticipantRegistrationRequestDto.builder()
        .firstName(participantViewModel.getFirstName())
        .lastName(participantViewModel.getLastName())
        .email(participantViewModel.getEmail())
        .gender(participantViewModel.getGender())
        .dateOfBirth(participantViewModel.getDateOfBirth())
        .build();
  }
}
