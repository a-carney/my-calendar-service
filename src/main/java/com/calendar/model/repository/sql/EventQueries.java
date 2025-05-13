package com.calendar.model.repository.sql;

public class EventQueries {
    public static final String FIND_BY_STATUS = """
            SELECT
                *
            FROM
                events
            WHERE
                status = ?1
            """;

    public static final String FIND_BY_LOCATION_ID = """
            SELECT
                *
            FROM
                events
            WHERE
                location_id = ?1
            """;

    public static final String FIND_BY_DATE_RANGE = """
            SELECT
                *
            FROM
                events
            WHERE
                    start_datetime >= ?1
                AND end_datetime <= ?2
            """;
}
