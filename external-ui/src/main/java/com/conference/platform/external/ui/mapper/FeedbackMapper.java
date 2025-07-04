package com.conference.platform.external.ui.mapper;

import com.conference.platform.external.ui.dto.httpclient.FeedbackRequestDto;
import com.conference.platform.external.ui.dto.httpclient.FeedbackResponseDto;
import com.conference.platform.external.ui.view.FeedbackFormViewModel;
import com.conference.platform.external.ui.view.NewFeedbackViewModel;

public final class FeedbackMapper {

  private FeedbackMapper() {
  }

  public static FeedbackRequestDto toRequestDto(FeedbackFormViewModel feedbackFormViewModel) {
    return FeedbackRequestDto.builder()
        .text(feedbackFormViewModel.getText())
        .title(feedbackFormViewModel.getTitle())
        .build();
  }

  public static NewFeedbackViewModel toViewModel(FeedbackResponseDto feedbackResponseDto) {
    var viewModel = new NewFeedbackViewModel();
    viewModel.setAuthor(feedbackResponseDto.getAuthor());
    viewModel.setCreatedAt(feedbackResponseDto.getCreatedAt());
    viewModel.setText(feedbackResponseDto.getText());
    viewModel.setTitle(feedbackResponseDto.getTitle());
    return viewModel;
  }
}
