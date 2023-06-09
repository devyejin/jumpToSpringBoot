package com.mysite.sbb.question;


import com.mysite.sbb.DataNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor //생성자 주입
@Service
public class QuestionService {

    private final QuestionRepository questionRepository;

    public List<Question> getList() {
        return this.questionRepository.findAll();
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
}
