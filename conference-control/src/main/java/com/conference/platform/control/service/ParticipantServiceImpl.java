package com.conference.platform.control.service;

import com.conference.platform.control.dto.controller.ParticipantRegistrationRequestDto;
import com.conference.platform.control.dto.controller.ParticipantResponseDto;
import com.conference.platform.control.error.ConferenceConflictStateException;
import com.conference.platform.control.error.ParticipantConflictStateException;
import com.conference.platform.control.mapper.ParticipantMapper;
import com.conference.platform.control.model.ConferenceStatus;
import com.conference.platform.control.model.ParticipantStatus;
import com.conference.platform.control.model.entity.Conference;
import com.conference.platform.control.repository.ConferenceRepository;
import com.conference.platform.control.repository.ParticipantRepository;
import com.conference.platform.control.util.Utils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class ParticipantServiceImpl implements ParticipantService {

  private final ParticipantRepository participantRepository;
  private final ConferenceRepository conferenceRepository;
  private final IdentificationCodeService identificationCodeService;

  @Override
  public ParticipantResponseDto registerParticipant(
      String conferenceCode,
      ParticipantRegistrationRequestDto requestDto) {
    var conference = conferenceRepository.getByConferenceCode(conferenceCode);

    if (conference.getStatus() != ConferenceStatus.SCHEDULED) {
      throw new ConferenceConflictStateException(
          "The conference must have the status 'SCHEDULED' to register a new participant. Current conference status: '%s'"
              .formatted(conference.getStatus()));
    }
    var maxCapacity = conference.getTotalCapacity();
    var activeRegistrations =
        participantRepository.countAllActiveConferenceParticipant(conferenceCode);

    if (Utils.hasFreeCapacity(maxCapacity, activeRegistrations)) {
      return createConferenceNewParticipant(conferenceCode, requestDto, conference);

    } else {
      throw new ConferenceConflictStateException(
          "The conference has no more capacity for a new participant. Max capacity for the conference: '%s'"
              .formatted(conference.getTotalCapacity()));    }
  }

  private ParticipantResponseDto createConferenceNewParticipant(String conferenceCode,
      ParticipantRegistrationRequestDto requestDto, Conference conference) {
    var totalNumberOfParticipant =
        participantRepository.countAllConferenceParticipant(conferenceCode);
    var newRegistrationCode =
        identificationCodeService.createRegistrationCode(conferenceCode, totalNumberOfParticipant);
    var participant = ParticipantMapper.toModel(requestDto);
    participant.setRegistrationCode(newRegistrationCode);

    conference.addParticipant(participant);
    var createdParticipant = participantRepository.save(participant);
    return ParticipantMapper.toDto(createdParticipant, conferenceCode);
  }

  @Override
  public ParticipantResponseDto cancelRegistration(String registrationCode) {
    var participant = participantRepository.getByRegistrationCode(registrationCode);
    if (participant.getStatus() != ParticipantStatus.REGISTERED) {
      throw new ParticipantConflictStateException(
          "The participant must have the status REGISTERED before it can be cancelled. Current status: ‘%s’"
              .formatted(participant.getStatus()));
    }
    participant.setStatus(ParticipantStatus.CANCELLED);
    var canceledParticipant = participantRepository.save(participant);
    return ParticipantMapper.toDto(canceledParticipant, canceledParticipant.getConference().getConferenceCode());
  }

  @Override
  public ParticipantResponseDto getByParticipantCode(String registrationCode) {
    var participant = participantRepository.getByRegistrationCode(registrationCode);
    return ParticipantResponseDto.builder()
        .firstName(participant.getFirstName())
        .lastName(participant.getLastName())
        .conferenceCode(participant.getConference().getConferenceCode())
        .build();
  }

}
