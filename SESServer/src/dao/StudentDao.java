package dao;
/******************
 * 
 *    学生Dao 
 *
 ******************/
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import db.DataBaseConnection;
import model.Student;

public class StudentDao {
	Connection conn = DataBaseConnection.getConnection();
	PreparedStatement ptmt;
	// 重置密码
	public boolean resetPassword(String userid, String password) throws SQLException {
		String sql = "update student set student_password = ? where student_id =?";
		ptmt = conn.prepareStatement(sql);
		ptmt.setString(1, password);
		ptmt.setString(2, userid);

		int flag = ptmt.executeUpdate();
		if (flag > 0)
			return true;
		else
			return false;
	}
	//查询所有学生
	public List<Student> query() throws SQLException {
		List<Student> result = new ArrayList<Student>();
		StringBuilder sb = new StringBuilder();
		sb.append("select * from student ");
		ptmt = conn.prepareStatement(sb.toString());
		ResultSet rs = ptmt.executeQuery();
		Student g = null;
		while (rs.next()) {
			g = new Student();
			g.setUserID(rs.getString("student_id"));
			g.setUserName(rs.getString("student_name"));
			g.setSex(rs.getString("student_sex"));
			g.setUserPSW(rs.getString("student_password"));
			g.setInstitudeOf(rs.getString("student_institude"));
			g.setMajorOf(rs.getString("student_major"));
			g.setClassOf(rs.getString("student_class"));
			g.setUniversityCourse(rs.getBoolean("universityCourse"));
			result.add(g);
		}
		ptmt.execute();
		ptmt.close();
		return result;
	}
	// 查询学生
	public Student queryId(String id) throws SQLException {
		String sql = ("select * from student where student_id=?");
		ptmt = conn.prepareStatement(sql);
		ptmt.setString(1, id);
		ResultSet rs = ptmt.executeQuery();
		Student g = null;
		while (rs.next()) {
			g = new Student();
			g.setUserID(rs.getString("student_id"));
			g.setUserName(rs.getString("student_name"));
			g.setSex(rs.getString("student_sex"));
			g.setUserPSW(rs.getString("student_password"));
			g.setInstitudeOf(rs.getString("student_institude"));
			g.setMajorOf(rs.getString("student_major"));
			g.setClassOf(rs.getString("student_class"));
			g.setUniversityCourse(rs.getBoolean("universityCourse"));
		}
		ptmt.execute();
		ptmt.close();
		return g;
	}
	// 通过姓名查询学生
	public List<Student> query(String name) throws SQLException {
		List<Student> result = new ArrayList<Student>();
		String sql = ("select * from admin where admin_name=?");
		ptmt = conn.prepareStatement(sql);
		ptmt.setString(1, name);
		ResultSet rs = ptmt.executeQuery();
		Student g = null;
		while (rs.next()) {
			g = new Student();
			g.setUserID(rs.getString("student_id"));
			g.setUserName(rs.getString("student_name"));
			g.setSex(rs.getString("student_sex"));
			g.setUserPSW(rs.getString("student_password"));
			g.setInstitudeOf(rs.getString("student_institude"));
			g.setMajorOf(rs.getString("student_major"));
			g.setClassOf(rs.getString("student_class"));
			g.setUniversityCourse(rs.getBoolean("universityCourse"));
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
