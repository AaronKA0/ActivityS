package com.actreport.dto;

import com.actreport.constant.ReportTitle;

import javax.validation.constraints.NotNull;


public class ActivityReportRequest {

    @NotNull
    private Integer actId;

    private Integer memId;

    private Integer empId; //不需要

    @NotNull
    private ReportTitle repTitle;

    @NotNull
    private String repContent;

    private byte[] repPic;

    private Byte repStatus = 1;

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

	public Integer getEmpId() {
		return empId;
	}

	public void setEmpId(Integer empId) {
		this.empId = empId;
	}

	public ReportTitle getRepTitle() {
		return repTitle;
	}

	public void setRepTitle(ReportTitle repTitle) {
		this.repTitle = repTitle;
	}

	public String getRepContent() {
		return repContent;
	}

	public void setRepContent(String repContent) {
		this.repContent = repContent;
	}

	public byte[] getRepPic() {
		return repPic;
	}

	public void setRepPic(byte[] repPic) {
		this.repPic = repPic;
	}

	public Byte getRepStatus() {
		return repStatus;
	}

	public void setRepStatus(Byte repStatus) {
		this.repStatus = repStatus;
	}

//    private Date repTime;
    
    
    
    
}
