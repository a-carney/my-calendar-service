package com.calendar.api;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class ContactApiTest {

    @Autowired
    private MockMvc mock;

    @Test
    void getAllContacts() throws Exception {
        mock.perform(get("/api/contacts"))
                .andExpect(status().isOk());
    }

    @Test
    void getContactById() throws Exception {
        mock.perform(get("/api/contacts/1"))
                .andExpect(status().isOk());
    }

    @Test
    void searchContactsByName() throws Exception {
        mock.perform(get("/api/contacts/search?name=Alex"))
                .andExpect(status().isOk());
    }

    @Test
    void createContact() throws Exception {
        String json = """
            {
                "firstName": "Alex",
                "lastName": "Carney",
                "email": "alex.carney@example.com"
            }""";

        mock.perform(post("/api/contacts")
                        .contentType("application/json")
                        .content(json))
                .andExpect(status().isCreated());
    }

}
