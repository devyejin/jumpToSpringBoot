package com.mysite.sbb.answer;

import com.mysite.sbb.question.Question;
import com.mysite.sbb.question.QuestionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;

@Slf4j
@Controller
@RequiredArgsConstructor //생성자 주입
@RequestMapping("/answer")
public class AnswerController {

        private final QuestionService questionService; //answer등록하는데 왜 question?
        private final AnswerService answerService;

        @PostMapping("/create/{id}")
        public String createAnswer(
                Model model,
                @PathVariable("id") Integer id,
                @Valid AnswerForm answerForm, BindingResult bindingResult)
        {
                //해당 질문을 가져와야해서 questionService가 필요
                Question question = this.questionService.getQuestion(id);

                //대안 로직
                if(bindingResult.hasErrors()) {
                        model.addAttribute("question", question); //몇번째 질문인지 정보 담아줘야함.
                        return "question_detail";
                }

                //정상로직

                this.answerService.create(question,answerForm.getContent());
                //답변 저장하고나면 보낼 페이지, 질문 페이지
                return String.format("redirect:/question/detail/%s",id); //문자열 형식 지정해주는 메서드

        }

}
