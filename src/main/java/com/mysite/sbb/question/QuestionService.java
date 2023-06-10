package com.mysite.sbb.question;


import com.mysite.sbb.DataNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor //생성자 주입
@Service
public class QuestionService {

    private final QuestionRepository questionRepository;

    public Page<Question> getList(int page) {
        Pageable pageable = PageRequest.of(page,10);
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

     public void create(String subject, String content) { //이렇게 바로 Entitiy를 넘기지말고 DTO사용해야 안전 추후 보완하기
         Question question = new Question();
         question.setSubject(subject);
         question.setContent(content);
         question.setCreateDate(LocalDateTime.now());
         this.questionRepository.save(question);

     }
}
