package com.venclosed.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.venclosed.model.VenClosedVO;
import com.venclosed.service.VenClosedService;

@Controller
@Validated
@RequestMapping("/back_end/ven-closed")
public class ClosedDateIdController {

    @Autowired
    VenClosedService venClosedSvc;  
    
    @PostMapping("getOne_For_Display")
    public String getOne_For_Display(@RequestParam("closedDateId") String closedDateId, ModelMap model) {
        
        VenClosedVO venClosedVO = venClosedSvc.getOneVenClosed(Integer.valueOf(closedDateId));

        model.addAttribute("venClosedVO", venClosedVO);
        return "back-end/ven-closed/listOneVenClosed";
    }
       
}
