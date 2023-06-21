package com.mysite.sbb.comment;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;

@Getter
@Setter
public class CommentForm {

    @NotEmpty(message = "댓글 내용은 필수입니다.")
    private String content;
}
