package com.details.act.controller;

import com.details.act.dto.ActQueryParams;
import com.details.act.model.ActVOs;
import com.details.act.service.IActServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

@RestController
public class ActControllers {

    @Autowired
    private IActServices actService;

    @GetMapping("/acts")
    public ResponseEntity<Page<ActVOs>> reviewActs(
            @PageableDefault(size = 5, sort = "actStartTime", direction = Sort.Direction.DESC) Pageable pageable,
            @RequestParam(required = false) Byte actStatus,
            HttpSession session
    ) {
        //模擬從session取出會員id
        Integer testMemId = 1;
        session.setAttribute("memId", testMemId);
        Integer memId = (Integer) session.getAttribute("memId");

        ActQueryParams actQueryParams = new ActQueryParams();
        actQueryParams.setMemId(memId);
        actQueryParams.setActStatus(actStatus);

        Page<ActVOs> actRegList = actService.reviewActs(actQueryParams, pageable);

        return ResponseEntity.status(HttpStatus.OK).body(actRegList);
    }

    @PutMapping("/acts/{actId}")
    public ResponseEntity<ActVOs> deleteActReg(@PathVariable Integer actId) {
        ActVOs actVO = actService.deleteActReg(actId);

        if (actVO != null) {
            return ResponseEntity.status(HttpStatus.OK).body(actVO);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
}
