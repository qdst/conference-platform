package com.conference.platform.backoffice.ui.view;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@Setter
@Getter
@NoArgsConstructor
public class ConferenceViewModel {

    private String code;
    private String title;
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private LocalDateTime startTime;
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private LocalDateTime endTime;
    private String roomCode;
    private Integer totalCapacity;
    private ConferenceStatus status;
}
