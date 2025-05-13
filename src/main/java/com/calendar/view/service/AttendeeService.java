package com.calendar.view.service;

import com.calendar.view.dto.request.AttendeeRequest;
import com.calendar.view.dto.response.AttendeeResponse;
import com.calendar.model.entity.Attendee;
import com.calendar.model.entity.Contact;
import com.calendar.model.entity.Event;
import com.calendar.exception.CalendarBadRequestException;
import com.calendar.exception.CalendarNotFoundException;
import com.calendar.model.repository.AttendeeRepository;
import com.calendar.model.repository.ContactRepository;
import com.calendar.model.repository.EventRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AttendeeService {
    private static final String EVENT_NOT_FOUND = "event not found";
    private static final String ATTENDEE_ALREADY_EXISTS = "contact is already an attendee for this event";
    private static final String ATTENDEE_NOT_FOUND = "attendee not found for given event";

    private final AttendeeRepository attendeeRepo;
    private final EventRepository eventRepo;
    private final ContactRepository contactRepo;

    public List<AttendeeResponse> getAttendeesByEventId(Integer id) {
        if (!eventRepo.existsById(id)) {
            throw new CalendarNotFoundException(EVENT_NOT_FOUND);
        }

        return attendeeRepo
                .findByEventId(id)
                .stream()
                .map(AttendeeResponse::fromEntity)
                .collect(Collectors.toList());
    }

    public List<AttendeeResponse> getEventOrganizers(Integer id) {
        if (!eventRepo.existsById(id)) {
            throw new CalendarNotFoundException(EVENT_NOT_FOUND);
        }

        return attendeeRepo
                .findOrganizersByEventId(id)
                .stream()
                .map(AttendeeResponse::fromEntity)
                .collect(Collectors.toList());
    }

    @Transactional
    public AttendeeResponse addAttendee(Integer eventId, AttendeeRequest request) {
        Event event = eventRepo
                .findById(eventId)
                .orElseThrow(() -> new CalendarNotFoundException(EVENT_NOT_FOUND));
        Contact contact = contactRepo
                .findById(request.getContactId())
                .orElseThrow(() -> new CalendarNotFoundException(EVENT_NOT_FOUND));

        attendeeRepo
                .findByEventIdAndContactId(eventId, request.getContactId())
                .ifPresent( a-> { throw new CalendarBadRequestException(ATTENDEE_ALREADY_EXISTS); });

        Attendee attendee = new Attendee();
        attendee.setId(new Attendee.AttendeeId(eventId, request.getContactId()));
        attendee.setEvent(event);
        attendee.setContact(contact);
        attendee.setIsOrganizer(request.getIsOrganizer());

        Attendee saved = attendeeRepo.save(attendee);
        return AttendeeResponse.fromEntity(saved);
    }

    @Transactional
    public AttendeeResponse updateAttendeeStatus(Integer eventId, Integer contactId, Boolean isOrganizer) {
        Attendee attendee = attendeeRepo
                .findByEventIdAndContactId(eventId, contactId)
                .orElseThrow(() -> new CalendarNotFoundException(ATTENDEE_NOT_FOUND));
        attendee.setIsOrganizer(isOrganizer);

        Attendee updated = attendeeRepo.save(attendee);
        return AttendeeResponse.fromEntity(updated);
    }

    @Transactional
    public void removeAttendee(Integer eventId, Integer contactId) {
        Attendee.AttendeeId id = new Attendee.AttendeeId(eventId, contactId);
        if (!attendeeRepo.existsById(id)) {
            throw new CalendarNotFoundException(ATTENDEE_NOT_FOUND);
        }
        attendeeRepo.deleteById(id);
    }
}
