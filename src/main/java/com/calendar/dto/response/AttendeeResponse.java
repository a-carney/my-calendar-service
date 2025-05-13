package com.calendar.dto.response;

import com.calendar.entity.Attendee;
import lombok.Data;

@Data
public class AttendeeResponse {

    private Integer eventId;
    private ContactResponse contact;
    private Boolean isOrganizer;

    public static AttendeeResponse fromEntity(Attendee attendee) {
        AttendeeResponse rsp = new AttendeeResponse();
        rsp.setEventId(attendee.getEvent().getId());
        rsp.setContact(ContactResponse.fromEntity(attendee.getContact()));
        rsp.setIsOrganizer(attendee.getIsOrganizer());

        return rsp;
    }
}
