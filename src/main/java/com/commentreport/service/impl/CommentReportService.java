package com.commentreport.service.impl;

import com.comment.model.CommentVO;
import com.comment.model.CommentVOForComRep;
import com.comment.repository.CommentRepository;
import com.comment.service.impl.CommentService;
import com.commentreport.dto.CommentReportQueryParams;
import com.commentreport.dto.CommentReportRequest;
import com.commentreport.dto.CommentReportStatus;
import com.commentreport.model.CommentReportVO;
import com.commentreport.repository.CommentReportRepository;
import com.commentreport.service.ICommentReportService;
import com.membership.model.MembershipVO;
import com.membership.service.MembershipService;
import com.notify.service.NotifyNow;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Slf4j
@Component
public class CommentReportService implements ICommentReportService {

    @Autowired
    private CommentReportRepository commentReportRepository;
    @Autowired
    private CommentRepository commentRepository;
    @Autowired
    private RedisTemplate<String, CommentVO> redisTemplate;
    @Autowired
    private CommentService commentService;

    @Override
    public Page<CommentReportVO> getCommentReports(
            CommentReportQueryParams commentReportQueryParams,
            Pageable pageable) {

        //狀態查詢
        if (commentReportQueryParams.getRepStatus() != null && commentReportQueryParams.getMemId() != null && commentReportQueryParams.getEmpId() == null) {
            return commentReportRepository.findByRepStatusAndMemId(commentReportQueryParams.getRepStatus(), commentReportQueryParams.getMemId(), pageable);

        } else if (commentReportQueryParams.getRepStatus() != null && commentReportQueryParams.getEmpId() == null) {
            return commentReportRepository.findByRepStatus(commentReportQueryParams.getRepStatus(), pageable);

        } else if (commentReportQueryParams.getMemId() != null && commentReportQueryParams.getEmpId() == null) {
            return commentReportRepository.findByMemId(commentReportQueryParams.getMemId(), pageable);
        }

        if (commentReportQueryParams.getEmpId() != null && commentReportQueryParams.getRepStatus() != null) {
            return commentReportRepository.findByRepStatusAndEmpId(commentReportQueryParams.getRepStatus(), commentReportQueryParams.getEmpId(), pageable);

        } else if (commentReportQueryParams.getRepStatus() != null) {
            return commentReportRepository.findByRepStatus(commentReportQueryParams.getRepStatus(), pageable);

        } else if (commentReportQueryParams.getEmpId() != null) {
            return commentReportRepository.findByEmpId(commentReportQueryParams.getEmpId(), pageable);
        }

        return commentReportRepository.findAll(pageable);
    }

    @Override
    public CommentReportVO getCommentReportById(Integer repId) {

        return commentReportRepository.findById(repId).orElse(null);

    }

    @Override
    @Transactional
    public CommentReportVO createCommentReport(CommentReportRequest commentReportRequest) {

        CommentVOForComRep comment = commentRepository.findById(commentReportRequest.getComId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST));

        CommentReportVO commentReport = new CommentReportVO();
        BeanUtils.copyProperties(commentReportRequest, commentReport);
        commentReport.setComment(comment);
        commentReport.setRepTime(new Date());

        return commentReportRepository.save(commentReport);
    }

    @Override
    public CommentReportVO updateCommentReport(Integer repId, CommentReportStatus commentReportStatus) {

        Optional<CommentReportVO> commentReport = commentReportRepository.findById(repId);
        if (commentReport.isPresent()) {
            commentReport.get().setRepStatus(commentReportStatus.getRepStatus());

            switch (commentReportStatus.getRepStatus()) {
                case 1:
                    commentReport.get().getComment().setComStatus(Byte.valueOf("1"));
                    break;
                case 2:
                    commentReport.get().getComment().setComStatus((byte) 2); //2=檢舉成功 不顯示留言
//                  檢舉審核成功清掉redis 把舊資料清掉
                    String redisKey = commentService.buildRedisKey(commentReportStatus.getActId());
                    redisTemplate.delete(redisKey);
                    break;
                case 3:
                    commentReport.get().getComment().setComStatus((byte) 1);
                    break;
            }
            return commentReportRepository.save(commentReport.get());
        } else {
            return null;
        }
    }
}
