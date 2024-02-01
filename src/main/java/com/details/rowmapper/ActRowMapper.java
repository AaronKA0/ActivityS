package com.details.rowmapper;

import com.details.dto.ActDTO;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ActRowMapper implements RowMapper {
    @Override
    public Object mapRow(ResultSet rs, int row) throws SQLException {
        ActDTO actDTO = new ActDTO();
        actDTO.setActId(rs.getInt("act_id"));
        actDTO.setMemId(rs.getInt("mem_id"));
        actDTO.setActName(rs.getString("act_name"));
        actDTO.setActStartTime(rs.getTimestamp("act_start_time"));
        actDTO.setActEndTime(rs.getTimestamp("act_end_time"));
        actDTO.setActLoc(rs.getString("act_loc"));
        actDTO.setActDescr(rs.getString("act_descr"));
        actDTO.setActUpper(rs.getInt("act_upper"));
        actDTO.setActCount(rs.getInt("act_count"));
        actDTO.setActStatus(rs.getByte("act_status"));
        actDTO.setActCrTime(rs.getTimestamp("act_cr_time").toLocalDateTime());
        actDTO.setRegStartTime(rs.getTimestamp("reg_start_time").toLocalDateTime());
        actDTO.setRegEndTime(rs.getTimestamp("reg_end_time").toLocalDateTime());
        actDTO.setActPic(rs.getBytes("act_pic"));
        actDTO.setActTotRating(rs.getDouble("act_tot_rating"));
        actDTO.setActRateCount(rs.getInt("act_rate_count"));
        actDTO.setActFollowCount(rs.getInt("act_follow_count"));
        actDTO.setLat(rs.getBigDecimal("lat"));
        actDTO.setLon(rs.getBigDecimal("lon"));
        actDTO.setMemPic(rs.getBytes("mem_pic"));
        actDTO.setMemName(rs.getString("mem_name"));
        actDTO.setActTypeId(rs.getInt("act_type_id"));
        actDTO.setActTypeName(rs.getString("act_type_name"));

        return actDTO;
    }
}
