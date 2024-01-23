package com.notify.controller;

import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotEmpty;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.notify.model.NotifyVO;
import com.notify.service.NotifyService;

@Controller
@Validated
@RequestMapping("/notify")
public class NotifyIdController {

    @Autowired
    NotifyService notifySvc;  
    
    @PostMapping("getOne_For_Display")
    public String getOne_For_Display(

        @NotEmpty(message="編號: 請勿空白")
        @Digits(integer = 4, fraction = 0, message = "編號: 請填數字")
        @RequestParam("notifyId") String notifyId,
        ModelMap model) {

        NotifyVO notifyVO = notifySvc.getOneNotify(Integer.valueOf(notifyId));
        
        List<NotifyVO> list = notifySvc.getAll();
        model.addAttribute("notifyListData", list);
        
        if (notifyVO == null) {
            model.addAttribute("errorMessage", "查無資料");
            return "back-end/notify/notify_page";
        }
        
        model.addAttribute("notifyVO", notifyVO);
        model.addAttribute("getOne_For_Display", "true");
        
        return "back-end/notify/notify_page";
    }
    
    
    @ExceptionHandler(value = { ConstraintViolationException.class })
    //@ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public ModelAndView handleError(HttpServletRequest req,ConstraintViolationException e,Model model) {
        Set<ConstraintViolation<?>> violations = e.getConstraintViolations();
        StringBuilder strBuilder = new StringBuilder();
        for (ConstraintViolation<?> violation : violations ) {
              strBuilder.append(violation.getMessage() + "<br>");
        }
        
        List<NotifyVO> list = notifySvc.getAll();
        model.addAttribute("notifyListData", list);
        
        String message = strBuilder.toString();
        return new ModelAndView("back-end/notify/notify_page", "errorMessage", "請修正以下錯誤:<br>"+message);
    }
    
}
