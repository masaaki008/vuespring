package com.tsone.vuespring.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@Slf4j
public class ApiController {

    @GetMapping("/sample")
    public String sample() {
        return "サンプル";
    }

    @GetMapping("/islogin")
    public boolean isLogin() {
        return true;
    }
}
