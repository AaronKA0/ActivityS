package com.actreport.dao.impl;//package com.example.report.dao.impl;
//
//import com.example.report.dao.IActivityReportDAO;
//import com.example.report.model.ActivityReportVO;
//
//import javax.naming.Context;
//import javax.naming.InitialContext;
//import javax.naming.NamingException;
//import javax.sql.DataSource;
//import java.sql.Connection;
//import java.sql.PreparedStatement;
//import java.sql.ResultSet;
//import java.sql.SQLException;
//import java.util.ArrayList;
//import java.util.List;
//
//
//public class OldActivityReportDAO implements IActivityReportDAO {
//
//	private static DataSource ds = null;
//	static {
//		try {
//			Context ctx = new InitialContext();
//			ds = (DataSource) ctx.lookup("java:comp/env/jdbc/TestDB3");
//		} catch (NamingException e) {
//			e.printStackTrace();
//		}
//	}
//
//	private static final String INSERT_STMT = "INSERT INTO activity_report "
//			+ "(act_id, mem_id, emp_id, rep_title, rep_content, rep_pic, rep_time) VALUES (?, ?, ?, ?, ?, ?, ?)";
//	private static final String GET_ALL_STMT = "SELECT rep_id, act_id, mem_id, emp_id, rep_title, "
//			+ "rep_content, rep_pic, rep_status, rep_time FROM activity_report order by rep_id";
//	private static final String GET_ONE_STMT = "SELECT rep_id, act_id, mem_id, emp_id, rep_title,"
//			+ "rep_content, rep_pic, rep_status, rep_time FROM activity_report WHERE rep_id = ?";
//	private static final String DELETE = "DELETE FROM activity_report WHERE rep_id = ?";
//	private static final String UPDATE = "UPDATE activity_report set rep_status=? WHERE rep_id = ?";
//
//	@Override
//	public void insert(ActivityReportVO activityReportVO) {
//		Connection con = null;
//		PreparedStatement pstmt = null;
//
//		try {
//
//			con = ds.getConnection();
//			pstmt = con.prepareStatement(INSERT_STMT);
//
//			pstmt.setInt(1, activityReportVO.getActId());
//			pstmt.setInt(2, activityReportVO.getMemId());
//			pstmt.setInt(3, activityReportVO.getEmpId());
//			pstmt.setString(4, activityReportVO.getRepTitle());
//			pstmt.setString(5, activityReportVO.getRepContent());
//			pstmt.setBytes(6, activityReportVO.getRepPic());
//			pstmt.setTimestamp(7, activityReportVO.getRepTime());
//
//			pstmt.executeUpdate();
//
//		} catch (SQLException e) {
//			e.printStackTrace();
//		} finally {
//			if (con != null) {
//				try {
//					con.close();
//				} catch (SQLException e) {
//					e.printStackTrace();
//				}
//			}
//		}
//	}
//
//	@Override
//	public void update(ActivityReportVO activityReportVO) {
//		Connection con = null;
//		PreparedStatement pstmt = null;
//
//		try {
//
//			con = ds.getConnection();
//			pstmt = con.prepareStatement(UPDATE);
//
//			pstmt.setByte(1, activityReportVO.getRepStatus());
//			pstmt.setInt(2, activityReportVO.getRepId());
//
//			pstmt.executeUpdate();
//
//		} catch (SQLException e) {
//			e.printStackTrace();
//		} finally {
//			if (con != null) {
//				try {
//					con.close();
//				} catch (SQLException e) {
//					e.printStackTrace();
//				}
//			}
//		}
//
//	}
//
//	@Override
//	public void delete(Integer repId) {
//
//		Connection con = null;
//		PreparedStatement pstmt = null;
//
//		try {
//
//			con = ds.getConnection();
//			pstmt = con.prepareStatement(DELETE);
//
//			pstmt.setInt(1, repId);
//			pstmt.executeUpdate();
//
//		} catch (SQLException e) {
//			e.printStackTrace();
//		} finally {
//			if (con != null) {
//				try {
//					con.close();
//				} catch (SQLException e) {
//					e.printStackTrace();
//				}
//			}
//		}
//
//	}
//
//	@Override
//	public ActivityReportVO findByPrimaryKey(Integer repId) {
//
//		ActivityReportVO activityReportVO = null;
//		Connection con = null;
//		PreparedStatement pstmt = null;
//		ResultSet rs = null;
//
//		try {
//
//			con = ds.getConnection();
//			pstmt = con.prepareStatement(GET_ONE_STMT);
//
//			pstmt.setInt(1, repId);
//			rs = pstmt.executeQuery();
//
//			while (rs.next()) {
//				activityReportVO = new ActivityReportVO();
//				activityReportVO.setRepId(rs.getInt("rep_id"));
//				activityReportVO.setActId(rs.getInt("act_id"));
//				activityReportVO.setMemId(rs.getInt("mem_id"));
//				activityReportVO.setEmpId(rs.getInt("emp_id"));
//				activityReportVO.setRepTitle(rs.getString("rep_title"));
//				activityReportVO.setRepContent(rs.getString("rep_content"));
//				activityReportVO.setRepPic(rs.getBytes("rep_pic"));
//				activityReportVO.setRepStatus(rs.getByte("rep_status"));
//				activityReportVO.setRepTime(rs.getTimestamp("rep_time"));
//			}
//
//		} catch (SQLException e) {
//			e.printStackTrace();
//		} finally {
//			if (con != null) {
//				try {
//					con.close();
//				} catch (SQLException e) {
//					e.printStackTrace();
//				}
//			}
//		}
//
//		return activityReportVO;
//	}
//
//	@Override
//	public List<ActivityReportVO> getAll() {
//
//		Connection con = null;
//		PreparedStatement pstmt = null;
//		ResultSet rs = null;
//
//		List<ActivityReportVO> list = new ArrayList<ActivityReportVO>();
//
//		try {
//
//			con = ds.getConnection();
//			pstmt = con.prepareStatement(GET_ALL_STMT);
//			rs = pstmt.executeQuery();
//
//			while (rs.next()) {
//				ActivityReportVO activityReportVO = new ActivityReportVO();
//				activityReportVO.setRepId(rs.getInt("rep_id"));
//				activityReportVO.setActId(rs.getInt("act_id"));
//				activityReportVO.setMemId(rs.getInt("mem_id"));
//				activityReportVO.setEmpId(rs.getInt("emp_id"));
//				activityReportVO.setRepTitle(rs.getString("rep_title"));
//				activityReportVO.setRepContent(rs.getString("rep_content"));
//				activityReportVO.setRepPic(rs.getBytes("rep_pic"));
//				activityReportVO.setRepStatus(rs.getByte("rep_status"));
//				activityReportVO.setRepTime(rs.getTimestamp("rep_time"));
//				list.add(activityReportVO);
//			}
//
//		} catch (SQLException e) {
//			e.printStackTrace();
//		} finally {
//			if (con != null) {
//				try {
//					con.close();
//				} catch (SQLException e) {
//					e.printStackTrace();
//				}
//			}
//		}
//		return list;
//	}
//}
