package com.mysite.sbb.question;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

//JpaRepository<리포지토리대상이되는타입, PK속상타입>
public interface QuestionRepository extends JpaRepository<Question, Integer> {

    Question findBySubject(String subject);
    Question findBySubjectAndContent(String subject, String content);

    //제목 검색(특정 문자 포함)
    List<Question> findBySubjectLike(String subject);

    //페이징처리
    Page<Question> findAll(Pageable pageable); // findAll를 기본으로 제공하는데, 이걸 명시했으니 이게 우선순위높음

    //검색
    Page<Question> findAll(Specification<Question> spec, Pageable pageable); //Specification과 Pageable 객체를 입력으로 Question 엔티티를 조회
    //검색된 조건으로 페이지를 가져와서 출력해주는거쥬?
}
