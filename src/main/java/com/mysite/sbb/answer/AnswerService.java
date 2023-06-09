package com.mysite.sbb.answer;


import com.mysite.sbb.question.Question;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;


@Slf4j
@Service
@RequiredArgsConstructor
public class AnswerService {

    private final AnswerRepository answerRepository; //@RequiredArgsConstructor 어노테이션을 사용할 때는 final

    public void create(Question question, String content) {
        Answer answer = new Answer(); // Answer DTO를 만들고
        answer.setContent(content);
        answer.setCreateDate(LocalDateTime.now());
        answer.setQuestion(question);
        this.answerRepository.save(answer);
    }
}
