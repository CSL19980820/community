package csl.individual.community.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class IndexController {
    @GetMapping("/")
    public String hello(Model model){
        return "index";
    }

    @GetMapping("/quit")
    public String quit(Model model){
        return "index";
    }
}
