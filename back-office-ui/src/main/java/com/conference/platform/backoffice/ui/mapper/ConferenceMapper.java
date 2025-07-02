package com.conference.platform.backoffice.ui.mapper;

import com.conference.platform.backoffice.ui.httpclient.dto.ConferenceResponseDto;
import com.conference.platform.backoffice.ui.httpclient.dto.CreateConferenceRequestDto;
import com.conference.platform.backoffice.ui.httpclient.dto.UpdateConferenceRequestDto;
import com.conference.platform.backoffice.ui.view.ConferenceViewModel;

public class ConferenceMapper {

  public static UpdateConferenceRequestDto toUpdateDto(ConferenceViewModel viewModel) {
    return UpdateConferenceRequestDto.builder()
        .endTime(viewModel.getEndTime())
        .startTime(viewModel.getStartTime())
        .roomCode(viewModel.getRoomCode())
        .build();
  }

  public static CreateConferenceRequestDto toCreateDto(ConferenceViewModel conferenceViewModel) {
    return CreateConferenceRequestDto.builder()
        .title(conferenceViewModel.getTitle())
        .startTime(conferenceViewModel.getStartTime())
        .endTime(conferenceViewModel.getEndTime())
        .roomCode(conferenceViewModel.getRoomCode())
        .build();
  }

  public static ConferenceViewModel toViewModel(ConferenceResponseDto responseDto) {
    var viewModel = new ConferenceViewModel();
    viewModel.setCode(responseDto.getCode());
    viewModel.setRoomCode(responseDto.getRoomCode());
    viewModel.setEndTime(responseDto.getEndTime());
    viewModel.setStartTime(responseDto.getStartTime());
    viewModel.setTitle(responseDto.getTitle());
    viewModel.setRoomCode(responseDto.getRoomCode());
    viewModel.setTotalCapacity(responseDto.getTotalCapacity());
    viewModel.setStatus(responseDto.getStatus());
    return viewModel;
  }
}
