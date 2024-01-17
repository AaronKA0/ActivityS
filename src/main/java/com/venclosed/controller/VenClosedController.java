package com.venclosed.controller;

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

import com.venclosed.model.VenClosedVO;
import com.venclosed.service.VenClosedService;
import com.venue.model.VenVO;
import com.venue.service.VenService;

@Controller
@RequestMapping("/ven-closed")
public class VenClosedController {

    @Autowired
    VenClosedService venClosedSvc;
    
    @Autowired
    VenService venSvc;

    @GetMapping("addVenClosedDate")
    public String addVenClosedDate(ModelMap model) {
        VenClosedVO venClosedVO = new VenClosedVO();
        model.addAttribute("venClosedVO", venClosedVO);
        return "back-end/ven-closed/addVenClosedDate";
    }

    @PostMapping("insert")
    public String insert(@Valid VenClosedVO venClosedVO, BindingResult result, ModelMap model) throws IOException {

        if (result.hasErrors()) {
            return "back-end/ven-closed/addVenClosedDate";
        }
        
        venClosedSvc.addVenClosed(venClosedVO);
        
        List<VenClosedVO> list = venClosedSvc.getAll();
        model.addAttribute("venClosedListData", list);
        model.addAttribute("success", "- (新增成功)");
        return "redirect:/ven-closed/listAllVenClosed";
    }

    
    @PostMapping("getOne_For_Update")
    public String getOne_For_Update(@RequestParam("closedDateId") String closedDateId, ModelMap model) {
        
        VenClosedVO venClosedVO = venClosedSvc.getOneVenClosed(Integer.valueOf(closedDateId));

        model.addAttribute("venClosedVO", venClosedVO);
        return "back-end/ven-closed/update_venClosed_input";
    }

    @PostMapping("update")
    public String update(@Valid VenClosedVO venClosedVO, BindingResult result, ModelMap model) throws IOException {

        if (result.hasErrors()) {
            return "back-end/ven-closed/update_venClosed_input";
        }

        venClosedSvc.updateVenClosed(venClosedVO);

        model.addAttribute("success", "- (修改成功)");
        venClosedVO = venClosedSvc.getOneVenClosed(Integer.valueOf(venClosedVO.getClosedDateId()));
        model.addAttribute("venClosedVO", venClosedVO);
        return "back-end/ven-closed/listOneVenClosed";
    }

    
    @ModelAttribute("venListData")
    protected List<VenVO> venListData(Model model) {        
        List<VenVO> list = venSvc.getAll();
        return list;
    } 
    
    
    // 去除BindingResult中某個欄位的FieldError紀錄
    public BindingResult removeFieldError(VenClosedVO venClosedVO, BindingResult result, String removedFieldname) {
        List<FieldError> errorsListToKeep = result.getFieldErrors().stream()
                .filter(fieldname -> !fieldname.getField().equals(removedFieldname)).collect(Collectors.toList());
        result = new BeanPropertyBindingResult(venClosedVO, "venClosedVO");
        for (FieldError fieldError : errorsListToKeep) {
            result.addError(fieldError);
        }
        return result;
    }

}
