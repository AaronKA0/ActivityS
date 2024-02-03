package com.faq.model;



import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "faq")
public class FaqVO implements java.io.Serializable {
	
	private static final long serialVersionUID = 1L;

	public FaqVO() {
	}
	
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "faq_id", insertable = false, updatable = false)
	private Integer faqId;

	@Column(name = "emp_id")
	private Integer empId;

	@Column(name = "faq_que")
	private String faqQue;

	@Column(name = "faq_ans")
	private String faqAns;

	public Integer getFaqId() {
		return faqId;
	}

	public void setFaqId(Integer faqId) {
		this.faqId = faqId;
	}

	public Integer getEmpId() {
		return empId;
	}

	public void setEmpId(Integer empId) {
		this.empId = empId;
	}

	public String getFaqQue() {
		return faqQue;
	}

	public void setFaqQue(String faqQue) {
		this.faqQue = faqQue;
	}

	public String getFaqAns() {
		return faqAns;
	}

	public void setFaqAns(String faqAns) {
		this.faqAns = faqAns;
	}
}
