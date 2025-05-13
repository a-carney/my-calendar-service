package com.calendar.dto.request;

import com.calendar.entity.Event;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class EventRequest {

    @NotBlank(message = "event title is required")
    @Size(max = 100, message = "event title cannot exceed 100 chars")
    private String title;

    private String description;

    @NotNull(message = "start date and time is required")
    private LocalDateTime startDate;

    @NotNull(message = "end date and time is required")
    private LocalDateTime endDate;

    private Boolean allDay = false;

    private Event.EventStatus status = Event.EventStatus.SCHEDULED;

    private Integer locationId;
}
