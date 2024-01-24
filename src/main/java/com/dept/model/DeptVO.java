package com.dept.model;

import java.util.*;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.OrderBy;
import com.emp.model.EmpVO;

@Entity
@Table(name = "dept")
public class DeptVO implements java.io.Serializable {
	private static final long serialVersionUID = 1L;
	
	private Integer deptno;
	private String dname;
	private Set<EmpVO> emps = new HashSet<EmpVO>();
	
	public DeptVO() {
	}

	@Id
	@Column(name = "deptno")
	@GeneratedValue(strategy = GenerationType.IDENTITY) //@GeneratedValue的generator屬性指定要用哪個generator //【strategy的GenerationType, 有四種值: AUTO, IDENTITY, SEQUENCE, TABLE】 
	public Integer getDeptno() {
		return this.deptno;
	}

	public void setDeptno(Integer deptno) {
		this.deptno = deptno;
	}

	@Column(name = "dname")
	public String getDname() {
		return this.dname;
	}

	public void setDname(String dname) {
		this.dname = dname;
	}


	@OneToMany(cascade=CascadeType.ALL, fetch=FetchType.EAGER, mappedBy="deptVO")
	@OrderBy("empId asc")
	//註1:【現在是設定成 cascade="all" lazy="false" inverse="true"之意】
	//註2:【mappedBy="多方的關聯屬性名"：用在雙向關聯中，把關係的控制權反轉】【deptVO是EmpVO的屬性】
	//註3:【原預設為@OneToMany(fetch=FetchType.LAZY, mappedBy="deptVO")之意】--> 【是指原為  lazy="true"  inverse="true"之意】
	//FetchType.EAGER : Defines that data must be eagerly fetched
	//FetchType.LAZY  : Defines that data can be lazily fetched
	public Set<EmpVO> getEmps() {
		return this.emps;
	}

	public void setEmps(Set<EmpVO> emps) {
		this.emps = emps;
	}

}
