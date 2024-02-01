package com.act.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class AllController {
	
    @RequestMapping("")
    public String index(){
        return "front-end/zuo-huo";
    }

    @RequestMapping("/act/createAct")
    public String createAct(){
        return "front-end/act/createAct";
    }

    @RequestMapping("/act/updateAct")
    public String updateAct(){
        return "front-end/act/updateAct";
    }

    @RequestMapping("/act/joinAct")
    public String joinAct(){
        return "front-end/act/joinAct";
    }
}