package com.details.act.repository;

import com.details.act.model.ActVOs;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ActRepositorys extends JpaRepository<ActVOs, Integer> {

    Page<ActVOs> findByMemId(Integer memId, Pageable pageable);

    Page<ActVOs> findByMemIdAndActStatus(Integer memId, Byte actStatus, Pageable pageable);

}
