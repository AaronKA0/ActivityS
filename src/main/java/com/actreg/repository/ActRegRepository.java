package com.actreg.repository;

import com.actreg.model.ActRegVO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ActRegRepository extends JpaRepository<ActRegVO, Integer> {

    ActRegVO findByAct_ActIdAndMemId(Integer actId, Integer memId);

    @Query(value = "SELECT m.mem_name, m.mem_pic, m.mem_id FROM activity_registration r" +
            " JOIN membership m ON r.mem_id = m.mem_id" +
            " WHERE r.act_id = :actId AND r.is_act_part = :isActPart AND r.reg_status = 3", nativeQuery = true)
    List<Object[]> findMembersAndPicAndMemIdByPart(@Param("actId") Integer actId, @Param("isActPart") Integer isActPart);

    // <> = 不等於
    @Query("SELECT r FROM ActRegVO r JOIN r.act a WHERE r.memId = :memId AND r.regStatus <> 4")
    Page<ActRegVO> findRegByMemId(@Param("memId") Integer memId, Pageable pageable);

    Page<ActRegVO> findByMemIdAndRegStatus(Integer memId, Byte regStatus, Pageable pageable);

    Page<ActRegVO> findByAct_ActId(Integer actId, Pageable pageable);

    @Query("SELECT r FROM ActRegVO r JOIN r.act a WHERE r.memId = :memId AND a.actStatus = 1")
    Page<ActRegVO> findRegByMemIdAndActStatus(@Param("memId") Integer memId, Pageable pageable);

}
