package com.calendar.repository.sql;

import org.springframework.stereotype.Repository;

@Repository
public class AttendeeQueries {

    public static final String FIND_BY_EVENT_ID = """
            SELECT
                *
            FROM
                attendees
            WHERE
                event_id = ?1
            """;

    public static final String FIND_BY_CONTACT_ID = """
            SELECT
                *
            FROM
                attendees
            WHERE
                contact_id = ?1
            """;

    public static final String FIND_ORGANIZERS_BY_EVENT_ID = """
            SELECT
                *
            FROM
                attendees
            WHERE
                    event_id = ?1
                AND is_organizer = true
            """;

    public static final String FIND_BY_EVENT_ID_AND_CONTACT_ID = """
            SELECT
                *
            FROM
                attendees
            WHERE
                    event_id = ?1
                AND contact_id = ?2
            """;
}
