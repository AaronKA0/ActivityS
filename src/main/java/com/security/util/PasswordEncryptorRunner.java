package com.security.util;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import com.emp.model.EmpRepository;
import com.emp.model.EmpService;
import com.emp.model.EmpVO;


//@Component
//public class PasswordEncryptorRunner implements CommandLineRunner {
//
//    private final EmpService empService;
//
//    public PasswordEncryptorRunner(EmpService empService) {
//        this.empService = empService;
//    }
//
//    @Override
//    public void run(String... args) throws Exception {
//        // 調用service方法進行加密
//        empService.encryptAllPasswords();
//    }
//}