package control;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

import data.MyGetConnection;

/**
 * @Author ����
 * @Description ˵������ȡ������ļ�¼��
 * @Date ʱ�䣺2020-12-2
 */
public class ResultSetNum {

	private Connection connection;
	private PreparedStatement preSQL;
	private Statement statement;
	private ResultSet rs;

	/**
	 * @Description ˵�������췽��
	 */
	public ResultSetNum() {
		connection = MyGetConnection.GetConnection("weibo", "root", "");
	}

	/**
	 * @Description ˵�������������˺Ų���΢�����󣬻�ȡ�����˺�д�ĵ�΢����
	 */
	public Integer resultSetNumWeibo(String writer_id) {
		int count = 0;
		// SQL���
		String sqlString = "select count(*) from weibo where writer_id=?";
		try {
			// ����sql���
			preSQL = connection.prepareStatement(sqlString);
			// ����Ҫ����Ĳ���
			preSQL.setString(1, writer_id);
			// ִ��sql���
			rs = preSQL.executeQuery();
			if (rs.next()) {
				count = rs.getInt(1);
			}
			connection.close();
		} catch (Exception e) {
			System.out.println("û�и��ݶ����˺Ų���΢�����󣬻�ȡ������˺���ص�΢����");
			System.out.println(e);
		}
		return count;
	}

	/**
	 * @Description ˵������ȡ�ÿ���
	 */
	public Integer resultSetNumVisitor() {
		int count = 0;
		try {
			statement = connection.createStatement();
			rs = statement.executeQuery("select count(*) from visitor");
			if (rs.next()) {
				count = rs.getInt(1);
			}
		} catch (Exception e) {
			System.out.println("û�л�ȡ�ÿ���");
			System.out.println(e);
		}
		return count;
	}

	/**
	 * @Description ˵������ȡ��ע��˿������ͨ��Attention_id
	 */
	public Integer resultSetNumAttention(String attention_id) {
		int count = 0;
		// SQL���
		String sqlString = "select count(*) from attentionfans where attention_id=?";
		try {
			// ����sql���
			preSQL = connection.prepareStatement(sqlString);
			// ����Ҫ����Ĳ���
			preSQL.setString(1, attention_id);
			// ִ��sql���
			rs = preSQL.executeQuery();
			if (rs.next()) {
				count = rs.getInt(1);
			}
			connection.close();
		} catch (Exception e) {
			System.out.println("û�л�ȡ��ע��˿������ͨ��Attention_id");
			System.out.println(e);
		}
		return count;
	}

	/**
	 * @Description ˵������ȡ��ע��˿������ͨ��Fans_id
	 */
	public Integer resultSetNumFans(String fans_id) {
		int count = 0;
		// SQL���
		String sqlString = "select count(*) from attentionfans where fans_id=?";
		try {
			// ����sql���
			preSQL = connection.prepareStatement(sqlString);
			// ����Ҫ����Ĳ���
			preSQL.setString(1, fans_id);
			// ִ��sql���
			rs = preSQL.executeQuery();
			if (rs.next()) {
				count = rs.getInt(1);
			}
			connection.close();
		} catch (Exception e) {
			System.out.println("û�л�ȡ��ע��˿������ͨ��Fans_id");
			System.out.println(e);
		}
		return count;
	}
}
