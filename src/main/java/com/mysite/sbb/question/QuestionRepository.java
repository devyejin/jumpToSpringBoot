package com.mysite.sbb.question;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

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

    @Query //JPA대신 직접 쿼리 작성
            ("select distinct q from Question q " +
                    "left outer join SiteUser u1 on q.author = u1 " +
                    "left outer join Answer a on a.question = q " +
                    "left outer join SiteUser u2 on a.author = u2 " +
                    "where " +
                    "   q.subject like %:kw% " +
                    "   or q.content like %:kw% " +
                    "   or u1.username like %:kw% " +
                    "   or a.content like %:kw% " +
                    "   or u2.username like %:kw% ")
    Page<Question> findAllByKeyword(@Param("kw") String kw, Pageable pageable);


}
