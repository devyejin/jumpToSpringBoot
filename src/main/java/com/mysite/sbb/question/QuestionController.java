package com.mysite.sbb.question;

import com.mysite.sbb.answer.AnswerForm;
import com.mysite.sbb.user.SiteUser;
import com.mysite.sbb.user.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.security.Principal;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor //생성자 주입
@Controller
@RequestMapping("/question")
public class QuestionController {

    private final QuestionService questionService;
    private final UserService userService;

    @GetMapping("/list")
    //페이징 default = 0인 이유는, 스프링부트의 페이징은 0이 첫페에지
    public String list(Model model, @RequestParam(value="page", defaultValue = "0")int page, Principal principal) {


        if(principal != null) {
            SiteUser  user = this.userService.getUser(principal.getName());
            model.addAttribute("loginUsername",user.getUsername());
        }

        Page<Question> paging = this.questionService.getList(page); //paging객체에는 해당 페이지 데이터 + 10개 데이터 출력함
        model.addAttribute("paging", paging);

        return "question_list";
    }

    @GetMapping("/detail/{id}") //(value="question/detail/{id}") value 라는 속성 생략 가능
    public String detail(Model model, @PathVariable("id") Integer id, AnswerForm answerForm, Principal principal) { //AnswerForm 빈객체라도 넘겨줘야 에러안남

        if(principal != null) {
            SiteUser  user = this.userService.getUser(principal.getName());
            model.addAttribute("loginUsername",user.getUsername());
        }


        Question question = this.questionService.getQuestion(id);
        model.addAttribute("question", question);
        return "question_detail";
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/create")
    public String questionCreate(QuestionForm questionForm) { //화면단에서 사용하려면 빈객체라도 넘겨줘야함
        return "question_form";
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/create")
    public String questionSave(@Valid QuestionForm questionForm,
                               BindingResult bindingResult,
                               Principal principal
                               ) {
        //작성자 정보
        SiteUser siteUser = this.userService.getUser(principal.getName());

        //대안로직
        if(bindingResult.hasErrors()) {
            return "question_form";
        }

        //정상로직
        this.questionService.create(questionForm.getSubject(),questionForm.getContent(),siteUser);
        return "redirect:/question/list";
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/modify/{id}")
    public String questionModify(QuestionForm questionForm,@PathVariable("id") Integer id,Principal principal ) {
        //수정을 요구하면, QuestionForm으로 보내는데, 기존 내용 출력해줘야겠지
        //데이터 찾아서 보내줘야겠찌?!
        Question question = this.questionService.getQuestion(id);
        //프론트단말고, 서버에서 한번 더 질문작성자 == 로그인유저인지 더블체킹
        if(!question.getAuthor().getUsername().equals(principal.getName())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "수정권한이 업습니다.");
        }

        questionForm.setContent(question.getContent());
        questionForm.setSubject(question.getSubject());
        return "question_form";
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/modify/{id}")
    public String questionModify(@Valid QuestionForm questionForm,
                                 BindingResult bindingResult,
                                 @PathVariable("id") Integer id,
                                 Principal principal
                                 )
    {
        if(bindingResult.hasErrors()) {
            return "question_form";
        }

        Question question = this.questionService.getQuestion(id);
        //더블 체킹
        if(!question.getAuthor().getUsername().equals(principal.getName())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"수정 권한이 없습니다.");
        }

        this.questionService.modify(question,questionForm.getSubject(),questionForm.getContent());


        return String.format("redirect:/question/detail/%s",id);
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/delete/{id}")
    public String questionDelete(@PathVariable("id") Integer id,
                                 Principal  principal) {
        Question question = this.questionService.getQuestion(id);

        //더블 체킹!
        if(!question.getAuthor().getUsername().equals(principal.getName())){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"삭제 권한이 없습니다.");
        }

        this.questionService.delete(question);
        return "redirect:/";
    }


}
