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
 * @Author ����
 * @Description ˵�������
 * @Date ʱ�䣺2020-12-2
 */
public class Insert {

	private Connection connection;
	private PreparedStatement preSQL;

	/**
	 * @Description ˵�������췽��
	 */
	public Insert() {
		connection = MyGetConnection.GetConnection("weibo", "root", "");
	}

	/**
	 * @Description ˵������Ӹ��˶���
	 */
	public void insert(Personal personal) {
		int ok = 0;
		// SQL���
		String sqlString = "insert into personal values(?,?,?,?)";
		try {
			// ����sql���
			preSQL = connection.prepareStatement(sqlString);
			// ����Ҫ����Ĳ���
			preSQL.setString(1, personal.getId());
			preSQL.setString(2, personal.getPassword());
			preSQL.setString(3, personal.getPower());
			preSQL.setString(4, personal.getLogin_success());
			// ִ��sql���
			ok = preSQL.executeUpdate();
			connection.close();
		} catch (SQLException e) {
			System.out.println(e);
			System.out.println("δ�ܳɹ���Ӹ��˶���");
		}
		if (ok != 0) {
			System.out.println("�Ѿ��ɹ���Ӹ��˶���");
		}
	}

	/**
	 * @Description ˵������ӷÿͶ���
	 */
	public void insert(Visitor visitor) {
		int ok = 0;
		// SQL���
		String sqlString = "insert into visitor values(?,?,?,?,?)";
		try {
			// ����sql���
			preSQL = connection.prepareStatement(sqlString);
			// ����Ҫ����Ĳ���
			preSQL.setString(1, visitor.getId());
			preSQL.setString(2, visitor.getVisitor_name());
			preSQL.setString(3, visitor.getVisitor_sex());
			preSQL.setString(4, visitor.getVisitor_birthday());
			preSQL.setString(5, visitor.getVisitor_yes_no());
			// ִ��sql���
			ok = preSQL.executeUpdate();
			connection.close();
		} catch (SQLException e) {
			System.out.println(e);
			System.out.println("δ�ܳɹ���ӷÿͶ���");
		}
		if (ok != 0) {
			System.out.println("�Ѿ��ɹ���ӷÿͶ���");
		}
	}

	/**
	 * @Description ˵�������΢������
	 */
	public void insert(Weibo weibo) {
		int ok = 0;
		// SQL���
		String sqlString = "insert into weibo values(?,?,?,?)";
		try {
			// ����sql���
			preSQL = connection.prepareStatement(sqlString);
			// ����Ҫ����Ĳ���
			preSQL.setString(1, weibo.getWeibo_id());
			preSQL.setString(2, weibo.getWriter_id());
			preSQL.setString(3, weibo.getResder_id());
			preSQL.setString(4, weibo.getWeibo_content());
			// ִ��sql���
			ok = preSQL.executeUpdate();
			connection.close();
		} catch (SQLException e) {
			System.out.println(e);
			JOptionPane.showMessageDialog(null, "δ�ܳɹ����΢������", "����", JOptionPane.INFORMATION_MESSAGE);
			System.out.println("δ�ܳɹ����΢������");
		}
		if (ok != 0) {
			System.out.println("�Ѿ��ɹ����΢������");
		}
	}

	/**
	 * @Description ˵������ӹ�ע���˿����
	 */
	public boolean insert(AttentionFans attentionFans) {
		int ok = 0;
		// SQL���
		String sqlString = "insert into attentionfans values(?,?)";
		try {
			// ����sql���
			preSQL = connection.prepareStatement(sqlString);
			// ����Ҫ����Ĳ���
			preSQL.setString(1, attentionFans.getAttention_id());
			preSQL.setString(2, attentionFans.getFans_id());
			// ִ��sql���
			ok = preSQL.executeUpdate();
			connection.close();
		} catch (SQLException e) {
			System.out.println(e);
			JOptionPane.showMessageDialog(null, "��עʧ��", "����", JOptionPane.INFORMATION_MESSAGE);
			System.out.println("δ�ܳɹ���ӹ�ע���˿����");
			return false;
		}
		if (ok != 0) {
			if (attentionFans.getAttention_id().equals(attentionFans.getFans_id()) == true) {
				System.out.println("�Ѿ��ɹ���ӹ�ע���˿����");
				return true;
			} else {
				JOptionPane.showMessageDialog(null, "��ע�ɹ�", "��ϲ", JOptionPane.INFORMATION_MESSAGE);
				System.out.println("�Ѿ��ɹ���ӹ�ע���˿����");
				return true;
			}
		} else {
			return false;
		}
	}

	// ���ַ�������׷��������
	public static String[] insert(String[] arr, String... str) {
		int size = arr.length; // ��ȡԭ���鳤��
		int newSize = size + str.length; // ԭ���鳤�ȼ���׷�ӵ����ݵ��ܳ���

		// �½���ʱ�ַ�������
		String[] tmp = new String[newSize];
		// �ȱ�����ԭ�����ַ�������������ӵ���ʱ�ַ�������
		for (int i = 0; i < size; i++) {
			tmp[i] = arr[i];
		}
		// ��ĩβ�������Ҫ׷�ӵ�����
		for (int i = size; i < newSize; i++) {
			tmp[i] = str[i - size];
		}
		return tmp; // ����ƴ����ɵ��ַ�������
	}

	// ����������׷��������
	public static Music[] insert(Music[] musicArr, Music... musics) {
		int size = musicArr.length; // ��ȡԭ���鳤��
		int newSize = size + musics.length; // ԭ���鳤�ȼ���׷�ӵ����ݵ��ܳ���

		// �½���ʱ��������
		Music[] tmp = new Music[newSize];
		// �ȱ�����ԭ��������������ӵ���ʱ����
		for (int i = 0; i < size; i++) {
			tmp[i] = musicArr[i];
		}
		// ��ĩβ�������Ҫ׷�ӵ�����
		for (int i = size; i < newSize; i++) {
			tmp[i] = musics[i - size];
		}
		return tmp; // ����ƴ����ɵ���������
	}

	// ��΢������׷��������
	public static Weibo[] insert(Weibo[] weiboArr, Weibo... weibos) {
		int size = weiboArr.length; // ��ȡԭ���鳤��
		int newSize = size + weibos.length; // ԭ���鳤�ȼ���׷�ӵ����ݵ��ܳ���

		// �½���ʱ΢������
		Weibo[] tmp = new Weibo[newSize];
		// �ȱ�����ԭ��������������ӵ���ʱ����
		for (int i = 0; i < size; i++) {
			tmp[i] = weiboArr[i];
		}
		// ��ĩβ�������Ҫ׷�ӵ�����
		for (int i = size; i < newSize; i++) {
			tmp[i] = weibos[i - size];
		}
		return tmp; // ����ƴ����ɵ�΢������
	}

	// ���ÿ�����׷��������
	public static Visitor[] insert(Visitor[] visitorArr, Visitor... visitors) {
		int size = visitorArr.length; // ��ȡԭ���鳤��
		int newSize = size + visitors.length; // ԭ���鳤�ȼ���׷�ӵ����ݵ��ܳ���

		// �½���ʱ�ÿ�����
		Visitor[] tmp = new Visitor[newSize];
		// �ȱ�����ԭ��������������ӵ���ʱ����
		for (int i = 0; i < size; i++) {
			tmp[i] = visitorArr[i];
		}
		// ��ĩβ�������Ҫ׷�ӵ�����
		for (int i = size; i < newSize; i++) {
			tmp[i] = visitors[i - size];
		}
		return tmp; // ����ƴ����ɵķÿ�����
	}

}
