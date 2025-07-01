package com.conference.platform.feedback.httpclient;

import com.conference.platform.feedback.dto.httpclient.ParticipantResponseDto;

public interface ConferenceControlHttpClient {

  ParticipantResponseDto getParticipantByRegistrationCode(String registrationCode);
}
