package com.calendar.controller;

import com.calendar.service.dto.request.AttendeeRequest;
import com.calendar.service.dto.response.AttendeeResponse;
import com.calendar.service.service.AttendeeService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/events/{eventId}/attendees")
@RequiredArgsConstructor
public class AttendeeController {

    private final AttendeeService service;


    @GetMapping
    public ResponseEntity<List<AttendeeResponse>> getEventAttendees(@PathVariable Integer eventId) {
        return ResponseEntity.ok(service.getAttendeesByEventId(eventId));
    }

    @GetMapping("/organizers")
    public ResponseEntity<List<AttendeeResponse>> getEventOrganizers(@PathVariable Integer eventId) {
        return ResponseEntity.ok(service.getEventOrganizers(eventId));
    }

    @PostMapping
    public ResponseEntity<AttendeeResponse> addAttendee(
            @PathVariable Integer eventId,
            @Valid @RequestBody AttendeeRequest request) {
        return new ResponseEntity<>(service.addAttendee(eventId, request), HttpStatus.CREATED);
    }

    @PutMapping("/{contactId}")
    public ResponseEntity<AttendeeResponse> updateAttendeeStatus(
            @PathVariable Integer eventId,
            @PathVariable Integer contactId,
            @RequestParam Boolean isOrganizer) {
        return ResponseEntity.ok(service.updateAttendeeStatus(eventId, contactId, isOrganizer));
    }

    @DeleteMapping("/{contactId}")
    public ResponseEntity<Void> removeAttendee(
            @PathVariable Integer eventId,
            @PathVariable Integer contactId) {
        service.removeAttendee(eventId, contactId);

        return ResponseEntity.noContent().build();
    }
}
