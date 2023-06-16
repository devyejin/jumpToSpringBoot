package com.mysite.sbb.answer;

import com.mysite.sbb.DataNotFoundException;
import com.mysite.sbb.question.Question;
import com.mysite.sbb.question.QuestionForm;
import com.mysite.sbb.question.QuestionService;
import com.mysite.sbb.user.SiteUser;
import com.mysite.sbb.user.UserService;
import com.sun.tools.jconsole.JConsoleContext;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.security.Principal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Slf4j
@Controller
@RequiredArgsConstructor //생성자 주입
@RequestMapping("/answer")
public class AnswerController {

    private final QuestionService questionService; //answer등록하는데 왜 question?
    private final AnswerService answerService;

    private final UserService userService;

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/create/{id}")
    public String createAnswer(
            Model model,
            @PathVariable("id") Integer id,
            @Valid AnswerForm answerForm, BindingResult bindingResult,
            Principal principal
    ) {
        //작성자 정보
//                String username = principal.getName();
//                SiteUser author = this.userService.getUser(username);
        SiteUser siteUser = this.userService.getUser(principal.getName());


        //해당 질문을 가져와야해서 questionService가 필요
        Question question = this.questionService.getQuestion(id);

        //대안 로직
        if (bindingResult.hasErrors()) {
            model.addAttribute("question", question); //몇번째 질문인지 정보 담아줘야함.
            return "question_detail";
        }

        //정상로직

        this.answerService.create(question, answerForm.getContent(), siteUser);
        //답변 저장하고나면 보낼 페이지, 질문 페이지
        return String.format("redirect:/question/detail/%s", id); //문자열 형식 지정해주는 메서드

    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/modify/{id}")
    public String modify(AnswerForm answerForm, //요청으로부터 DTO받아올때는 선두에 놔야함
                         @PathVariable("id") Integer id,
                         Principal principal
                         ) {

        log.info("message={}","GET 호출");
        Answer answer = this.answerService.getAnswer(id); //답변 찾음, 내용을 뷰로 보내줘야함

        //더블 체킹 (답변 작성자랑 현재 사용자(Principal)
        if (!answer.getAuthor().getUsername().equals(principal.getName())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "수정 권한이 없습니다.");
        }
        //정보 보내주기
        answerForm.setContent(answer.getContent()); //DTO에 담은건 model에 자동으로 add됨 , 이때는 수정날짜는저장이 안될텐데
        // 기존 답변 answerForm 받아온거에서 Content값만 수정한거같음!

        return "answer_form";
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/modify/{id}")
    public String modify(@Valid AnswerForm answerForm,
                         BindingResult bindingResult,
                         @PathVariable("id") Integer id,
                         Principal principal) {

        log.info("message={}","post 호출");
        if(bindingResult.hasErrors()) {
            log.error("error={}",bindingResult.hasErrors());
//            return String.format("redirect:/answer/modify/%c",id);
            return "answer_form";
        }

        //정상 로직
        Answer answer = this.answerService.getAnswer(id);

        //더블 체킹
        if(!answer.getAuthor().getUsername().equals(principal.getName())){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "수정 권한이 없습니다.");
        }
        log.info("content={}", answerForm.getContent());
        // 매개변수로 answer PK 를 넘기는게 더 깔끔하지 않나?? 더블체킹한 값으로 넘기는게 더 좋은건가?
        this.answerService.modify(answer, answerForm.getContent()); //기존 답변, 새로작성한 답변내용

        return String.format("redirect:/question/detail/%s",answer.getQuestion().getId()); //질문페이지 출력이니까
    }

}
