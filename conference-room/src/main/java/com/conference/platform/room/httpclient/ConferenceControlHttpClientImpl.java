package com.conference.platform.room.httpclient;

import com.conference.platform.room.dto.httpclient.CapacityCheckResponseDto;
import com.conference.platform.room.dto.httpclient.RoomOccupationResponseDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class ConferenceControlHttpClientImpl implements ConferenceControlHttpClient {

  private final String participantPath;
  private final RestTemplate restTemplate;

  public ConferenceControlHttpClientImpl(
      @Value("${conference.control.rest.client.paths.participant.find}") String participantPath,
      RestTemplate restTemplate) {
    this.restTemplate = restTemplate;
    this.participantPath = participantPath;
  }

  @Override
  public RoomOccupationResponseDto roomHasUpcomingConference(String roomCode) {
    return null;
  }

  @Override
  public CapacityCheckResponseDto conferenceWillExceedCapacity(String roomCode, Integer newCapacity) {
    return null;
  }

}
