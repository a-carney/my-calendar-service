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
public class EventApiTest {

    @Autowired
    private MockMvc mock;

    @Test
    void getEventsByStatus() throws Exception {
        mock.perform(get("/api/events/by-status?status=CONFIRMED"))
                .andExpect(status().isOk());
    }

    @Test
    void createEvent() throws Exception {
        String json = """
            {
                "title": "Meeting",
                "status": "CONFIRMED",
                "locationId": 1
            }""";

        mock.perform(post("/api/events")
                        .contentType("application/json")
                        .content(json))
                .andExpect(status().isCreated());
    }
}