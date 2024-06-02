package com.example.lesure.pulse.reaction;

import com.example.lesure.pulse.event.EventRepository;
import com.example.lesure.pulse.reaction.model.Reaction;
import com.example.lesure.pulse.reaction.model.ReactionResponse;
import com.example.lesure.pulse.reaction.model.ReactionType;
import com.example.lesure.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ReactionService {

    private final ReactionRepository reactionRepository;
    private final UserRepository userRepository;
    private final EventRepository eventRepository;

    @Transactional
    public ReactionResponse reaction(Long userId, Long eventId, ReactionType reactionType) {
        Reaction reaction = reactionRepository.findByUserIdAndEventId(userId, eventId)
                .orElse(new Reaction());

        if (reaction.getId() == null) {
            reaction.setUser(userRepository.getReferenceById(userId));
            reaction.setEvent(eventRepository.getReferenceById(eventId));
            reaction.setUpdatedAt(LocalDateTime.now());
        }

        reaction.setReactionType(reactionType);
        reactionRepository.save(reaction);

        int likes = reactionRepository.countByEventIdAndReactionType(eventId, ReactionType.LIKE);
        int dislikes = reactionRepository.countByEventIdAndReactionType(eventId, ReactionType.DISLIKE);

        return new ReactionResponse(likes, dislikes);
    }

    @Transactional(readOnly = true)
    public ReactionResponse getReactionCountsByEventId(Long eventId) {
        List<Object[]> results = reactionRepository.countReactionsByEventId(eventId);
        int likes = 0;
        int dislikes = 0;
        for (Object[] result : results) {
            ReactionType reactionType = (ReactionType) result[0];
            Long count = (Long) result[1];
            if (reactionType == ReactionType.LIKE) {
                likes = count.intValue();
            } else if (reactionType == ReactionType.DISLIKE) {
                dislikes = count.intValue();
            }
        }
        return new ReactionResponse(likes, dislikes);
    }

    @Transactional(readOnly = true)
    public Boolean isUserSubscribedToEvent(Long eventId, Long userId) {
        return reactionRepository.isUserSubscribedToEvent(userId, eventId);
    }

}
