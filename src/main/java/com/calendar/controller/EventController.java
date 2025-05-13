package com.calendar.controller;

import com.calendar.view.dto.request.EventRequest;
import com.calendar.view.dto.response.EventResponse;
import com.calendar.model.entity.Event;
import com.calendar.view.service.EventService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/events")
@RequiredArgsConstructor
public class EventController {

    private final EventService service;

    @GetMapping
    public ResponseEntity<List<EventResponse>> getAllEvents() {
        return ResponseEntity.ok(service.getAllEvents());
    }

    @GetMapping("/{id}")
    public ResponseEntity<EventResponse> getEventById(@PathVariable Integer id) {
        return ResponseEntity.ok(service.getEventById(id));
    }

    @GetMapping("/by-status")
    public ResponseEntity<List<EventResponse>> getEventByStatus(@RequestParam Event.EventStatus status) {
        return ResponseEntity.ok(service.getEventByStatus(status));
    }

    @GetMapping("/by-date-range")
    public ResponseEntity<List<EventResponse>> getEventsByDateRange(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime start,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime end) {
        return ResponseEntity.ok(service.getEventsByDateRange(start, end));
    }

    @PostMapping
    public ResponseEntity<EventResponse> createEvent(@Valid @RequestBody EventRequest request) {
        return new ResponseEntity<>(service.createEvent(request), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<EventResponse> updateEvent(
            @PathVariable Integer id,
            @Valid @RequestBody EventRequest request) {
       return ResponseEntity.ok(service.updateEvent(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEvent(@PathVariable Integer id) {
        service.deleteEvent(id);
        return ResponseEntity.noContent().build();
    }
}
