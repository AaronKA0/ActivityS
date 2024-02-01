package com.actreport.dto;

import javax.validation.constraints.NotNull;

public class ReportStatus {
    @NotNull
    private Byte repStatus;

    public Byte getRepStatus() {
        return repStatus;
    }

    public void setRepStatus(Byte repStatus) {
        this.repStatus = repStatus;
    }

    @Override
    public String toString() {
        return "CommentReportStatus{" +
                "repStatus=" + repStatus +
                '}';
    }
}
