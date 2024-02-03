package com.memRelation;

public class MemRelation {
	
	Integer memIdA;
	
	Integer memIdB;
	
	Byte status;

	public Integer getMemIdA() {
		return memIdA;
	}

	public void setMemIdA(Integer memIdA) {
		this.memIdA = memIdA;
	}

	public Integer getMemIdB() {
		return memIdB;
	}

	public void setMemIdB(Integer memIdB) {
		this.memIdB = memIdB;
	}

	public Byte getStatus() {
		return status;
	}

	public void setStatus(Byte status) {
		this.status = status;
	}

	@Override
	public String toString() {
		return "MemRelation [memIdA=" + memIdA + ", memIdB=" + memIdB + ", status=" + status + "]";
	}
	
	

}
