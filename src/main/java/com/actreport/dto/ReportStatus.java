package com.actreport.dto;

import javax.validation.constraints.NotNull;

public class ReportStatus {
    @NotNull
    private Byte repStatus;

    private Integer empId;

    public Byte getRepStatus() {
        return repStatus;
    }

    public void setRepStatus(Byte repStatus) {
        this.repStatus = repStatus;
    }

    public Integer getEmpId() {
        return empId;
    }

    public void setEmpId(Integer empId) {
        this.empId = empId;
    }

    @Override
    public String toString() {
        return "CommentReportStatus{" +
                "repStatus=" + repStatus +
                '}';
    }
}
