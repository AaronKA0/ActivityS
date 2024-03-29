package com.ventype.model;

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "venue_type")
public class VenTypeVO implements Serializable {
	
	private static final long serialVersionUID = 5448854661041354856L;
	
	@Id 
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ven_type_id", insertable = false, updatable = false)
	private Integer venTypeId;
	
	@Column(name = "ven_type_name")
	private String venTypeName;
	
	@Column(name = "ven_type_descr")
	private String venTypeDescr;

	public String getVenTypeDescr() {
		return venTypeDescr;
	}

	public void setVenTypeDescr(String venTypeDescr) {
		this.venTypeDescr = venTypeDescr;
	}


	public Integer getVenTypeId() {
		return venTypeId;
	}

	public void setVenTypeId(Integer venTypeId) {
		this.venTypeId = venTypeId;
	}

	public String getVenTypeName() {
		return venTypeName;
	}

	public void setVenTypeName(String venTypeName) {
		this.venTypeName = venTypeName;
	}
	

	// fetch 預設為 LAZY
//	@OneToMany(mappedBy = "venType", cascade = CascadeType.ALL, fetch=FetchType.EAGER)
//	@OrderBy("venId asc")
//	private Set<VenVO> vens = new HashSet<VenVO>();;
//	
//	
//	public Set<VenVO> getVens() {
//		return this.vens;
//	}
//
//	public void setVens(Set<VenVO> vens) {
//		this.vens = vens;
//	}

	@Override
	public int hashCode() {
		return Objects.hash(venTypeId, venTypeName);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		VenTypeVO other = (VenTypeVO) obj;
		return Objects.equals(venTypeId, other.venTypeId) && Objects.equals(venTypeName, other.venTypeName);
	}

	@Override
	public String toString() {
		return "VenTypeVO [venTypeId=" + venTypeId + ", venTypeName=" + venTypeName + "]";
	}
	 
	
	
	

}
