package com.conference.platform.control.service;

import de.huxhorn.sulky.ulid.ULID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class IdentificationCodeServiceImpl implements IdentificationCodeService {

  private final ULID ulid = new ULID();

  @Override
  public String generateConferenceCode() {
    return ulid.nextULID();
  }

  @Override
  public String createRegistrationCode(String conferenceCode, Integer totalConferenceParticipants) {
    var newParticipantNumber = totalConferenceParticipants + 1;
    var participantNumberString = String.format("%05d", newParticipantNumber);
    return conferenceCode + "-" + participantNumberString;
  }

  private static int getNumberLength(int newParticipantNumber) {
    return String.valueOf(newParticipantNumber).length();
  }

}
