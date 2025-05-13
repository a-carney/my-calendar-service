package com.calendar.repository;

import com.calendar.entity.Attendee;
import com.calendar.repository.sql.AttendeeQueries;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AttendeeRepository extends JpaRepository<Attendee, Attendee.AttendeeId> {

    @Query(value = AttendeeQueries.FIND_BY_EVENT_ID, nativeQuery = true)
    List<Attendee> findByEventId(Integer id);

    @Query(value = AttendeeQueries.FIND_BY_CONTACT_ID, nativeQuery = true)
    List<Attendee> findByContactId(Integer id);

    @Query(value = AttendeeQueries.FIND_ORGANIZERS_BY_EVENT_ID, nativeQuery = true)
    List<Attendee> findOrganizersByEventId(Integer id);

    @Query(value = AttendeeQueries.FIND_BY_EVENT_ID_AND_CONTACT_ID, nativeQuery = true)
    Optional<Attendee> findByEventIdAndContactId(Integer eventId, Integer contactId);

}
