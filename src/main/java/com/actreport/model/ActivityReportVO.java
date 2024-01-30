package com.actreport.model;

import com.details.act.model.ActVOs;
import com.actreport.constant.ReportTitle;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "activity_report")
public class ActivityReportVO implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "rep_id")
    private Integer repId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "act_id", referencedColumnName = "act_id")
    private ActVOs act;

    @Column(name = "mem_id")
    private Integer memId;

    @Column(name = "emp_id")
    private Integer empId;

    @Column(name = "rep_title")
    @Enumerated(EnumType.STRING)
    private ReportTitle repTitle;

    @Column(name = "rep_content")
    private String repContent;

    @Column(name = "rep_pic", columnDefinition = "longblob")
    private byte[] repPic;

    @Column(name = "rep_status")
    private Byte repStatus;

    @Column(name = "rep_time")
    private Date repTime;

	public Integer getRepId() {
		return repId;
	}

	public void setRepId(Integer repId) {
		this.repId = repId;
	}

	public ActVOs getAct() {
		return act;
	}

	public void setAct(ActVOs act) {
		this.act = act;
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

	public Date getRepTime() {
		return repTime;
	}

	public void setRepTime(Date repTime) {
		this.repTime = repTime;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}