package com.calendar.dto.response;

import com.calendar.entity.Contact;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ContactResponse {
    private Integer id;
    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    private String organization;
    private String notes;
    private LocalDateTime createdAt;

    public static ContactResponse fromEntity(Contact contact) {
        ContactResponse rsp = new ContactResponse();
        rsp.setId(contact.getId());
        rsp.setFirstName(contact.getFirstName());
        rsp.setLastName(contact.getLastName());
        rsp.setEmail(contact.getEmail());
        rsp.setPhone(contact.getPhone());
        rsp.setOrganization(contact.getOrganization());
        rsp.setNotes(contact.getNotes());
        rsp.setCreatedAt(contact.getCreatedAt());

        return rsp;
    }
}
