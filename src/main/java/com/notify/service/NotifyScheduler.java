package com.notify.service;

import java.sql.Date;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.act.service.impl.GetActService;
import com.actreg.service.impl.ActRegService;
import com.membership.model.MembershipVO;
import com.membership.service.MembershipService;
import com.venorder.model.VenOrderVO;
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
    
    @Autowired
    GetActService getActSrv;
    
    @Autowired
    ActRegService ActRegSrv;
    
    // 活動與場地訂單通知
    @Scheduled(cron = "0 0 0 * * ?")
    public void getBookingNotify() throws InterruptedException {
        
        Integer memId = null;
        
        MembershipVO venMemVO = new MembershipVO();
        Set<MembershipVO> venOrderMemset = new HashSet<>();
        
        Date afterTwoDays = Date.valueOf(LocalDate.now().plusDays(2));
        System.out.println(afterTwoDays);
       
        List<VenOrderVO> bookings = venOrderSrv.getByOrderDate(afterTwoDays);
        
        for(VenOrderVO booking : bookings) {
            memId = (booking.getMemVO()).getMemId();
            venMemVO = memSrv.getOneMembership(memId);
            venOrderMemset.add(venMemVO);
        }
        notifyNow.sendNotifyNow(venOrderMemset, "場地通知", "提醒您在" + afterTwoDays + "有預定場地");
        
        System.out.println("新增完成");
    }    

    
    // 抽獎活動
    @Scheduled(cron = "0 0 12 * * ?")
    public void lottery() throws InterruptedException {
        
        String title = "一般通知";
        String content = "恭喜您獲得免費使用場地入場卷一張";
        
        Jedis jedis = null;

        try {
              jedis = new Jedis("localhost", 6379);
              jedis.select(7);
          
              List<MembershipVO> members = memSrv.getAll();
          
              for (MembershipVO member : members) {
                  String memId = Integer.toString(member.getMemId());
                  jedis.sadd("AllMembers", memId);             
              }
          
              List<String> winners = jedis.srandmember("AllMembers", 3);
              System.out.println(winners);
              
              Set<MembershipVO> memVOset = new HashSet<>();
              
              for (String winner : winners) {
                  Integer winnerId = Integer.valueOf(winner);
                  MembershipVO winnerVO = memSrv.getOneMembership(winnerId);
                  
                  memVOset.add(winnerVO);
                  
                  jedis.sadd("winnerMembers", winnerVO.getMemName());
              }
              notifyNow.sendNotifyNow(memVOset, title, content);
              
          } finally {
              if(jedis != null)
                  jedis.close();
          }
             
    }
    
    
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
