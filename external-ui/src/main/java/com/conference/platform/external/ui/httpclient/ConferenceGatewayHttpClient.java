package com.conference.platform.external.ui.httpclient;

import com.conference.platform.external.ui.dto.httpclient.ConferenceSummaryResponseDto;
import java.time.LocalDateTime;
import java.util.List;

public interface ConferenceGatewayHttpClient {

  List<ConferenceSummaryResponseDto> searchAvailableConferences(LocalDateTime startTime, LocalDateTime endTime);
}
