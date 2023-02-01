package control;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

import data.MyGetConnection;

/**
 * @Author 作者
 * @Description 说明：获取结果集的记录数
 * @Date 时间：2020-12-2
 */
public class ResultSetNum {

	private Connection connection;
	private PreparedStatement preSQL;
	private Statement statement;
	private ResultSet rs;

	/**
	 * @Description 说明：构造方法
	 */
	public ResultSetNum() {
		connection = MyGetConnection.GetConnection("weibo", "root", "");
	}

	/**
	 * @Description 说明：根据作者账号查找微博对象，获取作者账号写的的微博数
	 */
	public Integer resultSetNumWeibo(String writer_id) {
		int count = 0;
		// SQL语句
		String sqlString = "select count(*) from weibo where writer_id=?";
		try {
			// 发送sql语句
			preSQL = connection.prepareStatement(sqlString);
			// 设置要传入的参数
			preSQL.setString(1, writer_id);
			// 执行sql语句
			rs = preSQL.executeQuery();
			if (rs.next()) {
				count = rs.getInt(1);
			}
			connection.close();
		} catch (Exception e) {
			System.out.println("没有根据读者账号查找微博对象，获取与读者账号相关的微博数");
			System.out.println(e);
		}
		return count;
	}

	/**
	 * @Description 说明：获取访客数
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
			System.out.println("没有获取访客数");
			System.out.println(e);
		}
		return count;
	}

	/**
	 * @Description 说明：获取关注粉丝数——通过Attention_id
	 */
	public Integer resultSetNumAttention(String attention_id) {
		int count = 0;
		// SQL语句
		String sqlString = "select count(*) from attentionfans where attention_id=?";
		try {
			// 发送sql语句
			preSQL = connection.prepareStatement(sqlString);
			// 设置要传入的参数
			preSQL.setString(1, attention_id);
			// 执行sql语句
			rs = preSQL.executeQuery();
			if (rs.next()) {
				count = rs.getInt(1);
			}
			connection.close();
		} catch (Exception e) {
			System.out.println("没有获取关注粉丝数——通过Attention_id");
			System.out.println(e);
		}
		return count;
	}

	/**
	 * @Description 说明：获取关注粉丝数——通过Fans_id
	 */
	public Integer resultSetNumFans(String fans_id) {
		int count = 0;
		// SQL语句
		String sqlString = "select count(*) from attentionfans where fans_id=?";
		try {
			// 发送sql语句
			preSQL = connection.prepareStatement(sqlString);
			// 设置要传入的参数
			preSQL.setString(1, fans_id);
			// 执行sql语句
			rs = preSQL.executeQuery();
			if (rs.next()) {
				count = rs.getInt(1);
			}
			connection.close();
		} catch (Exception e) {
			System.out.println("没有获取关注粉丝数——通过Fans_id");
			System.out.println(e);
		}
		return count;
	}
}
