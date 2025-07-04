package com.conference.platform.room.httpclient.config;

import java.time.Duration;
import java.time.temporal.ChronoUnit;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.convert.DurationUnit;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class ConferenceControlRestTemplateConfig {

  @Bean
  public RestTemplate conferenceControlRestTemplate(
      RestTemplateBuilder restTemplateBuilder,

      @Value("${conference.control.rest.client.root-uri}")
      String rootUri,

      @DurationUnit(ChronoUnit.SECONDS)
      @Value("${conference.control.rest.client.connect-timeout}")
      Duration connectTimeout,

      @DurationUnit(ChronoUnit.SECONDS)
      @Value("${conference.control.rest.client.read-timeout}")
      Duration readTimeout
  ) {
    return restTemplateBuilder
        .rootUri(rootUri)
        .connectTimeout(connectTimeout)
        .readTimeout(readTimeout)
        .build();
  }

}
