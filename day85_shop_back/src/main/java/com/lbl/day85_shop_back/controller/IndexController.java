package com.lbl.day85_shop_back.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class IndexController {

    @RequestMapping("/")
    public String index() {
        return "login";
    }

    @RequestMapping("/topage/{page}")
    public String topage(@PathVariable("page") String page) {
        return page;
    }
}
