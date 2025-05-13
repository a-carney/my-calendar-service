package com.calendar.view.service;

import com.calendar.view.dto.request.LocationRequest;
import com.calendar.view.dto.response.LocationResponse;
import com.calendar.model.entity.Location;
import com.calendar.exception.CalendarNotFoundException;
import com.calendar.model.repository.LocationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class LocationService {
    private final LocationRepository repo;

    private static final String LOCATION_NOT_FOUND = "location no found";

    public List<LocationResponse> getAllLocations() {
        return repo
                .findAll()
                .stream()
                .map(LocationResponse::fromEntity)
                .collect(Collectors.toList());
    }

    public LocationResponse getLocationById(Integer id) {
        Location location = repo
                            .findById(id)
                            .orElseThrow(() -> new CalendarNotFoundException(LOCATION_NOT_FOUND));
        return LocationResponse.fromEntity(location);
    }

    public List<LocationResponse> getLocationsByCity(String city) {
        return repo.findByCity(city).stream()
                .map(LocationResponse::fromEntity)
                .collect(Collectors.toList());
    }

    @Transactional
    public LocationResponse createLocation(LocationRequest request) {
        Location location = new Location();
        updateLocationFromRequest(location, request);
        Location savedLocation = repo.save(location);
        return LocationResponse.fromEntity(savedLocation);
    }

    @Transactional
    public LocationResponse updateLocation(Integer id, LocationRequest request) {
        Location location = repo.findById(id)
                .orElseThrow(() -> new CalendarNotFoundException(LOCATION_NOT_FOUND));

        updateLocationFromRequest(location, request);
        Location updatedLocation = repo.save(location);
        return LocationResponse.fromEntity(updatedLocation);
    }

    @Transactional
    public void deleteLocation(Integer id) {
        if (!repo.existsById(id)) {
            throw new CalendarNotFoundException(LOCATION_NOT_FOUND);
        }
        repo.deleteById(id);
    }

    private void updateLocationFromRequest(Location location, LocationRequest request) {
        location.setName(request.getName());
        location.setAddress(request.getAddress());
        location.setCity(request.getCity());
        location.setPostalCode(request.getPostalCode());
    }
}