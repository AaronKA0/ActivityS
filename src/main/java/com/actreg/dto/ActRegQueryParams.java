package com.actreg.dto;

public class ActRegQueryParams {

    private Byte regStatus;
    private Byte actStatus;
    private Integer page;
    private Integer size;

    public Byte getRegStatus() {
        return regStatus;
    }

    public void setRegStatus(Byte regStatus) {
        this.regStatus = regStatus;
    }

    public Byte getActStatus() {
        return actStatus;
    }

    public void setActStatus(Byte actStatus) {
        this.actStatus = actStatus;
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
}
