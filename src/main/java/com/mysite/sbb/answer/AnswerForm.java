package com.mysite.sbb.answer;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Getter
@Setter
public class AnswerForm {


    @NotEmpty(message = "내용은 필수항목입니다.")
    private String content;
}
