package com.mysite.sbb.question;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@RequiredArgsConstructor //생성자 주입
@Service
public class QuestionService {

    private QuestionRepository questionRepository;

    public List<Question> getList() {
        return questionRepository.findAll();
    }
}
