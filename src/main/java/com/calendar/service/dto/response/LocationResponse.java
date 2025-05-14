package com.calendar.service.dto.response;

import com.calendar.model.entity.Location;
import lombok.Data;

@Data
public class LocationResponse {
    private Integer id;
    private String name;
    private String address;
    private String city;
    private String postalCode;

    public static LocationResponse fromEntity(Location location) {
        LocationResponse rsp = new LocationResponse();
        rsp.setId(location.getId());
        rsp.setName(location.getName());
        rsp.setAddress(location.getAddress());
        rsp.setCity(location.getCity());
        rsp.setPostalCode(location.getPostalCode());

        return rsp;
    }
}
