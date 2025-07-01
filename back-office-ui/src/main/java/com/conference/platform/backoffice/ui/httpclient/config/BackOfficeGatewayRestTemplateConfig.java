package com.conference.platform.backoffice.ui.httpclient.config;

import java.time.Duration;
import java.time.temporal.ChronoUnit;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.convert.DurationUnit;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class BackOfficeGatewayRestTemplateConfig {

  @Bean
  public RestTemplate backOfficeGatewayRestTemplate(
      RestTemplateBuilder restTemplateBuilder,

      @Value("${backoffice.gateway.rest.client.root-uri}")
      String rootUri,

      @DurationUnit(ChronoUnit.SECONDS)
      @Value("${backoffice.gateway.rest.client.connect-timeout}")
      Duration connectTimeout,

      @DurationUnit(ChronoUnit.SECONDS)
      @Value("${backoffice.gateway.rest.client.read-timeout}")
      Duration readTimeout
  ) {
    return restTemplateBuilder
        .rootUri(rootUri)
        .connectTimeout(connectTimeout)
        .readTimeout(readTimeout)
        .build();
  }

}
