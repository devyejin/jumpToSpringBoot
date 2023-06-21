package com.mysite.sbb.question;


import com.mysite.sbb.DataNotFoundException;
import com.mysite.sbb.answer.Answer;
import com.mysite.sbb.user.SiteUser;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Slf4j
@RequiredArgsConstructor //생성자 주입
@Service
public class QuestionService {

    private final QuestionRepository questionRepository;

    private Specification<Question> search(String kw) {
        return new Specification<Question>() { //Specification는 인터페이스니까, 익명객체로 구현
            @Override
            public Predicate toPredicate(Root<Question> q, CriteriaQuery<?> query, CriteriaBuilder cb) {
                // Question 테이블이 root (기준 테이블 이란 의미인 듯)
                query.distinct(true); //테이블 데이터 중복 제거

                Join<Question, SiteUser> u1 = q.join("author", JoinType.LEFT);// 기준(Question)테이블을 기준으로 author 테이블을 LEFT JOIN
                Join<Question, Answer> a = q.join("answerList", JoinType.LEFT);
                Join<Answer, SiteUser> u2 = a.join("author", JoinType.LEFT);
                //CriteriaBuilder을 이용해서 or 연산
                // JPA Criteria 쿼리를 생성하기 위한 빌더 클래스
                return cb.or(cb.like(q.get("subject"),"%"+kw+"%"),
                             cb.like(q.get("content"),"%"+kw+"%"),
                             cb.like(u1.get("username"),"%"+kw+"%"),
                             cb.like(a.get("content"),"%"+kw+"%"),
                             cb.like(u2.get("username"),"%"+kw+"%"));
            }
        };

    }

    public Page<Question> getList(int page,String kw) {
        List<Sort.Order> sorts = new ArrayList<>();
        sorts.add(Sort.Order.desc("createDate"));
        Pageable pageable = PageRequest.of(page,10,Sort.by(sorts)); //해당 page부터 10개의 데이터
        Specification<Question> spec = search(kw);
//        return this.questionRepository.findAll(spec,pageable);
        return this.questionRepository.findAllByKeyword(kw, pageable);
    }


    public Question getQuestion(Integer id) {
        Optional<Question> oq = this.questionRepository.findById(id);
        if(oq.isPresent()) {
            return oq.get();
        } else {
            throw new DataNotFoundException("question not found"); //런타임으로 로직은 정상 수행하면 호출한 계층으로 넘기기
            //여기서 서버에러500대 대신 404에러 던짐 ( Exception 처리 )
        }
     }

     public void create(String subject, String content, SiteUser author) { //이렇게 바로 Entitiy를 넘기지말고 DTO사용해야 안전 추후 보완하기
         Question question = new Question();
         question.setSubject(subject);
         question.setContent(content);
         question.setCreateDate(LocalDateTime.now());
         question.setAuthor(author);
         this.questionRepository.save(question);

     }

     public void modify(Question question, String subject, String content) {
         question.setSubject(subject);
         question.setContent(content);
         question.setModifyDate(LocalDateTime.now());
         this.questionRepository.save(question); //기존 question객체에 일부 수정이니까 (id값이 있쥬)
    }

    public void delete(Question question) {
        this.questionRepository.delete(question);
    }

    public void vote(Question question, SiteUser siteUser) {
//        Set<SiteUser> voter = question.getVoter();
//        voter.add(siteUser);

        question.getVoter().add(siteUser);
        this.questionRepository.save(question);
    }
}
