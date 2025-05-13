package com.calendar.view.service;

import com.calendar.view.dto.request.EventRequest;
import com.calendar.view.dto.response.EventResponse;
import com.calendar.model.entity.Event;
import com.calendar.model.entity.Location;
import com.calendar.exception.CalendarBadRequestException;
import com.calendar.exception.CalendarNotFoundException;
import com.calendar.model.repository.EventRepository;
import com.calendar.model.repository.LocationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EventService {

    private static final String EVENT_NOT_FOUND = "event not found";
    private static final String INVALID_DATE = "start date must be before end date";
    private static final String LOCATION_NOT_FOUND = "location not found";


    private final EventRepository eventRepo;
    private final LocationRepository locationRepo;

    public List<EventResponse> getAllEvents() {
        return eventRepo
                .findAll()
                .stream()
                .map(EventResponse::fromEntity)
                .collect(Collectors.toList());
    }

    public EventResponse getEventById(Integer id) {
        Event event = eventRepo
                .findById(id)
                .orElseThrow(() -> new CalendarNotFoundException(EVENT_NOT_FOUND));

        return EventResponse.fromEntity(event);
    }

    public List<EventResponse> getEventByStatus(Event.EventStatus status) {
        return eventRepo
                .findByStatus(status.toString())
                .stream()
                .map(EventResponse::fromEntity)
                .collect(Collectors.toList());
    }

    public List<EventResponse> getEventsByDateRange(LocalDateTime start, LocalDateTime end) {
        if (start.isAfter(end)) throw new CalendarNotFoundException(INVALID_DATE);

        return eventRepo
                .findByDateRange(start, end)
                .stream()
                .map(EventResponse::fromEntity)
                .collect(Collectors.toList());
    }

    @Transactional
    public EventResponse createEvent(EventRequest request) {
        validateEventDates(request);

        Event event = new Event();
        updateEventFromRequest(event, request);
        Event saved = eventRepo.save(event);
        return EventResponse.fromEntity(saved);
    }

    @Transactional
    public EventResponse updateEvent(Integer id, EventRequest request) {
        validateEventDates(request);

        Event event = eventRepo
                .findById(id)
                .orElseThrow(() -> new CalendarNotFoundException(EVENT_NOT_FOUND));

        updateEventFromRequest(event, request);
        Event updated = eventRepo.save(event);
        return EventResponse.fromEntity(updated);
    }

    @Transactional
    public void deleteEvent(Integer id) {
        if (!eventRepo.existsById(id)) throw new CalendarNotFoundException(EVENT_NOT_FOUND);

        eventRepo.deleteById(id);
    }

    private void updateEventFromRequest(Event event, EventRequest request) {
        event.setTitle(request.getTitle());
        event.setDescription(request.getDescription());
        event.setStartDate(request.getStartDate());
        event.setEndDate(request.getEndDate());
        event.setAllDay(request.getAllDay());
        event.setStatus(request.getStatus());

        if (request.getLocationId() != null) {
            Location location = locationRepo
                    .findById(request.getLocationId())
                    .orElseThrow(() -> new CalendarNotFoundException(LOCATION_NOT_FOUND));

            event.setLocation(location);
        } else {
            event.setLocation(null);
        }
    }

    private void validateEventDates(EventRequest request) {
        if (request.getStartDate().isAfter(request.getEndDate())) {
            throw new CalendarBadRequestException(INVALID_DATE);
        }
    }
}
