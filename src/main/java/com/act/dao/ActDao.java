package com.act.dao;

import com.act.model.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * 使用JPA完成資料庫增刪改查
 * JpaRepository<T, ID>
 * T:表格VO
 * ID:對應主鍵的類型
 * */
@Repository
public interface ActDao extends JpaRepository<ActVO, Integer> {


}
