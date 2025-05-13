package com.calendar.controller;

import com.calendar.dto.request.LocationRequest;
import com.calendar.dto.response.LocationResponse;
import com.calendar.service.LocationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/locations")
@RequiredArgsConstructor
public class LocationController {

    private final LocationService service;

    @GetMapping
    public ResponseEntity<List<LocationResponse>> getAllLocations() {
        return ResponseEntity.ok(service.getAllLocations());
    }

    @GetMapping("/{id}")
    public ResponseEntity<LocationResponse> getLocationById(@PathVariable Integer id) {
        return ResponseEntity.ok(service.getLocationById(id));
    }

    @GetMapping("/by-city")
    public ResponseEntity<List<LocationResponse>> getLocationsByCity(@RequestParam String city) {
        return ResponseEntity.ok(service.getLocationsByCity(city));
    }

    @PostMapping
    public ResponseEntity<LocationResponse> createLocation(@Valid @RequestBody LocationRequest request) {
        return new ResponseEntity<>(service.createLocation(request), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<LocationResponse> updateLocation(
            @PathVariable Integer id,
            @Valid @RequestBody LocationRequest request) {
        return ResponseEntity.ok(service.updateLocation(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteLocation(@PathVariable Integer id) {
        service.deleteLocation(id);
        return ResponseEntity.noContent().build();
    }
}
