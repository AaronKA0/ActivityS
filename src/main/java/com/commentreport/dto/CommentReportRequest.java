package com.commentreport.dto;

import com.commentreport.constant.CommentReportRepTitle;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class CommentReportRequest {

    @NotNull
    private Integer comId;

    private Integer memId;
    @NotNull
    private CommentReportRepTitle repTitle;
    @NotBlank
    private String repContent;

    private Byte repStatus = 1;

    public Integer getComId() {
        return comId;
    }

    public void setComId(Integer comId) {
        this.comId = comId;
    }

    public Integer getMemId() {
        return memId;
    }

    public void setMemId(Integer memId) {
        this.memId = memId;
    }

    public CommentReportRepTitle getRepTitle() {
        return repTitle;
    }

    public void setRepTitle(CommentReportRepTitle repTitle) {
        this.repTitle = repTitle;
    }

    public String getRepContent() {
        return repContent;
    }

    public void setRepContent(String repContent) {
        this.repContent = repContent;
    }

    public Byte getRepStatus() {
        return repStatus;
    }

    public void setRepStatus(Byte repStatus) {
        this.repStatus = repStatus;
    }
}
