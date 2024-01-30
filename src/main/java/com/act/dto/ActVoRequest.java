package com.act.dto;

import lombok.Data;

import java.sql.Timestamp;

@Data
public class ActVoRequest {

//    @NonNull
    private Integer actId;
//    @NonNull
    private Integer memId;
//    @NonNull
    private String actName;
//    @NonNull
    private Timestamp actStartTime;
//    @NonNull
    private Timestamp actEndTime;
//    @NonNull
    private Timestamp regStartTime;
//    @NonNull
    private Timestamp regEndTime;
//    @NonNull
    private String actLoc;
//    @NonNull
    private String actDescr;
//    @NonNull
    private Integer actUpper;

    private byte[] actPic;

    private Integer actType;
    
    
    
   
    
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






	public String getActName() {
		return actName;
	}






	public void setActName(String actName) {
		this.actName = actName;
	}






	public Timestamp getActStartTime() {
		return actStartTime;
	}






	public void setActStartTime(Timestamp actStartTime) {
		this.actStartTime = actStartTime;
	}






	public Timestamp getActEndTime() {
		return actEndTime;
	}






	public void setActEndTime(Timestamp actEndTime) {
		this.actEndTime = actEndTime;
	}






	public Timestamp getRegStartTime() {
		return regStartTime;
	}






	public void setRegStartTime(Timestamp regStartTime) {
		this.regStartTime = regStartTime;
	}






	public Timestamp getRegEndTime() {
		return regEndTime;
	}






	public void setRegEndTime(Timestamp regEndTime) {
		this.regEndTime = regEndTime;
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






	public Integer getActUpper() {
		return actUpper;
	}






	public void setActUpper(Integer actUpper) {
		this.actUpper = actUpper;
	}






	public byte[] getActPic() {
		return actPic;
	}






	public void setActPic(byte[] actPic) {
		this.actPic = actPic;
	}






	public Integer getActType() {
		return actType;
	}






	public void setActType(Integer actType) {
		this.actType = actType;
	}






	public ActVoRequest() {

    }
}
