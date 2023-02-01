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
 * @Author ����
 * @Description ˵��������
 * @Date ʱ�䣺2020-12-2
 */
public class Select {

	private Connection connection;
	private PreparedStatement preSQL;
	private ResultSet resultSet;

	/**
	 * @Description ˵�������췽��
	 */
	public Select() {
		connection = MyGetConnection.GetConnection("weibo", "root", "");
	}

	/**
	 * @Description ˵�������Ҹ���
	 */
	public Personal select(Personal personal) {
		// SQL���
		String sqlString = "select * from personal where id = ?";
		try {
			// ����sql���
			preSQL = connection.prepareStatement(sqlString);
			// ����Ҫ����Ĳ���
			preSQL.setString(1, personal.getId());
			// ִ��sql���
			resultSet = preSQL.executeQuery();
			// ����Ƿ��Ѿ�ע����û�
			if (resultSet.next()) {
				personal.setId(resultSet.getString(1));
				personal.setPassword(resultSet.getString(2));
				personal.setPower(resultSet.getString(3));
				personal.setLogin_success(Constant.YES_STRING);
			}
			connection.close();
		} catch (SQLException e) {
			System.out.println("û�в��ҵ����˶���");
			System.out.println(e);
		}
		return personal;
	}

	/**
	 * @Description ˵�������ҷÿ�
	 */
	public Visitor select(Visitor visitor) {
		// SQL���
		String sqlString = "select * from visitor where id = ?";
		try {
			// ����sql���
			preSQL = connection.prepareStatement(sqlString);
			// ����Ҫ����Ĳ���
			preSQL.setString(1, visitor.getId());
			// ִ��sql���
			resultSet = preSQL.executeQuery();
			// ����Ƿ��Ѿ�ע����û�
			if (resultSet.next()) {
				visitor.setId(resultSet.getString(1));
				visitor.setVisitor_name(resultSet.getString(2));
				visitor.setVisitor_sex(resultSet.getString(3));
				visitor.setVisitor_birthday(resultSet.getString(4));
				visitor.setVisitor_yes_no(Constant.YES_STRING);
			}
			connection.close();
		} catch (SQLException e) {
			System.out.println("û�в��ҵ��ÿͶ���");
			System.out.println(e);
		}
		return visitor;
	}

	/**
	 * @Description ˵��������΢��
	 */
	public Weibo select(Weibo w) {
		// SQL���
		String sqlString = "select * from weibo where weibo_id = ?";
		try {
			// ����sql���
			preSQL = connection.prepareStatement(sqlString);
			// ����Ҫ����Ĳ���
			preSQL.setString(1, w.getWeibo_id());
			// ִ��sql���
			resultSet = preSQL.executeQuery();
			// ����Ƿ��Ѿ�ע����û�
			if (resultSet.next()) {
				w.setWeibo_id(resultSet.getString(1));
				w.setWriter_id(resultSet.getString(2));
				w.setResder_id(resultSet.getString(3));
				w.setWeibo_content(resultSet.getString(4));
			}
			connection.close();
		} catch (SQLException e) {
			System.out.println("û�в��ҵ�΢������");
			System.out.println(e);
		}
		return w;
	}

