package com.act.controller;

import com.act.util.ActStatusUpdateService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class utilController {
    private final ActStatusUpdateService actStatusUpdateService;

    public utilController(ActStatusUpdateService actStatusUpdateService) {
        this.actStatusUpdateService = actStatusUpdateService;
    }

    @GetMapping("/act/update-act-statuses")
    public String updateActStatuses() {
        actStatusUpdateService.updateActStatuses();
        return "排程器啟動完畢";
    }
}
