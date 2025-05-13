package com.calendar.repository;

import com.calendar.entity.Event;
import com.calendar.repository.sql.EventQueries;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface EventRepository extends JpaRepository<Event, Integer> {


    @Query(value = EventQueries.FIND_BY_STATUS, nativeQuery = true)
    List<Event> findByStatus(String status);

    @Query(value = EventQueries.FIND_BY_LOCATION_ID, nativeQuery = true)
    List<Event> findByLocationId(String locationId);

    @Query(value = EventQueries.FIND_BY_DATE_RANGE, nativeQuery = true)
    List<Event> findByDateRange(LocalDateTime start, LocalDateTime end);
}
