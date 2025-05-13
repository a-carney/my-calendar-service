package com.calendar.service;

import com.calendar.dto.response.ContactResponse;
import com.calendar.entity.Contact;
import com.calendar.repository.ContactRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ContactServiceTest {

    @Mock
    private ContactRepository repo;

    @InjectMocks
    private ContactService service;

    private Contact getContactAlex() {
        Contact c = new Contact();
        c.setId(1);
        c.setFirstName("Alex");
        c.setLastName("Carney");

        return c;
    }

    private Contact getContactBob() {
        Contact c = new Contact();
        c.setId(1);
        c.setFirstName("Bob");
        c.setLastName("Smith");

        return c;
    }


    @Test
    void getAllContactsTest() {
        Contact c1 = getContactAlex();
        Contact c2 = getContactBob();

        when(repo.findAll()).thenReturn(Arrays.asList(c1, c2));

        List<ContactResponse> result = service.getAllContacts();

        assertEquals(2, result.size());
        assertEquals("Alex", result.get(0).getFirstName());
        assertEquals("Bob", result.get(1).getFirstName());
    }

    @Test
    void getContactByIdTest() {
        Contact c = getContactAlex();

        when(repo.findById(1)).thenReturn(Optional.of(c));
        ContactResponse result = service.getContactById(1);

        assertEquals("Alex", result.getFirstName());
        assertEquals("Carney", result.getLastName());
    }

}
