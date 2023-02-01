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
 * @Author ����
 * @Description ˵����ɾ��
 * @Date ʱ�䣺2020-12-10
 */
public class Delete {
	private Connection connection;
	private PreparedStatement preSQL;

	/**
	 * @Description ˵�������췽��
	 */
	public Delete() {
		connection = MyGetConnection.GetConnection("weibo", "root", "");
	}

	/**
	 * @Description ˵����ɾ������
	 */
	public void delete(Personal personal) {
		int ok = 0;
		// SQL���
		String sqlString = "delete from personal where id=?";
		try {
			// ����sql���
			preSQL = connection.prepareStatement(sqlString);
			// ����Ҫ����Ĳ���
			preSQL.setString(1, personal.getId());
			// ִ��sql���
			ok = preSQL.executeUpdate();
			connection.close();
		} catch (SQLException e) {
			System.out.println(e);
			System.out.println("δ�ܳɹ�ɾ�����˶���");
		}
		if (ok != 0) {
			System.out.println("�Ѿ��ɹ�ɾ�����˶���");
		}
	}

	/**
	 * @Description ˵����ɾ���ÿ�
	 */
	public Visitor delete(Visitor visitor) {
		int ok = 0;
		// SQL���
		String sqlString = "delete from visitor where id=?";
		try {
			// ����sql���
			preSQL = connection.prepareStatement(sqlString);
			// ����Ҫ����Ĳ���
			preSQL.setString(1, visitor.getId());
			// ִ��sql���
			ok = preSQL.executeUpdate();
			connection.close();
		} catch (SQLException e) {
			System.out.println(e);
			System.out.println("δ�ܳɹ�ɾ���ÿͶ���");
		}
		if (ok != 0) {
			System.out.println("�Ѿ��ɹ�ɾ���ÿͶ���");
		}
		return visitor;
	}

	/**
	 * @Description ˵����ɾ��΢��
	 */
	public Weibo delete(Weibo w) {
		int ok = 0;
		// SQL���
		String sqlString = "delete from weibo where weibo_id=?";
		try {
			// ����sql���
			preSQL = connection.prepareStatement(sqlString);
			// ����Ҫ����Ĳ���
			preSQL.setString(1, w.getWeibo_id());
			// ִ��sql���
			ok = preSQL.executeUpdate();
			connection.close();
		} catch (SQLException e) {
			System.out.println(e);
			System.out.println("δ�ܳɹ�ɾ��΢������");
		}
		if (ok != 0) {
			System.out.println("�Ѿ��ɹ�ɾ��΢������");
		}
		return w;
	}

	/**
	 * @Description ˵����ɾ����ע���˿
	 */
	public boolean delete(AttentionFans attentionFans) {
		int ok = 0;
		// SQL���
		String sqlString = "delete from attentionfans where attention_id=? and fans_id=?";
		try {
			// ����sql���
			preSQL = connection.prepareStatement(sqlString);
			// ����Ҫ����Ĳ���
			preSQL.setString(1, attentionFans.getAttention_id());
			preSQL.setString(2, attentionFans.getFans_id());
			ok = preSQL.executeUpdate();
			connection.close();
		} catch (SQLException e) {
			System.out.println(e);
			System.out.println("δ�ܳɹ�ɾ����ע���˿����");
			return false;
		}
		if (ok != 0) {
			JOptionPane.showMessageDialog(null, "ȡ����ע�ɹ�", "��Ϣ", JOptionPane.INFORMATION_MESSAGE);
			System.out.println("�Ѿ��ɹ�ɾ����ע���˿����");
			return true;
		} else {
			return false;
		}
	}

	/**
	 * @Description ˵����ɾ����ע���˿����attention_id
	 */
	public void delete1(String attention_id) {
		int ok = 0;
		// SQL���
		String sqlString = "delete from attentionfans where attention_id=?";
		try {
			// ����sql���
			preSQL = connection.prepareStatement(sqlString);
			// ����Ҫ����Ĳ���
			preSQL.setString(1, attention_id);
			ok = preSQL.executeUpdate();
			connection.close();
		} catch (SQLException e) {
			System.out.println(e);
			System.out.println("δ�ܳɹ�ɾ����ע���˿����");
		}
		if (ok != 0) {
			System.out.println("�Ѿ��ɹ�ɾ����ע���˿����");
		}
	}

	/**
	 * @Description ˵����ɾ����ע���˿����fans_id
	 */
	public void delete2(String fans_id) {
		int ok = 0;
		// SQL���
		String sqlString = "delete from attentionfans where fans_id=?";
		try {
			// ����sql���
			preSQL = connection.prepareStatement(sqlString);
			// ����Ҫ����Ĳ���
			preSQL.setString(1, fans_id);
			ok = preSQL.executeUpdate();
			connection.close();
		} catch (SQLException e) {
			System.out.println(e);
			System.out.println("δ�ܳɹ�ɾ����ע���˿����");
		}
		if (ok != 0) {
			System.out.println("�Ѿ��ɹ�ɾ����ע���˿����");
		}
	}

	/**
	 * @Description ˵�������id����д��΢��
	 */
	public boolean deleteWeibo(String id) {
		int ok = 0;
		boolean b = false;
		// SQL���
		String sqlString = "delete from weibo where writer_id=?";
		try {
			// ����sql���
			preSQL = connection.prepareStatement(sqlString);
			// ����Ҫ����Ĳ���
			preSQL.setString(1, id);
			// ִ��sql���
			ok = preSQL.executeUpdate();
			connection.close();
		} catch (SQLException e) {
			System.out.println(e);
			b = false;
			System.out.println("δ�ܳɹ����id����д��΢��");
		}
		if (ok != 0) {
			b = true;
			System.out.println("�Ѿ��ɹ����id����д��΢��");
		}
		return b;
	}

	/**
	 * @Description ˵�������id�ŵĹ�ע���˿
	 */
	public boolean deleteAttentionFans(String id) {
		int ok = 0;
		boolean b = false;
		// SQL���
		String sqlString = "delete from attentionFans where attention_id=? and fans_id=?";
		try {
			// ����sql���
			preSQL = connection.prepareStatement(sqlString);
			// ����Ҫ����Ĳ���
			preSQL.setString(1, id);
			preSQL.setString(2, id);
			// ִ��sql���
			ok = preSQL.executeUpdate();
			connection.close();
		} catch (SQLException e) {
			System.out.println(e);
			b = false;
			System.out.println("δ�ܳɹ����id�ŵĹ�ע���˿");
		}
		if (ok != 0) {
			b = true;
			System.out.println("�Ѿ��ɹ����id�ŵĹ�ע���˿");
		}
		return b;
	}
}
