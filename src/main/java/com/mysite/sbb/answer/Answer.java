package com.mysite.sbb.answer;

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
    private Question question; //FK (질문 ENTITY 연결)
}
