package com.act.service.impl;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.act.model.ActVO;
import com.act.service.IActService;

@Service("GetActService")
public class GetActService {

    @Autowired
    IActService repository;
    
    public List<ActVO> getByStart(Date start, Date end) {
        List<ActVO> actVO = repository.getByStart(start, end);
        System.out.println(actVO);
        return actVO; 
    }
    
}
