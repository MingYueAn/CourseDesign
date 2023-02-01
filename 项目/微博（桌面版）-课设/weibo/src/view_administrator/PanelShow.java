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
 * @Author 作者：物联19102 谢晓艳 190611201
 * @Description 说明：查看访客
 * @Date 时间：2020-12-2
 */
@SuppressWarnings("serial")
public class PanelShow extends BackgroundPanel {

	public PanelShow() {
		super(new ImageIcon("images\\背景_2.jpg").getImage());
		this.setLayout(new AfAnyWhere());
	}

	/**
	 * @Description 说明：查看访客面板初始化
	 */
	public void init(int w, int h) {
		JLabel Title = new JLabel("查看访客");
		Title.setFont(Constant.FONT1);
		this.add(Title, AfMargin.TOP_CENTER);
		JLabel numJLabel = new JLabel("访客总数：" + new ResultSetNum().resultSetNumVisitor());
		numJLabel.setFont(Constant.FONT2);
		this.add(numJLabel, new AfMargin(45, -1, -1, -1));

		Visitor[] visitors = new Select().select_Visitor();
		String[] columnNames = { "ID", "昵称", "性别", "生日", "微博数", "关注数", "粉丝数" };// 定义表格列
		String[][] tableValues = new String[visitors.length][columnNames.length];// 定义数组，用来存储表格数据
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
		table.setSelectionForeground(Color.RED);// 字体颜色
		table.setBackground(Color.PINK);
		table.setSelectionBackground(Color.yellow);// 背景色
		table.setRowHeight(30);// 设置行高30像素
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);// 选择模式(单选)
		table.setEnabled(false);// 不可编辑
		table.getTableHeader().setReorderingAllowed(false);// 设置列表头不可重新拖动排列
		int[] width = new int[] { 30, 30, 20, 70, 20, 20, 20 };// width是int型一维数组
		table.setColumnModel(getColumn(table, width)); // 设置不等列宽
		System.out.println("表格共有" + table.getRowCount() + "行" + table.getColumnCount() + "列");
		JScrollPane scrollPane = new JScrollPane(table);
		this.add(scrollPane, new AfMargin(60, -1, 50, -1));
	}

	/**
	 * @Description 说明：设置表格不等宽度
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
