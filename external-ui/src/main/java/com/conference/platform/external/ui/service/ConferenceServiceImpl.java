package com.conference.platform.external.ui.service;

import com.conference.platform.external.ui.dto.httpclient.ConferenceSummaryResponseDto;
import com.conference.platform.external.ui.httpclient.ConferenceGatewayHttpClient;
import java.time.LocalDateTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ConferenceServiceImpl implements ConferenceService {

  private final ConferenceGatewayHttpClient gatewayHttpClient;

  @Override
  public List<ConferenceSummaryResponseDto> searchAvailableConferences(LocalDateTime sartTime, LocalDateTime endTime) {
    return gatewayHttpClient.searchAvailableConferences(sartTime, endTime);
  }

  @Override
  public ConferenceSummaryResponseDto getConferenceSummary(String conferenceCode) {
    return gatewayHttpClient.getConferenceSummary(conferenceCode);
  }

}
