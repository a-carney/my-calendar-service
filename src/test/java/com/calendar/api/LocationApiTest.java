package com.calendar.api;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@SpringBootTest
@AutoConfigureMockMvc
public class LocationApiTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void getLocationsByCity() throws Exception {
        mockMvc.perform(get("/api/locations/by-city?city=New+Place"));
    }

    @Test
    void createLocation() throws Exception {
        String json = """
            {
                "name": "The Building",
                "city": "New Place"
            }""";

        mockMvc.perform(post("/api/locations")
                        .contentType("application/json")
                        .content(json))
                .andExpect(status().isCreated());
    }
}