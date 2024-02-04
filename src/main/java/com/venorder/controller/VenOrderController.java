package com.venorder.controller;

import java.io.IOException;
import java.sql.Date;
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

    
  //******************************* 新增 ******************************* //
    
    @GetMapping("addVenOrder")
    public String addVenOrder(ModelMap model) {
        VenOrderVO venOrderVO = new VenOrderVO();
        model.addAttribute("venOrderVO", venOrderVO);
        return "back-end/ven-order/addVenOrder";
    }

    @PostMapping("insert")
    public String insert(@Valid VenOrderVO venOrderVO, BindingResult result, ModelMap model) throws IOException {

        if (result.hasErrors()) {
            return "back-end/ven-order/addVenOrder";
        }
        
        venOrderSvc.addVenOrder(venOrderVO);
        
        List<VenOrderVO> list = venOrderSvc.getAll();
        model.addAttribute("venOrderListData", list);
        model.addAttribute("success", "- (新增成功)");
        return "redirect:/back_end/ven-order/ven_order_page";
    }

    
  //******************************* 修改 ******************************* //
    
    @PostMapping("getOne_For_Update")
    public String getOne_For_Update(@RequestParam("venOrderId") String venOrderId, ModelMap model) {
        
        VenOrderVO venOrderVO = venOrderSvc.getOneVenOrder(Integer.valueOf(venOrderId));

        model.addAttribute("venOrderVO", venOrderVO);
        return "back-end/ven-order/updateVenOrder";
    }

    @PostMapping("update")
    public String update(@Valid VenOrderVO venOrderVO, BindingResult result, ModelMap model) throws IOException {

        System.out.println(venOrderVO.getVenComTime());
        if (result.hasErrors()) {
            System.out.println(result);
            return "back-end/ven-order/updateVenOrder";
        }
        
        // 檢查選擇日期該場地是否已有預訂
        Date date = venOrderVO.getOrderDate();
        List<VenVO> VenCanOrder = venSvc.pickByOrderDate(date);
        
        if( !(VenCanOrder.contains( venOrderVO.getVenVO() )) ) {
            model.addAttribute("hasOrder", date + "已無法預訂");
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
    
    @ModelAttribute("venOnListData")
    protected List<VenVO> venOnListData(Model model) {        
        List<VenVO> list = venSvc.getVenueOn();
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
