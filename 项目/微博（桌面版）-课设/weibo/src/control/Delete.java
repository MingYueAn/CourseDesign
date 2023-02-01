package control;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.swing.JOptionPane;

import data.MyGetConnection;
import model.AttentionFans;
import model.Personal;
import model.Visitor;
import model.Weibo;

/**
 * @Author 作者
 * @Description 说明：删除
 * @Date 时间：2020-12-10
 */
public class Delete {
	private Connection connection;
	private PreparedStatement preSQL;

	/**
	 * @Description 说明：构造方法
	 */
	public Delete() {
		connection = MyGetConnection.GetConnection("weibo", "root", "");
	}

	/**
	 * @Description 说明：删除个人
	 */
	public void delete(Personal personal) {
		int ok = 0;
		// SQL语句
		String sqlString = "delete from personal where id=?";
		try {
			// 发送sql语句
			preSQL = connection.prepareStatement(sqlString);
			// 设置要传入的参数
			preSQL.setString(1, personal.getId());
			// 执行sql语句
			ok = preSQL.executeUpdate();
			connection.close();
		} catch (SQLException e) {
			System.out.println(e);
			System.out.println("未能成功删除个人对象");
		}
		if (ok != 0) {
			System.out.println("已经成功删除个人对象");
		}
	}

	/**
	 * @Description 说明：删除访客
	 */
	public Visitor delete(Visitor visitor) {
		int ok = 0;
		// SQL语句
		String sqlString = "delete from visitor where id=?";
		try {
			// 发送sql语句
			preSQL = connection.prepareStatement(sqlString);
			// 设置要传入的参数
			preSQL.setString(1, visitor.getId());
			// 执行sql语句
			ok = preSQL.executeUpdate();
			connection.close();
		} catch (SQLException e) {
			System.out.println(e);
			System.out.println("未能成功删除访客对象");
		}
		if (ok != 0) {
			System.out.println("已经成功删除访客对象");
		}
		return visitor;
	}

	/**
	 * @Description 说明：删除微博
	 */
	public Weibo delete(Weibo w) {
		int ok = 0;
		// SQL语句
		String sqlString = "delete from weibo where weibo_id=?";
		try {
			// 发送sql语句
			preSQL = connection.prepareStatement(sqlString);
			// 设置要传入的参数
			preSQL.setString(1, w.getWeibo_id());
			// 执行sql语句
			ok = preSQL.executeUpdate();
			connection.close();
		} catch (SQLException e) {
			System.out.println(e);
			System.out.println("未能成功删除微博对象");
		}
		if (ok != 0) {
			System.out.println("已经成功删除微博对象");
		}
		return w;
	}

	/**
	 * @Description 说明：删除关注与粉丝
	 */
	public boolean delete(AttentionFans attentionFans) {
		int ok = 0;
		// SQL语句
		String sqlString = "delete from attentionfans where attention_id=? and fans_id=?";
		try {
			// 发送sql语句
			preSQL = connection.prepareStatement(sqlString);
			// 设置要传入的参数
			preSQL.setString(1, attentionFans.getAttention_id());
			preSQL.setString(2, attentionFans.getFans_id());
			ok = preSQL.executeUpdate();
			connection.close();
		} catch (SQLException e) {
			System.out.println(e);
			System.out.println("未能成功删除关注与粉丝对象");
			return false;
		}
		if (ok != 0) {
			JOptionPane.showMessageDialog(null, "取消关注成功", "消息", JOptionPane.INFORMATION_MESSAGE);
			System.out.println("已经成功删除关注与粉丝对象");
			return true;
		} else {
			return false;
		}
	}

	/**
	 * @Description 说明：删除关注与粉丝——attention_id
	 */
	public void delete1(String attention_id) {
		int ok = 0;
		// SQL语句
		String sqlString = "delete from attentionfans where attention_id=?";
		try {
			// 发送sql语句
			preSQL = connection.prepareStatement(sqlString);
			// 设置要传入的参数
			preSQL.setString(1, attention_id);
			ok = preSQL.executeUpdate();
			connection.close();
		} catch (SQLException e) {
			System.out.println(e);
			System.out.println("未能成功删除关注与粉丝对象");
		}
		if (ok != 0) {
			System.out.println("已经成功删除关注与粉丝对象");
		}
	}

	/**
	 * @Description 说明：删除关注与粉丝——fans_id
	 */
	public void delete2(String fans_id) {
		int ok = 0;
		// SQL语句
		String sqlString = "delete from attentionfans where fans_id=?";
		try {
			// 发送sql语句
			preSQL = connection.prepareStatement(sqlString);
			// 设置要传入的参数
			preSQL.setString(1, fans_id);
			ok = preSQL.executeUpdate();
			connection.close();
		} catch (SQLException e) {
			System.out.println(e);
			System.out.println("未能成功删除关注与粉丝对象");
		}
		if (ok != 0) {
			System.out.println("已经成功删除关注与粉丝对象");
		}
	}

	/**
	 * @Description 说明：清空id号所写的微博
	 */
	public boolean deleteWeibo(String id) {
		int ok = 0;
		boolean b = false;
		// SQL语句
		String sqlString = "delete from weibo where writer_id=?";
		try {
			// 发送sql语句
			preSQL = connection.prepareStatement(sqlString);
			// 设置要传入的参数
			preSQL.setString(1, id);
			// 执行sql语句
			ok = preSQL.executeUpdate();
			connection.close();
		} catch (SQLException e) {
			System.out.println(e);
			b = false;
			System.out.println("未能成功清空id号所写的微博");
		}
		if (ok != 0) {
			b = true;
			System.out.println("已经成功清空id号所写的微博");
		}
		return b;
	}

	/**
	 * @Description 说明：清空id号的关注与粉丝
	 */
	public boolean deleteAttentionFans(String id) {
		int ok = 0;
		boolean b = false;
		// SQL语句
		String sqlString = "delete from attentionFans where attention_id=? and fans_id=?";
		try {
			// 发送sql语句
			preSQL = connection.prepareStatement(sqlString);
			// 设置要传入的参数
			preSQL.setString(1, id);
			preSQL.setString(2, id);
			// 执行sql语句
			ok = preSQL.executeUpdate();
			connection.close();
		} catch (SQLException e) {
			System.out.println(e);
			b = false;
			System.out.println("未能成功清空id号的关注与粉丝");
		}
		if (ok != 0) {
			b = true;
			System.out.println("已经成功清空id号的关注与粉丝");
		}
		return b;
	}
}
