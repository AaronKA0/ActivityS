package com.comment.rowmapper;

import com.comment.model.CommentVO;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class CommentJoinRowMapper implements RowMapper<CommentVO> {
    @Override
    public CommentVO mapRow(ResultSet rs, int rowNum) throws SQLException {
        CommentVO comment = new CommentVO();

        comment.setComId(rs.getInt("com_id"));
        comment.setActId(rs.getInt("act_id"));
        comment.setMemId(rs.getInt("mem_id"));
        comment.setComReplyId(rs.getInt("com_reply_id"));
        comment.setComContent(rs.getString("com_content"));
        comment.setComTime(rs.getTimestamp("com_time"));
        comment.setComStatus(rs.getByte("com_status"));

        comment.setMemName(rs.getString("mem_name"));
        comment.setMemPic(rs.getBytes("mem_pic"));

        return comment;
    }
}
