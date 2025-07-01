package com.conference.platform.feedback.repository;

import com.conference.platform.feedback.model.entity.Feedback;
import java.util.List;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FeedbackRepository extends CrudRepository<Feedback, Long> {

  List<Feedback> findAllByConferenceCode(String conferenceCode);
}
