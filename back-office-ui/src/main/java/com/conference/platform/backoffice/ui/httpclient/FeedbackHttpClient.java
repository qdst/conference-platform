package com.conference.platform.backoffice.ui.httpclient;

import com.conference.platform.backoffice.ui.httpclient.dto.FeedbackResponseDto;
import java.util.List;

public interface FeedbackHttpClient {

  List<FeedbackResponseDto> getConferenceFeedback(String conferenceCode);
}