	/**
	 * @Description ˵�������ҹ�ע���˿
	 */
	public boolean select(AttentionFans attentionFans) {
		boolean b = false;
		// SQL���
		String sqlString = "select * from attentionfans where attention_id = ? and fans_id = ?";
		try {
			// ����sql���
			preSQL = connection.prepareStatement(sqlString);
			// ����Ҫ����Ĳ���
			preSQL.setString(1, attentionFans.getAttention_id());
			preSQL.setString(2, attentionFans.getFans_id());
			// ִ��sql���
			resultSet = preSQL.executeQuery();
			// ����Ƿ��Ѿ�ע����û�
			if (resultSet.next()) {
				b = true;
				attentionFans.setAttention_id(resultSet.getString(1));
				attentionFans.setFans_id(resultSet.getString(2));
			}
			connection.close();
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, "��û�й�ע������", "����", JOptionPane.INFORMATION_MESSAGE);
			System.out.println("û�в��ҵ���ע���˿����");
			System.out.println(e);
		}
		return b;
	}

	/**
	 * @Description ˵�������ҷÿͶ��󣬻�ȡ�ÿ�����
	 */
	public Visitor[] select_Visitor() {
		Visitor[] visitors = new Visitor[] {};
		try {
			Statement sql = connection.createStatement();
			// ִ��sql���
			resultSet = sql.executeQuery("select * from visitor");
			// ���������������
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
			System.out.println("û�в��ҷÿͶ��󣬻�ȡ�ÿ�����");
			System.out.println(e);
		}
		return visitors;
	}

	/**
	 * @Description ˵������������id���ҷÿͶ��󣬻�ȡ�ÿ�����
	 */
	public Visitor[] select_Visitor_id(String Visitor_id) {
		Visitor[] visitors = new Visitor[] {};
		// SQL���
		String sqlString = "select * from visitor where id = ?";
		try {
			// ����sql���
			preSQL = connection.prepareStatement(sqlString);
			// ����Ҫ����Ĳ���
			preSQL.setString(1, Visitor_id);
			// ִ��sql���
			resultSet = preSQL.executeQuery();
			// ���������������
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
			System.out.println("û�и�������id���ҷÿͶ��󣬻�ȡ�ÿ�����");
			System.out.println(e);
		}
		return visitors;
	}

	/**
	 * @Description ˵������������id����΢�����󣬻�ȡ΢������
	 */
	public Weibo[] select_Writer_id(String Writer_id) {
		Weibo[] weibos = new Weibo[] {};
		// SQL���
		String sqlString = "select * from weibo where writer_id = ?";
		try {
			// ����sql���
			preSQL = connection.prepareStatement(sqlString);
			// ����Ҫ����Ĳ���
			preSQL.setString(1, Writer_id);
			// ִ��sql���
			resultSet = preSQL.executeQuery();
			// ���������������
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
			System.out.println("û�и�������id����΢�����󣬻�ȡ΢������");
			System.out.println(e);
		}
		return weibos;
	}

	/**
	 * @Description ˵�������ݶ���id����΢�����󣬻�ȡ΢������
	 */
	public Weibo[] select_Reader_id(String Reader_id) {
		Weibo[] weibos = new Weibo[] {};
		// SQL���
		String sqlString = "select * from weibo where reader_id = ?";
		try {
			// ����sql���
			preSQL = connection.prepareStatement(sqlString);
			// ����Ҫ����Ĳ���
			preSQL.setString(1, Reader_id);
			// ִ��sql���
			resultSet = preSQL.executeQuery();
			// ���������������
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
			System.out.println("û�и��ݶ���id����΢�����󣬻�ȡ΢������");
			System.out.println(e);
		}
		return weibos;
	}

	/**
	 * @Description ˵�������ҹ�ע���˿���󡪡�ͨ��Attention_id
	 */
	public String[] select_Attention_id(String Attention_id) {
		String[] fansid = new String[] {};
		// SQL���
		String sqlString = "select * from attentionfans where attention_id = ?";
		try {
			// ����sql���
			preSQL = connection.prepareStatement(sqlString);
			// ����Ҫ����Ĳ���
			preSQL.setString(1, Attention_id);
			// ִ��sql���
			resultSet = preSQL.executeQuery();
			// ����Ƿ��Ѿ�ע����û�
			while (resultSet.next()) {
				String string = resultSet.getString(2);
				fansid = Insert.insert(fansid, string);
			}
			connection.close();
		} catch (SQLException e) {
			System.out.println("û�в��ҹ�ע���˿���󡪡�ͨ��Attention_id");
			System.out.println(e);
		}
		return fansid;
	}

	/**
	 * @Description ˵�������ҹ�ע���˿���󡪡�ͨ��Fans_id
	 */
	public String[] select_Fans_id(String Fans_id) {
		String[] attentionsid = new String[] {};
		// SQL���
		String sqlString = "select * from attentionfans where fans_id = ?";
		try {
			// ����sql���
			preSQL = connection.prepareStatement(sqlString);
			// ����Ҫ����Ĳ���
			preSQL.setString(1, Fans_id);
			// ִ��sql���
			resultSet = preSQL.executeQuery();
			// ����Ƿ��Ѿ�ע����û�
			while (resultSet.next()) {
				String string = resultSet.getString(1);
				attentionsid = Insert.insert(attentionsid, string);
			}
			connection.close();
		} catch (SQLException e) {
			System.out.println("û�в��ҹ�ע���˿���󡪡�ͨ��Fans_id");
			System.out.println(e);
		}
		return attentionsid;
	}

	/**
	 * @Description ˵�������Ҿٱ����󡪡�ͨ��Operator_id
	 */
	public String[] select_Operator_id(String Operator_id) {
		String[] weiboid = new String[] {};
		// SQL���
		String sqlString = "select * from report where operator_id = ?";
		try {
			// ����sql���
			preSQL = connection.prepareStatement(sqlString);
			// ����Ҫ����Ĳ���
			preSQL.setString(1, Operator_id);
			// ִ��sql���
			resultSet = preSQL.executeQuery();
			// ����Ƿ��Ѿ�ע����û�
			while (resultSet.next()) {
				String string = resultSet.getString(2);
				weiboid = Insert.insert(weiboid, string);
			}
			connection.close();
		} catch (SQLException e) {
			System.out.println("û�в��Ҿٱ����󡪡�ͨ��Operator_id");
			System.out.println(e);
		}
		return weiboid;
	}

}
