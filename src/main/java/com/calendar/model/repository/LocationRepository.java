package com.calendar.model.repository;

import com.calendar.model.entity.Location;
import com.calendar.model.repository.sql.LocationQueries;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LocationRepository extends JpaRepository<Location, Integer> {

    @Query(value = LocationQueries.FIND_BY_CITY, nativeQuery = true)
    List<Location> findByCity(String city);
}
