package com.commentreport.dto;

import javax.validation.constraints.NotNull;

public class CommentReportStatus {
    @NotNull
    private Byte repStatus;

    @NotNull
    private Integer actId;

    public Byte getRepStatus() {
        return repStatus;
    }

    public void setRepStatus(Byte repStatus) {
        this.repStatus = repStatus;
    }

    public Integer getActId() {
        return actId;
    }

    public void setActId(Integer actId) {
        this.actId = actId;
    }

    @Override
    public String toString() {
        return "CommentReportStatus{" +
                "repStatus=" + repStatus +
                '}';
    }
}
