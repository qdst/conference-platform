package com.conference.platform.external.ui.mapper;

import com.conference.platform.external.ui.dto.httpclient.ConferenceSummaryResponseDto;
import com.conference.platform.external.ui.view.ConferenceSummaryViewModel;

public class ConferenceSummaryMapper {


  private ConferenceSummaryMapper() {
  }

  public static ConferenceSummaryViewModel toViewModel(ConferenceSummaryResponseDto dto) {
    ConferenceSummaryViewModel conferenceViewModel = new ConferenceSummaryViewModel();
    conferenceViewModel.setCode(dto.getCode());
    conferenceViewModel.setTitle(dto.getTitle());
    conferenceViewModel.setStartTime(dto.getStartTime());
    conferenceViewModel.setEndTime(dto.getEndTime());
    conferenceViewModel.setTotalCapacity(dto.getTotalCapacity());
    conferenceViewModel.setRegisteredParticipants(dto.getRegisteredParticipants());
    conferenceViewModel.setAddressLine1(dto.getAddressLine1());
    conferenceViewModel.setAddressLine2(dto.getAddressLine2());
    conferenceViewModel.setCity(dto.getCity());
    return conferenceViewModel;
  }
}
