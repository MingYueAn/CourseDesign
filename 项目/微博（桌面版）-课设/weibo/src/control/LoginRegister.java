package control;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JOptionPane;

import data.Constant;
import data.GlobalVar;
import data.MyGetConnection;
import model.AttentionFans;
import model.Personal;
import model.Visitor;

/**
 * @Author 作者
 * @Description 说明：登录注册
 * @Date 时间：2020-12-2
 */
public class LoginRegister {

	private Connection connection;
	private PreparedStatement preSQL;
	private ResultSet resultSet;

	/**
	 * @Description 说明：构造方法
	 */
	public LoginRegister() {
		connection = MyGetConnection.GetConnection("weibo", "root", "");
	}

	/**
	 * @Description 说明：注册
	 */
	public Personal register(String id, String password1) {
		Personal personal = new Personal(id, password1);
		personal.setPower(Constant.POWER[0]);
		Visitor visitor = new Visitor(id);
		new Select().select(personal);
		new Select().select(visitor);
		if (personal.getLogin_success().equals(Constant.YES_STRING) || visitor.getVisitor_yes_no().equals(Constant.YES_STRING)) {
			JOptionPane.showMessageDialog(null, "账号重复", "警告", JOptionPane.WARNING_MESSAGE);
		} else {
			new Insert().insert(personal);
			new Insert().insert(visitor);
			AttentionFans attentionFans = new AttentionFans();
			attentionFans.setAttention_id(id);
			attentionFans.setFans_id(id);
			new Insert().insert(attentionFans);
			JOptionPane.showMessageDialog(null, "注册成功");
		}
		System.out.println("-----注册个人对象的信息");
		System.out.println("账号：" + personal.getId());
		System.out.println("密码：" + personal.getPassword());
		System.out.println("权限：" + personal.getPower());
		System.out.println("Login_success：" + personal.getLogin_success());
		return personal;
	}

	/**
	 * @Description 说明：登录——查找个人、判断权限进行查找
	 */
	public Personal login(String id, String password) {
		Personal personal = new Personal(id, password);
		// SQL语句
		String sqlString = "select id,password,power from personal where " + "id = ? and password = ?";
		try {
			// 发送sql语句
			preSQL = connection.prepareStatement(sqlString);
			// 设置要传入的参数
			preSQL.setString(1, id);
			preSQL.setString(2, password);
			// 执行sql语句
			resultSet = preSQL.executeQuery();
			// 检查是否已经注册的用户
			if (resultSet.next()) {
				personal.setId(resultSet.getString("id"));
				personal.setPassword(resultSet.getString("password"));
				personal.setPower(resultSet.getString("power"));
				personal.setLogin_success(Constant.YES_STRING);
				GlobalVar.login_personal = personal;// 当前登录个人
				// 获取个人权限并判断是否存在该用户
				switch (personal.getPower()) {
				case "访客":
					/* 访客 */
					Visitor visitor = new Visitor(id);
					new Select().select(visitor);// 查找访客，判断访客是否登录
					if (visitor.getVisitor_yes_no().equals(Constant.YES_STRING) == true) {
						GlobalVar.login_visitor = visitor;// 当前登录访客
						String s1[] = new Select().select_Attention_id(GlobalVar.login_visitor.getId());
						for (int i = 0; i < s1.length; i++) {
							Visitor[] visitors1 = new Select().select_Visitor_id(s1[i]);
							for (int j = 0; j < visitors1.length; j++) {
								GlobalVar.weibo_num2 = GlobalVar.weibo_num2 + new ResultSetNum().resultSetNumWeibo(visitors1[j].getId());
							}
						}
						GlobalVar.weibo_num1 = new ResultSetNum().resultSetNumWeibo(GlobalVar.login_visitor.getId());
						GlobalVar.attention_num = new ResultSetNum().resultSetNumAttention(GlobalVar.login_visitor.getId());
						GlobalVar.fans_num = new ResultSetNum().resultSetNumFans(GlobalVar.login_visitor.getId());
					} else {
						System.out.println("不可查看");
					}
					break;
				case "管理员":
					System.out.println("查找管理员");
					break;
				default:
					System.out.println("完成");
				}
			}
			connection.close();
		} catch (SQLException e) {
		}
		return personal;
	}
}
