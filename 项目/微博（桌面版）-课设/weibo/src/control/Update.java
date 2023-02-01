package control;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import data.MyGetConnection;
import model.Visitor;

/**
 * @Author ����
 * @Description ˵�����޸�
 * @Date ʱ�䣺2020-12-10
 */
public class Update {
	private Connection connection;
	private PreparedStatement preSQL;

	/**
	 * @Description ˵�������췽��
	 */
	public Update() {
		connection = MyGetConnection.GetConnection("weibo", "root", "");
	}

	/**
	 * @Description ˵�����޸ķÿ�
	 */
	public Visitor update(Visitor visitor) {
		int ok = 0;
		// SQL���
		String sqlString = "update visitor set visitor_name=?,visitor_sex=?,visitor_birthday=? where id=?";
		try {
			// ����sql���
			preSQL = connection.prepareStatement(sqlString);
			// ����Ҫ����Ĳ���
			preSQL.setString(1, visitor.getVisitor_name());
			preSQL.setString(2, visitor.getVisitor_sex());
			preSQL.setString(3, visitor.getVisitor_birthday());
			preSQL.setString(4, visitor.getId());
			// ִ��sql���
			ok = preSQL.executeUpdate();
			connection.close();
		} catch (SQLException e) {
			System.out.println("û���޸ķÿͶ���");
			System.out.println(e);
		}
		if (ok != 0) {
			System.out.println("�Ѿ��ɹ��޸ķÿͶ���");
		}
		return visitor;
	}
}
