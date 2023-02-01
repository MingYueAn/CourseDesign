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
 * @Author 作者
 * @Description 说明：主要的显示面板
 * @Date 时间：2020-11-20
 */
@SuppressWarnings("serial")
public class Panel3_MainFunction extends JTabbedPane {

	// 选项卡面板
	private BackgroundPanel p1 = new BackgroundPanel(new ImageIcon("images\\背景_3.jpg").getImage());
	private BackgroundPanel p2 = new BackgroundPanel(new ImageIcon("images\\背景_3.jpg").getImage());

	public Panel3_MainFunction() {
		super(JTabbedPane.RIGHT);// 设置标签显示位置
//		setTabLayoutPolicy(JTabbedPane.SCROLL_TAB_LAYOUT);// 设置选项卡标签的布局方式（滚动）
//		setTabLayoutPolicy(JTabbedPane.WRAP_TAB_LAYOUT);// 设置选项卡标签的布局方式（换行）
		init();
	}

	private void init() {

		// 主页
		p1.setLayout(new AfAnyWhere());
		p1.setPreferredSize(new Dimension(300, Constant.NUM * GlobalVar.weibo_num2));
		// 根据当前访客的id获取所有关注的id数组
		// 根据id数组获取微博数组(查找attention_id，获取fans_id)
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
		this.add("主页", scrollPane1);

		// 微博
		p2.setLayout(new AfAnyWhere());
		p2.setPreferredSize(new Dimension(300, Constant.NUM * GlobalVar.weibo_num1));
		// 通过当前访客id获取微博数组
		Weibo[] weibos = new Select().select_Writer_id(GlobalVar.login_visitor.getId());
		for (int i = 0; i < weibos.length; i++) {
			p2.add(new PanelTXT(weibos[i]), new AfMargin(Constant.NUM * i, 0, -1, 0));
		}
		JScrollPane scrollPane2 = new JScrollPane(p2);
		this.add("微博", scrollPane2);

		// 关注
		String s1[] = new Select().select_Attention_id(GlobalVar.login_visitor.getId());
		String[] columnNames1 = { "ID", "昵称", "性别", "生日", "微博数", "关注数", "粉丝数" };// 定义表格列
		String[][] tableValues1 = new String[s1.length][columnNames1.length];// 定义数组，用来存储表格数据
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
		table1.setSelectionForeground(Color.RED);// 字体颜色
		table1.setBackground(Color.PINK);
		table1.setSelectionBackground(Color.yellow);// 背景色
		table1.setRowHeight(30);// 设置行高30像素
		table1.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);// 选择模式(单选)
		table1.setEnabled(false);// 不可编辑
		table1.getTableHeader().setReorderingAllowed(false);// 设置列表头不可重新拖动排列
		int[] width1 = new int[] { 50, 50, 5, 60, 5, 5, 5 };// width是int型一维数组
		table1.setColumnModel(getColumn(table1, width1)); // 设置不等列宽
		System.out.println("表格共有" + table1.getRowCount() + "行" + table1.getColumnCount() + "列");
		JScrollPane scrollPane3 = new JScrollPane(table1);
		this.add("关注", scrollPane3);

		// 粉丝
		String s2[] = new Select().select_Fans_id(GlobalVar.login_visitor.getId());
		String[] columnNames2 = { "ID", "昵称", "性别", "生日", "微博数", "关注数", "粉丝数" };// 定义表格列
		String[][] tableValues2 = new String[s2.length][columnNames2.length];// 定义数组，用来存储表格数据
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
		table2.setSelectionForeground(Color.RED);// 字体颜色
		table2.setBackground(Color.PINK);
		table2.setSelectionBackground(Color.yellow);// 背景色
		table2.setRowHeight(30);// 设置行高30像素
		table2.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);// 选择模式(单选)
		table2.setEnabled(false);// 不可编辑
		table2.getTableHeader().setReorderingAllowed(false);// 设置列表头不可重新拖动排列
		int[] width2 = new int[] { 50, 50, 5, 60, 5, 5, 5 };// width是int型一维数组
		table2.setColumnModel(getColumn(table1, width2)); // 设置不等列宽
		System.out.println("表格共有" + table2.getRowCount() + "行" + table2.getColumnCount() + "列");
		JScrollPane scrollPane4 = new JScrollPane(table2);
		this.add("粉丝", scrollPane4);
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
