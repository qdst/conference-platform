package com.conference.platform.room.repository;

import com.conference.platform.room.model.entity.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoomRepository extends JpaRepository<Room,Integer> {
  Room findByRoomCode(String roomCode);
}
