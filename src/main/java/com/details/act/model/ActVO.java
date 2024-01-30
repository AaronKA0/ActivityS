package com.details.act.model;

import com.actreg.model.ActRegVO;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.Set;


@Entity
@Table(name = "activity")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class ActVO implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "act_id")
    private Integer actId;

    @Column(name = "mem_id",nullable = false)
    private Integer memId;

//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "mem_id", referencedColumnName = "mem_id")
//    private MembershipVO membership;

    @Column(name = "act_name", nullable = false)
    private String actName;

    @Column(name = "act_start_time", nullable = false)
    private Date actStartTime;

    @Column(name = "act_end_time", nullable = false)
    private Date actEndTime;

    @Column(name = "act_loc", nullable = false)
    private String actLoc;

    @Column(name = "act_descr", nullable = false)
    private String actDescr;

    @Column(name = "act_upper", nullable = false)
    private Integer actUpper;

    @Column(name = "act_count")
    private Integer actCount;

    @Column(name = "act_status", nullable = false)
    private Byte actStatus;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm",timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
    @Column(name = "act_cr_time", nullable = false)
    private LocalDateTime actCrTime;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm",timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
    @Column(name = "reg_start_time", nullable = false)
    private LocalDateTime regStartTime;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm",timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
    @Column(name = "reg_end_time", nullable = false)
    private LocalDateTime regEndTime;

    @Column(name = "act_pic")
    private byte[] actPic;

    @Column(name = "act_tot_rating")
    private Double actTotRating;

    @Column(name = "act_rate_count")
    private Integer actRateCount;

    @Column(name = "act_follow_count")
    private Integer actFollowCount;

    @Column(name = "lat")
    private BigDecimal lat;

    @Column(name = "lon")
    private BigDecimal lon;

    @Column(name = "act_type_id")
    private Integer actTypeId;

    @OneToMany(mappedBy = "act", cascade = CascadeType.ALL)
    @JsonIgnore
    private Set<ActRegVO> actReg;

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

	public Integer getActUpper() {
		return actUpper;
	}

	public void setActUpper(Integer actUpper) {
		this.actUpper = actUpper;
	}

	public Integer getActCount() {
		return actCount;
	}

	public void setActCount(Integer actCount) {
		this.actCount = actCount;
	}

	public Byte getActStatus() {
		return actStatus;
	}

	public void setActStatus(Byte actStatus) {
		this.actStatus = actStatus;
	}

	public LocalDateTime getActCrTime() {
		return actCrTime;
	}

	public void setActCrTime(LocalDateTime actCrTime) {
		this.actCrTime = actCrTime;
	}

	public LocalDateTime getRegStartTime() {
		return regStartTime;
	}

	public void setRegStartTime(LocalDateTime regStartTime) {
		this.regStartTime = regStartTime;
	}

	public LocalDateTime getRegEndTime() {
		return regEndTime;
	}

	public void setRegEndTime(LocalDateTime regEndTime) {
		this.regEndTime = regEndTime;
	}

	public byte[] getActPic() {
		return actPic;
	}

	public void setActPic(byte[] actPic) {
		this.actPic = actPic;
	}

	public Double getActTotRating() {
		return actTotRating;
	}

	public void setActTotRating(Double actTotRating) {
		this.actTotRating = actTotRating;
	}

	public Integer getActRateCount() {
		return actRateCount;
	}

	public void setActRateCount(Integer actRateCount) {
		this.actRateCount = actRateCount;
	}

	public Integer getActFollowCount() {
		return actFollowCount;
	}

	public void setActFollowCount(Integer actFollowCount) {
		this.actFollowCount = actFollowCount;
	}

	public BigDecimal getLat() {
		return lat;
	}

	public void setLat(BigDecimal lat) {
		this.lat = lat;
	}

	public BigDecimal getLon() {
		return lon;
	}

	public void setLon(BigDecimal lon) {
		this.lon = lon;
	}

	public Integer getActTypeId() {
		return actTypeId;
	}

	public void setActTypeId(Integer actTypeId) {
		this.actTypeId = actTypeId;
	}

	public Set<ActRegVO> getActReg() {
		return actReg;
	}

	public void setActReg(Set<ActRegVO> actReg) {
		this.actReg = actReg;
	}
    
    

}
