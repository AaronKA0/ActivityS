package com.ven;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.ventype.VenTypeVO;

@Entity
@Table(name = "venue")
public class VenVO implements java.io.Serializable {

//	@Transient
	private static final long serialVersionUID = 7247353469714932743L;
	
	
	@Transient
	private Double venRating;
	
	public void setVenRating(Double venRating) {
		this.venRating = venRating;
	}

	public Double getVenRating() {
		return venRating;
	}
	
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY) // uses auto_increment
	@Column(name = "ven_id", insertable = false, updatable = false)
	private Integer venId;

	// fetch 預設為 EAGER
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name = "ven_type_id", referencedColumnName = "ven_type_id")
	private VenTypeVO venType;

	@Column(name = "ven_name", unique=true)
	private String venName;

	@Column(name = "ven_descr")
	private String venDescr;

	@Column(name = "ven_pic", columnDefinition = "longblob")
	private byte[] venPic;

	@Column(name = "ven_loc")
	private String venLoc;
	
	@Column(name = "ven_city")
	private String venCity;
	
	@Column(name = "ven_district")
	private String venDistrict;

	@Column(name = "ven_price")
	private BigDecimal venPrice;

	@Column(name = "ven_status", nullable = false, columnDefinition = "tinyint default 1")
	private Byte venStatus;

	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss", timezone = "Asia/Taipei")
	@Column(name = "ven_uptime")
	private Timestamp venUptime;

	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss", timezone = "Asia/Taipei")
	@Column(name = "ven_downtime")
	private Timestamp venDowntime;

	@Column(name = "ven_mod_time", insertable = false)
	private Timestamp venModTime;

	@Column(name = "ven_tot_rating", insertable = false)
	private Double venTotRating;

	@Column(name = "ven_rate_count", insertable = false)
	private Integer venRateCount;

	public VenVO() {
	}

	public Integer getVenId() {
		return venId;
	}

	public void setVenId(Integer venId) {
		this.venId = venId;
	}

	public VenTypeVO getVenType() {
		return venType;
	}

	public void setVenType(VenTypeVO venType) {
		this.venType = venType;
	}

	public String getVenName() {
		return venName;
	}

	public void setVenName(String venName) {
		this.venName = venName;
	}

	public String getVenDescr() {
		return venDescr;
	}

	public void setVenDescr(String venDescr) {
		this.venDescr = venDescr;
	}

	public byte[] getVenPic() {
		return venPic;
	}

	public void setVenPic(byte[] venPic) {
		this.venPic = venPic;
	}

	public String getVenLoc() {
		return venLoc;
	}

	public void setVenLoc(String venLoc) {
		this.venLoc = venLoc;
	}
	
	public String getVenCity() {
		return venCity;
	}

	public void setVenCity(String venCity) {
		this.venCity = venCity;
	}
	
	public String getVenDistrict() {
		return venDistrict;
	}

	public void setVenDistrict(String venDistrict) {
		this.venDistrict = venDistrict;
	}

	public BigDecimal getVenPrice() {
		return venPrice;
	}

	public void setVenPrice(BigDecimal venPrice) {
		this.venPrice = venPrice;
	}

	public Byte getVenStatus() {
		return venStatus;
	}

	public void setVenStatus(Byte venStatus) {
		this.venStatus = venStatus;
	}

	public Timestamp getVenUptime() {
		return venUptime;
	}

	public void setVenUptime(Timestamp venUptime) {
		this.venUptime = venUptime;
	}

	public Timestamp getVenDowntime() {
		return venDowntime;
	}

	public void setVenDowntime(Timestamp venDowntime) {
		this.venDowntime = venDowntime;
	}

	public Timestamp getVenModTime() {
		return venModTime;
	}

	public void setVenModTime(Timestamp venModTime) {
		this.venModTime = venModTime;
	}

	public Double getVenTotRating() {
		return venTotRating;
	}

	public void setVenTotRating(Double venTotRating) {
		this.venTotRating = venTotRating;
	}

	public Integer getVenRateCount() {
		return venRateCount;
	}

	public void setVenRateCount(Integer venRateCount) {
		this.venRateCount = venRateCount;
	}



	@Override
	public String toString() {
		return "VenueVO [venId=" + venId + ", venType=" + venType + ", venName=" + venName + ", venDescr="
				+ venDescr + ", venLoc=" + venLoc + ", venPrice=" + venPrice + ", venStatus=" + venStatus
				+ ", venUptime=" + venUptime + ", venDowntime=" + venDowntime + ", venModTime=" + venModTime
				+ ", venTotRating=" + venTotRating + ", venRateCount=" + venRateCount + "]";
	}

	@Override
	public int hashCode() {
		return Objects.hash(venId);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		VenVO other = (VenVO) obj;
		return Objects.equals(venId, other.venId);
	}

}
