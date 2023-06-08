package com.mysite.sbb;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;


@Setter
@Getter
@Entity
public class Question {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(length=200)
    private String subject;
    @Column(columnDefinition = "TEXT")
    private String content;
    private LocalDateTime createDate;

    //질문은 질문하나당 여러개의 답변을 가질 수 있으므로 컬렉션으로
    @OneToMany(mappedBy = "question", cascade = CascadeType.REMOVE)
    private List<Answer> answerList; //DB테이블에는  answerList 데이터가 없는데, 자바 객체(Entity)로 변환하고 나면 알아서 맵핑이 됨 , 이게 하이버네이트 기술?!


}
