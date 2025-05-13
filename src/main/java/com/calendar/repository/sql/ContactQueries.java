package com.calendar.repository.sql;

public class ContactQueries {
    public static final String FIND_BY_NAME = """
            SELECT
                *
            FROM
                contacts
            WHERE
                    LOWER(first_name) LIKE LOWER(CONCAT('%', ?1, '%'))
                OR  LOWER(last_name) LIKE LOWER(CONCAT('%', ?2, '%'))
            """;

    public static final String FIND_BY_ORGANIZATION = """
            SELECT
                *
            FROM
                contacts
            WHERE
                LOWER(organization) LIKE LOWER(CONCAT('%', ?1, '%'))
            """;
}
