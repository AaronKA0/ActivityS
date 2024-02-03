package com.announcement.model;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import com.emp.model.EmpVO;
import com.venue.model.VenVO;

@Entity
@Table(name = "announcement")
public class AnnouncementVO implements java.io.Serializable {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ann_id", insertable = false , updatable = false)
	private Integer annId;
	
	@ManyToOne
    @JoinColumn(name = "emp_id", referencedColumnName = "emp_id")
    private EmpVO empVO;
	
	@Column(name = "ann_name")
	@NotEmpty(message="請填公告標題")
	private String annName;
	
	@Column(name = "ann_descr")
	@NotEmpty(message="請填公告內容")
	private String annDescr;
	
	@Column(name = "ann_time", insertable = false, updatable = false)
	private Timestamp annTime;

	
	public AnnouncementVO() {
        super();
    }

    public AnnouncementVO(Integer annId, @NotNull(message = "請選擇編號") Integer empId,
            @NotEmpty(message = "請填公告標題") String annName, @NotEmpty(message = "請填公告內容") String annDescr,
            Timestamp annTime) {
        super();
        this.annId = annId;
        this.annName = annName;
        this.annDescr = annDescr;
        this.annTime = annTime;
    }

	
    public Integer getAnnId() {
		return annId;
	}

	public void setAnnId(Integer annId) {
		this.annId = annId;
	}

    public EmpVO getEmpVO() {
        return empVO;
    }

    public void setEmpVO(EmpVO empVO) {
        this.empVO = empVO;
    }

    public String getAnnName() {
		return annName;
	}

	public void setAnnName(String annName) {
		this.annName = annName;
	}

	public String getAnnDescr() {
		return annDescr;
	}

	public void setAnnDescr(String annDescr) {
		this.annDescr = annDescr;
	}

	public Timestamp getAnnTime() {
		return annTime;
	}

	public void setAnnTime(Timestamp annTime) {
		this.annTime = annTime;
	}


	
	
}
