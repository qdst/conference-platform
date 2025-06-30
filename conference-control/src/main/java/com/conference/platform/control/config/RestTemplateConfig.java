package com.conference.platform.control.config;

import java.time.Duration;
import java.time.temporal.ChronoUnit;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.convert.DurationUnit;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class RestTemplateConfig {

  @Bean
  public RestTemplate conferenceRoomRestTemplate(
      RestTemplateBuilder restTemplateBuilder,

      @Value("${conference.room.rest.client.root-uri}")
      String rootUri,

      @DurationUnit(ChronoUnit.SECONDS)
      @Value("${conference.room.rest.client.connect-timeout}")
      Duration connectTimeout,

      @DurationUnit(ChronoUnit.SECONDS)
      @Value("${conference.room.rest.client.read-timeout}")
      Duration readTimeout
  ) {
    return restTemplateBuilder
        .rootUri(rootUri)
        .connectTimeout(connectTimeout)
        .readTimeout(readTimeout)
        .build();
  }

}
