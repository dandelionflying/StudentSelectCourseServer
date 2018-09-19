package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import db.DataBaseConnection;
import model.Teacher;

public class TeacherDao {

	Connection conn = DataBaseConnection.getConnection();
	PreparedStatement ptmt;

	public void addTeacher(Teacher g) throws SQLException {
		conn = DataBaseConnection.getConnection();
		String sql = "insert into teacher (teacher_id,teacher_name,teacher_password,teacher_institute) values(default,?,?,?,?)";
		ptmt = conn.prepareStatement(sql);
		ptmt.setString(1, g.getUserID());
		ptmt.setString(2, g.getUserName());
		ptmt.setString(3, g.getUserPSW());
		ptmt.setString(4, g.getInstituteOf());

		ptmt.execute();
		ptmt.close();

	}

	public void updateTeacher(Teacher g) throws SQLException {
		conn = DataBaseConnection.getConnection();
		String sql = "update teacher set teacher_name=?,teacher_password=?,teacher_institute=? where teacher_id=?";
		ptmt = conn.prepareStatement(sql);

		ptmt.setString(1, g.getUserName());
		ptmt.setString(2, g.getUserPSW());
		ptmt.setString(3, g.getInstituteOf());
		ptmt.setString(4, g.getUserID());

		ptmt.execute();
		ptmt.close();
	}

	public void delTeacher(String id) throws SQLException {
		conn = DataBaseConnection.getConnection();
		String sql = "delete from teacher where teacher_id=?";
		ptmt = conn.prepareStatement(sql);
		ptmt.setString(1, id);

		ptmt.execute();
		ptmt.close();

	}

	public List<Teacher> query() throws SQLException {
		List<Teacher> result = new ArrayList<Teacher>();

		conn = DataBaseConnection.getConnection();
		StringBuilder sb = new StringBuilder();
		sb.append("select * from teacher ");

		ptmt = conn.prepareStatement(sb.toString());

		ResultSet rs = ptmt.executeQuery();

		Teacher g = null;
		while (rs.next()) {
			g = new Teacher();
			g.setUserID(rs.getString("teacher_id"));
			g.setUserName(rs.getString("teacher_name"));
			g.setUserPSW(rs.getString("teacher_password"));
			g.setInstituteOf(rs.getString("teacher_institute"));
			result.add(g);
		}

		ptmt.execute();
		ptmt.close();

		return result;
	}

	public Teacher queryId(String id) throws SQLException {
		conn = DataBaseConnection.getConnection();
		String sql = ("select * from teacher where teacher_id=?");

		ptmt = conn.prepareStatement(sql);

		ptmt.setString(1, id);

		ResultSet rs = ptmt.executeQuery();

		Teacher g = null;
		while (rs.next()) {
			g = new Teacher();
			g.setUserID(rs.getString("teacher_id"));
			g.setUserName(rs.getString("teacher_name"));
			g.setUserPSW(rs.getString("teacher_password"));
			g.setInstituteOf(rs.getString("teacher_institute"));
		}

		ptmt.execute();
		ptmt.close();

		return g;
	}

	public List<Teacher> query(String name) throws SQLException {
		List<Teacher> result = new ArrayList<Teacher>();

		conn = DataBaseConnection.getConnection();
		String sql = ("select * from teacher where teacher_name=?");

		ptmt = conn.prepareStatement(sql);

		ptmt.setString(1, name);

		ResultSet rs = ptmt.executeQuery();

		Teacher g = null;
		while (rs.next()) {
			g = new Teacher();
			g.setUserID(rs.getString("teacher_id"));
			g.setUserName(rs.getString("teacher_name"));
			g.setUserPSW(rs.getString("teacher_password"));
			g.setInstituteOf(rs.getString("teacher_institute"));
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
