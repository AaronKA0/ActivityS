package com.act.model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "activity")
public class ActVO {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "act_id")
    private Integer actId;

    //	@ManyToOne
//	@JoinColumn(name = "mem_id" , referencedColumnName ="mem_id" )
	@Column(name = "mem_id")
    private Integer memId ;

    @Column(name = "act_name", nullable = false, length = 50)
    private String actName;

    //	@Temporal(TemporalType.TIMESTAMP)
    @Column(name = "act_start_time", nullable = false)
    private Timestamp actStartTime;

    //	@Temporal(TemporalType.TIMESTAMP)
    @Column(name = "act_end_time", nullable = false)
    private Timestamp actEndTime;

    @Column(name = "act_loc")
    private String actLoc;

    @Column(name = "act_descr", length = 2000)
    private String actDescr;

    @Column(name = "act_upper")
    private Integer actUpper;

    @Column(name = "act_count")
    private Byte actCount = 0;

    @Column(name = "act_status")
    private Byte actStatus;

    //	@Temporal(TemporalType.TIMESTAMP)
    @Column(name = "act_cr_time")
    private Date actCrTime;

    //	@Temporal(TemporalType.TIMESTAMP)
    @Column(name = "reg_start_time")
    private Timestamp regStartTime;

    //	@Temporal(TemporalType.TIMESTAMP)
    @Column(name = "reg_end_time")
    private Timestamp regEndTime;

    @Column(name = "act_pic" ,columnDefinition = "longblob")
    private byte[] actPic;

    @Column(name = "act_tot_rating")
    private Double actTotRating = 0.0;

    @Column(name = "act_rate_count")
    private Double actRateCount = 0.0;

    @Column(name = "act_follow_count")
    private Integer actFollowCount = 0;

    @Column(name = "lat", precision = 8, scale = 6)
    private BigDecimal lat;

    @Column(name = "lon", precision = 9, scale = 6)
    private BigDecimal lon;

    @Column(name = "act_type_id")
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

	public Byte getActCount() {
		return actCount;
	}

	public void setActCount(Byte actCount) {
		this.actCount = actCount;
	}

	public Byte getActStatus() {
		return actStatus;
	}

	public void setActStatus(Byte actStatus) {
		this.actStatus = actStatus;
	}

	public Date getActCrTime() {
		return actCrTime;
	}

	public void setActCrTime(Date actCrTime) {
		this.actCrTime = actCrTime;
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

	public Double getActRateCount() {
		return actRateCount;
	}

	public void setActRateCount(Double actRateCount) {
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

	public Integer getActType() {
		return actType;
	}

	public void setActType(Integer actType) {
		this.actType = actType;
	}
    
    

//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "act_type_id", referencedColumnName = "act_type_id")
//    private ActTypeVO actType;


}
