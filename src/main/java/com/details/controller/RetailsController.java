package com.details.controller;

import com.details.dto.ActDTO;
import com.details.dto.ActRandomDTO;
import com.details.service.IRetailsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.server.ResponseStatusException;

import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Base64;
import java.util.List;

@Controller
//@RequestMapping("/CHA104G3")
@Slf4j
public class RetailsController {

    @Autowired
    private IRetailsService retailsService;

    @GetMapping("/activity/{actId}")
    public String actDetail(@PathVariable Integer actId, Model model, HttpSession session) {
        ActDTO act = retailsService.getDetail(actId);

        //格式化活動開始跟結束日期
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        String actStartTime = dateFormat.format(act.getActStartTime());
        String actEndTime = dateFormat.format(act.getActEndTime());
        model.addAttribute("actStartTime", actStartTime);
        model.addAttribute("actEndTime", actEndTime);

        model.addAttribute("act", act);

        //處理會員DB的大頭照如果為null的話
        String base64Pic;
        if (act.getMemPic() != null) {
            base64Pic = Base64.getEncoder().encodeToString(act.getMemPic());
            model.addAttribute("memPic", base64Pic);
        } else {
            String defPic = "/static/front-end/defaultPic/defMemPic.jpg";
            try {
                Resource resource = new ClassPathResource(defPic);
                byte[] defPicBytes = StreamUtils.copyToByteArray(resource.getInputStream());
                base64Pic = Base64.getEncoder().encodeToString(defPicBytes);
                model.addAttribute("memPic", base64Pic);
            } catch (IOException e) {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND);
            }
        }

        //模擬從session取會員id
//        Integer testMemId = 1;
//        session.setAttribute("memId", testMemId);

        //判斷會員有沒有登入過 沒有的話將留言牆輸入框隱藏
        Integer memId = (Integer) session.getAttribute("memId");
        if (memId != null) {
            model.addAttribute("user", memId);
        } else {
            model.addAttribute("user", null);
        }
        return "front-end/actdetails/actDetails";
    }

    @GetMapping("/activity/images/{actId}")
    public ResponseEntity<byte[]> getActPic(@PathVariable Integer actId) {

        ActDTO act = retailsService.getDetail(actId);
        byte[] actPic = act.getActPic();

        //處理會員沒有放活動照片 給預設圖
        if (actPic == null) {
            String defActPic = "/static/front-end/defaultPic/defActPic.png";
            try {
                Resource resource = new ClassPathResource(defActPic);
                byte[] defPicBytes = StreamUtils.copyToByteArray(resource.getInputStream());
                actPic = defPicBytes;
            } catch (IOException e) {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND);
            }
            return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.IMAGE_PNG).body(actPic);
        } else {
            return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.IMAGE_PNG).body(actPic);
        }
    }

    @GetMapping("/activity/random")
    public ResponseEntity<List<ActRandomDTO>> randomFourAct(@RequestParam Integer actTypeId,
                                                            @RequestParam Integer actId) {

        List<ActRandomDTO> actList = retailsService.randomFourAct(actTypeId, actId);

        return ResponseEntity.status(HttpStatus.OK).body(actList);
    }
}
