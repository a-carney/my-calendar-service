package com.calendar.service;

import com.calendar.model.entity.Location;
import com.calendar.model.repository.LocationRepository;
import com.calendar.service.dto.request.LocationRequest;
import com.calendar.service.dto.response.LocationResponse;
import com.calendar.service.service.LocationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class LocationServiceTest {

    @Mock
    private LocationRepository locationRepository;

    @InjectMocks
    private LocationService locationService;

    private Location location;
    private LocationRequest locationRequest;

    @BeforeEach
    void setUp() {
        location = new Location();
        location.setId(1);
        location.setName("The Building");
        location.setAddress("123 Business St");
        location.setCity("Place");

        locationRequest = new LocationRequest();
        locationRequest.setName("The Building");
        locationRequest.setAddress("123 BusinessSt");
        locationRequest.setCity("Place");
    }

    @Test
    void getAllLocationsTest() {
        when(locationRepository.findAll()).thenReturn(Collections.singletonList(location));

        List<LocationResponse> result = locationService.getAllLocations();

        assertEquals(1, result.size());
        assertEquals("The Building", result.get(0).getName());
    }

    @Test
    void getLocationByIdTest() {
        when(locationRepository.findById(1)).thenReturn(Optional.of(location));

        LocationResponse result = locationService.getLocationById(1);

        assertNotNull(result);
        assertEquals("The Building", result.getName());
    }

    @Test
    void getLocationsByCity() {
        when(locationRepository.findByCity("The Building")).thenReturn(Collections.singletonList(location));

        List<LocationResponse> result = locationService.getLocationsByCity("The Building");

        assertEquals(1, result.size());
        assertEquals("The Building", result.get(0).getName());
    }

    @Test
    void createLocationTest() {
        when(locationRepository.save(any(Location.class))).thenReturn(location);

        LocationResponse result = locationService.createLocation(locationRequest);

        assertNotNull(result);
        assertEquals("The Building", result.getName());
    }
}