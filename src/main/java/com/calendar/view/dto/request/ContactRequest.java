package com.calendar.view.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class ContactRequest {

    @NotBlank(message = "first name required")
    @Size(max = 50, message = "first name cannot exceed 50 chars")
    private String firstName;

    @NotBlank(message = "last name required")
    @Size(max = 50, message = "last name cannot exceed 50 chars")
    private String lastName;

    @Email(message = "invalid email address")
    @Size(max = 100, message = "email cannot exceed 100 chars")
    private String email;

    @Size(max = 20, message = "phone number cannot exceed 20 chars")
    private String phone;

    @Size(max = 100, message = "organization name cannot exceed 100 chars")
    private String organization;

    private String notes;
}
