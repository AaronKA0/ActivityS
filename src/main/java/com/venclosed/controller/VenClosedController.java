package com.venclosed.controller;

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

import com.venclosed.model.VenClosedVO;
import com.venclosed.service.VenClosedService;
import com.venorder.model.VenOrderVO;
import com.venorder.service.VenOrderService;
import com.venue.model.VenVO;
import com.venue.service.VenService;

@Controller
@RequestMapping("/back_end/ven-closed")
public class VenClosedController {

    @Autowired
    VenClosedService venClosedSvc;
    
    @Autowired
    VenOrderService venOrderSvc;
    
    @Autowired
    VenService venSvc;

    
  //******************************* 新增單筆 ******************************* //
    
    @GetMapping("addVenClosedDate")
    public String addVenClosedDate(ModelMap model) {
        VenClosedVO venClosedVO = new VenClosedVO();
        model.addAttribute("venClosedVO", venClosedVO);
        return "back-end/ven-closed/addVenClosedDate";
    }

    @PostMapping("insert")
    public String insert(@Valid VenClosedVO venClosedVO, BindingResult result, ModelMap model) throws IOException {

        // 輸入錯誤處理
        if (result.hasErrors()) {
            return "back-end/ven-closed/addVenClosedDate";
        }
        
        // 檢查選擇日期該場地是否已有預訂
        Integer selectedVenId = (venClosedVO.getVenVO()).getVenId();
        
        Date date = venClosedVO.getClosedDate();
        List<VenOrderVO> venOrderVO = venOrderSvc.getByOrderDate(date);
        
        for (VenOrderVO venOrder : venOrderVO) {
            Integer orderVenId = (venOrder.getVenVO()).getVenId();
            if (selectedVenId == orderVenId) {
                model.addAttribute("hasOrder", date + " 已有訂單，無法設定");
                return "back-end/ven-closed/addVenClosedDate";
            }
        }
        
        // 確認新增
        venClosedSvc.addVenClosed(venClosedVO);
        
        List<VenClosedVO> list = venClosedSvc.getAll();
        model.addAttribute("venClosedListData", list);
        model.addAttribute("success", "- (新增成功)");
        return "redirect:/back_end/ven-closed/ven_closed_date";
    }
    
    
  //******************************* 新增全部場地關閉 ******************************* //
    
    @GetMapping("addAllClosed")
    public String addAllClosed(ModelMap model) {
        VenClosedVO venClosedVO = new VenClosedVO();
        model.addAttribute("venClosedVO", venClosedVO);
        return "back-end/ven-closed/allClosed";
    }
    
    @PostMapping("allClosed")
    public String allClosed(@Valid VenClosedVO venClosedVO, BindingResult result, ModelMap model) throws IOException {
        
        // 輸入錯誤處理
        if (result.hasErrors()) {
            return "back-end/ven-closed/allClosed";
        }
        
        // 檢查選擇日期是否有任何場地已預訂
        Date date = venClosedVO.getClosedDate();
        String reason = venClosedVO.getClosedReason();
        List<VenOrderVO> venOrderVO = venOrderSvc.getByOrderDate(date);
        
        if (!venOrderVO.isEmpty()) {
            model.addAttribute("hasOrder", date + " 已有 " + venOrderVO.size() + " 筆訂單，無法設定");
            return "back-end/ven-closed/allClosed";
        }
        
        // 確認新增
        List<VenVO> vens = venSvc.getAll();
        
        for(VenVO ven :vens) {
            VenClosedVO v = new VenClosedVO(ven, date, reason);       
            venClosedSvc.addVenClosed(v);
        }
        
        List<VenClosedVO> list = venClosedSvc.getAll();
        model.addAttribute("venClosedListData", list);
        model.addAttribute("success", "- (新增成功)");
        return "redirect:/back_end/ven-closed/ven_closed_date";
    } 

    
  //******************************* 修改 ******************************* //
    
    @PostMapping("getOne_For_Update")
    public String getOne_For_Update(@RequestParam("closedDateId") String closedDateId, ModelMap model) {
        
        VenClosedVO venClosedVO = venClosedSvc.getOneVenClosed(Integer.valueOf(closedDateId));

        model.addAttribute("venClosedVO", venClosedVO);
        return "back-end/ven-closed/updateVenClosed";
    }

    @PostMapping("update")
    public String update(@Valid VenClosedVO venClosedVO, BindingResult result, ModelMap model) throws IOException {
        
        // 輸入錯誤處理
        if (result.hasErrors()) {
            return "back-end/ven-closed/updateVenClosed";
        }
        
        // 檢查選擇日期該場地是否已有預訂
        Integer selectedVenId = (venClosedVO.getVenVO()).getVenId();
        
        Date date = venClosedVO.getClosedDate();
        List<VenOrderVO> venOrderVO = venOrderSvc.getByOrderDate(date);
        
        for (VenOrderVO venOrder : venOrderVO) {
            Integer orderVenId = (venOrder.getVenVO()).getVenId();
            if (selectedVenId == orderVenId) {
                model.addAttribute("hasOrder", date + " 已有訂單，無法設定");
                return "back-end/ven-closed/updateVenClosed";
            }
        }

        // 確認新增
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
