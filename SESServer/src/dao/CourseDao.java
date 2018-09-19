package dao;
/***********
 * 
 * 课程Dao
 * 
 ************/
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import db.DataBaseConnection;
import model.Course;

public class CourseDao {
	Connection conn = DataBaseConnection.getConnection();// 数据库连接
	PreparedStatement ptmt;
	// 更新课程余量
	public boolean updateQuantity(String courseid, int currentQuantity) throws SQLException {
		String sql = "update course set remainding_quantity=? where course_id=?";
		ptmt = conn.prepareStatement(sql);
		ptmt.setInt(1, ++currentQuantity);
		ptmt.setString(2, courseid);
		int flag = ptmt.executeUpdate();
		ptmt.close();
		if (flag > 0)
			return true;
		else
			return false;
	}
	//查询所有课程
	public List<Course> query() throws SQLException {
		List<Course> result = new ArrayList<Course>();

		StringBuilder sb = new StringBuilder();
		sb.append("select * from course ");

		ptmt = conn.prepareStatement(sb.toString());

		ResultSet rs = ptmt.executeQuery();

		Course g = null;
		while (rs.next()) {
			g = new Course();
			g.setCourseID(rs.getString("course_id"));
			g.setCourseName(rs.getString("course_name"));
			g.setCourseCredit(rs.getDouble("course_credit"));
			g.setCourseAddress(rs.getString("course_address"));
			g.setInsitituteOf(rs.getString("course_insititute"));
			g.setMajorOf(rs.getString("course_major"));
			g.setCourseTime(rs.getString("course_time"));
			g.setCourseTeacherId(rs.getString("course_teacherid"));
			g.setStuMaxNum(rs.getInt("course_maxnum"));
			g.setRemaindingQuantity(rs.getInt("remainding_quantity"));
			g.setCourseUniversity(rs.getBoolean("iscommon"));
			g.setCourseType(rs.getString("course_type"));
			result.add(g);
		}

		ptmt.execute();
		ptmt.close();

		return result;
	}
	// 通过id查询课程
	public Course queryId(String id) throws SQLException {
		String sql = ("select * from course where course_id=?");

		ptmt = conn.prepareStatement(sql);

		ptmt.setString(1, id);

		ResultSet rs = ptmt.executeQuery();

		Course g = null;
		while (rs.next()) {
			g = new Course();
			g.setCourseID(rs.getString("course_id"));
			g.setCourseName(rs.getString("course_name"));
			g.setCourseCredit(rs.getDouble("course_credit"));
			g.setCourseAddress(rs.getString("course_address"));
			g.setInsitituteOf(rs.getString("course_insititute"));
			g.setMajorOf(rs.getString("course_major"));
			g.setCourseTime(rs.getString("course_time"));
			g.setCourseTeacherId(rs.getString("course_teacherid"));
			g.setRemaindingQuantity(rs.getInt("remainding_quantity"));
			g.setStuMaxNum(rs.getInt("course_maxnum"));
			g.setCourseUniversity(rs.getBoolean("iscommon"));
			g.setCourseType(rs.getString("course_type"));
		}

		ptmt.execute();
		ptmt.close();

		return g;
	}
	//通过名称查询课程
	public List<Course> query(String name) throws SQLException {
		List<Course> result = new ArrayList<Course>();

		String sql = ("select * from course where course_name=?");

		ptmt = conn.prepareStatement(sql);

		ptmt.setString(1, name);

		ResultSet rs = ptmt.executeQuery();

		Course g = null;

		while (rs.next()) {
			g = new Course();
			g.setCourseID(rs.getString("course_id"));
			g.setCourseName(rs.getString("course_name"));
			g.setCourseCredit(rs.getDouble("course_credit"));
			g.setCourseAddress(rs.getString("course_address"));
			g.setInsitituteOf(rs.getString("course_insititute"));
			g.setMajorOf(rs.getString("course_major"));
			g.setCourseTime(rs.getString("course_time"));
			g.setCourseTeacherId(rs.getString("course_teacherid"));
			g.setRemaindingQuantity(rs.getInt("remainding_quantity"));
			g.setStuMaxNum(rs.getInt("course_maxnum"));
			g.setCourseUniversity(rs.getBoolean("iscommon"));
			g.setCourseType(rs.getString("course_type"));

			result.add(g);
		}

		ptmt.execute();
		ptmt.close();

		return result;
	}
	
	public void closeConnection() {
		try {
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
