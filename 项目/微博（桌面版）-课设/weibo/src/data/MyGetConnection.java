package data;

import java.sql.Connection;
import java.sql.DriverManager;

/**
 * @Author ���ߣ�����19102 л���� 190611201
 * @Description ˵����
 * @Date ʱ�䣺2020-11-20
 */
public class MyGetConnection {
	public static Connection GetConnection(String Name, String id, String p) {
		Connection connection = null;
		String uri = "jdbc:mysql://localhost:3306/" + Name + "?userSSL=true&characterEncoding=utf-8";
		try {
			// ����JDBC_MySQL���ݿ�����
			Class.forName("com.mysql.jdbc.Driver");
			// �������ݿ����
			connection = DriverManager.getConnection(uri, id, p);
		} catch (Exception e) {
			System.out.println(e);
		}
		return connection;
	}
}
