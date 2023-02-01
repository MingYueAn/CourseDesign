package data;

import java.sql.Connection;
import java.sql.DriverManager;

/**
 * @Author 作者：物联19102 谢晓艳 190611201
 * @Description 说明：
 * @Date 时间：2020-11-20
 */
public class MyGetConnection {
	public static Connection GetConnection(String Name, String id, String p) {
		Connection connection = null;
		String uri = "jdbc:mysql://localhost:3306/" + Name + "?userSSL=true&characterEncoding=utf-8";
		try {
			// 加载JDBC_MySQL数据库驱动
			Class.forName("com.mysql.jdbc.Driver");
			// 连接数据库代码
			connection = DriverManager.getConnection(uri, id, p);
		} catch (Exception e) {
			System.out.println(e);
		}
		return connection;
	}
}
