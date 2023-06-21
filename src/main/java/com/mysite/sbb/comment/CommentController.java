package com.mysite.sbb.comment;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/comment")
public class CommentController {

    @PostMapping("/create")
    public String createComment(@) {

        return null;
    }
}
