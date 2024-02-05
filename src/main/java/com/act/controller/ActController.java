package com.act.controller;

import com.act.dto.ActVoRequest;
import com.act.model.ActVO;
import com.act.service.IActService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.List;

// 標註這是一個 REST 控制器，自動將方法的返回值序列化為 JSON
@RestController
@RequestMapping
public class ActController {

    // 注入 ActService，用於處理與活動相關的業務邏輯
    @Autowired
    private IActService actService;


    // 獲取所有活動
    @GetMapping("/act/allAct")
    public ResponseEntity<List<ActVO>> getAllActivities() {
        List<ActVO> allAct = actService.getAllActs();
        return ResponseEntity.ok(allAct);
    }

    // 根據 ID 獲取單個活動
    // 處理 GET 請求至 "/activities/{id}"，其中 {id} 是活動的ID
    @GetMapping("/act/{id}")
    public ResponseEntity<ActVO> getActivityById(@PathVariable Integer id) {
        // 使用 @PathVariable 獲取 URL 中的 id 參數
        ActVO actVO = actService.getActById(id);
        // 調用 service 層的方法根據 ID 獲取特定活動
        return ResponseEntity.ok().body(actVO);
        // 返回包含找到的活動的 ResponseEntity，HTTP 狀態碼為 200 OK
    }

    
    // 創建新活動
    // 處理 POST 請求至 "/activities"，用於創建新的活動
    @PostMapping("/act/addOrUpdateAct")
    public ResponseEntity<ActVO> createActivity(
            @RequestParam(name = "actId", required = false) Integer actId,
//            @SessionAttribute("memId") Integer memId,
            @RequestParam("actName") String actName,
            @RequestParam("actUpper") Integer actUpper,
            @RequestParam("regStartTime") Timestamp regStartTime,
            @RequestParam("regEndTime") Timestamp regEndTime,
            @RequestParam("actStartTime") Timestamp actStartTime,
            @RequestParam("actEndTime") Timestamp actEndTime,
            @RequestParam("actLoc") String actLoc,
            @RequestParam("actDescr") String actDescr,
            @RequestParam("actType") Integer actType,
            @RequestParam(required = false) MultipartFile actPic,
            HttpSession session


    ) {
        Integer memId = (Integer) session.getAttribute("memId");

        ActVoRequest actVoRequest = new ActVoRequest();
        actVoRequest.setActName(actName);
        actVoRequest.setMemId(memId);//1為測試用 要使用時改成memId
        actVoRequest.setActUpper(actUpper);
        actVoRequest.setRegStartTime(regStartTime);
        actVoRequest.setRegEndTime(regEndTime);
        actVoRequest.setActStartTime(actStartTime);
        actVoRequest.setActEndTime(actEndTime);
        actVoRequest.setActLoc(actLoc);
        actVoRequest.setActDescr(actDescr);
        actVoRequest.setActType(actType);

        // 處理文件數據
        if (actPic != null && !actPic.isEmpty()) {
            byte[] actPicBytes = new byte[0]; // 或其他所需的文件處理邏輯
            try {
                actPicBytes = actPic.getBytes();
                actVoRequest.setActPic(actPicBytes);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

        }else {
            // 若actPic為空值，則使用預設圖片
            String defaultPicPath = "/static/front-end/10/fotor-ai.jpg"; // 根據實際路徑調整
            try {
                // 讀取預設圖片作為byte[]，這裡假設預設圖片放在resources下
                Resource resource = new ClassPathResource(defaultPicPath);
                byte[] defaultPicBytes = StreamUtils.copyToByteArray(resource.getInputStream());
                actVoRequest.setActPic(defaultPicBytes);
            } catch (IOException e) {
                throw new RuntimeException("Failed to load default activity picture", e);
            }
        }

        if (actId != null) {
            // 如果有actId，則執行更新活動
            ActVO updatedAct = actService.updateAct(actId, actVoRequest);
            return ResponseEntity.status(HttpStatus.OK).body(updatedAct);
        } else {
            // 如果沒有actId，則執行新增活動
            ActVO actVO = actService.createAct(actVoRequest);
            return ResponseEntity.status(HttpStatus.CREATED).body(actVO);
        }
    }

}
