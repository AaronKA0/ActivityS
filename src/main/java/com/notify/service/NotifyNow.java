package com.notify.service;

import java.util.List;
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
        
        NotifyWebSocket notifyWebSocket = new NotifyWebSocket();
        
        for (MembershipVO mem : memVO) {
            NotifyVO notifyVO = new NotifyVO(mem, title, content);
            
            notifySvc.addNotify(notifyVO);
            notifyWebSocket.sendNotification(notifyVO);
            System.out.println("傳送成功");
        }
         
        
    }
    
}
