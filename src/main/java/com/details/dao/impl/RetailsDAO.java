package com.details.dao.impl;

import com.details.dao.IRetailsDAO;
import com.details.dto.ActDTO;
import com.details.dto.ActRandomDTO;
import com.details.rowmapper.ActRandomRowMappers;
import com.details.rowmapper.ActRowMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
@Slf4j
public class RetailsDAO implements IRetailsDAO {

    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Override
    public ActDTO getDetail(Integer actId) {

        String sql = "SELECT a.act_id, a.mem_id, a.act_name, a.act_start_time, a.act_end_time, a.act_loc, a.act_descr, a.act_upper, a.act_count, a.act_status, " +
                " a.act_cr_time, a.reg_start_time, a.reg_end_time, a.act_pic, a.act_tot_rating, a.act_rate_count, a.act_follow_count, a.lat,a.lon, " +
                " m.mem_pic, m.mem_name, a.act_type_id, t.act_type_name FROM activity a JOIN membership m ON a.mem_id = m.mem_id" +
                " JOIN activity_type t ON a.act_type_id = t.act_type_id WHERE a.act_id = :actId";

        Map<String, Object> map = new HashMap<>();
        map.put("actId", actId);

        List<ActDTO> list = namedParameterJdbcTemplate.query(sql, map, new ActRowMapper());

        if (list.size() > 0) {
            return list.get(0);
        } else {
            return null;
        }
    }

    @Override
    public List<ActRandomDTO> randomFourAct(Integer actTypeId, Integer actId) {

//        String sql = "SELECT * FROM activity WHERE act_type_id = :actTypeId";

        String sql = "SELECT act_id, act_name, act_loc, act_descr, act_start_time, act_end_time, act_pic" +
                " FROM activity WHERE act_type_id = :actTypeId AND act_id <> :actId";

        Map<String, Object> map = new HashMap<>();
        map.put("actTypeId", actTypeId);
        map.put("actId", actId);

        List<ActRandomDTO> list = namedParameterJdbcTemplate.query(sql, map, new ActRandomRowMappers());

        return list;
    }
}
