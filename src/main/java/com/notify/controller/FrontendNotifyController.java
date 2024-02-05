package com.notify.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.membership.model.MembershipVO;
import com.notify.model.NotifyVO;
import com.notify.service.NotifyService;


@Controller
@RequestMapping("/front_end/notify")
public class FrontendNotifyController {

    @Autowired
    NotifyService notifySvc;
    
    
    @RequestMapping("memberNotify")
    public @ResponseBody List<NotifyVO> findByMemId(@RequestBody String json) {
        
        MembershipVO memVO = null;
        try {
            memVO = new ObjectMapper().readValue(json, MembershipVO.class);
        } catch (JsonMappingException e) {
            e.printStackTrace();
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return notifySvc.findByMemId(memVO.getMemId());
    } 

    
    @RequestMapping("notifyTitle")
    public @ResponseBody List<NotifyVO> findByTitle(@RequestBody String json) {
        
        NotifyVO notify = null;
        
        try {
            notify = new ObjectMapper().readValue(json, NotifyVO.class);
        } catch (JsonMappingException e) {
            e.printStackTrace();
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return notifySvc.findByTitle(notify.getNotifyTitle());
    }
    
    
    @RequestMapping("Unread")
    public @ResponseBody List<NotifyVO> findUnread(@RequestBody String json) {
        
        MembershipVO memVO = null;
        
        try {
            memVO = new ObjectMapper().readValue(json, MembershipVO.class);
        } catch (JsonMappingException e) {
            e.printStackTrace();
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        
        return notifySvc.findUnread(memVO.getMemId());
    }
    
    @RequestMapping("readAll")
    public @ResponseBody List<NotifyVO> readAll(@RequestBody String json) {
        
        MembershipVO memVO = null;
        
        try {
            memVO = new ObjectMapper().readValue(json, MembershipVO.class);
        } catch (JsonMappingException e) {
            e.printStackTrace();
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }      
        return notifySvc.readAll(memVO.getMemId());
    }
    
    
    @RequestMapping("all")
    public @ResponseBody List<NotifyVO> getAll(){
        return notifySvc.getAll();
    }
    
    
    
    

    
    // 去除BindingResult中某個欄位的FieldError紀錄
    public BindingResult removeFieldError(NotifyVO notifyVO, BindingResult result, String removedFieldname) {
        List<FieldError> errorsListToKeep = result.getFieldErrors().stream()
                .filter(fieldname -> !fieldname.getField().equals(removedFieldname)).collect(Collectors.toList());
        result = new BeanPropertyBindingResult(notifyVO, "notifyVO");
        for (FieldError fieldError : errorsListToKeep) {
            result.addError(fieldError);
        }
        return result;
    }

}
