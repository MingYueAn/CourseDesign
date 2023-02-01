package control;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import data.MyGetConnection;
import model.Visitor;

/**
 * @Author 作者
 * @Description 说明：修改
 * @Date 时间：2020-12-10
 */
public class Update {
	private Connection connection;
	private PreparedStatement preSQL;

	/**
	 * @Description 说明：构造方法
	 */
	public Update() {
		connection = MyGetConnection.GetConnection("weibo", "root", "");
	}

	/**
	 * @Description 说明：修改访客
	 */
	public Visitor update(Visitor visitor) {
		int ok = 0;
		// SQL语句
		String sqlString = "update visitor set visitor_name=?,visitor_sex=?,visitor_birthday=? where id=?";
		try {
			// 发送sql语句
			preSQL = connection.prepareStatement(sqlString);
			// 设置要传入的参数
			preSQL.setString(1, visitor.getVisitor_name());
			preSQL.setString(2, visitor.getVisitor_sex());
			preSQL.setString(3, visitor.getVisitor_birthday());
			preSQL.setString(4, visitor.getId());
			// 执行sql语句
			ok = preSQL.executeUpdate();
			connection.close();
		} catch (SQLException e) {
			System.out.println("没有修改访客对象");
			System.out.println(e);
		}
		if (ok != 0) {
			System.out.println("已经成功修改访客对象");
		}
		return visitor;
	}
}
