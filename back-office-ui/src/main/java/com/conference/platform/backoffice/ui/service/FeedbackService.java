package com.conference.platform.backoffice.ui.service;

import com.conference.platform.backoffice.ui.httpclient.dto.FeedbackResponseDto;
import java.util.List;

public interface FeedbackService {

  List<FeedbackResponseDto> getConferenceFeedback(String conferenceCode);
}
