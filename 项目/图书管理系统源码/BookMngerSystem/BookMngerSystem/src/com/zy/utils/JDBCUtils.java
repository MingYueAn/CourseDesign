package com.zy.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * jdbc�ķ�װ
 * 
 * @author ���෢
 *
 */
public class JDBCUtils {
	/**
	 * ��ȡ����
	 * 
	 * @return
	 */

	public static Connection getConnection() {
		Connection ct = null;
		Statement sm = null;
		ResultSet rs = null;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			ct = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/db_book", "root", "");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return ct;
	}

	/**
	 * �ͷ���Դ
	 * 
	 * @param rs
	 * @param stm
	 * @param ps
	 * @param conn
	 */
	public static void close(ResultSet rs, Statement stm, PreparedStatement ps, Connection conn) {
		try {
			if (null != rs) {
				rs.close();
			}
		} catch (Exception e) {
		}
		try {
			if (null != stm) {
				stm.close();
			}
		} catch (Exception e) {
		}
		try {
			if (null != ps) {
				ps.close();
			}
		} catch (Exception e) {
		}
		try {
			if (null != conn) {
				conn.close();
			}
		} catch (Exception e) {
		}
	}
}
