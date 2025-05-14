package com.calendar.service.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class LocationRequest {

    @NotBlank(message = "location name is required")
    @Size(max = 100, message = "location name cannot exceed 100 chars")
    private String name;

    @Size(max = 255, message = "address cannot exceed 255 chars")
    private String address;

    @Size(max = 100, message = "city name cannot exceed 100 chars")
    private String city;

    @Size(max = 20, message = "postal code cannot exceed 20 chars")
    private String postalCode;
}
