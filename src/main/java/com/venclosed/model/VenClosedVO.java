package com.venclosed.model;

import java.sql.Date;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.Future;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import com.venue.model.VenVO;

@Entity
@Table(name = "venue_closed_date")
public class VenClosedVO implements java.io.Serializable {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "closed_date_id", insertable = false, updatable = false)
	private	Integer	closedDateId;
	
	@ManyToOne
    @JoinColumn(name = "ven_Id", referencedColumnName = "ven_Id")
    private VenVO venVO;
	
	@Column(name = "closed_date")
	@NotNull(message="請選擇日期")
	@Future(message="日期必須是在今日(不含)之後")
	private	Date	closedDate;
	
	@Column(name = "closed_reason")
	@NotEmpty(message="請填寫原因")
	private	String	closedReason;

	
	public VenClosedVO() {
		super();
	}

	public Integer getClosedDateId() {
		return closedDateId;
	}

	public void setClosedDateId(Integer closedDateId) {
		this.closedDateId = closedDateId;
	}
	
	public VenVO getVenVO() {
        return venVO;
    }

    public void setVenVO(VenVO venVO) {
        this.venVO = venVO;
    }

	public Date getClosedDate() {
		return closedDate;
	}

	public void setClosedDate(Date closedDate) {
		this.closedDate = closedDate;
	}

	public String getClosedReason() {
		return closedReason;
	}

	public void setClosedReason(String closedReason) {
		this.closedReason = closedReason;
	}

    @Override
    public int hashCode() {
        return Objects.hash(closedDateId);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        VenClosedVO other = (VenClosedVO) obj;
        return Objects.equals(closedDateId, other.closedDateId);
    }
	
	
}
