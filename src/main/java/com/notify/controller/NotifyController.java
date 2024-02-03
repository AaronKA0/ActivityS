package com.notify.controller;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.membership.model.MembershipVO;
import com.membership.service.MembershipService;
import com.notify.model.NotifyVO;
import com.notify.service.NotifyService;
import com.notify.service.NotifyWebSocket;


@Controller
@RequestMapping("/back_end/notify")
public class NotifyController {

    @Autowired
    NotifyService notifySvc;
    
    @Autowired
    MembershipService membershipSvc;

    @GetMapping("addNotify")
    public String addNotify(ModelMap model) {
        NotifyVO notifyVO = new NotifyVO();
        model.addAttribute("notifyVO", notifyVO);
        return "back-end/notify/addNotify";
    }

    @PostMapping("insert")
    public String insert(@Valid NotifyVO notifyVO, BindingResult result, ModelMap model) throws IOException {

        if (result.hasErrors()) {
            return "back-end/notify/addNotify";
        }
        
        notifySvc.addNotify(notifyVO);
        
        List<NotifyVO> list = notifySvc.getAll();
        model.addAttribute("notifyListData", list);
        model.addAttribute("success", "- (新增成功)");
        return "redirect:/back_end/notify/notify_page";
    }

    
    @PostMapping("getOne_For_Update")
    public String getOne_For_Update(@RequestParam("notifyId") String notifyId, ModelMap model) {
        
        NotifyVO notifyVO = notifySvc.getOneNotify(Integer.valueOf(notifyId));

        model.addAttribute("notifyVO", notifyVO);
        return "back-end/notify/updateNotify";
    }

    @PostMapping("update")
    public String update(@Valid NotifyVO notifyVO, BindingResult result, ModelMap model) throws IOException {

        if (result.hasErrors()) {
            return "back-end/notify/updateNotify";
        }

        notifySvc.updateNotify(notifyVO);

        model.addAttribute("success", "- (修改成功)");
        notifyVO = notifySvc.getOneNotify(Integer.valueOf(notifyVO.getNotifyId()));
        model.addAttribute("notifyVO", notifyVO);
        return "back-end/notify/listOneNotify";
    }

    
    @ModelAttribute("membershipListData")
    protected List<MembershipVO> referenceListData1(Model model) {
        
        List<MembershipVO> list = membershipSvc.getAll();
        return list;
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
