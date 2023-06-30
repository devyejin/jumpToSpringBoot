package com.mysite.sbb.comment;

import com.mysite.sbb.question.Question;
import com.mysite.sbb.question.QuestionService;
import com.mysite.sbb.user.SiteUser;
import com.mysite.sbb.user.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;
import java.security.Principal;

@Slf4j
@RequiredArgsConstructor
@Controller
@RequestMapping("/comment")
public class CommentController {

    private final UserService userService;
    private final QuestionService questionService;

    private final CommentService commentService;

    //질문, 답변 url을 어떻게 잡아야할지 고민이네,,

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/create/{id}")
    public String createQuesComment(@Valid CommentForm commentForm,
                                    BindingResult bindingResult,
                                    @PathVariable("id") Integer id,
                                    Principal principal,
                                    Model model
                                    ) {



        //댓글 내용을 저장해야지
        //cotnet, question, siteuser 정보를 Service로 넘겨줘야함
        log.info("siteUserName ={}", principal.getName());
        SiteUser siteUser = this.userService.getUser(principal.getName());
        Question question = this.questionService.getQuestion(id);

        //더블체킹
        if(bindingResult.hasErrors()) {
            model.addAttribute("question", question);
            return "question_detail";
        }

        this.commentService.create(question,siteUser,commentForm.getContent());
        return String.format("redirect:/question/detail/%s",question.getId());
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/modify/{id}")
    public String modifyQuesComment(@Valid CommentForm commentForm,
                                    BindingResult bindingResult,
                                    Principal principal,
                                    Model model
                                    ) {

        return null;

    }
}
