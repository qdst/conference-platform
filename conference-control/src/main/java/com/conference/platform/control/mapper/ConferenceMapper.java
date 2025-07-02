package com.conference.platform.control.mapper;

import com.conference.platform.control.dto.controller.ConferenceResponseDto;
import com.conference.platform.control.dto.controller.ConferenceSummaryResponseDto;
import com.conference.platform.control.dto.controller.CreateConferenceRequestDto;
import com.conference.platform.control.dto.httpclient.LocationDto;
import com.conference.platform.control.model.ConferenceStatus;
import com.conference.platform.control.model.entity.Conference;

public final class ConferenceMapper {

  private ConferenceMapper() {
  }

  public static ConferenceSummaryResponseDto toSummaryResponseDto(Conference conference, LocationDto locationDto) {
    return ConferenceSummaryResponseDto.builder()
        .code(conference.getCode())
        .title(conference.getTitle())
        .startTime(conference.getStartTime())
        .endTime(conference.getEndTime())
        .addressLine1(locationDto.getAddressLine1())
        .addressLine2(locationDto.getAddressLine2())
        .city(locationDto.getCity())
        .registeredParticipants(conference.getActiveParticipantsCount())
        .totalCapacity(conference.getTotalCapacity())
        .build();
  }

  public static ConferenceResponseDto toResponseDto(Conference conference) {
    return ConferenceResponseDto.builder()
        .code(conference.getCode())
        .title(conference.getTitle())
        .startTime(conference.getStartTime())
        .endTime(conference.getEndTime())
        .roomCode(conference.getRoomCode())
        .status(conference.getStatus())
        .totalCapacity(conference.getTotalCapacity())
        .build();
  }

  public static Conference toNewModel(
      CreateConferenceRequestDto requestDto,
      String conferenceCode,
      Integer roomCapacity) {
    var conference = new Conference();
    conference.setCode(conferenceCode);
    conference.setRoomCode(requestDto.getRoomCode());
    conference.setTitle(requestDto.getTitle());
    conference.setStatus(ConferenceStatus.SCHEDULED);
    conference.setStartTime(requestDto.getStartTime());
    conference.setEndTime(requestDto.getEndTime());
    conference.setTotalCapacity(roomCapacity);
    return conference;
  }
}
