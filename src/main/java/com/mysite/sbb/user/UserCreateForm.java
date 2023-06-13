package com.mysite.sbb.user;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Setter
@Getter
public class UserCreateForm {

    @Size(min=5,max=25)
    @NotEmpty(message="사용자 ID는 필수항목입니다.") // null, 공백 비허용
    private String username;

    @NotEmpty(message = "이메일은 필수항목입니다.")
    @Email //이메일형식과 일치여부 확인
    private String email;

    @NotEmpty(message = "비밀번호는 필수항목입니다.")
    private String password1;

    @NotEmpty(message = "비밀번호는 필수항목입니다.")
    private String password2; //비밀번호확인속성


}
