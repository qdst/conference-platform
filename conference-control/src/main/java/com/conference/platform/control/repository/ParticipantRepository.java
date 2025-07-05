package com.conference.platform.control.repository;

import com.conference.platform.control.error.ParticipantNotFoundException;
import com.conference.platform.control.model.entity.Participant;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ParticipantRepository extends JpaRepository<Participant, Long> {


  default Participant getByRegistrationCode(String registrationCode) {
    return findByRegistrationCode(registrationCode)
        .orElseThrow(() -> new ParticipantNotFoundException(registrationCode));
  }

  Optional<Participant> findByRegistrationCode(String registrationCode);

  @Modifying(clearAutomatically = true)
  @Query("""
        UPDATE Participant p
           SET p.status = com.conference.platform.control.model.ParticipantStatus.CANCELLED
         WHERE p.conference.conferenceCode = :conferenceCode
         AND p.status <> com.conference.platform.control.model.ParticipantStatus.CANCELLED
      """)
  int cancelAllRegistrationsForConference(@Param("conferenceCode") String conferenceCode);

  @Query("""
        SELECT COUNT(p) FROM Participant p
        WHERE p.conference.conferenceCode = :conferenceCode
        AND p.status != com.conference.platform.control.model.ParticipantStatus.CANCELLED
      """)
  int countAllActiveConferenceParticipant(@Param("conferenceCode") String conferenceCode);

  @Query("""
      SELECT COUNT(*) FROM Participant p
      WHERE p.conference.conferenceCode = :conferenceCode
      """)
  int countAllConferenceParticipant(@Param("conferenceCode") String conferenceCode);
}
