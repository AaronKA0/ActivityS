package com.commentreport.dto;

import com.commentreport.constant.CommentReportRepTitle;

public class CommentReportQueryParams {
    private CommentReportRepTitle commentReportRepTitle;
    private Byte repStatus;
    private Integer page;
    private Integer size;

    private Integer memId;

    private Integer empId;

    public CommentReportRepTitle getCommentReportRepTitle() {
        return commentReportRepTitle;
    }

    public void setCommentReportRepTitle(CommentReportRepTitle commentReportRepTitle) {
        this.commentReportRepTitle = commentReportRepTitle;
    }

    public Byte getRepStatus() {
        return repStatus;
    }

    public void setRepStatus(Byte repStatus) {
        this.repStatus = repStatus;
    }

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }

    public Integer getMemId() {
        return memId;
    }

    public void setMemId(Integer memId) {
        this.memId = memId;
    }

    public Integer getEmpId() {
        return empId;
    }

    public void setEmpId(Integer empId) {
        this.empId = empId;
    }
}
