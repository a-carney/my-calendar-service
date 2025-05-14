package com.calendar.controller;

import com.calendar.service.dto.request.ContactRequest;
import com.calendar.service.dto.response.ContactResponse;
import com.calendar.service.service.ContactService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/contacts")
@RequiredArgsConstructor
public class ContactController {

    private final ContactService service;

    @GetMapping
    public ResponseEntity<List<ContactResponse>> getAllContacts() {
        return ResponseEntity.ok(service.getAllContacts());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ContactResponse> getContactById(@PathVariable Integer id) {
        return ResponseEntity.ok(service.getContactById(id));
    }

    @GetMapping("/search")
    public ResponseEntity<List<ContactResponse>> searchContacts(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String organization) {

        if (name != null) {
            return ResponseEntity.ok(service.searchContactsByName(name));
        } else if (organization != null) {
            return ResponseEntity.ok(service.searchContactsByOrganization(organization));
        } else {
            return ResponseEntity.ok(service.getAllContacts());
        }
    }

    @PostMapping
    public ResponseEntity<ContactResponse> createContact(@Valid @RequestBody ContactRequest request) {
        return new ResponseEntity<>(service.createContact(request), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ContactResponse> updateContact(
            @PathVariable Integer id,
            @Valid @RequestBody ContactRequest request) {
        return ResponseEntity.ok(service.updateContact(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteContact(@PathVariable Integer id) {
        service.deleteContact(id);
        return ResponseEntity.noContent().build();
    }
}
