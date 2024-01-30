package com.commentreport.service;

import com.commentreport.dto.CommentReportQueryParams;
import com.commentreport.dto.CommentReportRequest;
import com.commentreport.dto.CommentReportStatus;
import com.commentreport.model.CommentReportVO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ICommentReportService {

    Page<CommentReportVO> getCommentReports(CommentReportQueryParams commentReportQueryParams, Pageable pageable);

    CommentReportVO getCommentReportById(Integer repId);

    CommentReportVO createCommentReport(CommentReportRequest commentReportRequest);

    CommentReportVO updateCommentReport(Integer repId, CommentReportStatus commentReportStatus);

}
