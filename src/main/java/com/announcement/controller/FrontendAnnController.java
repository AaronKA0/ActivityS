package com.announcement.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.announcement.model.AnnouncementVO;
import com.announcement.service.AnnouncementService;

@Controller
@Validated
@RequestMapping("/Zuo-Huo")
public class FrontendAnnController {

    @Autowired
    AnnouncementService annSvc;  
    
    @ModelAttribute("annListData")
    protected List<AnnouncementVO> annListData(Model model) {
        
        List<AnnouncementVO> list = annSvc.getAll();
        return list;
    }
    
    @PostMapping("getOne_For_Display")
    public String getOne_For_Display(@RequestParam("annId") String annId, ModelMap model) {
        
        AnnouncementVO annVO = annSvc.getOneAnnouncement(Integer.valueOf(annId));

        model.addAttribute("announcementVO", annVO);
        model.addAttribute("getOne_For_Display", "true");
        return "front-end/zuo-huo";

    }
}
