package com.conference.platform.control.error;

public class ConferenceInvalidInputException extends IllegalArgumentException  {

  public ConferenceInvalidInputException(String message) {
    super(message);
  }
}
