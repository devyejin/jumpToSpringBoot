package com.mysite.sbb.question;

import com.mysite.sbb.answer.Answer;
import com.mysite.sbb.user.SiteUser;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;


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

    private LocalDateTime modifyDate;

    @ManyToOne
    private SiteUser author; //Entity관계를 생각하면, 한명의 사용자가 여러 질문가능

    //질문은 질문하나당 여러개의 답변을 가질 수 있으므로 컬렉션으로
    @OneToMany(mappedBy = "question", cascade = CascadeType.REMOVE)
    private List<Answer> answerList; //DB테이블에는  answerList 데이터가 없는데, 자바 객체(Entity)로 변환하고 나면 알아서 맵핑이 됨 , 이게 하이버네이트 기술?!

    @ManyToMany
    Set<SiteUser> voter; //중복 허용하지 않으므로 Set

}
