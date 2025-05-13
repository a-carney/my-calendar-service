package com.calendar.view.service;

import com.calendar.view.dto.request.ContactRequest;
import com.calendar.view.dto.response.ContactResponse;
import com.calendar.model.entity.Contact;
import com.calendar.exception.CalendarNotFoundException;
import com.calendar.model.repository.ContactRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ContactService {

    private final ContactRepository repo;

    public List<ContactResponse> getAllContacts() {
        return repo
                .findAll()
                .stream()
                .map(ContactResponse::fromEntity)
                .collect(Collectors.toList());
    }

    public ContactResponse getContactById(Integer id) {
        return ContactResponse.fromEntity(repo
                .findById(id)
                .orElseThrow(() -> new CalendarNotFoundException("contact not found"))
        );
    }

    public List<ContactResponse> searchContactsByName(String query) {
        return repo
                .findByName(query)
                .stream()
                .map(ContactResponse::fromEntity)
                .collect(Collectors.toList());
    }

    public List<ContactResponse> searchContactsByOrganization(String organization) {
        return repo
                .findByOrganization(organization)
                .stream()
                .map(ContactResponse::fromEntity)
                .collect(Collectors.toList());
    }

    @Transactional
    public ContactResponse createContact(ContactRequest request) {
        Contact contact = new Contact();
        updateContactFromRequest(contact, request);
        Contact saved = repo.save(contact);

        return ContactResponse.fromEntity(saved);
    }

    @Transactional
    public ContactResponse updateContact(Integer id, ContactRequest request) {
        Contact contact = repo.findById(id)
                .orElseThrow(() -> new CalendarNotFoundException("contact not found"));
        updateContactFromRequest(contact, request);
        Contact updated = repo.save(contact);

        return ContactResponse.fromEntity(updated);
    }

    @Transactional
    public void deleteContact(Integer id) {
        if (!repo.existsById(id)) {
            throw new CalendarNotFoundException("contact not found");
        }
        repo.deleteById(id);
    }


    private void updateContactFromRequest(Contact contact, ContactRequest request) {
        contact.setFirstName(request.getFirstName());
        contact.setLastName(request.getLastName());
        contact.setEmail(request.getEmail());
        contact.setPhone(request.getPhone());
        contact.setOrganization(request.getOrganization());
        contact.setNotes(request.getNotes());
    }
}
