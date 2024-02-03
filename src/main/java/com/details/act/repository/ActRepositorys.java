package com.details.act.repository;

import com.actreg.dto.MemNameAndPicDTO;
import com.details.act.model.ActVOs;
import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ActRepositorys extends JpaRepository<ActVOs, Integer> {

    Page<ActVOs> findByMemId(Integer memId, Pageable pageable);

    @Query("SELECT a FROM ActVOs a WHERE a.memId = :memId AND (a.actStatus = :status1 OR a.actStatus = :actStatus2)")
    Page<ActVOs> findByMemIdAndActStatus(Integer memId, Byte status1, Byte actStatus2, Pageable pageable);


    //找主辦人的會員資料
    @Query(value = "SELECT m.mem_name, m.mem_pic, m.mem_id FROM activity a " +
            "JOIN membership m ON a.mem_id = m.mem_id WHERE a.act_id = :actId", nativeQuery = true)
    List<Object[]> findMembersAndPicAndMemIdByActId(@Param("actId") Integer actId);


    //最新4筆活動(扣掉官方)
    List<ActVOs> findByMemIdNotOrderByActCrTimeDesc(Integer memId, Pageable pageable);

    //官方最新4筆活動
    List<ActVOs> findByMemIdOrderByActCrTimeDesc(Integer memId, Pageable pageable);
}
