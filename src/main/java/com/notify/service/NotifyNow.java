package com.notify.service;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.membership.model.MembershipVO;
import com.notify.model.NotifyVO;


@Service
public class NotifyNow {
    
    @Autowired
    NotifyService notifySvc;
    
    public void sendNotifyNow(Set<MembershipVO> memVO, String title, String content) {
        
        for (MembershipVO mem : memVO) {
            NotifyVO notifyVO = new NotifyVO(mem, title, content);
            
            notifySvc.addNotify(notifyVO);
            NotifyWebSocket.sendNotification(notifyVO);
            System.out.println("傳送成功");
            System.out.println(notifyVO);
        }
         
        
    }
    
}
