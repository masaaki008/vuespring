package com.tsone.vuespring.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Vueの静的ファイルを返す
 * 存在しないURLへのアクセス時はHtml5HistoryModeResourceConfigで返す
 *
 * @author
 */
@Controller
public class BaseController {

    @GetMapping("/")
    public String getSpa() {
        return "forward:index.html";
    }
}
