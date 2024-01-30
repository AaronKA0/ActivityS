package com.actreg.dto;

import javax.validation.constraints.NotNull;

public class ActRegStatus {
    @NotNull
    private Integer actId;

    private Integer memId;
    private Integer regTotal;
    @NotNull
    private Byte regStatus;

    private String regReason;

    public Byte getRegStatus() {
        return regStatus;
    }

    public void setRegStatus(Byte regStatus) {
        this.regStatus = regStatus;
    }

    public Integer getMemId() {
        return memId;
    }

    public void setMemId(Integer memId) {
        this.memId = memId;
    }

    public Integer getRegTotal() {
        return regTotal;
    }

    public void setRegTotal(Integer regTotal) {
        this.regTotal = regTotal;
    }

    public Integer getActId() {
        return actId;
    }

    public void setActId(Integer actId) {
        this.actId = actId;
    }

    public String getRegReason() {
        return regReason;
    }

    public void setRegReason(String regReason) {
        this.regReason = regReason;
    }
}
