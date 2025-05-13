package com.calendar.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class AttendeeRequest {

    @NotNull(message = "contact ID required")
    private Integer contactId;

    private Boolean isOrganizer = false;
}
