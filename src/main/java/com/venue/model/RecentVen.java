package com.venue.model;

import java.util.Objects;

public class RecentVen {
	Integer venId;
	Integer memId;
	public Integer getVenId() {
		return venId;
	}
	public void setVenId(Integer venId) {
		this.venId = venId;
	}
	public Integer getMemId() {
		return memId;
	}
	public void setMemId(Integer memId) {
		this.memId = memId;
	}
	@Override
	public String toString() {
		return "RecentVen [venId=" + venId + ", memId=" + memId + "]";
	}
	@Override
	public int hashCode() {
		return Objects.hash(memId, venId);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		RecentVen other = (RecentVen) obj;
		return Objects.equals(venId, other.venId);
	}
	
	

}
