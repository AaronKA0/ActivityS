package com.actfollowed.dto;

import com.sun.istack.NotNull;

public class ActFollowRequest {

    @NotNull
    private Integer actId;
    @NotNull
    private Integer memId;
    private Byte folStatus = 1;

    public Integer getActId() {
        return actId;
    }

    public void setActId(Integer actId) {
        this.actId = actId;
    }

    public Integer getMemId() {
        return memId;
    }

    public void setMemId(Integer memId) {
        this.memId = memId;
    }

    public Byte getFolStatus() {
        return folStatus;
    }

    public void setFolStatus(Byte folStatus) {
        this.folStatus = folStatus;
    }
}
