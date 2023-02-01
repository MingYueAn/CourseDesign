package control;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JOptionPane;

import data.Constant;
import data.MyGetConnection;
import model.AttentionFans;
import model.Personal;
import model.Visitor;
import model.Weibo;

/**
 * @Author 作者
 * @Description 说明：查找
 * @Date 时间：2020-12-2
 */
public class Select {

	private Connection connection;
	private PreparedStatement preSQL;
	private ResultSet resultSet;

	/**
	 * @Description 说明：构造方法
	 */
	public Select() {
		connection = MyGetConnection.GetConnection("weibo", "root", "");
	}

	/**
	 * @Description 说明：查找个人
	 */
	public Personal select(Personal personal) {
		// SQL语句
		String sqlString = "select * from personal where id = ?";
		try {
			// 发送sql语句
			preSQL = connection.prepareStatement(sqlString);
			// 设置要传入的参数
			preSQL.setString(1, personal.getId());
			// 执行sql语句
			resultSet = preSQL.executeQuery();
			// 检查是否已经注册的用户
			if (resultSet.next()) {
				personal.setId(resultSet.getString(1));
				personal.setPassword(resultSet.getString(2));
				personal.setPower(resultSet.getString(3));
				personal.setLogin_success(Constant.YES_STRING);
			}
			connection.close();
		} catch (SQLException e) {
			System.out.println("没有查找到个人对象");
			System.out.println(e);
		}
		return personal;
	}

	/**
	 * @Description 说明：查找访客
	 */
	public Visitor select(Visitor visitor) {
		// SQL语句
		String sqlString = "select * from visitor where id = ?";
		try {
			// 发送sql语句
			preSQL = connection.prepareStatement(sqlString);
			// 设置要传入的参数
			preSQL.setString(1, visitor.getId());
			// 执行sql语句
			resultSet = preSQL.executeQuery();
			// 检查是否已经注册的用户
			if (resultSet.next()) {
				visitor.setId(resultSet.getString(1));
				visitor.setVisitor_name(resultSet.getString(2));
				visitor.setVisitor_sex(resultSet.getString(3));
				visitor.setVisitor_birthday(resultSet.getString(4));
				visitor.setVisitor_yes_no(Constant.YES_STRING);
			}
			connection.close();
		} catch (SQLException e) {
			System.out.println("没有查找到访客对象");
			System.out.println(e);
		}
		return visitor;
	}

	/**
	 * @Description 说明：查找微博
	 */
	public Weibo select(Weibo w) {
		// SQL语句
		String sqlString = "select * from weibo where weibo_id = ?";
		try {
			// 发送sql语句
			preSQL = connection.prepareStatement(sqlString);
			// 设置要传入的参数
			preSQL.setString(1, w.getWeibo_id());
			// 执行sql语句
			resultSet = preSQL.executeQuery();
			// 检查是否已经注册的用户
			if (resultSet.next()) {
				w.setWeibo_id(resultSet.getString(1));
				w.setWriter_id(resultSet.getString(2));
				w.setResder_id(resultSet.getString(3));
				w.setWeibo_content(resultSet.getString(4));
			}
			connection.close();
		} catch (SQLException e) {
			System.out.println("没有查找到微博对象");
			System.out.println(e);
		}
		return w;
	}

