package com.conference.platform.control.repository;

import com.conference.platform.control.error.ConferenceException;
import com.conference.platform.control.model.entity.Conference;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ConferenceRepository extends JpaRepository<Conference, Long> {

  default Conference getByCode(String code) {
    return findByCode(code)
        .orElseThrow(() ->
            new ConferenceException("Conference with code " + code + " not found"));
  }

  Optional<Conference> findByCode(String conferenceCode);

  @Query("""
      SELECT CASE WHEN COUNT(c)>0 THEN TRUE ELSE FALSE END
      FROM Conference c
      WHERE c.roomCode = :roomCode
      AND c.startTime <= :newConferenceEndTime
      AND c.endTime   >= :newConferenceStartTime
      AND c.status = com.conference.platform.control.model.ConferenceStatus.SCHEDULED
      AND (:conferenceCode IS NULL OR c.code <> :conferenceCode)
      """)
  boolean existsOverlapping(
      @Param("roomCode") String roomCode,
      @Param("newConferenceStartTime") LocalDateTime newConferenceStartTime,
      @Param("newConferenceEndTime") LocalDateTime newConferenceEndTime,
      @Param("conferenceCode") String conferenceCode
  );

  @Query("""
        SELECT c
            FROM Conference c
           WHERE c.startTime >= :startTime
             AND c.endTime   <= :endTime
      """)
  List<Conference> findAllWithinTimeRange(
      @Param("startTime") LocalDateTime startTime,
      @Param("endTime") LocalDateTime endTime);

}
