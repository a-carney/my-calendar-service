package com.calendar.model.repository.sql;

public class LocationQueries {

    public static final String FIND_BY_CITY = """
            SELECT
                *
            FROM
                locations
            WHERE
                LOWER(city) = LOWER(?1)
            """;
}
