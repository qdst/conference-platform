package com.conference.platform.feedback.service;

import com.conference.platform.feedback.dto.controller.FeedbackRequestDto;
import com.conference.platform.feedback.dto.controller.FeedbackResponseDto;
import com.conference.platform.feedback.httpclient.ConferenceControlHttpClientImpl;
import com.conference.platform.feedback.mapper.FeedbackMapper;
import com.conference.platform.feedback.repository.FeedbackRepository;
import java.time.Clock;
import java.time.LocalDateTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FeedbackServiceImpl implements FeedbackService {

  private final static String MASK = "************";

  private final Clock clock;
  private final FeedbackRepository feedbackRepository;
  private final ConferenceControlHttpClientImpl conferenceControlHttpClientImpl;

  @Override
  public FeedbackResponseDto createFeedback(String registrationCode, FeedbackRequestDto requestDto) {
    var participant = conferenceControlHttpClientImpl.getParticipantByRegistrationCode(registrationCode);
    var anonymizedAuthor = anonymizeAuthor(participant.getFirstName(), participant.getLastName());
    var conferenceCode = participant.getConferenceCode();

    var feedback = FeedbackMapper.toModel(requestDto);
    feedback.setAuthor(anonymizedAuthor);
    feedback.setConferenceCode(conferenceCode);
    feedback.setCreatedAt(getCurrentTimestamp());

    var createdFeedback = feedbackRepository.save(feedback);
    return FeedbackMapper.toDto(createdFeedback);
  }

  @Override
  public List<FeedbackResponseDto> getFeedbacksForConference(String conferenceCode) {
    return feedbackRepository.findAllByConferenceCode(conferenceCode).stream()
        .map(FeedbackMapper::toDto)
        .toList();
  }

  private static String anonymizeAuthor(String firstName, String lastName) {
    return firstName + " " + lastName.charAt(0) + MASK;
  }

  private LocalDateTime getCurrentTimestamp() {
    return LocalDateTime.now(clock);
  }

}
