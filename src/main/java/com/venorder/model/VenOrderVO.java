package com.venorder.model;

import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.Digits;
import javax.validation.constraints.Future;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.membership.model.MembershipVO;
import com.venue.model.VenVO;

@Entity
@Table(name = "venue_order")
public class VenOrderVO implements java.io.Serializable {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ven_order_id", insertable = false, updatable = false)
	private Integer venOrderId;
	
	@ManyToOne
    @JoinColumn(name = "ven_id", referencedColumnName = "ven_id")
    private VenVO venVO;
	
	@ManyToOne
    @JoinColumn(name = "mem_id", referencedColumnName = "mem_id")
	private MembershipVO memVO;
		
	@Column(name = "emp_id")
	private Integer empId;
	
	@JsonFormat(pattern="yyyy-MM-dd", timezone = "Asia/Taipei")
	@Column(name = "order_date")
    @Future(message="日期必須是在今日(不含)之後")
	private Date orderDate;
	
	@Column(name = "mem_phone", columnDefinition = "char")
	@Pattern(regexp = "^09[0-9]{8}$", message = "請輸入正確手機號碼(ex：09xxxxxxxx)")
	private String memPhone;
	
	@Column(name = "act_descr")
	@NotEmpty(message="請填寫活動內容概述")
	private String actDescr;
	
	@Column(name = "user_count")
	@NotNull(message="請填寫預估人數")
	@Digits(integer = 2, fraction = 0, message = "不符合人數規定")
	private Integer userCount;
	
	@Column(name = "mem_taxid", columnDefinition = "char")
	private String memTaxid;
	
	@Column(name = "order_time", insertable = false, updatable = false)
	private Timestamp orderTime;
	
	@Column(name = "order_pay_type")
	@NotNull(message="請選擇付款方式")
	private Byte orderPayType;
	
	@Column(name = "mem_transfer_num")
	private String memTransferNum;
	
	@Column(name = "mem_credit_num", columnDefinition = "char")
	private String memCreditNum;
	
	@Column(name = "order_status", insertable = false, updatable = false)
	private Byte orderStatus;
	
	@Column(name = "ven_rent_status", insertable = false, updatable = false)
	private Byte venRentStatus;
	
	@Column(name = "ven_rating")
	private Double venRating;
	
	@Column(name = "ven_com")
	private String venCom;
	
	@Column(name = "ven_com_status")
	private Byte venComStatus;
	
	@Column(name = "ven_com_time")
	private Timestamp venComTime;
	
	@Column(name = "ven_res_fee")
	private BigDecimal venResFee;

	public VenOrderVO() {
		super();
	}
	

	public Integer getVenOrderId() {
		return venOrderId;
	}

	public void setVenOrderId(Integer venOrderId) {
		this.venOrderId = venOrderId;
	}

	public VenVO getVenVO() {
        return venVO;
    }

    public void setVenVO(VenVO venVO) {
        this.venVO = venVO;
    }
    
    public MembershipVO getMemVO() {
        return memVO;
    }

    public void setMemVO(MembershipVO memVO) {
        this.memVO = memVO;
    }

    public Integer getEmpId() {
		return empId;
	}

	public void setEmpId(Integer empId) {
		this.empId = empId;
	}

	public Date getOrderDate() {
		return orderDate;
	}

	public void setOrderDate(Date orderDate) {
		this.orderDate = orderDate;
	}

	public String getMemPhone() {
		return memPhone;
	}

	public void setMemPhone(String memPhone) {
		this.memPhone = memPhone;
	}

	public String getActDescr() {
		return actDescr;
	}

	public void setActDescr(String actDescr) {
		this.actDescr = actDescr;
	}

	public Integer getUserCount() {
		return userCount;
	}

	public void setUserCount(Integer userCount) {
		this.userCount = userCount;
	}

	public String getMemTaxid() {
		return memTaxid;
	}

	public void setMemTaxid(String memTaxid) {
		this.memTaxid = memTaxid;
	}

	public Timestamp getOrderTime() {
		return orderTime;
	}

	public void setOrderTime(Timestamp orderTime) {
		this.orderTime = orderTime;
	}

	public Byte getOrderPayType() {
		return orderPayType;
	}

	public void setOrderPayType(Byte orderPayType) {
		this.orderPayType = orderPayType;
	}

	public String getMemTransferNum() {
		return memTransferNum;
	}

	public void setMemTransferNum(String memTransferNum) {
		this.memTransferNum = memTransferNum;
	}

	public String getMemCreditNum() {
		return memCreditNum;
	}

	public void setMemCreditNum(String memCreditNum) {
		this.memCreditNum = memCreditNum;
	}

	public Byte getOrderStatus() {
		return orderStatus;
	}

	public void setOrderStatus(Byte orderStatus) {
		this.orderStatus = orderStatus;
	}

	public Byte getVenRentStatus() {
		return venRentStatus;
	}

	public void setVenRentStatus(Byte venRentStatus) {
		this.venRentStatus = venRentStatus;
	}

	public Double getVenRating() {
		return venRating;
	}

	public void setVenRating(Double venRating) {
		this.venRating = venRating;
	}

	public String getVenCom() {
		return venCom;
	}

	public void setVenCom(String venCom) {
		this.venCom = venCom;
	}

	public Byte getVenComStatus() {
		return venComStatus;
	}

	public void setVenComStatus(Byte venComStatus) {
		this.venComStatus = venComStatus;
	}

	public Timestamp getVenComTime() {
		return venComTime;
	}

	public void setVenComTime(Timestamp venComTime) {
		this.venComTime = venComTime;
	}

	public BigDecimal getVenResFee() {
		return venResFee;
	}

	public void setVenResFee(BigDecimal venResFee) {
		this.venResFee = venResFee;
	}

	
	
	
	
    @Override
    public int hashCode() {
        return Objects.hash(venOrderId);
    }


    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        VenOrderVO other = (VenOrderVO) obj;
        return Objects.equals(venOrderId, other.venOrderId);
    }
	
	
	
}
