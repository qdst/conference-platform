package com.conference.platform.external.ui.httpclient;

import com.conference.platform.external.ui.dto.httpclient.ConferenceSummaryResponseDto;
import com.conference.platform.external.ui.dto.httpclient.FeedbackRequestDto;
import com.conference.platform.external.ui.dto.httpclient.FeedbackResponseDto;
import com.conference.platform.external.ui.dto.httpclient.ParticipantRegistrationRequestDto;
import com.conference.platform.external.ui.dto.httpclient.ParticipantResponseDto;
import java.time.LocalDateTime;
import java.util.List;

public interface ConferenceGatewayHttpClient {

  List<ConferenceSummaryResponseDto> searchAvailableConferences(LocalDateTime startTime, LocalDateTime endTime);

  ConferenceSummaryResponseDto getConferenceSummary(String conferenceCode);

  ParticipantResponseDto registerParticipant (ParticipantRegistrationRequestDto requestDto, String conferenceCode);

  ParticipantResponseDto cancelParticipantRegistration(String registrationCode);

  FeedbackResponseDto leaveFeedback (FeedbackRequestDto feedbackRequestDto, String registrationCode);
}
