package com.emp.model;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
//import org.hibernate.validator.constraints.NotEmpty;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Pattern;

import org.springframework.format.annotation.DateTimeFormat;

import com.dept.model.DeptVO;

/*
 * 註1: classpath必須有javax.persistence-api-x.x.jar 
 * 註2: Annotation可以添加在屬性上，也可以添加在getXxx()方法之上
 */

@Entity // 要加上@Entity才能成為JPA的一個Entity類別
@Table(name = "employee") // 代表這個class是對應到資料庫的實體table，目前對應的table是EMP2
public class EmpVO implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	private Integer empId;
	private DeptVO deptVO;
	private String empAcc;
	private String empEmail;
	private String empPwd;
	private String empName;
	private String empPhone;
	private Date empBirthdate;
	private Integer empGender;
	private Date empCrTime;
	private LocalDateTime empLoginTime;
	private byte[] empPic;
	

	public EmpVO() { // 必需有一個不傳參數建構子(JavaBean基本知識)
	}

	@Id // @Id代表這個屬性是這個Entity的唯一識別屬性，並且對映到Table的主鍵
	@Column(name = "emp_id") // @Column指這個屬性是對應到資料庫Table的哪一個欄位 //【非必要，但當欄位名稱與屬性名稱不同時則一定要用】
	@GeneratedValue(strategy = GenerationType.IDENTITY) // @GeneratedValue的generator屬性指定要用哪個generator【strategy的GenerationType,// // 有四種值: AUTO, IDENTITY, SEQUENCE,// TABLE】
	public Integer getEmpId() {
		return this.empId;
	}

	public void setEmpId(Integer empId) {
		this.empId = empId;
	}

	// @ManyToOne (雙向多對一/一對多) 的多對一
	// 【此處預設為 @ManyToOne(fetch=FetchType.EAGER) --> 是指 lazy="false"之意】【注意:此處的預設值與XML版 (p.127及p.132) 的預設值相反】
	// 【如果修改為 @ManyToOne(fetch=FetchType.LAZY) --> 則指 lazy="true" 之意】
	@ManyToOne
	@JoinColumn(name = "deptno") // 指定用來join table的column
	public DeptVO getDeptVO() {
		return deptVO;
	}

	public void setDeptVO(DeptVO deptVO) {
		this.deptVO = deptVO;
	}

	@NotEmpty(message = "帳號: 請勿空白")
	@Pattern(regexp = "^[(\u4e00-\u9fa5)(a-zA-Z0-9_)]{2,10}$", message = "帳號: 只能是中、英文字母、數字和_ , 且長度必需在2到10之間")
	@Column(name = "emp_acc")
	public String getEmpAcc() {
		return empAcc;
	}

	public void setEmpAcc(String empAcc) {
		this.empAcc = empAcc;
	}

	@NotEmpty(message = "Email: 請勿空白")
	@Pattern(regexp = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}$", message = "Email格式不正確")
	@Column(name = "emp_email")
	public String getEmpEmail() {
		return empEmail;
	}

	public void setEmpEmail(String empEmail) {
		this.empEmail = empEmail;
	}

	@NotEmpty(message = "密碼: 請勿空白")
	@Column(name = "emp_pwd")
	public String getEmpPwd() {
		return empPwd;
	}

	public void setEmpPwd(String empPwd) {
		this.empPwd = empPwd;
	}

	@NotEmpty(message = "姓名: 請勿空白")
	@Pattern(regexp = "^[(\u4e00-\u9fa5)(a-zA-Z0-9)]{2,10}$", message = "姓名: 只能是中、英文字母 , 且長度必需在2到10之間")
	@Column(name = "emp_name")
	public String getEmpName() {
		return empName;
	}

	public void setEmpName(String empName) {
		this.empName = empName;
	}

	@NotEmpty(message = "電話號碼不能為空")
	@Pattern(regexp = "^0[0-9]{8,9}$", message = "電話號碼格式不正確")
	@Column(name = "emp_phone")
	public String getEmpPhone() {
		return empPhone;
	}

	public void setEmpPhone(String empPhone) {
		this.empPhone = empPhone;
	}

	@NotNull(message = "生日不得為空")
	@Past(message = "生日必須是過去的日期")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@Column(name = "emp_birthdate")
	public Date getEmpBirthdate() {
		return empBirthdate;
	}

	public void setEmpBirthdate(Date empBirthdate) {
		this.empBirthdate = empBirthdate;
	}

	@NotNull(message = "性別不能為空")
	@Column(name = "emp_gender")
	public Integer getEmpGender() {
		return empGender;
	}

	public void setEmpGender(Integer empGender) {
		this.empGender = empGender;
	}


	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@Column(name = "emp_cr_time")
	public Date getEmpCrTime() {
		return empCrTime;
	}

	public void setEmpCrTime(Date empCrTime) {
		this.empCrTime = empCrTime;
	}

	@DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
	@Column(name = "emp_login_time")
	public LocalDateTime getEmpLoginTime() {
		return empLoginTime;
	}

	public void setEmpLoginTime(LocalDateTime empLoginTime) {
		this.empLoginTime = empLoginTime;
	}

	@Column(name = "emp_pic")
//	@NotEmpty(message="員工照片: 請上傳照片") --> 由EmpController.java 第60行處理錯誤信息
	public byte[] getEmpPic() {
		return empPic;
	}

	public void setEmpPic(byte[] empPic) {
		this.empPic = empPic;
	}

	@Override
	public String toString() {
		return "EmpVO [empId=" + empId + ", empAcc=" + empAcc + ", empEmail=" + empEmail + ", empPwd=" + empPwd
				+ ", empName=" + empName + ", empPhone=" + empPhone + ", empBirthdate=" + empBirthdate + ", empGender="
				+ empGender + ", empCrTime=" + empCrTime + ", empLoginTime=" + empLoginTime + ", empPic="
				+ Arrays.toString(empPic) + ", deptVO=" + deptVO + "]";
	}

	

}
