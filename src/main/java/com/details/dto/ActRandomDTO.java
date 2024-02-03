package com.details.dto;

import java.util.Date;

public class ActRandomDTO {

    private Integer actId;

//    private Integer memId;

    private String actName;

    private Date actStartTime;

    private Date actEndTime;

    private String actLoc;

    private String actDescr;

//    private Integer actUpper;

    private byte[] actPic;

    public Integer getActId() {
        return actId;
    }

    public void setActId(Integer actId) {
        this.actId = actId;
    }

    public String getActName() {
        return actName;
    }

    public void setActName(String actName) {
        this.actName = actName;
    }

    public Date getActStartTime() {
        return actStartTime;
    }

    public void setActStartTime(Date actStartTime) {
        this.actStartTime = actStartTime;
    }

    public Date getActEndTime() {
        return actEndTime;
    }

    public void setActEndTime(Date actEndTime) {
        this.actEndTime = actEndTime;
    }

    public String getActLoc() {
        return actLoc;
    }

    public void setActLoc(String actLoc) {
        this.actLoc = actLoc;
    }

    public String getActDescr() {
        return actDescr;
    }

    public void setActDescr(String actDescr) {
        this.actDescr = actDescr;
    }

    public byte[] getActPic() {
        return actPic;
    }

    public void setActPic(byte[] actPic) {
        this.actPic = actPic;
    }
}
