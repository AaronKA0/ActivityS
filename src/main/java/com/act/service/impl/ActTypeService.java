package com.act.service.impl;


import com.act.dao.ActTypeDao;
import com.act.model.ActTypeVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ActTypeService {

    @Autowired
    private ActTypeDao actTypeDao;

    public List<ActTypeVO> getAllActTypes() {
        return actTypeDao.findAll();
    }
}
