package com.details.repository;//package com.example.details.repository;
//
//import com.example.act.model.ActVO;
//import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.data.jpa.repository.Query;
//import org.springframework.data.repository.query.Param;
//
//public interface RetailsRepository extends JpaRepository<ActVO, Integer> {
//
//    @Query(value = "SELECT a.act_id,a.mem_id,a.act_name,a.act_start_time,a.act_end_time,a.act_loc,a.act_descr,a.act_upper,a.act_count,a.act_status," +
//            "a.act_cr_time,a.reg_start_time,a.reg_end_time,a.act_pic,a.act_tot_rating,a.act_rate_count,a.act_follow_count,a.lat,a.lon," +
//            "m.mem_pic FROM activity a JOIN membership m ON a.mem_id = m.mem_id WHERE a.act_id = :actId", nativeQuery = true)
//    Object[] findActWithMem(@Param("actId") Integer actId);
//
//}
