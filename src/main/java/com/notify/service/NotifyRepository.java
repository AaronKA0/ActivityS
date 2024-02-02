package com.notify.service;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.announcement.model.AnnouncementVO;
import com.notify.model.NotifyVO;

public interface NotifyRepository extends JpaRepository<NotifyVO, Integer>{
    
    @Query(value = "from NotifyVO order by notifyTime desc")
    List<NotifyVO> findAll();
    
    @Query(value = "from NotifyVO where notifyTitle =:notifyTitle order by notifyTime desc")
    List<NotifyVO> findByTitle(String notifyTitle);
      
    @Query(value = "select n from NotifyVO n where n.memVO.memId = :memId order by n.notifyTime desc")
    List<NotifyVO> findByMemId(Integer memId);
    
    @Query(value = "select n from NotifyVO n where n.memVO.memId = :memId and n.notifyStatus = 1 order by n.notifyTime desc")
    List<NotifyVO> findUnread(Integer memId); 
      
}
