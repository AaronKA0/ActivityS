package com.announcement.controller;

import java.io.IOException;
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

@Controller
@RequestMapping("/announcement")
public class AnnController {

    @Autowired
    AnnouncementService annSvc;  
    
    
    @GetMapping("addAnn")
    public String addAnn(ModelMap model) {
        AnnouncementVO announcementVO = new AnnouncementVO();
        model.addAttribute("announcementVO", announcementVO);
        return "back-end/announcement/addAnn";
    }
    
    @PostMapping("insert")
    public String insert(@Valid AnnouncementVO announcementVO, BindingResult result, ModelMap model
                                                                        ) throws IOException {

        /*************************** 1.接收請求參數 - 輸入格式的錯誤處理 ************************/
        if (result.hasErrors()) {
            return "back-end/announcement/addAnn";
        }
        /*************************** 2.開始新增資料 *****************************************/
//      EmpService empSvc = new EmpService();
        annSvc.addAnnouncement(announcementVO);
        /*************************** 3.新增完成,準備轉交(Send the Success view) **************/
        List<AnnouncementVO> list = annSvc.getAll();
        model.addAttribute("annListData", list);
        model.addAttribute("success", "- (新增成功)");
        return "redirect:/announcement/listAllAnn";   // 新增成功後重導至IndexController_inSpringBoot.java的第50行@GetMapping("/emp/listAllEmp")
    }
    
    
    @PostMapping("getOne_For_Update")
    public String getOne_For_Update(@RequestParam("annId") String annId, ModelMap model) {
        /*************************** 1.接收請求參數 - 輸入格式的錯誤處理 ************************/
        
        /*************************** 2.開始查詢資料 *****************************************/
//      EmpService empSvc = new EmpService();
        AnnouncementVO announcementVO = annSvc.getOneAnnouncement(Integer.valueOf(annId));

        /*************************** 3.查詢完成,準備轉交(Send the Success view) **************/
        model.addAttribute("announcementVO", announcementVO);
        return "back-end/announcement/updateAnn";   // 查詢完成後轉交update_emp_input.html
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
        annSvc.updateAnnouncement(announcementVO);

        /*************************** 3.修改完成,準備轉交(Send the Success view) **************/
        model.addAttribute("success", "- (修改成功)");
        announcementVO = annSvc.getOneAnnouncement(Integer.valueOf(announcementVO.getAnnId()));
        model.addAttribute("announcementVO", announcementVO);
        return "back-end/announcement/listOneAnn";   // 修改成功後轉交listOneEmp.html
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
