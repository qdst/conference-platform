package com.conference.platform.control.service;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import de.huxhorn.sulky.ulid.ULID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class IdentificationCodeServiceTest {

  private IdentificationCodeService identificationCodeService;

  @BeforeEach
  void setUp() {
    identificationCodeService = new IdentificationCodeServiceImpl();
  }

  @Test
  void getNewUlidConferenceCode() {
    // when
    var newUlidSting = identificationCodeService.generateConferenceCode();

    // then
    var ulidCode = ULID.parseULID(newUlidSting);
    assertNotNull(ulidCode);
  }


  @Test
  void createNewRegistrationCodeForParticipant() {
    // given
    var ulidCodeString = new ULID().nextULID();
    var numberOfParticipants = 10;

    // when
    var newRegistrationCode = identificationCodeService.createRegistrationCode(ulidCodeString, numberOfParticipants);

    // then
    assertNotNull(newRegistrationCode);
    assertThat(newRegistrationCode.length(), is(32));

    var registrationCodeParts = newRegistrationCode.split("-");
    assertThat(registrationCodeParts.length, is(2));

    var conferenceCodePart = registrationCodeParts[0];
    var participantCode = registrationCodeParts[1];

    assertThat(conferenceCodePart, is(ulidCodeString));
    assertThat(participantCode, is("00011"));

  }
}
