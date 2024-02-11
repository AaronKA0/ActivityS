package com.venorder.controller;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.membership.model.MembershipVO;
import com.notify.service.NotifyNow;
import com.venorder.model.VenOrderVO;
import com.venorder.service.BookingMail;
import com.venorder.service.VenOrderService;

import redis.clients.jedis.Jedis;

@Controller
@Validated
@RequestMapping("/back_end/ven-order")
public class VenOrderIdController {

    @Autowired
    VenOrderService venOrderSvc;  
    
    @Autowired
    NotifyNow notifyNow;
    
    
    @PostMapping("getOne_For_Display")
    public String getOne_For_Display(@RequestParam("venOrderId") String venOrderId, ModelMap model) {
        
        VenOrderVO venOrderVO = venOrderSvc.getOneVenOrder(Integer.valueOf(venOrderId));

        model.addAttribute("venOrderVO", venOrderVO);
        return "back-end/ven-order/listOneVenOrder";
    }
    
    
    @PostMapping("checkIn")
    public String checkIn(@RequestParam("venOrderId") String venOrderId, ModelMap model) {
        
        VenOrderVO venOrderVO = venOrderSvc.getOneVenOrder(Integer.valueOf(venOrderId));
        venOrderVO.setVenRentStatus((byte)2);
        venOrderSvc.updateVenOrder(venOrderVO);

        model.addAttribute("venOrderVO", venOrderVO);
        return "redirect:/back_end/ven-order/ven_order_page";
    }
    
    
    @PostMapping("checkOut")
    public String checkOut(@RequestParam("venOrderId") String venOrderId, ModelMap model) {
        
        VenOrderVO venOrderVO = venOrderSvc.getOneVenOrder(Integer.valueOf(venOrderId));
        
        // 更改狀態
        venOrderVO.setVenRentStatus((byte)3);
        venOrderSvc.updateVenOrder(venOrderVO);
        
        // 發送Mail
        String to = venOrderVO.getMemVO().getMemEmail();
        String subject = "租借場地滿意度通知";
        String memName = venOrderVO.getMemVO().getMemName();
        
//        String feedbackURL = "http://localhost:8080/front_end/venue/feedbackform?venOrderId="+venOrderId;
        String feedbackURL = "http://zuohuo.ddns.net/front_end/venue/feedbackform?venOrderId="+venOrderId;
        
        String messageText = "Hello! " + memName + " : 感謝您租借我們的場地，請在'20天內'幫我們填寫使用滿意度!!" + feedbackURL;

        String thisId = venOrderId;
        Jedis jedis = null;

          try {
                  jedis = new Jedis("localhost", 6379);
                  jedis.select(7);
            
                  jedis.set(venOrderId, venOrderId);
                  jedis.expire(venOrderId, 1728000L);
          } finally {
              if(jedis != null)
                  jedis.close();
          }
        
        BookingMail bookingMail = new BookingMail();
        bookingMail.sendMail(to, subject, messageText);
        
        // 發送會員通知
        Set<MembershipVO> memVO = new HashSet<>();
        memVO.add(venOrderVO.getMemVO());
        notifyNow.sendNotifyNow(memVO, "一般通知", "您好"+memName+" : 請至Email收信，幫我們填寫使用滿意度");
        
        model.addAttribute("venOrderVO", venOrderVO);
        return "redirect:/back_end/ven-order/ven_order_page";
    }
    
    
    @PostMapping("cancelCheck")
    public String cancelCheck(@RequestParam("venOrderId") String venOrderId, ModelMap model) {
        
        VenOrderVO venOrderVO = venOrderSvc.getOneVenOrder(Integer.valueOf(venOrderId));
        venOrderVO.setOrderStatus((byte)3);
        venOrderSvc.updateVenOrder(venOrderVO);

        // 發送會員通知
        String memName = venOrderVO.getMemVO().getMemName();
        Set<MembershipVO> memVO = new HashSet<>();
        memVO.add(venOrderVO.getMemVO());
        notifyNow.sendNotifyNow(memVO, "一般通知", "您好"+memName+" : 您取消的場地預訂已在審核中，請等候通知");
        
        model.addAttribute("venOrderVO", venOrderVO);
        return "redirect:/back_end/ven-order/ven_order_page";
    }
    
    
    @PostMapping("refundCheck")
    public String refundCheck(@RequestParam("venOrderId") String venOrderId, ModelMap model) {
        
        VenOrderVO venOrderVO = venOrderSvc.getOneVenOrder(Integer.valueOf(venOrderId));
        venOrderVO.setOrderStatus((byte)4);
        venOrderSvc.updateVenOrder(venOrderVO);

        // 發送會員通知
        String memName = venOrderVO.getMemVO().getMemName();
        Set<MembershipVO> memVO = new HashSet<>();
        memVO.add(venOrderVO.getMemVO());
        notifyNow.sendNotifyNow(memVO, "一般通知", "您好"+memName+" : 您取消的場地預訂已退款，請確認");
        
        model.addAttribute("venOrderVO", venOrderVO);
        return "redirect:/back_end/ven-order/ven_order_page";
    }
    
}
