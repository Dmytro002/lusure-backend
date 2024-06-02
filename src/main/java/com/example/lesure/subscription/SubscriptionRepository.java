package com.example.lesure.subscription;

import com.example.lesure.pulse.event.models.Event;
import com.example.lesure.subscription.models.Subscription;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SubscriptionRepository extends JpaRepository<Subscription, Long> {

    @Query("SELECT s.event FROM Subscription s WHERE s.user.id = :userId")
    List<Event> findEventsByUserId(Long userId);

    @Query("SELECT COUNT(s) > 0 FROM Subscription s WHERE s.user.id = :userId AND s.event.id = :eventId")
    boolean isUserSubscribedToEvent(@Param("userId") Long userId, @Param("eventId") Long eventId);

    void deleteByEventIdAndUserId(Long subId, Long userId);

}
