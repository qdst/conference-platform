package com.conference.platform.external.ui.service;

import com.conference.platform.external.ui.dto.httpclient.ConferenceSummaryResponseDto;
import java.time.LocalDateTime;
import java.util.List;

public interface ConferenceService {

  List<ConferenceSummaryResponseDto> searchAvailableConferences(LocalDateTime sartTime, LocalDateTime endTime);
}
