package com.example.lesure.pulse.comment;

import com.example.lesure.user.model.User;
import com.example.lesure.pulse.comment.model.CommentDto;
import com.example.lesure.pulse.comment.model.CommentForm;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/comment")
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    @PostMapping
    public CommentForm createComment(@RequestBody CommentDto comment, Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        return commentService.comment(comment, user.getId());
    }
}
