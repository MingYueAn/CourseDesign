package view_administrator;

import java.awt.Color;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;

import control.ResultSetNum;
import control.Select;
import data.Constant;
import model.Visitor;
import tools.AfAnyWhere;
import tools.AfMargin;
import tools.BackgroundPanel;

/**
 * @Author ���ߣ�����19102 л���� 190611201
 * @Description ˵�����鿴�ÿ�
 * @Date ʱ�䣺2020-12-2
 */
@SuppressWarnings("serial")
public class PanelShow extends BackgroundPanel {

	public PanelShow() {
		super(new ImageIcon("images\\����_2.jpg").getImage());
		this.setLayout(new AfAnyWhere());
	}

	/**
	 * @Description ˵�����鿴�ÿ�����ʼ��
	 */
	public void init(int w, int h) {
		JLabel Title = new JLabel("�鿴�ÿ�");
		Title.setFont(Constant.FONT1);
		this.add(Title, AfMargin.TOP_CENTER);
		JLabel numJLabel = new JLabel("�ÿ�������" + new ResultSetNum().resultSetNumVisitor());
		numJLabel.setFont(Constant.FONT2);
		this.add(numJLabel, new AfMargin(45, -1, -1, -1));

		Visitor[] visitors = new Select().select_Visitor();
		String[] columnNames = { "ID", "�ǳ�", "�Ա�", "����", "΢����", "��ע��", "��˿��" };// ��������
		String[][] tableValues = new String[visitors.length][columnNames.length];// �������飬�����洢�������
		for (int i = 0; i < visitors.length; i++) {
			tableValues[i][0] = visitors[i].getId();
			tableValues[i][1] = visitors[i].getVisitor_name();
			tableValues[i][2] = visitors[i].getVisitor_sex();
			tableValues[i][3] = visitors[i].getVisitor_birthday();
			tableValues[i][4] = new ResultSetNum().resultSetNumWeibo(visitors[i].getId()).toString();
			tableValues[i][5] = new ResultSetNum().resultSetNumAttention(visitors[i].getId()).toString();
			tableValues[i][6] = new ResultSetNum().resultSetNumFans(visitors[i].getId()).toString();
		}
		JTable table = new JTable(tableValues, columnNames);
		table.setSelectionForeground(Color.RED);// ������ɫ
		table.setBackground(Color.PINK);
		table.setSelectionBackground(Color.yellow);// ����ɫ
		table.setRowHeight(30);// �����и�30����
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);// ѡ��ģʽ(��ѡ)
		table.setEnabled(false);// ���ɱ༭
		table.getTableHeader().setReorderingAllowed(false);// �����б�ͷ���������϶�����
		int[] width = new int[] { 30, 30, 20, 70, 20, 20, 20 };// width��int��һά����
		table.setColumnModel(getColumn(table, width)); // ���ò����п�
		System.out.println("�����" + table.getRowCount() + "��" + table.getColumnCount() + "��");
		JScrollPane scrollPane = new JScrollPane(table);
		this.add(scrollPane, new AfMargin(60, -1, 50, -1));
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
