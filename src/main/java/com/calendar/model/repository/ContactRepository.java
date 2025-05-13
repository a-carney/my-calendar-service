package com.calendar.model.repository;

import com.calendar.model.entity.Contact;
import com.calendar.model.repository.sql.ContactQueries;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ContactRepository extends JpaRepository<Contact, Integer> {

    @Query(value = ContactQueries.FIND_BY_NAME, nativeQuery = true)
    List<Contact> findByName(String query);

    @Query(value = ContactQueries.FIND_BY_ORGANIZATION, nativeQuery = true)
    List<Contact> findByOrganization(String organization);
}
