package com.announcement.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.announcement.model.AnnouncementVO;
import com.announcement.service.AnnouncementService;

@Controller
@Validated
@RequestMapping("/back_end/announcement")
public class AnnIdController {

    @Autowired
    AnnouncementService annSvc;  
    
    @PostMapping("getOne_For_Display")
    public String getOne_For_Display(@RequestParam("annId") String annId, ModelMap model) {
        
        AnnouncementVO annVO = annSvc.getOneAnnouncement(Integer.valueOf(annId));

        model.addAttribute("announcementVO", annVO);
        return "back-end/announcement/listOneAnn";
    }
}
