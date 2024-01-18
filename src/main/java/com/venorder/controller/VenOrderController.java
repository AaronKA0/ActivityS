package com.venorder.controller;

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

import com.venorder.model.VenOrderVO;
import com.venorder.service.VenOrderService;
import com.venue.model.VenVO;
import com.venue.service.VenService;

@Controller
@RequestMapping("/back_end/ven-order")
public class VenOrderController {

    @Autowired
    VenOrderService venOrderSvc;
    
    @Autowired
    VenService venSvc;

    @GetMapping("addVenOrder")
    public String addVenOrder(ModelMap model) {
        VenOrderVO venOrderVO = new VenOrderVO();
        model.addAttribute("venOrderVO", venOrderVO);
        return "back-end/ven-order/order_test";
    }

    @PostMapping("insert")
    public String insert(@Valid VenOrderVO venOrderVO, BindingResult result, ModelMap model) throws IOException {

        if (result.hasErrors()) {
            return "back-end/ven-order/order_test";
        }
        
        venOrderSvc.addVenOrder(venOrderVO);
        
        List<VenOrderVO> list = venOrderSvc.getAll();
        model.addAttribute("venOrderListData", list);
        model.addAttribute("success", "- (新增成功)");
        return "redirect:/ven-order/ven_order_page";
    }

    
    @PostMapping("getOne_For_Update")
    public String getOne_For_Update(@RequestParam("venOrderId") String venOrderId, ModelMap model) {
        
        VenOrderVO venOrderVO = venOrderSvc.getOneVenOrder(Integer.valueOf(venOrderId));

        model.addAttribute("venOrderVO", venOrderVO);
        return "back-end/ven-order/updateVenOrder";
    }

    @PostMapping("update")
    public String update(@Valid VenOrderVO venOrderVO, BindingResult result, ModelMap model) throws IOException {

        if (result.hasErrors()) {
            return "back-end/ven-order/updateVenOrder";
        }

        venOrderSvc.updateVenOrder(venOrderVO);

        model.addAttribute("success", "- (修改成功)");
        venOrderVO = venOrderSvc.getOneVenOrder(Integer.valueOf(venOrderVO.getVenOrderId()));
        model.addAttribute("venOrderVO", venOrderVO);
        return "back-end/ven-order/listOneVenOrder";
    }

    
    @ModelAttribute("venListData")
    protected List<VenVO> venListData(Model model) {        
        List<VenVO> list = venSvc.getAll();
        return list;
    } 
    
    
    // 去除BindingResult中某個欄位的FieldError紀錄
    public BindingResult removeFieldError(VenOrderVO venOrderVO, BindingResult result, String removedFieldname) {
        List<FieldError> errorsListToKeep = result.getFieldErrors().stream()
                .filter(fieldname -> !fieldname.getField().equals(removedFieldname)).collect(Collectors.toList());
        result = new BeanPropertyBindingResult(venOrderVO, "venOrderVO");
        for (FieldError fieldError : errorsListToKeep) {
            result.addError(fieldError);
        }
        return result;
    }

}
