package com.comment.dto;

public class CommentStatus {
     private Byte comStatus = 2;

     private Integer memId;

    public Byte getComStatus() {
        return comStatus;
    }

    public void setComStatus(Byte comStatus) {
        this.comStatus = comStatus;
    }

    public Integer getMemId() {
        return memId;
    }

    public void setMemId(Integer memId) {
        this.memId = memId;
    }
}
