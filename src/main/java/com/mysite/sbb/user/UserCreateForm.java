package com.mysite.sbb.user;


import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Setter
@Getter
public class UserCreateForm { //회원가입할 때 클라이언트에서 넘기는 데이타, DTO

    @Size(min = 5, max = 25)
    @NotEmpty(message="사용자ID는 필수입력값입니다.")
    private String username;

    @NotEmpty(message="비밀번호는 필수입력값입니다.")
    private String password1;

    @NotEmpty(message="비밀번호 확인은 필수입력값입니다.")
    private String password2;

    @NotEmpty(message="이메일은 필수입력값입니다.")
    @Email
    private String email;
}
