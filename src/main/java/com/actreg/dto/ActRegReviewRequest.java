package com.actreg.dto;

public class ActRegReviewRequest {
    private Integer actRegId;

    private Byte regStatus;

    public Integer getActRegId() {
        return actRegId;
    }

    public void setActRegId(Integer actRegId) {
        this.actRegId = actRegId;
    }

    public Byte getRegStatus() {
        return regStatus;
    }

    public void setRegStatus(Byte regStatus) {
        this.regStatus = regStatus;
    }
}
