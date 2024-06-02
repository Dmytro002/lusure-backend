package com.example.lesure.pulse.reaction;

import com.example.lesure.user.model.User;
import com.example.lesure.pulse.reaction.model.ReactionRequest;
import com.example.lesure.pulse.reaction.model.ReactionResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/reaction")
@RequiredArgsConstructor
public class ReactionController {

    private final ReactionService reactionService;

    @PostMapping
    public ReactionResponse reaction(@RequestBody ReactionRequest request, Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        return reactionService.reaction(user.getId(), request.getEventId(), request.getReaction());
    }

    @GetMapping
    public Boolean reaction(@RequestParam Long eventId, Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        return reactionService.isUserSubscribedToEvent(eventId, user.getId());
    }

}
