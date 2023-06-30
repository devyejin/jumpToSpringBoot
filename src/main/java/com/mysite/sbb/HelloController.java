package com.mysite.sbb;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;


@Controller
public class HelloController {

    @GetMapping("/hello")
    @ResponseBody // 응답을 "string"으로 바로 내려줌
    public String hello() {

        return "hello zi";
    }
}
