package com.notify.service;

import java.sql.Date;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.membership.model.MembershipVO;
import com.membership.service.MembershipService;
import com.notify.model.NotifyVO;
import com.notify.service.NotifyService;
import com.venorder.model.VenOrderVO;
import com.venorder.service.BookingMail;
import com.venorder.service.VenOrderService;

import redis.clients.jedis.Jedis;

@Component
public class NotifyScheduler {   
    
    @Autowired
    private MembershipService memSrv;
    
    @Autowired
    private VenOrderService venOrderSrv;
    
    @Autowired
    private NotifyService notifySrv;
    
    @Autowired
    NotifyNow notifyNow;
    
    // 場地訂單通知
//    @Scheduled(cron = "0/3 * * * * ?")
//    public void getBookingNotify() throws InterruptedException {
//        
//        MembershipVO memVO = memSrv.getOneMembership(16);
//        
//        Set<MembershipVO> memVOset = new HashSet<>();
//        
//        memVOset.add(memVO);
//        
//        notifyNow.sendNotifyNow(memVOset, "系統通知", "通知成功成功成功");
//        
//        Integer memId = null;
//        Date orderDate = null;
//        
//        MembershipVO memVO = new MembershipVO();
//        
////        String d = "2024-02-15";
////        Date afterSomeDays = Date.valueOf(d);
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
    
    
    // 抽獎活動
//    @Scheduled(cron = "0/3 * * * * ?")
//    public void lottery() throws InterruptedException {
//        
//        String title = "一般通知";
//        String content = ":恭喜您獲得免費使用場地入場卷一張";
//        
//        Jedis jedis = null;
//
//        try {
//              jedis = new Jedis("localhost", 6379);
//              jedis.select(7);
//          
//              List<MembershipVO> members = memSrv.getAll();
//          
//              for (MembershipVO member : members) {
//                  String memId = Integer.toString(member.getMemId());
//                  jedis.sadd("AllMembers", memId);             
//              }
//          
//              List<String> winners = jedis.srandmember("AllMembers", 3);
//              System.out.println(winners);
//              
//              for (String winner : winners) {
//                  Integer winnerId = Integer.valueOf(winner);
//                  MembershipVO winnerVO = memSrv.getOneMembership(winnerId);
//                  NotifyVO notifyVO = new NotifyVO(winnerVO, title, winnerVO.getMemName() + content);
//                  notifySrv.addNotify(notifyVO);
//                  jedis.sadd("winnerMembers", winnerVO.getMemName());
//              }
//              
//          } finally {
//              if(jedis != null)
//                  jedis.close();
//          }
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
