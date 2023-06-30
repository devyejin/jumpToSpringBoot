package com.mysite.sbb.comment;

import com.mysite.sbb.question.Question;
import com.mysite.sbb.user.SiteUser;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class CommentRepositoryTest {

//    @AfterEach
//    void afterEach() {
//
//    }

    @Autowired
    CommentRepository commentRepository;

    //이래서 테스트 서버를 따로 만드는구나..ㅇㅎ

    @Test
    void save() {
        //given
        Comment comment = new Comment();
        comment.setContent("테스트 댓글 컨텐츠");
        comment.setAuthor(new SiteUser());
        comment.setQuestion(new Question());
        

        //when
        Comment savedComment = commentRepository.save(comment);
        
        //then
        Optional<Comment> findItemOp = commentRepository.findById(comment.getId());
        if(!findItemOp.isEmpty()) {
            Comment findComment = findItemOp.get();
            Assertions.assertThat(findComment).isEqualTo(savedComment);
        }



    }

}