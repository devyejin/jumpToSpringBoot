package com.mysite.sbb.user;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;

@Slf4j
@RequiredArgsConstructor
@Controller
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    @GetMapping("/signup") //회원가입 페이지
    public String signup(UserCreateForm userCreateForm) {
        return "signup_form";
    }

    @PostMapping("/signup")
    public String signup(@Valid UserCreateForm userCreateForm, BindingResult bindingResult) {
        //Validation 검증
        if(bindingResult.hasErrors()) {
            return "signup_form";
        }

        //Validation 통과 후 로직
        if(!userCreateForm.getPassword1().equals(userCreateForm.getPassword2())) {
            //rejectValue(필드명,오류코드,에러메시지) -> 오류코드는 잘 만들어줘야함,여기서 쓴건 임시
            bindingResult.rejectValue("password2","passwordInCorrect","비밀번호와 확인비밀번호가 일치하지 않습니다.");
            return "signup_form";
        }

        //정상 로직
        try {
            this.userService.create(userCreateForm.getUsername(),
                    userCreateForm.getEmail(),
                    userCreateForm.getPassword1());

        } catch (DataIntegrityViolationException e) {
            //unique 위반시, 이미 존재하는 username 혹은 email임을 알려주기

            log.error("error={}",e.getMessage());
            bindingResult.reject("signupFaild","이미 등록된 사용자입니다.");
            return "signup_form";

        } catch (Exception e) {
            log.error("error={}",e.getMessage());
            bindingResult.reject("signupFaild",e.getMessage());
            return "signup_form";
        }

        return "redirect:/"; //회원가입 성공하면 home으로 보냄

    }

}
