package com.act.controller;

import com.act.model.ActTypeVO;
import com.act.service.impl.ActTypeService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/type")
public class ActTypeController {

    @Autowired
    private ActTypeService actTypeService;
    //獲取所有類別
    @GetMapping("/actTypes")
    public ResponseEntity<List<ActTypeVO>> getAllActTypes() {
        List<ActTypeVO> types = actTypeService.getAllActTypes();
        return ResponseEntity.status(HttpStatus.OK).body(types);
    }
}
