package com.conference.platform.control.service;

public interface IdentificationCodeService {

  String generateConferenceCode();

  String createRegistrationCode(String conferenceCode, Integer totalNumberOfParticipants);

}
