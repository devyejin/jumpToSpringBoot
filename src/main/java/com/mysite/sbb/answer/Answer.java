package com.mysite.sbb.answer;

import com.mysite.sbb.user.SiteUser;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
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

    @ManyToOne
    private Question question; //

    @ManyToOne
    private SiteUser author; //한명의 사용자가 여러 댓글 가능
}
