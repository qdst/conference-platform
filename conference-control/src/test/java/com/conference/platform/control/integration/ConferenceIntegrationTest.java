package com.conference.platform.control.integration;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.BDDMockito.given;

import com.conference.platform.control.dto.controller.ConferenceResponseDto;
import com.conference.platform.control.dto.controller.UpdateConferenceRequestDto;
import com.conference.platform.control.dto.httpclient.LocationDto;
import com.conference.platform.control.dto.httpclient.RoomResponseDto;
import com.conference.platform.control.error.ErrorResponse;
import com.conference.platform.control.httpclient.RoomHttpClient;
import com.conference.platform.control.model.ConferenceStatus;
import com.conference.platform.control.model.ParticipantStatus;
import com.conference.platform.control.model.RoomStatus;
import com.conference.platform.control.repository.ConferenceRepository;
import com.conference.platform.control.repository.ParticipantRepository;
import java.time.LocalDateTime;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.Sql.ExecutionPhase;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

@Testcontainers
@Sql(executionPhase = ExecutionPhase.BEFORE_TEST_METHOD, scripts = {"/db/sql/conference_integration_test_data.sql"})
@Sql(executionPhase = ExecutionPhase.AFTER_TEST_METHOD, scripts = {"/db/sql/clean_up.sql"})
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ConferenceIntegrationTest {

  // code for non-existing conference
  private final String NOT_EXISTING_CONFERENCE_CODE = "01JZAC8449B7AFGD7ZEYGNXRS7";

  // CONFERENCE 1 is conference without participants
  private final String CONFERENCE_CODE_1 = "01JZACFFM53RAAXFY0WXXRZQ1S";

  // CONFERENCE 2 is conference with participants
  private final String CONFERENCE_CODE_2 = "01JZAY2D77RRPHTFZZ617P9RMV";

  private final static String CONFERENCE_TITLE_1 = "DEVOXX UK";
  private final static String CONFERENCE_TITLE_2 = "GEEK OUT";

  private final static Integer TOTAL_CAPACITY_1 = 100;

  private final LocalDateTime START_TIME_1 = LocalDateTime.of(2026, 7, 10, 10, 0);
  private final LocalDateTime END_TIME_1 = LocalDateTime.of(2026, 7, 10, 18, 0);

  private final LocalDateTime NEW_START_TIME = LocalDateTime.of(2026, 11, 12, 9, 0);
  private final LocalDateTime NEW_END_TIME = LocalDateTime.of(2026, 11, 12, 16, 0);

  private final String ROOM_CODE_1 = "ROOM1234567890123456789012";
  private final String NEW_ROOM_CODE = "01JZA67V6XMCQ4MDZN0TVJTW40";
  private final Integer NEW_ROOM_CAPACITY = 5;


  @Container
  static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:17.5")
      .withDatabaseName("test_db")
      .withUsername("test_db_user")
      .withPassword("test_db_pass")
      .withInitScript("./db/sql/init_db.sql");

  @DynamicPropertySource
  static void registerPgProperties(DynamicPropertyRegistry registry) {
    registry.add("spring.datasource.url", postgres::getJdbcUrl);
    registry.add("spring.datasource.username", () -> "c_control_user");
    registry.add("spring.datasource.password", () -> "c_control_user");
  }

  @Autowired
  private TestRestTemplate restTemplate;

  @Autowired
  private ConferenceRepository conferenceRepository;

  @Autowired
  private ParticipantRepository participantRepository;

  @MockitoBean
  private RoomHttpClient roomHttpClient;

  @Test
  void shouldGetConferenceByConferenceCode() {
    // when
    var response =
        restTemplate.getForEntity("/v1/conferences/{conferenceCode}", ConferenceResponseDto.class, CONFERENCE_CODE_1);

    // then
    assertThat(response.getStatusCode(), is(HttpStatus.OK));
    var conferenceResponseDto = response.getBody();
    assertNotNull(conferenceResponseDto);
    assertThat(conferenceResponseDto.getRoomCode(), is(ROOM_CODE_1));
    assertThat(conferenceResponseDto.getCode(), is(CONFERENCE_CODE_1));
    assertThat(conferenceResponseDto.getTitle(), is(CONFERENCE_TITLE_1));
    assertThat(conferenceResponseDto.getStartTime(), is(START_TIME_1));
    assertThat(conferenceResponseDto.getEndTime(), is(END_TIME_1));
    assertThat(conferenceResponseDto.getTotalCapacity(), is(TOTAL_CAPACITY_1));
    assertThat(conferenceResponseDto.getStatus(), is(ConferenceStatus.SCHEDULED));
  }

  @Test
  void shouldThrowExceptionWhenConferenceCodeDoesntExist() {
    // when
    var responseEntity =
        restTemplate.getForEntity("/v1/conferences/{conferenceCode}", ErrorResponse.class,
            NOT_EXISTING_CONFERENCE_CODE);

    // then
    assertThat(responseEntity.getStatusCode(), is(HttpStatus.NOT_FOUND));
    var responseBody = responseEntity.getBody();
    assertThat(responseBody, is(notNullValue()));
    assertThat(responseBody.getStatus(), is(404));
    assertThat(responseBody.getCode(), is(HttpStatus.NOT_FOUND.name()));
    assertThat(responseBody.getMessage(),
        is("Conference with code '%s' not found".formatted(NOT_EXISTING_CONFERENCE_CODE)));
  }

  @Test
  void shouldUpdateConferenceRoomWithStartAndEndTime() {
    // given
    var requestDto = UpdateConferenceRequestDto.builder()
        .roomCode(NEW_ROOM_CODE)
        .startTime(NEW_START_TIME)
        .endTime(NEW_END_TIME)
        .build();
    var httpRequestEntity = new HttpEntity<>(requestDto);

    var roomResponseDto = RoomResponseDto.builder()
        .roomCode(NEW_ROOM_CODE)
        .locationDto(LocationDto.builder().build())
        .roomStatus(RoomStatus.AVAILABLE)
        .capacity(NEW_ROOM_CAPACITY)
        .build();

    given(roomHttpClient.findByRoomCode(NEW_ROOM_CODE))
        .willReturn(roomResponseDto);

    // when
    var responseEntity =
        restTemplate.exchange(
            "/v1/conferences/{conferenceCode}", HttpMethod.PUT, httpRequestEntity, ConferenceResponseDto.class,
            CONFERENCE_CODE_2);

    // then
    assertThat(responseEntity.getStatusCode(), is(HttpStatus.OK));
    var responseDto = responseEntity.getBody();

    assertThat(responseDto, is(notNullValue()));
    assertThat(responseDto.getRoomCode(), is(NEW_ROOM_CODE));
    assertThat(requestDto.getStartTime(), is(NEW_START_TIME));
    assertThat(responseDto.getEndTime(), is(NEW_END_TIME));

    assertThat(responseDto.getTotalCapacity(), is(NEW_ROOM_CAPACITY));
    assertThat(responseDto.getTitle(), is(CONFERENCE_TITLE_2));
    assertThat(responseDto.getStatus(), is(ConferenceStatus.SCHEDULED));

    var updatedConferenceEntity = conferenceRepository.getByConferenceCode(CONFERENCE_CODE_2);

    assertThat(updatedConferenceEntity.getRoomCode(), is(NEW_ROOM_CODE));
    assertThat(updatedConferenceEntity.getStartTime(), is(NEW_START_TIME));
    assertThat(updatedConferenceEntity.getEndTime(), is(NEW_END_TIME));

    assertThat(updatedConferenceEntity.getTotalCapacity(), is(NEW_ROOM_CAPACITY));
    assertThat(updatedConferenceEntity.getTitle(), is(CONFERENCE_TITLE_2));
    assertThat(updatedConferenceEntity.getStatus(), is(ConferenceStatus.SCHEDULED));
  }

  @Test
  void shouldCancelConference() {
    // given
    var registrationCode1 = "01JZAY2D77RRPHTFZZ617P9RMV-00001";
    var registrationCode2 = "01JZAY2D77RRPHTFZZ617P9RMV-00002";

    // when
    var responseEntity =
        restTemplate.postForEntity(
            "/v1/conferences/{conferenceCode}/cancel", null, ConferenceResponseDto.class,
            CONFERENCE_CODE_2);

    // then
    assertThat(responseEntity.getStatusCode(), is(HttpStatus.OK));

    var responseDto = responseEntity.getBody();
    assertNotNull(responseDto);
    assertThat(responseDto.getStatus(), is(ConferenceStatus.CANCELLED));

    var updatedConference = conferenceRepository.getByConferenceCode(CONFERENCE_CODE_2);
    assertThat(updatedConference.getStatus(), is(ConferenceStatus.CANCELLED));

    var participant1 = participantRepository.getByRegistrationCode(registrationCode1);
    assertThat(participant1.getStatus(), equalTo(ParticipantStatus.CANCELLED));

    var participant2 = participantRepository.getByRegistrationCode(registrationCode2);
    assertThat(participant2.getStatus(), is(ParticipantStatus.CANCELLED));
  }

}
