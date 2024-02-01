package com.venorder.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.venorder.model.VenOrderVO;
import com.venorder.service.VenOrderService;

@Controller
@Validated
@RequestMapping("/back_end/ven-order")
public class VenOrderIdController {

    @Autowired
    VenOrderService venOrderSvc;  
     
    @PostMapping("getOne_For_Display")
    public String getOne_For_Display(@RequestParam("venOrderId") String venOrderId, ModelMap model) {
        
        VenOrderVO venOrderVO = venOrderSvc.getOneVenOrder(Integer.valueOf(venOrderId));

        model.addAttribute("venOrderVO", venOrderVO);
        return "back-end/ven-order/listOneVenOrder";
    }
    
}
