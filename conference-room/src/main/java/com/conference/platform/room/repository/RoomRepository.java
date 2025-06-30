package com.conference.platform.room.repository;

import com.conference.platform.room.model.entity.Room;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoomRepository extends JpaRepository<Room, Long> {

  Optional<Room> findByRoomCode(String roomCode);
}
