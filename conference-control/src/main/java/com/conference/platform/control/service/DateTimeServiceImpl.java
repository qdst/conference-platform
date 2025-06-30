package com.conference.platform.control.service;

import java.time.Clock;
import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DateTimeServiceImpl implements DateTimeService {

  private final Clock clock;

  @Override
  public LocalDateTime getCurrentDateTime() {
    return LocalDateTime.now(clock);
  }



}
