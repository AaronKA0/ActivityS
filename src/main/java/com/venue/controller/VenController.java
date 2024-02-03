package com.venue.controller;

import java.sql.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.notify.model.NotifyVO;
import com.venorder.model.VenOrderVO;
import com.venue.model.VenVO;
import com.venue.service.VenService;


@Controller
@RequestMapping("/front_end/venue")
public class VenController {

    @Autowired
    VenService venSvc;

    @RequestMapping("venType")
    public @ResponseBody VenVO findByName(@RequestBody String json) {
        
        VenVO ven = null;
       
        try {
            ven = new ObjectMapper().readValue(json, VenVO.class);
        } catch (JsonMappingException e) {
            e.printStackTrace();
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return venSvc.getByName(ven.getVenName());
    }
    
    @RequestMapping("orderVen")
    public @ResponseBody List<VenVO> findByDate(@RequestBody String json) {
        
        VenOrderVO ven = null;
       
        try {
            ven = new ObjectMapper().readValue(json, VenOrderVO.class);
        } catch (JsonMappingException e) {
            e.printStackTrace();
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return venSvc.pickByOrderDate(ven.getOrderDate());
    }
    
    
    @RequestMapping("all")
    public @ResponseBody List<VenVO> getAll(){        
        return venSvc.getAll();
    }
    
    
    
    
    

    
    // 去除BindingResult中某個欄位的FieldError紀錄
    public BindingResult removeFieldError(VenVO venVO, BindingResult result, String removedFieldname) {
        List<FieldError> errorsListToKeep = result.getFieldErrors().stream()
                .filter(fieldname -> !fieldname.getField().equals(removedFieldname)).collect(Collectors.toList());
        result = new BeanPropertyBindingResult(venVO, "venVO");
        for (FieldError fieldError : errorsListToKeep) {
            result.addError(fieldError);
        }
        return result;
    }

}
