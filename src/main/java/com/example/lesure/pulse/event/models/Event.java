package com.example.lesure.pulse.event.models;

import com.example.lesure.domain.base.AbstractVersionalIdentifiable;
import com.example.lesure.domain.constants.EventType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@Entity
@Table(name = "events")
public class Event extends AbstractVersionalIdentifiable {

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "location")
    private String location;

    private String imageUrl;

    @Enumerated(EnumType.STRING)
    @Column(name = "event_type")
    private EventType eventType;

    @Column(name = "date")
    private LocalDate date;

    @Column(name = "venue_capacity")
    private Integer venueCapacity;

    @Column(name = "price")
    private Double price;
}
