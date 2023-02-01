package view_visitor;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JToolBar;
import javax.swing.border.BevelBorder;

import control.Delete;
import data.Constant;
import data.GlobalVar;
import data.Refresh;
import model.Weibo;
import tools.AfAnyWhere;
import tools.AfMargin;

/**
 * @Author 作者
 * @Description 说明：查看微博内容
 * @Date 时间：2020-12-10
 */
@SuppressWarnings("serial")
public class PanelTXT extends JPanel {
	private JTextArea textArea = new JTextArea();
	private JToolBar toolBar = new JToolBar();
	Weibo weibo;

	/**
	 * @Description 说明：构造方法
	 */
	public PanelTXT(Weibo w) {
		super();
		this.weibo = w;
		if (w.getWriter_id().equals(GlobalVar.login_visitor.getId()) == true) {
			init();// 自己的微博
		} else if (w.getWriter_id().equals(GlobalVar.login_visitor.getId()) == false) {
			initwr();// 其他人的微博
		}
	}

	// 自己的微博
	private void init() {
		this.setLayout(new AfAnyWhere());
		this.setPreferredSize(new Dimension(500, 115));
		this.setBackground(new Color(149, 100, 219));
		// 微博内容显示
		textArea.setRows(4);// 4行
		textArea.setBackground(new Color(255, 249, 201));// 背景色
		textArea.setFont(Constant.FONT2);// 字体
		textArea.setEditable(false);// 不可编辑
		textArea.setLineWrap(true);// 自动换行
		textArea.setText(weibo.getWeibo_content());// 内容

		// 删除按钮
		JButton button = new JButton(new ImageIcon("images/2_删除.png"));
		button.setToolTipText("删除");
		button.addActionListener((e) -> {
			// 删除
			new Delete().delete(weibo);
			new Refresh();
		});

		// 工具条
		toolBar.setFloatable(false);
		toolBar.setBorder(new BevelBorder(BevelBorder.LOWERED));
		toolBar.add(button);
		toolBar.add(new JLabel("     微博id：" + weibo.getWeibo_id()));
		toolBar.add(new JLabel("     作者id：" + weibo.getWriter_id()));
		toolBar.add(new JLabel("     读者id：" + data.GlobalVar.login_visitor.getId()));

		JScrollPane scrollPane = new JScrollPane(textArea);
		this.add(scrollPane, new AfMargin(0, 0, -1, 0));
		this.add(toolBar, new AfMargin(-1, 0, 0, 0));
	}

	// 其他人的微博
	private void initwr() {
		this.setLayout(new AfAnyWhere());
		this.setPreferredSize(new Dimension(500, 115));
		this.setBackground(new Color(149, 100, 219));
		// 微博内容显示
		textArea.setRows(4);// 4行
		textArea.setBackground(new Color(255, 249, 201));// 背景色
		textArea.setFont(Constant.FONT2);// 字体
		textArea.setEditable(false);// 不可编辑
		textArea.setLineWrap(true);// 自动换行
		textArea.setText(weibo.getWeibo_content());// 内容

		// 工具条
		toolBar.setFloatable(false);
		toolBar.setBorder(new BevelBorder(BevelBorder.LOWERED));
		toolBar.add(new JLabel("     微博id：" + weibo.getWeibo_id()));
		toolBar.add(new JLabel("     作者id：" + weibo.getWriter_id()));
		toolBar.add(new JLabel("     读者id：" + data.GlobalVar.login_visitor.getId()));

		JScrollPane scrollPane = new JScrollPane(textArea);
		this.add(scrollPane, new AfMargin(0, 0, -1, 0));
		this.add(toolBar, new AfMargin(-1, 0, 0, 0));
	}
}
