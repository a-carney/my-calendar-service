package com.calendar.repository;

import com.calendar.entity.Contact;
import com.calendar.repository.sql.ContactQueries;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ContactRepository extends JpaRepository<Contact, Integer> {

    @Query(value = ContactQueries.FIND_BY_NAME, nativeQuery = true)
    List<Contact> findByName(String query);

    @Query(value = ContactQueries.FIND_BY_ORGANIZATION, nativeQuery = true)
    List<Contact> findByOrganization(String organization);
}
