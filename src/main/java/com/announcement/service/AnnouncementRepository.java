package com.announcement.service;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import com.announcement.model.AnnouncementVO;

public interface AnnouncementRepository extends JpaRepository<AnnouncementVO, Integer>{

//    @Transactional
//    @Modifying
//    @Query(value = "delete from project03 where ann_id =?1", nativeQuery = true)
//    void deleteByAnnId(int annId);

}
