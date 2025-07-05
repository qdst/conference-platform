package com.conference.platform.control.error;

public class ParticipantNotFoundException extends NotFoundException {

  public ParticipantNotFoundException(String registrationCode) {
    super("Participant with registration code '" + registrationCode + "' not found");
  }
}
