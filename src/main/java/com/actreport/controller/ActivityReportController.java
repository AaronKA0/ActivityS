package com.actreport.controller;

import com.actreport.constant.ReportTitle;
import com.actreport.dto.ActivityReportQueryParams;
import com.actreport.dto.ActivityReportRequest;
import com.actreport.dto.ReportStatus;
import com.actreport.model.ActivityReportVO;
import com.actreport.service.IActivityReportService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import javax.servlet.http.HttpSession;
import java.io.IOException;

@RestController
@Slf4j
public class ActivityReportController {

    @Autowired
    private IActivityReportService activityReportService;

    @GetMapping("/activityreport")
    public ResponseEntity<Page<ActivityReportVO>> getAll(
            @RequestParam(required = false) Byte repStatus,
            @RequestParam(required = false) Integer memId,
            @RequestParam(required = false) Integer empId,
            @RequestParam(required = false) String sortDirection,
            @PageableDefault(size = 5, sort = "repTime", direction = Sort.Direction.DESC) Pageable pageable
    ) {
        //傳進來是DESC的話替換掉pageable內的Sort
        if ("ASC".equalsIgnoreCase(sortDirection)) {
            pageable = PageRequest.of(
                    pageable.getPageNumber(),
                    pageable.getPageSize(),
                    Sort.by(Sort.Direction.ASC, "repTime")
            );
        }

        ActivityReportQueryParams activityReportQueryParams = new ActivityReportQueryParams();
        activityReportQueryParams.setRepStatus(repStatus);
        activityReportQueryParams.setMemId(memId);
        activityReportQueryParams.setEmpId(empId);

        Page<ActivityReportVO> reportList = activityReportService.getAll(activityReportQueryParams, pageable);

        return ResponseEntity.status(HttpStatus.OK).body(reportList);
    }

    @GetMapping("/activityreport/{repId}")
    public ResponseEntity<ActivityReportVO> findByPrimaryKey(@PathVariable Integer repId) {
        ActivityReportVO activityReport = activityReportService.findByPrimaryKey(repId);
        if (activityReport != null) {
            return ResponseEntity.status(HttpStatus.OK).body(activityReport);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @PostMapping("/activityreport")
    public ResponseEntity<ActivityReportVO> insert(
            @RequestParam("actId") Integer actId,
            @RequestParam("repTitle") ReportTitle repTitle,
            @RequestParam("repContent") String repContent,
            @RequestParam(required = false) MultipartFile repPic,
            HttpSession session
    ) {

        ActivityReportRequest activityReportRequest = new ActivityReportRequest();
        activityReportRequest.setActId(actId);
        activityReportRequest.setRepTitle(repTitle);
        activityReportRequest.setRepContent(repContent);
        // 處理圖片
        if (repPic != null && !repPic.isEmpty()) {
            byte[] repPicBytes = new byte[0]; // 或其他所需的文件處理邏輯
            try {
                repPicBytes = repPic.getBytes();
                activityReportRequest.setRepPic(repPicBytes);
            } catch (IOException e) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
            }
        }

        //模擬從session取出會員id
//        Integer testMemId = 1;
//        session.setAttribute("memId", testMemId);
        Integer memId = (Integer) session.getAttribute("memId");
        activityReportRequest.setMemId(memId);

        ActivityReportVO activityReport = activityReportService.insert(activityReportRequest);

        return ResponseEntity.status(HttpStatus.CREATED).body(activityReport);
    }

    @PutMapping("/activityreport/{repId}")
    public ResponseEntity<ActivityReportVO> update(@PathVariable Integer repId,
                                                   @RequestBody ReportStatus reportStatus,
                                                   HttpSession session) {
        //模擬哪位員工
        Integer testEmpId = 1;
        session.setAttribute("empId", testEmpId);
        Integer empId = (Integer)session.getAttribute("empId");
        reportStatus.setEmpId(empId);

        ActivityReportVO activityReport = activityReportService.update(repId, reportStatus);

        return ResponseEntity.status(HttpStatus.OK).body(activityReport);
    }

    @GetMapping("/activityreport/{repId}/pic")
    public ResponseEntity<byte[]> getPic(@PathVariable Integer repId) {

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.IMAGE_PNG);

        byte[] pic = activityReportService.getPic(repId);
        if (pic != null) {
            return ResponseEntity.status(HttpStatus.OK).headers(headers).body(pic);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

}
