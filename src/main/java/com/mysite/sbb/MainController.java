package com.mysite.sbb;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.security.Principal;
@Slf4j
@Controller
public class MainController {


    @GetMapping("/sbb")
    @ResponseBody
    public String index() {
        return "Welcome To SBB";
    }

    @GetMapping("/")
    public String home(Principal principal, Model model) {
        
        if(principal != null) {
            String loginUserName = principal.getName();
            log.info("loginUserName={}", loginUserName);
            model.addAttribute("loginUserName", loginUserName);
        }
        
        return "redirect:/question/list";
    }
}
