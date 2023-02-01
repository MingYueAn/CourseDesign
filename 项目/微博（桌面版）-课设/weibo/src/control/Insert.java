package control;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.swing.JOptionPane;

import data.MyGetConnection;
import model.AttentionFans;
import model.Music;
import model.Personal;
import model.Visitor;
import model.Weibo;

/**
 * @Author 作者
 * @Description 说明：添加
 * @Date 时间：2020-12-2
 */
public class Insert {

	private Connection connection;
	private PreparedStatement preSQL;

	/**
	 * @Description 说明：构造方法
	 */
	public Insert() {
		connection = MyGetConnection.GetConnection("weibo", "root", "");
	}

	/**
	 * @Description 说明：添加个人对象
	 */
	public void insert(Personal personal) {
		int ok = 0;
		// SQL语句
		String sqlString = "insert into personal values(?,?,?,?)";
		try {
			// 发送sql语句
			preSQL = connection.prepareStatement(sqlString);
			// 设置要传入的参数
			preSQL.setString(1, personal.getId());
			preSQL.setString(2, personal.getPassword());
			preSQL.setString(3, personal.getPower());
			preSQL.setString(4, personal.getLogin_success());
			// 执行sql语句
			ok = preSQL.executeUpdate();
			connection.close();
		} catch (SQLException e) {
			System.out.println(e);
			System.out.println("未能成功添加个人对象");
		}
		if (ok != 0) {
			System.out.println("已经成功添加个人对象");
		}
	}

	/**
	 * @Description 说明：添加访客对象
	 */
	public void insert(Visitor visitor) {
		int ok = 0;
		// SQL语句
		String sqlString = "insert into visitor values(?,?,?,?,?)";
		try {
			// 发送sql语句
			preSQL = connection.prepareStatement(sqlString);
			// 设置要传入的参数
			preSQL.setString(1, visitor.getId());
			preSQL.setString(2, visitor.getVisitor_name());
			preSQL.setString(3, visitor.getVisitor_sex());
			preSQL.setString(4, visitor.getVisitor_birthday());
			preSQL.setString(5, visitor.getVisitor_yes_no());
			// 执行sql语句
			ok = preSQL.executeUpdate();
			connection.close();
		} catch (SQLException e) {
			System.out.println(e);
			System.out.println("未能成功添加访客对象");
		}
		if (ok != 0) {
			System.out.println("已经成功添加访客对象");
		}
	}

	/**
	 * @Description 说明：添加微博对象
	 */
	public void insert(Weibo weibo) {
		int ok = 0;
		// SQL语句
		String sqlString = "insert into weibo values(?,?,?,?)";
		try {
			// 发送sql语句
			preSQL = connection.prepareStatement(sqlString);
			// 设置要传入的参数
			preSQL.setString(1, weibo.getWeibo_id());
			preSQL.setString(2, weibo.getWriter_id());
			preSQL.setString(3, weibo.getResder_id());
			preSQL.setString(4, weibo.getWeibo_content());
			// 执行sql语句
			ok = preSQL.executeUpdate();
			connection.close();
		} catch (SQLException e) {
			System.out.println(e);
			JOptionPane.showMessageDialog(null, "未能成功添加微博对象", "警告", JOptionPane.INFORMATION_MESSAGE);
			System.out.println("未能成功添加微博对象");
		}
		if (ok != 0) {
			System.out.println("已经成功添加微博对象");
		}
	}

	/**
	 * @Description 说明：添加关注与粉丝对象
	 */
	public boolean insert(AttentionFans attentionFans) {
		int ok = 0;
		// SQL语句
		String sqlString = "insert into attentionfans values(?,?)";
		try {
			// 发送sql语句
			preSQL = connection.prepareStatement(sqlString);
			// 设置要传入的参数
			preSQL.setString(1, attentionFans.getAttention_id());
			preSQL.setString(2, attentionFans.getFans_id());
			// 执行sql语句
			ok = preSQL.executeUpdate();
			connection.close();
		} catch (SQLException e) {
			System.out.println(e);
			JOptionPane.showMessageDialog(null, "关注失败", "警告", JOptionPane.INFORMATION_MESSAGE);
			System.out.println("未能成功添加关注与粉丝对象");
			return false;
		}
		if (ok != 0) {
			if (attentionFans.getAttention_id().equals(attentionFans.getFans_id()) == true) {
				System.out.println("已经成功添加关注与粉丝对象");
				return true;
			} else {
				JOptionPane.showMessageDialog(null, "关注成功", "恭喜", JOptionPane.INFORMATION_MESSAGE);
				System.out.println("已经成功添加关注与粉丝对象");
				return true;
			}
		} else {
			return false;
		}
	}

	// 往字符串数组追加新数据
	public static String[] insert(String[] arr, String... str) {
		int size = arr.length; // 获取原数组长度
		int newSize = size + str.length; // 原数组长度加上追加的数据的总长度

		// 新建临时字符串数组
		String[] tmp = new String[newSize];
		// 先遍历将原来的字符串数组数据添加到临时字符串数组
		for (int i = 0; i < size; i++) {
			tmp[i] = arr[i];
		}
		// 在末尾添加上需要追加的数据
		for (int i = size; i < newSize; i++) {
			tmp[i] = str[i - size];
		}
		return tmp; // 返回拼接完成的字符串数组
	}

	// 往音乐数组追加新数据
	public static Music[] insert(Music[] musicArr, Music... musics) {
		int size = musicArr.length; // 获取原数组长度
		int newSize = size + musics.length; // 原数组长度加上追加的数据的总长度

		// 新建临时音乐数组
		Music[] tmp = new Music[newSize];
		// 先遍历将原来的数组数据添加到临时数组
		for (int i = 0; i < size; i++) {
			tmp[i] = musicArr[i];
		}
		// 在末尾添加上需要追加的数据
		for (int i = size; i < newSize; i++) {
			tmp[i] = musics[i - size];
		}
		return tmp; // 返回拼接完成的音乐数组
	}

	// 往微博数组追加新数据
	public static Weibo[] insert(Weibo[] weiboArr, Weibo... weibos) {
		int size = weiboArr.length; // 获取原数组长度
		int newSize = size + weibos.length; // 原数组长度加上追加的数据的总长度

		// 新建临时微博数组
		Weibo[] tmp = new Weibo[newSize];
		// 先遍历将原来的数组数据添加到临时数组
		for (int i = 0; i < size; i++) {
			tmp[i] = weiboArr[i];
		}
		// 在末尾添加上需要追加的数据
		for (int i = size; i < newSize; i++) {
			tmp[i] = weibos[i - size];
		}
		return tmp; // 返回拼接完成的微博数组
	}

	// 往访客数组追加新数据
	public static Visitor[] insert(Visitor[] visitorArr, Visitor... visitors) {
		int size = visitorArr.length; // 获取原数组长度
		int newSize = size + visitors.length; // 原数组长度加上追加的数据的总长度

		// 新建临时访客数组
		Visitor[] tmp = new Visitor[newSize];
		// 先遍历将原来的数组数据添加到临时数组
		for (int i = 0; i < size; i++) {
			tmp[i] = visitorArr[i];
		}
		// 在末尾添加上需要追加的数据
		for (int i = size; i < newSize; i++) {
			tmp[i] = visitors[i - size];
		}
		return tmp; // 返回拼接完成的访客数组
	}

}
