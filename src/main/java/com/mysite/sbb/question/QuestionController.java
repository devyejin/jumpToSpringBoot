package com.mysite.sbb.question;

import com.mysite.sbb.answer.AnswerForm;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Slf4j
@RequiredArgsConstructor //생성자 주입
@Controller
@RequestMapping("/question")
public class QuestionController {

    private final QuestionService questionService;

    @GetMapping("/list")
    //페이징 default = 0인 이유는, 스프링부트의 페이징은 0이 첫페에지
    public String list(Model model, @RequestParam(value="page", defaultValue = "0")int page) {
        Page<Question> paging = this.questionService.getList(page);
        model.addAttribute("paging", paging);
        return "question_list";
    }

    @GetMapping("/detail/{id}") //(value="question/detail/{id}") value 라는 속성 생략 가능
    public String detail(Model model, @PathVariable("id") Integer id, AnswerForm answerForm) { //AnswerForm 빈객체라도 넘겨줘야 에러안남
        Question question = this.questionService.getQuestion(id);
        model.addAttribute("question", question);
        return "question_detail";
    }

    @GetMapping("/create")
    public String questionCreate(QuestionForm questionForm) { //화면단에서 사용하려면 빈객체라도 넘겨줘야함
        return "question_form";
    }

    @PostMapping("/create")
    public String questionSave(@Valid QuestionForm questionForm, BindingResult bindingResult) {
        //대안로직
        if(bindingResult.hasErrors()) {
            return "question_form";
        }

        //정상로직
        this.questionService.create(questionForm.getSubject(),questionForm.getContent());
        return "redirect:/question/list";
    }
}
