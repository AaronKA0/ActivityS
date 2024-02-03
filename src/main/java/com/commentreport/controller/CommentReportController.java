package com.commentreport.controller;

import com.commentreport.dto.CommentReportQueryParams;
import com.commentreport.dto.CommentReportRequest;
import com.commentreport.dto.CommentReportStatus;
import com.commentreport.model.CommentReportVO;
import com.commentreport.service.ICommentReportService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

@RestController
@Slf4j
public class CommentReportController {

    @Autowired
    private ICommentReportService commentReportService;

    @GetMapping("/commentreport")
    public ResponseEntity<Page<CommentReportVO>> getCommentReports(
            @RequestParam(required = false) Byte repStatus,
            @RequestParam(required = false) Integer memId,
            @RequestParam(required = false) Integer empId,
            @RequestParam(required = false) String sortDirection,
            @RequestParam(required = false) Integer size,
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

        CommentReportQueryParams commentReportQueryParams = new CommentReportQueryParams();
        commentReportQueryParams.setRepStatus(repStatus);
        commentReportQueryParams.setMemId(memId);
        commentReportQueryParams.setEmpId(empId);

        Page<CommentReportVO> commentReportList = commentReportService.getCommentReports(commentReportQueryParams, pageable);

        return ResponseEntity.status(HttpStatus.OK).body(commentReportList);
    }

    @GetMapping("/commentreport/{repId}")
    public ResponseEntity<CommentReportVO> getCommentReport(@PathVariable Integer repId) {

        CommentReportVO commentReport = commentReportService.getCommentReportById(repId);

        if (commentReport != null) {
            return ResponseEntity.status(HttpStatus.OK).body(commentReport);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }


    @PostMapping("/commentreport")
    public ResponseEntity<CommentReportVO> createCommentReport(
            @RequestBody @Valid CommentReportRequest commentReportRequest,
            HttpSession session) {

        //模擬從session取出會員id
        Integer testMemId = 1;
        session.setAttribute("memId", testMemId);
        Integer memId = (Integer) session.getAttribute("memId");
        commentReportRequest.setMemId(memId);

        CommentReportVO commentReport = commentReportService.createCommentReport(commentReportRequest);

        return ResponseEntity.status(HttpStatus.CREATED).body(commentReport);
    }

    @PutMapping("/commentreport/{repId}")
    public ResponseEntity<CommentReportVO> updateCommentReport(@PathVariable Integer repId,
                                                               @RequestBody CommentReportStatus commentReportStatus) {

        CommentReportVO updateCommentReport = commentReportService.updateCommentReport(repId, commentReportStatus);

        return ResponseEntity.status(HttpStatus.OK).body(updateCommentReport);
    }

}
