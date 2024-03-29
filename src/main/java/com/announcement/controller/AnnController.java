package com.announcement.controller;

import java.io.IOException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.announcement.model.AnnouncementVO;
import com.announcement.service.AnnouncementService;
import com.notify.service.NotifyRedisService;

@Controller
@RequestMapping("/back_end/announcement")
public class AnnController {

    @Autowired
    AnnouncementService annSvc; 
    
    @Autowired
    NotifyRedisService NotifyRedisSvc;  
    
    
    @GetMapping("addAnn")
    public String addAnn(ModelMap model) {
        AnnouncementVO announcementVO = new AnnouncementVO();
        model.addAttribute("announcementVO", announcementVO);
        return "back-end/announcement/addAnn";
    }
    
    @PostMapping("insert")
    public String insert(@Valid AnnouncementVO announcementVO, BindingResult result, ModelMap model
                                                                        ) throws IOException {
        // 輸入錯誤處理
        if (result.hasErrors()) {
            return "back-end/announcement/addAnn";
        }
        
        // 確認新增公告
        annSvc.addAnnouncement(announcementVO);
        
        // 新增會員訊息至 Redis
        // NotifyRedisSvc.addNotifyToRedis(announcementVO, 1800L);
        
        
        List<AnnouncementVO> list = annSvc.getAll();
        model.addAttribute("annListData", list);
        model.addAttribute("success", "- (新增成功)");
        return "redirect:/back_end/announcement/ann_page";  
    }
    
    
    @PostMapping("getOne_For_Update")
    public String getOne_For_Update(@RequestParam("annId") String annId, ModelMap model) {
        
        AnnouncementVO announcementVO = annSvc.getOneAnnouncement(Integer.valueOf(annId));

        model.addAttribute("announcementVO", announcementVO);
        return "back-end/announcement/updateAnn";
    }


    @PostMapping("update")
    public String update(@Valid AnnouncementVO announcementVO, BindingResult result, ModelMap model
                                                                        ) throws IOException {

        /*************************** 1.接收請求參數 - 輸入格式的錯誤處理 ************************/
        if (result.hasErrors()) {
            return "back-end/announcement/updateAnn";
        }
        /*************************** 2.開始修改資料 *****************************************/
//      EmpService empSvc = new EmpService();
        announcementVO.setAnnTime(Timestamp.valueOf(LocalDateTime.now()));
        annSvc.updateAnnouncement(announcementVO);

        /*************************** 3.修改完成,準備轉交(Send the Success view) **************/
        model.addAttribute("success", "- (修改成功)");
        announcementVO = annSvc.getOneAnnouncement(Integer.valueOf(announcementVO.getAnnId()));
        model.addAttribute("announcementVO", announcementVO);
        return "back-end/announcement/listOneAnn";
    }
    

    // 去除BindingResult中某個欄位的FieldError紀錄
    public BindingResult removeFieldError(AnnouncementVO announcementVO, BindingResult result, String removedFieldname) {
        List<FieldError> errorsListToKeep = result.getFieldErrors().stream()
                .filter(fieldname -> !fieldname.getField().equals(removedFieldname))
                .collect(Collectors.toList());
        result = new BeanPropertyBindingResult(announcementVO, "announcementVO");
        for (FieldError fieldError : errorsListToKeep) {
            result.addError(fieldError);
        }
        return result;
    }
    
    
}
