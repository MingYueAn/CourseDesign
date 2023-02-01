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
 * @Author ����
 * @Description ˵������¼ע��
 * @Date ʱ�䣺2020-12-2
 */
public class LoginRegister {

	private Connection connection;
	private PreparedStatement preSQL;
	private ResultSet resultSet;

	/**
	 * @Description ˵�������췽��
	 */
	public LoginRegister() {
		connection = MyGetConnection.GetConnection("weibo", "root", "");
	}

	/**
	 * @Description ˵����ע��
	 */
	public Personal register(String id, String password1) {
		Personal personal = new Personal(id, password1);
		personal.setPower(Constant.POWER[0]);
		Visitor visitor = new Visitor(id);
		new Select().select(personal);
		new Select().select(visitor);
		if (personal.getLogin_success().equals(Constant.YES_STRING) || visitor.getVisitor_yes_no().equals(Constant.YES_STRING)) {
			JOptionPane.showMessageDialog(null, "�˺��ظ�", "����", JOptionPane.WARNING_MESSAGE);
		} else {
			new Insert().insert(personal);
			new Insert().insert(visitor);
			AttentionFans attentionFans = new AttentionFans();
			attentionFans.setAttention_id(id);
			attentionFans.setFans_id(id);
			new Insert().insert(attentionFans);
			JOptionPane.showMessageDialog(null, "ע��ɹ�");
		}
		System.out.println("-----ע����˶������Ϣ");
		System.out.println("�˺ţ�" + personal.getId());
		System.out.println("���룺" + personal.getPassword());
		System.out.println("Ȩ�ޣ�" + personal.getPower());
		System.out.println("Login_success��" + personal.getLogin_success());
		return personal;
	}

	/**
	 * @Description ˵������¼�������Ҹ��ˡ��ж�Ȩ�޽��в���
	 */
	public Personal login(String id, String password) {
		Personal personal = new Personal(id, password);
		// SQL���
		String sqlString = "select id,password,power from personal where " + "id = ? and password = ?";
		try {
			// ����sql���
			preSQL = connection.prepareStatement(sqlString);
			// ����Ҫ����Ĳ���
			preSQL.setString(1, id);
			preSQL.setString(2, password);
			// ִ��sql���
			resultSet = preSQL.executeQuery();
			// ����Ƿ��Ѿ�ע����û�
			if (resultSet.next()) {
				personal.setId(resultSet.getString("id"));
				personal.setPassword(resultSet.getString("password"));
				personal.setPower(resultSet.getString("power"));
				personal.setLogin_success(Constant.YES_STRING);
				GlobalVar.login_personal = personal;// ��ǰ��¼����
				// ��ȡ����Ȩ�޲��ж��Ƿ���ڸ��û�
				switch (personal.getPower()) {
				case "�ÿ�":
					/* �ÿ� */
					Visitor visitor = new Visitor(id);
					new Select().select(visitor);// ���ҷÿͣ��жϷÿ��Ƿ��¼
					if (visitor.getVisitor_yes_no().equals(Constant.YES_STRING) == true) {
						GlobalVar.login_visitor = visitor;// ��ǰ��¼�ÿ�
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
						System.out.println("���ɲ鿴");
					}
					break;
				case "����Ա":
					System.out.println("���ҹ���Ա");
					break;
				default:
					System.out.println("���");
				}
			}
			connection.close();
		} catch (SQLException e) {
		}
		return personal;
	}
}
