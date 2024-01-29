package com.ven;


import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ventype.VenTypeVO;


@Controller
public class VenSpringJPAController {

	@Autowired
	VenService venService;

	@RequestMapping("/testVen")
	@ResponseBody
	public String testVen() throws SQLException {

		// ● 新增
		VenTypeVO venTypeVO = new VenTypeVO(); // 部門POJO
		venTypeVO.setVenTypeName("A");

//		EmpVO empVO1 = new EmpVO();
//		empVO1.setEname("吳永志1");
//		empVO1.setJob("MANAGER");
//		empVO1.setHiredate(java.sql.Date.valueOf("2005-01-01"));
//		empVO1.setSal(new Double(50000));
//		empVO1.setComm(new Double(500));
//		empVO1.setDeptVO(deptVO);
//		empService.addEmp(empVO1);

		// ● 修改
//		EmpVO empVO2 = new EmpVO();
//		empVO2.setEmpno(7001);
//		empVO2.setEname("吳永志2");
//		empVO2.setJob("MANAGER2");
//		empVO2.setHiredate(java.sql.Date.valueOf("2002-01-01"));
//		empVO2.setSal(new Double(20000));
//		empVO2.setComm(new Double(200));
//		empVO2.setDeptVO(deptVO);
//		empService.updateEmp(empVO2);

		// ● 刪除
//		empService.deleteEmp(7001);

		// ● 查詢-findByPrimaryKey (多方emp2.hbm.xml必須設為lazy="false")(優!)
//		EmpVO empVO3 = empService.getOneEmp(7001);
//		System.out.print(empVO3.getEmpno() + ",");
//		System.out.print(empVO3.getEname() + ",");
//		System.out.print(empVO3.getJob() + ",");
//		System.out.print(empVO3.getHiredate() + ",");
//		System.out.print(empVO3.getSal() + ",");
//		System.out.print(empVO3.getComm() + ",");
//		// 注意以下三行的寫法 (優!)
//		System.out.print(empVO3.getDeptVO().getDeptno() + ",");
//		System.out.print(empVO3.getDeptVO().getDname() + ",");
//		System.out.print(empVO3.getDeptVO().getLoc());
//		System.out.println("\n---------------------");

		// ● 查詢-getAll (多方emp2.hbm.xml必須設為lazy="false")(優!)
//    	Iterable<VenVO> list = venService.getAll();
//		for (VenVO ven : list) {
//			System.out.print(ven.getVenType().getVenTypeId() + ",");
//			// 注意以下三行的寫法 (優!)
//			System.out.print(ven.getVenType().getVenTypeName() + ",");
//			System.out.println();
//		}

		return "Test Ven OK";
	}

}