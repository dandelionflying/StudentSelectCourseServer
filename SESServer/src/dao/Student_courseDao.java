package dao;
/*****************
 * 
 *   学生选课Dao
 * 
 *****************/
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import db.DataBaseConnection;
import model.Student_course;

public class Student_courseDao {
	Connection conn = DataBaseConnection.getConnection();;
	PreparedStatement ptmt;
	
	// 查询学生是否选择该课
	public Student_course query_course(String studentid, String courseid) throws SQLException {
		String sql = "select * from sc where studentid = ? and courseid = ?";
		ptmt = conn.prepareStatement(sql);
		ptmt.setString(1, studentid);
		ptmt.setString(2, courseid);

		ResultSet rs = ptmt.executeQuery();
		Student_course g = null;
		while (rs.next()) {
			g = new Student_course();
			g.setStudent_id(rs.getString("studentid"));
			g.setCourse_id(rs.getString("courseid"));
		}
		return g;
	}
	//添加选课记录
	public boolean addStudent_course(Student_course g) throws SQLException {
		String sql = "insert into sc (studentid,courseid) values(?,?)";
		ptmt = conn.prepareStatement(sql);
		ptmt.setString(1, g.getStudent_id());
		ptmt.setString(2, g.getCourse_id());

		int i = ptmt.executeUpdate();
		ptmt.close();
		if (i > 0)
			return true;
		else
			return false;
	}
	// 更新选课记录
	public void updateStudent_course(Student_course g, String sid, String cid) throws SQLException {
		String sql = "update student_course set student_id=?,course_id=? where student_id=? and course_id=?";
		ptmt = conn.prepareStatement(sql);

		ptmt.setString(1, g.getStudent_id());
		ptmt.setString(2, g.getCourse_id());
		ptmt.setString(3, sid);
		ptmt.setString(4, cid);

		ptmt.execute();
		ptmt.close();
	}
	
	public void delStudent_course(String sid, String cid) throws SQLException {
		String sql = "delete from student_course where student_id=? and course_id=?";
		ptmt = conn.prepareStatement(sql);
		ptmt.setString(1, sid);
		ptmt.setString(2, cid);

		ptmt.execute();
		ptmt.close();
	}
	// 查询学生已选课程
	public List<Student_course> querySid(String sid) throws SQLException {
		List<Student_course> result = new ArrayList<Student_course>();

		String sql = ("select * from sc where studentid=?");

		ptmt = conn.prepareStatement(sql);

		ptmt.setString(1, sid);

		ResultSet rs = ptmt.executeQuery();

		Student_course g = null;
		while (rs.next()) {
			g = new Student_course();
			g.setStudent_id(rs.getString("studentid"));
			g.setCourse_id(rs.getString("courseid"));
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
