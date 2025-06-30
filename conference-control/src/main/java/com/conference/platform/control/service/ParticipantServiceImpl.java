package com.conference.platform.control.service;

import com.conference.platform.control.dto.controller.ParticipantRegistrationRequestDto;
import com.conference.platform.control.dto.controller.ParticipantRegistrationResponseDto;
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
  public ParticipantRegistrationResponseDto registerParticipant(
      String conferenceCode,
      ParticipantRegistrationRequestDto requestDto) {
    var conference = conferenceRepository.getByCode(conferenceCode);
    //TODO: Move validation into conference service, remove duplication

    if (conference.getStatus() != ConferenceStatus.SCHEDULED) {
      throw null;
    }
    var maxCapacity = conference.getTotalCapacity();
    var activeRegistrations =
        participantRepository.countAllActiveConferenceParticipant(conferenceCode);

    if (Utils.hasFreeCapacity(maxCapacity, activeRegistrations)) {
      return createConferenceNewParticipant(conferenceCode, requestDto, conference);

    } else {
      throw null;
    }
  }

  private ParticipantRegistrationResponseDto createConferenceNewParticipant(String conferenceCode,
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
  public void cancelRegistration(String participantCode) {
    var participant = participantRepository.getByParticipantCode(participantCode);
    if (participant.getStatus() != ParticipantStatus.REGISTERED) {
      throw null;
    }
    participant.setStatus(ParticipantStatus.CANCELLED);
    participantRepository.save(participant);
  }

}
