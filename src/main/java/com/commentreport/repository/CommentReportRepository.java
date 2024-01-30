package com.commentreport.repository;

import com.commentreport.model.CommentReportVO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentReportRepository extends JpaRepository<CommentReportVO, Integer> {

    //ENUM篩選
//    Page<CommentReportVO> findByRepTitle(CommentReportRepTitle commentReportRepTitle, Pageable pageable);

    //依照狀態查詢
    Page<CommentReportVO> findByRepStatus(Byte repStatus, Pageable pageable);

    Page<CommentReportVO> findByMemId(Integer memId, Pageable pageable);

    Page<CommentReportVO> findAll(Pageable pageable);

    Page<CommentReportVO> findByRepStatusAndMemId(Byte repStatus, Integer memId, Pageable pageable);

    //員工ID查詢
    Page<CommentReportVO> findByEmpId(Integer empId, Pageable pageable);
    Page<CommentReportVO> findByRepStatusAndEmpId(Byte repStatus, Integer empId, Pageable pageable);

}
