package com.example.lesure.pulse.reaction.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReactionRequest {
    private Long eventId;
    private ReactionType reaction;
}
