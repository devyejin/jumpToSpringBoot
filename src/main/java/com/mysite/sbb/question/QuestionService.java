package com.mysite.sbb.question;


import com.mysite.sbb.DataNotFoundException;
import com.mysite.sbb.user.SiteUser;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

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

    public Page<Question> getList(int page) {
        List<Sort.Order> sorts = new ArrayList<>();
        sorts.add(Sort.Order.desc("createDate"));
        Pageable pageable = PageRequest.of(page,10,Sort.by(sorts)); //해당 page부터 10개의 데이터
        return this.questionRepository.findAll(pageable);
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
