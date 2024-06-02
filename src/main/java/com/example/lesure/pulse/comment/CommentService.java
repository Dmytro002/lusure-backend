package com.example.lesure.pulse.comment;

import com.example.lesure.pulse.comment.model.Comment;
import com.example.lesure.pulse.comment.model.CommentDto;
import com.example.lesure.pulse.comment.model.CommentForm;
import com.example.lesure.pulse.event.EventRepository;
import com.example.lesure.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final UserRepository userRepository;
    private final EventRepository eventRepository;

    @Transactional
    public CommentForm comment(CommentDto commentDto, Long userId) {
        Comment comment = new Comment();
        comment.setAuthor(userRepository.getReferenceById(userId));
        comment.setText(commentDto.getText());
        comment.setEvent(eventRepository.getReferenceById(commentDto.getEventId()));
        comment.setCreateAt(LocalDateTime.now());
        Comment save = commentRepository.save(comment);

        return toForm(save);
    }

    @Transactional(readOnly = true)
    public List<CommentForm> getAllCommentByEventId(Long eventId) {
        return commentRepository.findAllByEventId(eventId).stream()
                .map(this::toForm)
                .toList();
    }

    public CommentForm toForm(Comment comment) {
        CommentForm commentForm = new CommentForm();
        commentForm.setText(comment.getText());
        commentForm.setFirstName(comment.getAuthor().getFirstName());
        commentForm.setLastName(comment.getAuthor().getLastName());
        return commentForm;
    }
}
