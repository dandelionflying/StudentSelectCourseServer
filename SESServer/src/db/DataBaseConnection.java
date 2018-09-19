package db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DataBaseConnection {
	private static final String URL = "jdbc:mysql://127.0.0.1:3306/scdatabase";
	private static final String USER = "root";
	private static final String PASSWORD = "789123";

	private static Connection conn = null;

	static {
		try {
			// 1.������������
			Class.forName("com.mysql.jdbc.Driver");
			// 2.������ݿ������
			conn = DriverManager.getConnection(URL, USER, PASSWORD);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public static Connection getConnection() {
		return conn;
	}
}
