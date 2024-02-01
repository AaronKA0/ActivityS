package com.act.dao;

import com.act.model.ActTypeVO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ActTypeDao extends JpaRepository<ActTypeVO, Integer> {
}
