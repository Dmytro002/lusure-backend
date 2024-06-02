package com.example.lesure.pulse.reaction;

import com.example.lesure.pulse.reaction.model.Reaction;
import com.example.lesure.pulse.reaction.model.ReactionType;
import jdk.jfr.Registered;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

@Registered
public interface ReactionRepository extends JpaRepository<Reaction, Long> {

    @Query("SELECT r FROM Reaction r WHERE r.user.id = :userId AND r.event.id = :eventId")
    Optional<Reaction> findByUserIdAndEventId(@Param("userId") Long userId, @Param("eventId") Long eventId);

    int countByEventIdAndReactionType(Long eventId, ReactionType reactionType);

    @Query("SELECT r.reactionType, COUNT(r) FROM Reaction r WHERE r.event.id = :eventId GROUP BY r.reactionType")
    List<Object[]> countReactionsByEventId(@Param("eventId") Long eventId);

    @Query("SELECT COUNT(s) > 0 FROM Subscription s WHERE s.user.id = :userId AND s.event.id = :eventId")
    boolean isUserSubscribedToEvent(@Param("userId") Long userId, @Param("eventId") Long eventId);

}