	/**
	 * @Description 说明：查找关注与粉丝
	 */
	public boolean select(AttentionFans attentionFans) {
		boolean b = false;
		// SQL语句
		String sqlString = "select * from attentionfans where attention_id = ? and fans_id = ?";
		try {
			// 发送sql语句
			preSQL = connection.prepareStatement(sqlString);
			// 设置要传入的参数
			preSQL.setString(1, attentionFans.getAttention_id());
			preSQL.setString(2, attentionFans.getFans_id());
			// 执行sql语句
			resultSet = preSQL.executeQuery();
			// 检查是否已经注册的用户
			if (resultSet.next()) {
				b = true;
				attentionFans.setAttention_id(resultSet.getString(1));
				attentionFans.setFans_id(resultSet.getString(2));
			}
			connection.close();
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, "你没有关注过此人", "警告", JOptionPane.INFORMATION_MESSAGE);
			System.out.println("没有查找到关注与粉丝对象");
			System.out.println(e);
		}
		return b;
	}

	/**
	 * @Description 说明：查找访客对象，获取访客数组
	 */
	public Visitor[] select_Visitor() {
		Visitor[] visitors = new Visitor[] {};
		try {
			Statement sql = connection.createStatement();
			// 执行sql语句
			resultSet = sql.executeQuery("select * from visitor");
			// 将结果集插入数组
			while (resultSet.next()) {
				Visitor visitor = new Visitor();
				visitor.setId(resultSet.getString(1));
				visitor.setVisitor_name(resultSet.getString(2));
				visitor.setVisitor_sex(resultSet.getString(3));
				visitor.setVisitor_birthday(resultSet.getString(4));
				visitor.setVisitor_yes_no(resultSet.getString(5));
				visitors = Insert.insert(visitors, visitor);
			}
			connection.close();
		} catch (SQLException e) {
			System.out.println("没有查找访客对象，获取访客数组");
			System.out.println(e);
		}
		return visitors;
	}

	/**
	 * @Description 说明：根据作者id查找访客对象，获取访客数组
	 */
	public Visitor[] select_Visitor_id(String Visitor_id) {
		Visitor[] visitors = new Visitor[] {};
		// SQL语句
		String sqlString = "select * from visitor where id = ?";
		try {
			// 发送sql语句
			preSQL = connection.prepareStatement(sqlString);
			// 设置要传入的参数
			preSQL.setString(1, Visitor_id);
			// 执行sql语句
			resultSet = preSQL.executeQuery();
			// 将结果集插入数组
			while (resultSet.next()) {
				Visitor visitor = new Visitor();
				visitor.setId(resultSet.getString(1));
				visitor.setVisitor_name(resultSet.getString(2));
				visitor.setVisitor_sex(resultSet.getString(3));
				visitor.setVisitor_birthday(resultSet.getString(4));
				visitor.setVisitor_yes_no(resultSet.getString(5));
				visitors = Insert.insert(visitors, visitor);
			}
			connection.close();
		} catch (SQLException e) {
			System.out.println("没有根据作者id查找访客对象，获取访客数组");
			System.out.println(e);
		}
		return visitors;
	}

	/**
	 * @Description 说明：根据作者id查找微博对象，获取微博数组
	 */
	public Weibo[] select_Writer_id(String Writer_id) {
		Weibo[] weibos = new Weibo[] {};
		// SQL语句
		String sqlString = "select * from weibo where writer_id = ?";
		try {
			// 发送sql语句
			preSQL = connection.prepareStatement(sqlString);
			// 设置要传入的参数
			preSQL.setString(1, Writer_id);
			// 执行sql语句
			resultSet = preSQL.executeQuery();
			// 将结果集插入数组
			while (resultSet.next()) {
				Weibo w = new Weibo();
				w.setWeibo_id(resultSet.getString(1));
				w.setWriter_id(resultSet.getString(2));
				w.setResder_id(resultSet.getString(3));
				w.setWeibo_content(resultSet.getString(4));
				weibos = Insert.insert(weibos, w);
			}
			connection.close();
		} catch (SQLException e) {
			System.out.println("没有根据作者id查找微博对象，获取微博数组");
			System.out.println(e);
		}
		return weibos;
	}

	/**
	 * @Description 说明：根据读者id查找微博对象，获取微博数组
	 */
	public Weibo[] select_Reader_id(String Reader_id) {
		Weibo[] weibos = new Weibo[] {};
		// SQL语句
		String sqlString = "select * from weibo where reader_id = ?";
		try {
			// 发送sql语句
			preSQL = connection.prepareStatement(sqlString);
			// 设置要传入的参数
			preSQL.setString(1, Reader_id);
			// 执行sql语句
			resultSet = preSQL.executeQuery();
			// 将结果集插入数组
			while (resultSet.next()) {
				Weibo w = new Weibo();
				w.setWeibo_id(resultSet.getString(1));
				w.setWriter_id(resultSet.getString(2));
				w.setResder_id(resultSet.getString(3));
				w.setWeibo_content(resultSet.getString(4));
				weibos = Insert.insert(weibos, w);
			}
			connection.close();
		} catch (SQLException e) {
			System.out.println("没有根据读者id查找微博对象，获取微博数组");
			System.out.println(e);
		}
		return weibos;
	}

	/**
	 * @Description 说明：查找关注与粉丝对象——通过Attention_id
	 */
	public String[] select_Attention_id(String Attention_id) {
		String[] fansid = new String[] {};
		// SQL语句
		String sqlString = "select * from attentionfans where attention_id = ?";
		try {
			// 发送sql语句
			preSQL = connection.prepareStatement(sqlString);
			// 设置要传入的参数
			preSQL.setString(1, Attention_id);
			// 执行sql语句
			resultSet = preSQL.executeQuery();
			// 检查是否已经注册的用户
			while (resultSet.next()) {
				String string = resultSet.getString(2);
				fansid = Insert.insert(fansid, string);
			}
			connection.close();
		} catch (SQLException e) {
			System.out.println("没有查找关注与粉丝对象——通过Attention_id");
			System.out.println(e);
		}
		return fansid;
	}

	/**
	 * @Description 说明：查找关注与粉丝对象——通过Fans_id
	 */
	public String[] select_Fans_id(String Fans_id) {
		String[] attentionsid = new String[] {};
		// SQL语句
		String sqlString = "select * from attentionfans where fans_id = ?";
		try {
			// 发送sql语句
			preSQL = connection.prepareStatement(sqlString);
			// 设置要传入的参数
			preSQL.setString(1, Fans_id);
			// 执行sql语句
			resultSet = preSQL.executeQuery();
			// 检查是否已经注册的用户
			while (resultSet.next()) {
				String string = resultSet.getString(1);
				attentionsid = Insert.insert(attentionsid, string);
			}
			connection.close();
		} catch (SQLException e) {
			System.out.println("没有查找关注与粉丝对象——通过Fans_id");
			System.out.println(e);
		}
		return attentionsid;
	}

	/**
	 * @Description 说明：查找举报对象——通过Operator_id
	 */
	public String[] select_Operator_id(String Operator_id) {
		String[] weiboid = new String[] {};
		// SQL语句
		String sqlString = "select * from report where operator_id = ?";
		try {
			// 发送sql语句
			preSQL = connection.prepareStatement(sqlString);
			// 设置要传入的参数
			preSQL.setString(1, Operator_id);
			// 执行sql语句
			resultSet = preSQL.executeQuery();
			// 检查是否已经注册的用户
			while (resultSet.next()) {
				String string = resultSet.getString(2);
				weiboid = Insert.insert(weiboid, string);
			}
			connection.close();
		} catch (SQLException e) {
			System.out.println("没有查找举报对象——通过Operator_id");
			System.out.println(e);
		}
		return weiboid;
	}

}
