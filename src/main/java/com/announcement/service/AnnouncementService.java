package com.announcement.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.announcement.model.AnnouncementVO;

@Service("AnnouncementService")
public class AnnouncementService {

    @Autowired
    AnnouncementRepository repository;
    
    public void addAnnouncement(AnnouncementVO announcementVO) {
        repository.save(announcementVO);
    }

    public void updateAnnouncement(AnnouncementVO announcementVO) {
        repository.save(announcementVO);
    }

    public AnnouncementVO getOneAnnouncement(Integer annId) {
        Optional<AnnouncementVO> optional = repository.findById(annId);
//      return optional.get();
        return optional.orElse(null);    // public T orElse(T other) : 如果值存在就回傳其值，否則回傳other的值
    }
    
    public List<AnnouncementVO> getAll(){
        return repository.findAll();
    }
    
}
