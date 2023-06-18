package com.mysite.sbb.answer;


import com.mysite.sbb.DataNotFoundException;
import com.mysite.sbb.question.Question;
import com.mysite.sbb.user.SiteUser;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;


@Slf4j
@Service
@RequiredArgsConstructor
public class AnswerService {

    private final AnswerRepository answerRepository; //@RequiredArgsConstructor 어노테이션을 사용할 때는 final

    public Answer create(Question question, String content, SiteUser author) {
        Answer answer = new Answer(); // Answer DTO를 만들고
        answer.setContent(content);
        answer.setCreateDate(LocalDateTime.now());
        answer.setQuestion(question);
        answer.setAuthor(author);
        return this.answerRepository.save(answer);
    }

    public Answer getAnswer(Integer id) {
        Optional<Answer> answer = this.answerRepository.findById(id);

        if(answer.isPresent()) {
            return answer.get();
        } else {
            throw new DataNotFoundException("answer not found.");
        }

    }

    public void modify(Answer answer, String content) { //content보다 modifyContent가 명확해보이는데..
//        Optional<Answer> findAnswer = this.answerRepository.findById(answer.getId());
//
//        if(!findAnswer.isPresent()) {
//            throw new DataNotFoundException("answer is not found.");
//        }
//
//        Answer answer1 = findAnswer.get();
        //-> 이미 answer이 repository에서 찾은 값이니까 똑같은 작업을 반복할 이유가 없음! 바로 변경 데이터 담아주기
        answer.setContent(content);
        answer.setModifyDate(LocalDateTime.now());
        this.answerRepository.save(answer);
    }

    public void delete(Answer answer) {
        this.answerRepository.delete(answer);
    }

    public void vote(Answer answer, SiteUser siteUser) {
        answer.getVoter().add(siteUser);
        this.answerRepository.save(answer);
    }
}
