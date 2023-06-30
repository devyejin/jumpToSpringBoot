package com.mysite.sbb.comment;

import com.mysite.sbb.question.Question;
import com.mysite.sbb.user.SiteUser;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@RequiredArgsConstructor
@Service
public class CommentService {

    private final CommentRepository commentRepository;


    public void create(Question question, SiteUser siteUser, String content) {

        Comment comment = new Comment();
        comment.setQuestion(question);
        comment.setContent(content);
        comment.setAuthor(siteUser);
        comment.setCreateDate(LocalDateTime.now());
        this.commentRepository.save(comment);


    }
}
