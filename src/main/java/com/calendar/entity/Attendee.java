package com.calendar.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Entity
@Table(name = "attendees")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Attendee {

    @EmbeddedId
    private AttendeeId id = new AttendeeId();

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("eventId")
    @JoinColumn(name = "event_id")
    private Event event;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("contactId")
    @JoinColumn(name = "contact_id")
    private Contact contact;

    @Embeddable
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class AttendeeId implements Serializable {
        @Column(name = "event_id")
        private Integer eventId;

        @Column(name = "contact_id")
        private Integer contactId;
    }
}
