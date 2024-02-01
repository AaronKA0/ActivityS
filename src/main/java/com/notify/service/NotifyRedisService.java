package com.notify.service;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import com.announcement.model.AnnouncementVO;
import com.membership.model.MembershipVO;
import com.membership.service.MembershipService;
import com.notify.model.NotifyVO;


@Service
public class NotifyRedisService {
    
    @Autowired
    @Qualifier("stringRedisTemplateDb7")
    StringRedisTemplate stringRedisTemplateDb7;
    
    @Autowired
    MembershipService memSvc; 
    
    private String memberKey = "";
    
    public void addNotifyToRedis(AnnouncementVO annVO, long expire) {
        
        NotifyVO notify = new NotifyVO();

        String now = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        
        notify.setNotifyTitle(annVO.getAnnName());
        notify.setNotifyContent(annVO.getAnnDescr());      
        notify.setNotifyStatus((byte)1);
        notify.setNotifyTime(Timestamp.valueOf(now));
          
        List<MembershipVO> members = memSvc.getAll();
        
        for(MembershipVO member : members) {
        
            String memUniStr = member.toUniString();
            
            memberKey = memUniStr + ":";
            
            String prefixedKey = memberKey + notify.getNotifyTitle();
            
            String notifyValue = new JSONObject(notify).toString();
            
            stringRedisTemplateDb7.opsForValue().set(prefixedKey, notifyValue);
            
            stringRedisTemplateDb7.expire(prefixedKey, expire, TimeUnit.SECONDS);
        }
        
    }
     
}
