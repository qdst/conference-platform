package com.conference.platform.control.error;

public class RoomConflictStateException extends ConflictStateException {

  public RoomConflictStateException(String message) {
    super(message);
  }
}
