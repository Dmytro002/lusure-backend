package com.example.lesure.subscription.models;

import com.example.lesure.domain.base.AbstractIdentifiable;
import com.example.lesure.pulse.event.models.Event;
import com.example.lesure.user.model.User;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "subscription")
public class Subscription extends AbstractIdentifiable {

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "event_id")
    private Event event;

}
