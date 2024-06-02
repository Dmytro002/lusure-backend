package com.example.lesure.pulse.event;


import com.example.lesure.domain.constants.EventType;
import com.example.lesure.pulse.event.models.Event;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EventRepository extends JpaRepository<Event, Long> {

    Page<Event> findAllByEventType(EventType eventType, Pageable pageable);

    List<Event> findByNameContainingIgnoreCase(String name);

    @Query("SELECT e FROM Event e WHERE MONTH(e.date) = :month AND YEAR(e.date) = :year")
    List<Event> findByMonthAndYear(@Param("month") int month, @Param("year") int year);


}
