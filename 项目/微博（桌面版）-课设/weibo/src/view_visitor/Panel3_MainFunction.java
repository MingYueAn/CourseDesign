package view_visitor;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.ImageIcon;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;

import control.ResultSetNum;
import control.Select;
import data.Constant;
import data.GlobalVar;
import model.Visitor;
import model.Weibo;
import tools.AfAnyWhere;
import tools.AfMargin;
import tools.BackgroundPanel;

/**
 * @Author ����
 * @Description ˵������Ҫ����ʾ���
 * @Date ʱ�䣺2020-11-20
 */
@SuppressWarnings("serial")
public class Panel3_MainFunction extends JTabbedPane {

	// ѡ����
	private BackgroundPanel p1 = new BackgroundPanel(new ImageIcon("images\\����_3.jpg").getImage());
	private BackgroundPanel p2 = new BackgroundPanel(new ImageIcon("images\\����_3.jpg").getImage());

	public Panel3_MainFunction() {
		super(JTabbedPane.RIGHT);// ���ñ�ǩ��ʾλ��
//		setTabLayoutPolicy(JTabbedPane.SCROLL_TAB_LAYOUT);// ����ѡ���ǩ�Ĳ��ַ�ʽ��������
//		setTabLayoutPolicy(JTabbedPane.WRAP_TAB_LAYOUT);// ����ѡ���ǩ�Ĳ��ַ�ʽ�����У�
		init();
	}

	private void init() {

		// ��ҳ
		p1.setLayout(new AfAnyWhere());
		p1.setPreferredSize(new Dimension(300, Constant.NUM * GlobalVar.weibo_num2));
		// ���ݵ�ǰ�ÿ͵�id��ȡ���й�ע��id����
		// ����id�����ȡ΢������(����attention_id����ȡfans_id)
		String s[] = new Select().select_Attention_id(GlobalVar.login_visitor.getId());
		int sum = 0;
		for (int i = 0; i < s.length; i++) {
			Weibo[] weibos = new Select().select_Writer_id(s[i]);
			for (int nm = 0; nm < weibos.length; nm++) {
				p1.add(new PanelTXT(weibos[nm]), new AfMargin(Constant.NUM * sum, 0, -1, 0));
				sum++;
			}
		}
		JScrollPane scrollPane1 = new JScrollPane(p1);
		this.add("��ҳ", scrollPane1);

		// ΢��
		p2.setLayout(new AfAnyWhere());
		p2.setPreferredSize(new Dimension(300, Constant.NUM * GlobalVar.weibo_num1));
		// ͨ����ǰ�ÿ�id��ȡ΢������
		Weibo[] weibos = new Select().select_Writer_id(GlobalVar.login_visitor.getId());
		for (int i = 0; i < weibos.length; i++) {
			p2.add(new PanelTXT(weibos[i]), new AfMargin(Constant.NUM * i, 0, -1, 0));
		}
		JScrollPane scrollPane2 = new JScrollPane(p2);
		this.add("΢��", scrollPane2);

		// ��ע
		String s1[] = new Select().select_Attention_id(GlobalVar.login_visitor.getId());
		String[] columnNames1 = { "ID", "�ǳ�", "�Ա�", "����", "΢����", "��ע��", "��˿��" };// ��������
		String[][] tableValues1 = new String[s1.length][columnNames1.length];// �������飬�����洢�������
		for (int i = 0; i < s1.length; i++) {
			Visitor[] visitors1 = new Select().select_Visitor_id(s1[i]);
			for (int j = 0; j < visitors1.length; j++) {
				tableValues1[i][0] = visitors1[j].getId();
				tableValues1[i][1] = visitors1[j].getVisitor_name();
				tableValues1[i][2] = visitors1[j].getVisitor_sex();
				tableValues1[i][3] = visitors1[j].getVisitor_birthday();
				tableValues1[i][4] = new ResultSetNum().resultSetNumWeibo(visitors1[j].getId()).toString();
				tableValues1[i][5] = new ResultSetNum().resultSetNumAttention(visitors1[j].getId()).toString();
				tableValues1[i][6] = new ResultSetNum().resultSetNumFans(visitors1[j].getId()).toString();
			}
		}
		JTable table1 = new JTable(tableValues1, columnNames1);
		table1.setSelectionForeground(Color.RED);// ������ɫ
		table1.setBackground(Color.PINK);
		table1.setSelectionBackground(Color.yellow);// ����ɫ
		table1.setRowHeight(30);// �����и�30����
		table1.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);// ѡ��ģʽ(��ѡ)
		table1.setEnabled(false);// ���ɱ༭
		table1.getTableHeader().setReorderingAllowed(false);// �����б�ͷ���������϶�����
		int[] width1 = new int[] { 50, 50, 5, 60, 5, 5, 5 };// width��int��һά����
		table1.setColumnModel(getColumn(table1, width1)); // ���ò����п�
		System.out.println("�����" + table1.getRowCount() + "��" + table1.getColumnCount() + "��");
		JScrollPane scrollPane3 = new JScrollPane(table1);
		this.add("��ע", scrollPane3);

		// ��˿
		String s2[] = new Select().select_Fans_id(GlobalVar.login_visitor.getId());
		String[] columnNames2 = { "ID", "�ǳ�", "�Ա�", "����", "΢����", "��ע��", "��˿��" };// ��������
		String[][] tableValues2 = new String[s2.length][columnNames2.length];// �������飬�����洢�������
		for (int i = 0; i < s2.length; i++) {
			Visitor[] visitors2 = new Select().select_Visitor_id(s2[i]);
			for (int j = 0; j < visitors2.length; j++) {
				tableValues2[i][0] = visitors2[j].getId();
				tableValues2[i][1] = visitors2[j].getVisitor_name();
				tableValues2[i][2] = visitors2[j].getVisitor_sex();
				tableValues2[i][3] = visitors2[j].getVisitor_birthday();
				tableValues2[i][4] = new ResultSetNum().resultSetNumWeibo(visitors2[j].getId()).toString();
				tableValues2[i][5] = new ResultSetNum().resultSetNumAttention(visitors2[j].getId()).toString();
				tableValues2[i][6] = new ResultSetNum().resultSetNumFans(visitors2[j].getId()).toString();
			}
		}
		JTable table2 = new JTable(tableValues2, columnNames2);
		table2.setSelectionForeground(Color.RED);// ������ɫ
		table2.setBackground(Color.PINK);
		table2.setSelectionBackground(Color.yellow);// ����ɫ
		table2.setRowHeight(30);// �����и�30����
		table2.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);// ѡ��ģʽ(��ѡ)
		table2.setEnabled(false);// ���ɱ༭
		table2.getTableHeader().setReorderingAllowed(false);// �����б�ͷ���������϶�����
		int[] width2 = new int[] { 50, 50, 5, 60, 5, 5, 5 };// width��int��һά����
		table2.setColumnModel(getColumn(table1, width2)); // ���ò����п�
		System.out.println("�����" + table2.getRowCount() + "��" + table2.getColumnCount() + "��");
		JScrollPane scrollPane4 = new JScrollPane(table2);
		this.add("��˿", scrollPane4);
	}

	/**
	 * @Description ˵�������ñ�񲻵ȿ��
	 */
	public static TableColumnModel getColumn(JTable table, int[] width) {
		TableColumnModel columns = table.getColumnModel();
		for (int i = 0; i < width.length; i++) {
			TableColumn column = columns.getColumn(i);
			column.setPreferredWidth(width[i]);
		}
		return columns;
	}
}
