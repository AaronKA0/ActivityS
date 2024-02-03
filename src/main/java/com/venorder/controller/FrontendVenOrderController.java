package com.venorder.controller;

import java.io.IOException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.membership.model.MembershipVO;
import com.membership.service.MembershipService;
import com.venorder.model.VenOrderVO;
import com.venorder.service.VenOrderService;
import com.venue.model.VenVO;
import com.venue.service.VenService;

@Controller
@RequestMapping("/front_end/venue")
public class FrontendVenOrderController {

    @Autowired
    MembershipService memSvc;
    
    @Autowired
    VenOrderService venOrderSvc;
    
    @Autowired
    VenService venSvc;
    
    @RequestMapping("getByDate")
    public @ResponseBody List<VenOrderVO> findByDate(@RequestBody String json) {
        
        VenOrderVO venOrder = null;

        try {
            venOrder = new ObjectMapper().readValue(json, VenOrderVO.class);
        } catch (JsonMappingException e) {
            e.printStackTrace();
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return venOrderSvc.getByOrderDate(venOrder.getOrderDate());

    }   
    
    @RequestMapping("getVenCom")
    public @ResponseBody List<VenOrderVO> findVenCom(@RequestBody String json) {
        
        VenVO venVO = null;
       
        try {
            venVO = new ObjectMapper().readValue(json, VenVO.class);
        } catch (JsonMappingException e) {
            e.printStackTrace();
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return venOrderSvc.getVenCom(venVO);
    }
    

    @RequestMapping("getMemInfo")
    public @ResponseBody MembershipVO findMemInfo(@RequestBody String json) {
        
        MembershipVO memVO = null;
       
        try {
            memVO = new ObjectMapper().readValue(json, MembershipVO.class);
        } catch (JsonMappingException e) {
            e.printStackTrace();
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return memSvc.getOneMembership(memVO.getMemId());
    }


    @PostMapping("addVenOrder")
    public String addVenOrder(ModelMap model, @ModelAttribute VenVO ven) {

        VenOrderVO venOrderVO = new VenOrderVO();
        VenVO venVO = venSvc.getOneVen(ven.getVenId());

        model.addAttribute("venOrderVO", venOrderVO);
        model.addAttribute("venVO", venVO);
        return "front-end/venue/venType";
    }

    @PostMapping("insert")
    public String insert(@Valid VenOrderVO venOrderVO, BindingResult result, ModelMap model) throws IOException {

        if (result.hasErrors()) {
            return "front-end/venue/venType";
        }
        
        venOrderSvc.addVenOrder(venOrderVO);

        List<VenOrderVO> list = venOrderSvc.getAll();
        model.addAttribute("venOrderListData", list);
        model.addAttribute("success", "- (新增成功)");
        return "redirect:/back_end/ven-order/ven_order_page";
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

    
    @PostMapping("feedback")
    public String feedback(@Valid VenOrderVO venOrderVO, ModelMap model) {
        
        venOrderVO.setVenComTime(Timestamp.valueOf(LocalDateTime.now()));
        venOrderSvc.updateVenOrder(venOrderVO);

        List<VenOrderVO> venOrders = venOrderSvc.getVenCom(venOrderVO.getVenVO());
        
        double venTotRating = 0;
        double rating = 0;
        int ratingSize = 0;
        
        // 累加場地評分算出平均
        for(VenOrderVO order : venOrders) {
            if(order.getVenRating() != 0) {
                rating = rating + order.getVenRating();
                ratingSize++;
            }
        }
        venTotRating = rating / ratingSize;
        
        venOrderVO.getVenVO().setVenTotRating(venTotRating);
        
        model.addAttribute("venOrderVO", venOrderVO);
        return "redirect:/Zuo-Huo";
    }
    
    
    
    @ModelAttribute("memListData")
    protected List<MembershipVO> memListData(Model model) {        
        List<MembershipVO> list = memSvc.getAll();
        return list;
    } 
    
    @ModelAttribute("venOnListData")
    protected List<VenVO> venListData(Model model) {        
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
