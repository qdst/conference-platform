package com.conference.platform.control.mapper;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import com.conference.platform.control.dto.controller.ConferenceResponseDto;
import com.conference.platform.control.dto.controller.CreateConferenceRequestDto;
import com.conference.platform.control.dto.httpclient.LocationDto;
import com.conference.platform.control.model.ConferenceStatus;
import com.conference.platform.control.model.entity.Conference;
import java.time.LocalDateTime;
import org.junit.jupiter.api.Test;

class ConferenceMapperTest {

  private final static LocalDateTime START_TIME = LocalDateTime.of(2026, 6, 15, 10, 0);
  private final static LocalDateTime END_TIME = LocalDateTime.of(2026, 6, 15, 16, 0);
  private final static String CONFERENCE_CODE = "CONFERENCE_CODE_0123456789";
  private final static String CONFERENCE_TITLE = "TECH CONF";
  private final static Integer TOTAL_CAPACITY = 5000;
  private final static Integer REGISTERED_PARTICIPANT = 2500;

  private final static String ADDRESS_LINE_1 = "221 BAKER STREET ";
  private final static String ADDRESS_LINE_2 = "Marylebone";
  private final static String CITY = "LONDON";
  private static final String ROOM_CODE = "ROOM_CODE_0123456789";

  @Test
    void shouldMapToSummaryResponseDto() {
      // given
      var conference = createTestConference();
      var locationDto = LocationDto.builder()
          .addressLine1(ADDRESS_LINE_1)
          .addressLine2(ADDRESS_LINE_2)
          .city(CITY)
          .build();

      // when
      var summaryResponseDto = ConferenceMapper.toSummaryResponseDto(conference, locationDto);

      // then
      assertEquals(CONFERENCE_CODE, summaryResponseDto.getCode());
      assertEquals(CONFERENCE_TITLE, summaryResponseDto.getTitle());
      assertEquals(START_TIME, summaryResponseDto.getStartTime());
      assertEquals(END_TIME, summaryResponseDto.getEndTime());
      assertEquals(ADDRESS_LINE_1, summaryResponseDto.getAddressLine1());
      assertEquals(ADDRESS_LINE_2, summaryResponseDto.getAddressLine2());
      assertEquals(CITY, summaryResponseDto.getCity());
      assertEquals(REGISTERED_PARTICIPANT, summaryResponseDto.getRegisteredParticipants());
      assertEquals(TOTAL_CAPACITY, summaryResponseDto.getTotalCapacity());
    }

    @Test
    void shouldMapToResponseDto() {
      // given
      Conference conference = createTestConference();

      // when
      ConferenceResponseDto result = ConferenceMapper.toResponseDto(conference);

      // then
      assertEquals(CONFERENCE_CODE, result.getCode());
      assertEquals(CONFERENCE_TITLE, result.getTitle());
      assertEquals(START_TIME, result.getStartTime());
      assertEquals(END_TIME, result.getEndTime());
      assertEquals(ROOM_CODE, result.getRoomCode());
      assertEquals(ConferenceStatus.SCHEDULED, result.getStatus());
      assertEquals(TOTAL_CAPACITY, result.getTotalCapacity());
    }


    @Test
    void shouldCreateNewConference() {
      // given
      var requestDto = CreateConferenceRequestDto.builder()
          .roomCode(ROOM_CODE)
          .title(CONFERENCE_TITLE)
          .startTime(START_TIME)
          .endTime(END_TIME)
          .build();

      // when
      var newConferenceModel = ConferenceMapper.toNewModel(requestDto, CONFERENCE_CODE, TOTAL_CAPACITY);

      // then
      assertNotNull(newConferenceModel);
      assertEquals(CONFERENCE_CODE, newConferenceModel.getConferenceCode());
      assertEquals(ROOM_CODE, newConferenceModel.getRoomCode());
      assertEquals(CONFERENCE_TITLE, newConferenceModel.getTitle());
      assertEquals(ConferenceStatus.SCHEDULED, newConferenceModel.getStatus());
      assertEquals(START_TIME, newConferenceModel.getStartTime());
      assertEquals(END_TIME, newConferenceModel.getEndTime());
      assertEquals(TOTAL_CAPACITY, newConferenceModel.getTotalCapacity());
    }

  private Conference createTestConference() {
    Conference conference = new Conference();
    conference.setRoomCode(ROOM_CODE);
    conference.setStatus(ConferenceStatus.SCHEDULED);
    conference.setConferenceCode(CONFERENCE_CODE);
    conference.setTitle(CONFERENCE_TITLE);
    conference.setStartTime(START_TIME);
    conference.setEndTime(END_TIME);
    conference.setTotalCapacity(TOTAL_CAPACITY);
    conference.setActiveParticipantsCount(REGISTERED_PARTICIPANT);
    return conference;
  }
}
