package com.lbl.day85_shop_back.controller;

import com.lbl.service.IUserService;
import com.lbl.service.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;

@Controller
@RequestMapping("/user")
@SessionAttributes("user")
public class UserController {

    @Autowired
    private IUserService userService;

    @RequestMapping("/login")
    public String login(String username, String password, ModelMap map){
        System.out.println(username+"   "+password);
        User user = userService.findById(username, password);
        if(user != null){
            map.addAttribute("user", user);
            return "index";
        }
        map.addAttribute("error", 1);
        return "login";
    }

}
