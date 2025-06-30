package com.conference.platform.control.mapper;

import com.conference.platform.control.dto.controller.ConferenceResponseDto;
import com.conference.platform.control.dto.controller.CreateConferenceRequestDto;
import com.conference.platform.control.model.ConferenceStatus;
import com.conference.platform.control.model.entity.Conference;

public final class ConferenceMapper {

  private ConferenceMapper() {
  }

  public static ConferenceResponseDto toDto(Conference conference) {
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
