package com.mysite.sbb.question;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Slf4j
@RequiredArgsConstructor //생성자 주입
@Controller
@RequestMapping("/question")
public class QuestionController {

    //필드 주입 -> 생성자주입 이용,  @AutoWired의 경우 순환참조 발생 가능성으로 권장하지 않음
    private final QuestionService questionService;

    @GetMapping("/list")
    public String list(Model model) {

        List<Question> questionList = questionService.getList();
        model.addAttribute("questionList", questionList);
        return "question_list";
    }
}
