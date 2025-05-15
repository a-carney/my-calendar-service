package com.calendar.service;

import com.calendar.config.TestSecurityConfig;
import com.calendar.exception.CalendarBadRequestException;
import com.calendar.model.entity.Event;
import com.calendar.model.entity.Location;
import com.calendar.model.repository.EventRepository;
import com.calendar.model.repository.LocationRepository;
import com.calendar.service.dto.request.EventRequest;
import com.calendar.service.dto.response.EventResponse;
import com.calendar.service.service.EventService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.annotation.Import;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class EventServiceTest {

    @Mock
    private EventRepository eventRepository;

    @Mock
    private LocationRepository locationRepository;

    @InjectMocks
    private EventService eventService;

    private Event event;
    private EventRequest eventRequest;
    private Location location;
    private LocalDateTime startTime;
    private LocalDateTime endTime;

    @BeforeEach
    void setUp() {
        startTime = LocalDateTime.now();
        endTime = startTime.plusHours(2);

        location = new Location();
        location.setId(1);
        location.setName("The Building");

        event = new Event();
        event.setId(1);
        event.setTitle("Event");
        event.setDescription("Description");
        event.setStartDate(startTime);
        event.setEndDate(endTime);
        event.setLocation(location);

        eventRequest = new EventRequest();
        eventRequest.setTitle("Event");
        eventRequest.setDescription("Description");
        eventRequest.setStartDate(startTime);
        eventRequest.setEndDate(endTime);
        eventRequest.setLocationId(1);
    }

    @Test
    void getAllEventsTest() {
        when(eventRepository.findAll()).thenReturn(Collections.singletonList(event));

        List<EventResponse> result = eventService.getAllEvents();

        assertEquals(1, result.size());
        assertEquals("Event", result.get(0).getTitle());
    }

    @Test
    void getEventByIdTest() {
        when(eventRepository.findById(1)).thenReturn(Optional.of(event));

        EventResponse result = eventService.getEventById(1);

        assertNotNull(result);
        assertEquals("Event", result.getTitle());
    }

    @Test
    void createEventTest() {
        when(locationRepository.findById(1)).thenReturn(Optional.of(location));
        when(eventRepository.save(any(Event.class))).thenReturn(event);

        EventResponse result = eventService.createEvent(eventRequest);

        assertNotNull(result);
        assertEquals("Event", result.getTitle());
    }

    @Test
    void createEventWithEndDateBeforeStartDate() {
        EventRequest invalidRequest = new EventRequest();
        invalidRequest.setTitle("Invalid Meeting");
        invalidRequest.setStartDate(endTime);
        invalidRequest.setEndDate(startTime);

        assertThrows(CalendarBadRequestException.class, () -> eventService.createEvent(invalidRequest));
    }
}