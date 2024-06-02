package com.example.lesure.pulse.event.models;

import com.example.lesure.domain.constants.EventType;
import com.example.lesure.pulse.comment.model.CommentForm;
import com.example.lesure.pulse.reaction.model.ReactionResponse;
import lombok.Getter;
import lombok.Setter;


import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
public class EventDto {
    private Long id;
    private Double   price ;
    private String name;
    private String description;
    private String location;
    private LocalDate date;
    private EventType eventType;
    private String imageUrl;
    private List<CommentForm> comments;
    private ReactionResponse reactionResponse;

}
