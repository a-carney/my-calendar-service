package com.calendar.service.dto.response;

import com.calendar.model.entity.Event;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class EventResponse {
    private Integer id;
    private String title;
    private String description;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private Boolean allDay;
    private Event.EventStatus status;
    private LocationResponse location;
    private LocalDateTime createdAt;

    public static EventResponse fromEntity(Event event) {
        EventResponse rsp = new EventResponse();
        rsp.setId(event.getId());
        rsp.setTitle(event.getTitle());
        rsp.setDescription(event.getDescription());
        rsp.setStartDate(event.getStartDate());
        rsp.setEndDate(event.getEndDate());
        rsp.setAllDay(event.getAllDay());
        rsp.setStatus(event.getStatus());
        rsp.setCreatedAt(event.getCreatedAt());

        if (event.getLocation() != null) {
            rsp.setLocation(LocationResponse.fromEntity(event.getLocation()));
        }

        return rsp;
    }
}
