package com.actreg.dto;



import javax.validation.constraints.NotNull;

public class ActRegRequest {

    private Integer memId;
    @NotNull
    private Integer actId;
    @NotNull
    private Integer regTotal;

    private Byte regStatus = 2; //2為審核 3為成功報名

    private Byte isActPart = 2;

    private Double actRating;

    private String regReason;

	public Integer getMemId() {
		return memId;
	}

	public void setMemId(Integer memId) {
		this.memId = memId;
	}

	public Integer getActId() {
		return actId;
	}

	public void setActId(Integer actId) {
		this.actId = actId;
	}

	public Integer getRegTotal() {
		return regTotal;
	}

	public void setRegTotal(Integer regTotal) {
		this.regTotal = regTotal;
	}

	public Byte getRegStatus() {
		return regStatus;
	}

	public void setRegStatus(Byte regStatus) {
		this.regStatus = regStatus;
	}

	public Byte getIsActPart() {
		return isActPart;
	}

	public void setIsActPart(Byte isActPart) {
		this.isActPart = isActPart;
	}

	public Double getActRating() {
		return actRating;
	}

	public void setActRating(Double actRating) {
		this.actRating = actRating;
	}

	public String getRegReason() {
		return regReason;
	}

	public void setRegReason(String regReason) {
		this.regReason = regReason;
	}
    
    
}
