package com.notify.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.notify.model.NotifyVO;
import com.notify.service.NotifyService;

@Controller
@Validated
@RequestMapping("/back_end/notify")
public class NotifyIdController {

    @Autowired
    NotifyService notifySvc;  
    
    @PostMapping("getOne_For_Display")
    public String getOne_For_Display(@RequestParam("notifyId") String notifyId, ModelMap model) {
        
        NotifyVO notifyVO = notifySvc.getOneNotify(Integer.valueOf(notifyId));

        model.addAttribute("notifyVO", notifyVO);
        return "back-end/notify/listOneNotify";
    }
    
}
