package com.venclosed.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.venclosed.model.VenClosedVO;
import com.venclosed.service.VenClosedService;
import com.venue.model.VenVO;

@Controller
@Validated
@RequestMapping("/front_end/ven-closed")
public class FrontendClosedDateController {

    @Autowired
    VenClosedService venClosedSvc;  
    
    @PostMapping("getClosedbyVen")
    public @ResponseBody List<VenClosedVO> getClosedbyVen(@RequestBody String json) {
        
        VenVO venVO = null;
       
        try {
            venVO = new ObjectMapper().readValue(json, VenVO.class);
        } catch (JsonMappingException e) {
            e.printStackTrace();
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return venClosedSvc.getClosedbyVen(venVO);
    }
       
}
