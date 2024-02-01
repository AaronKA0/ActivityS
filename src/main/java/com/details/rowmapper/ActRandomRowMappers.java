package com.details.rowmapper;

import com.details.dto.ActRandomDTO;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ActRandomRowMappers implements RowMapper {
    @Override
    public Object mapRow(ResultSet rs, int row) throws SQLException {
        ActRandomDTO actRandomDTO = new ActRandomDTO();
        actRandomDTO.setActId(rs.getInt("act_id"));
//        actRandomDTO.setMemId(rs.getInt("mem_id"));
        actRandomDTO.setActName(rs.getString("act_name"));
        actRandomDTO.setActStartTime(rs.getTimestamp("act_start_time"));
        actRandomDTO.setActEndTime(rs.getTimestamp("act_end_time"));
        actRandomDTO.setActLoc(rs.getString("act_loc"));
        actRandomDTO.setActDescr(rs.getString("act_descr"));
//        actRandomDTO.setActUpper(rs.getInt("act_upper"));
        actRandomDTO.setActPic(rs.getBytes("act_pic"));

        return actRandomDTO;
    }
}
