package com.notify.controller;

import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.membership.model.MembershipVO;
import com.membership.service.MembershipService;
import com.notify.model.NotifyVO;
import com.notify.service.NotifyService;
import com.venorder.model.VenOrderVO;
import com.venorder.service.VenOrderService;

@Component
public class NotifyScheduler {   
    
    @Autowired
    private MembershipService memSrv;
    
    @Autowired
    private VenOrderService venOrderSrv;
    
    @Autowired
    private NotifyService notifySrv;
    
    // 場地訂單通知
//    @Scheduled(cron = "0/3 * * * * ?")
//    public void getBookingNotify() throws InterruptedException {
//     
//        Integer memId = null;
//        Date orderDate = null;
//        
//        MembershipVO memVO = new MembershipVO();
//        
//        Date afterSomeDays = Date.valueOf(LocalDate.now().plusDays(3));
//       
//        List<VenOrderVO> bookings = venOrderSrv.getLessDay(afterSomeDays);
//        for(VenOrderVO booking : bookings) {
//            NotifyVO notifyVO = new NotifyVO();
//            memId = (booking.getMemVO()).getMemId();
//            memVO = memSrv.getOneMembership(memId);
//            orderDate = booking.getOrderDate();
//            
//            notifyVO.setMembershipVO(memVO);
//            notifyVO.setNotifyTitle("場地通知");
//            notifyVO.setNotifyContent("提醒您在" + orderDate + "有預定場地");
//            
//            notifySrv.addNotify(notifyVO);  
//        }
//        System.out.println("新增完成");
//        
//    }
    
    
    // 取消結束排程
//    public void cancelScheduleTask(String methodName) {
//        Set<ScheduledTask> setTasks = postProcessor.getScheduledTasks();
//        setTasks.stream().forEach((x)-> {
//            if(x.toString().equals(this.getClass().getName() + "." + methodName)) {
//                x.cancel();
//            }
//        });
//    }
}
