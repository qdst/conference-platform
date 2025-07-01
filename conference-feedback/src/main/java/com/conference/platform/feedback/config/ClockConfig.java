package com.conference.platform.feedback.config;

import java.time.Clock;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ClockConfig {

  @Bean
  public Clock clock() {
    return Clock.systemDefaultZone();
  }
}
