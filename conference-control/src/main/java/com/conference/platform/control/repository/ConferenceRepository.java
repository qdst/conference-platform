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

  default Conference getByConferenceCode(String conferenceCode) {
    return findByConferenceCode(conferenceCode)
        .orElseThrow(() ->
            new ConferenceException("Conference with code '" + conferenceCode + "' not found"));
  }

  Optional<Conference> findByConferenceCode(String conferenceCode);

  @Query("""
      SELECT CASE WHEN COUNT(c)>0 THEN TRUE ELSE FALSE END
      FROM Conference c
      WHERE c.roomCode = :roomCode
      AND c.startTime <= :newConferenceEndTime
      AND c.endTime   >= :newConferenceStartTime
      AND c.status = com.conference.platform.control.model.ConferenceStatus.SCHEDULED
      AND (:conferenceCode IS NULL OR c.conferenceCode <> :conferenceCode)
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

  @Query("""
        SELECT EXISTS
        (SELECT 1
            FROM Conference c
           WHERE c.roomCode = :roomCode
             AND c.status = com.conference.platform.control.model.ConferenceStatus.SCHEDULED
             AND c.startTime >= :currentTime)
      """)
  boolean roomHasUpcomingConference(
      @Param("roomCode") String roomCode,
      @Param("currentTime") LocalDateTime currentTime);

  @Query("""
        SELECT EXISTS
        (SELECT 1
            FROM Conference c
           WHERE c.roomCode = :roomCode
             AND c.status = com.conference.platform.control.model.ConferenceStatus.SCHEDULED
             AND c.startTime >= :currentTime
             AND c.activeParticipantsCount > :newRoomCapacity)
      """)
  boolean conferenceWillExceedCapacity(
      @Param("roomCode") String roomCode,
      @Param("currentTime") LocalDateTime currentTime,
      @Param("newRoomCapacity") Integer newRoomCapacity);

}
