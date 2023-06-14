package com.mysite.sbb.user;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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

    @GetMapping("/signup")
    public String signup(UserCreateForm userCreateForm) {
        return "signup_form";
    }

    @PostMapping("/signup")
    public String signup(@Valid UserCreateForm userCreateForm, BindingResult bindingResult) {

        //사용자 입력값 에러가 있으면 (Se->Re 보내지 않고 되돌려 보냄), 에러메시지는 어차피 Valid에 걸어놨음
        if(bindingResult.hasErrors()) {
            return "signup_form";
        }

        //@Valid로 처리못하는건 직접 bindingResult 객체를 사용해서 에러메시지 전달
        if(!userCreateForm.getPassword1().equals(userCreateForm.getPassword2())) {
            bindingResult.rejectValue("password1","passwordInCorrect","비밀번호와 확인 비밀번호가 일치하지 않습니다.");
            return "signup_form";
        }

        //unique 제약조건 위반하면 DBMS에서 Exception넘어옴 => 무결성 DataIntegrityViolationException
        try {
            this.userService.create(userCreateForm.getUsername(), userCreateForm.getEmail(),userCreateForm.getPassword1());
        } catch (DataIntegrityViolationException e) {
            //클라이언트한테 중복 ID,EMAIL이니 다시하라고 보내주기
            //난 구체적으로 어떤 필드가 오류라 알려주고싶었는데 보안 등 탈취 이런거때문에 특정값 알려주지 않는게 좋은 로직임
            log.error("error={}", e.getMessage());
            bindingResult.reject("signupFaild","이미 등록된 사용자입니다.");
            return "signup_form";
        } catch (Exception e) { //프로그램이 죽으면 안되니까 모든 에러 잡아주기
            log.error("error={}",e.getMessage());
            bindingResult.reject("signupFaild","에러가 발생했습니다.");
            return "signup_form";
        }

        return "redirect:/";
    }

    @GetMapping("/login") //POST처리는 스프링시큐리티가 해줌
    public String login() {
        return "login_form";
    }

    //로그아웃 처리 -> 스프링 시큐리티 SecurityConfig로 가자



}
