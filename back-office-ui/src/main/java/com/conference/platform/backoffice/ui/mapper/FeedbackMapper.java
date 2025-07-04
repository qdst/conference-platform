package com.conference.platform.backoffice.ui.mapper;


import com.conference.platform.backoffice.ui.httpclient.dto.FeedbackResponseDto;
import com.conference.platform.backoffice.ui.view.FeedbackResponseViewModel;

public final class FeedbackMapper {

  private FeedbackMapper() {
  }

  public static FeedbackResponseViewModel toResponseViewModel(FeedbackResponseDto responseDto) {
    FeedbackResponseViewModel feedbackViewModel = new FeedbackResponseViewModel();
    feedbackViewModel.setTitle(responseDto.getTitle());
    feedbackViewModel.setText(responseDto.getText());
    feedbackViewModel.setAuthor(responseDto.getAuthor());
    feedbackViewModel.setCreatedAt(responseDto.getCreatedAt());
    return feedbackViewModel;
  }
}
