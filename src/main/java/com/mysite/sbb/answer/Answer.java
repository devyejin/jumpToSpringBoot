package com.mysite.sbb.answer;

import com.mysite.sbb.comment.Comment;
import com.mysite.sbb.user.SiteUser;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

import com.mysite.sbb.question.Question;

@Getter
@Setter
@Entity
public class Answer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(columnDefinition = "TEXT") //글자수 제한X
    private String content;

    private LocalDateTime createDate;

    private  LocalDateTime modifyDate;

    @ManyToOne
    private Question question;

    @ManyToOne
    private SiteUser author;

    @ManyToMany
    Set<SiteUser> voter;

     //하나의 답글에 여러 댓글이 생길 수 있으니까
    //질문글과 달리, 답글이 삭제되도 댓글은 남아도 됨 (질문글 페이지 살아있으니까)
    @OneToMany(mappedBy ="answer")
    private List<Comment> commentList;
}
