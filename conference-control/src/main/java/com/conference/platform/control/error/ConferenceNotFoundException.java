package com.conference.platform.control.error;

public class ConferenceNotFoundException extends NotFoundException {

  public ConferenceNotFoundException(String conferenceCode) {
    super("Conference with code '" + conferenceCode + "' not found");
  }
}
