package com.calendar.service;

import com.calendar.config.TestSecurityConfig;
import com.calendar.model.entity.Contact;
import com.calendar.model.repository.ContactRepository;
import com.calendar.service.dto.request.ContactRequest;
import com.calendar.service.dto.response.ContactResponse;
import com.calendar.service.service.ContactService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.annotation.Import;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ContactServiceTest {

    @Mock
    private ContactRepository contactRepository;

    @InjectMocks
    private ContactService contactService;

    private Contact contact;
    private ContactRequest contactRequest;



    @BeforeEach
    void setUp() {
        contact = new Contact();
        contact.setId(1);
        contact.setFirstName("First");
        contact.setLastName("Last");
        contact.setEmail("first.last@example.com");

        contactRequest = new ContactRequest();
        contactRequest.setFirstName("First");
        contactRequest.setLastName("Last");
        contactRequest.setEmail("first.last@example.com");
    }

    @Test
    void getAllContactsTest() {
        when(contactRepository.findAll()).thenReturn(Collections.singletonList(contact));

        List<ContactResponse> result = contactService.getAllContacts();

        assertEquals(1, result.size());
        assertEquals("First", result.get(0).getFirstName());
        verify(contactRepository, times(1)).findAll();
    }

    @Test
    void getContactByIdTest() {
        when(contactRepository.findById(1)).thenReturn(Optional.of(contact));

        ContactResponse result = contactService.getContactById(1);

        assertNotNull(result);
        assertEquals("First", result.getFirstName());
        verify(contactRepository, times(1)).findById(1);
    }

    @Test
    void createContactTest() {
        when(contactRepository.save(any(Contact.class))).thenReturn(contact);

        ContactResponse result = contactService.createContact(contactRequest);

        assertNotNull(result);
        assertEquals("First", result.getFirstName());
        verify(contactRepository, times(1)).save(any(Contact.class));
    }
}