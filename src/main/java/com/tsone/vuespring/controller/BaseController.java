package com.tsone.vuespring.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class BaseController {

    @GetMapping("/")
    public String getSpa() {
        return "forward:index.html";
    }
}
