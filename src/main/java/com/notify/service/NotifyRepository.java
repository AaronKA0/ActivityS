package com.notify.service;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.notify.model.NotifyVO;

public interface NotifyRepository extends JpaRepository<NotifyVO, Integer>{

    @Query(value = "from NotifyVO where notifyTitle =:notifyTitle")
    List<NotifyVO> findByTitle(String notifyTitle);
}
