package com.calendar.repository;

import com.calendar.entity.Contact;
import com.calendar.entity.Location;
import com.calendar.repository.sql.LocationQueries;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LocationRepository extends JpaRepository<Contact, Integer> {

    @Query(value = LocationQueries.FIND_BY_CITY, nativeQuery = true)
    List<Location> findByCity(String city);
}
