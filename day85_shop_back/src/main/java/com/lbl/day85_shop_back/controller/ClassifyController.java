package com.lbl.day85_shop_back.controller;

import com.google.gson.Gson;
import com.lbl.service.IClassifyService;
import com.lbl.service.entity.Classify;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping("/classify")
public class ClassifyController {

    @Autowired
    private IClassifyService classifyService;

    @RequestMapping("/classifyList")
    @ResponseBody
    public String classifyList(){
        List<Classify> classifies = classifyService.ClassifyList();
       // System.out.println("classifies--->"+classifies);
        return "callback("+ new Gson().toJson(classifies) +")";
    }
}
