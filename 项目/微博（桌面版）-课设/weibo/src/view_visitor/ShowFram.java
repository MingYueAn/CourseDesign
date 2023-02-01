package view_visitor;

import java.awt.Dimension;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;

import control.Delete;
import control.Insert;
import control.ResultSetNum;
import control.Select;
import data.Constant;
import data.GlobalVar;
import model.AttentionFans;
import model.Visitor;
import model.Weibo;
import tools.AfAnyWhere;
import tools.AfMargin;
import tools.BackgroundPanel;

/**
 * @Author 作者
 * @Description 说明：查看
 * @Date 时间：2020-12-15
 */
@SuppressWarnings("serial")
public class ShowFram extends JFrame {

	private JButton button1 = new JButton("关注");
	private JButton button2 = new JButton("取消关注");
	private BackgroundPanel panel1, panel2;
	private JSplitPane splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT, true);
	private Visitor visitor;

	public ShowFram(Visitor visitor, JFrame frame) {
		this.visitor = visitor;
		// 设置窗口标题
		this.setTitle("查看");
		// 设置窗口大小
		this.setSize(600, 450);
		// 居中显示
		this.setLocationRelativeTo(null);
		// 设置图标
		this.setIconImage(new ImageIcon("images\\图标_微博.png").getImage());
		// 设置组件
		init();
		// 将分割面板添加到窗口
		this.add(splitPane);
		// 设置窗口是否可缩放
		this.setResizable(false);
		// 设置窗口是否可见
		this.setVisible(true);
	}

	private void init() {
		panel1 = new BackgroundPanel(new ImageIcon("images\\背景_3.jpg").getImage());
		panel2 = new BackgroundPanel(new ImageIcon("images\\背景_3.jpg").getImage());

		// panel1设置
		panel1.setLayout(new AfAnyWhere());
		JLabel label = new JLabel(visitor.getVisitor_name());
		label.setFont(Constant.FONT1);
		JLabel label1 = new JLabel("微博：" + new ResultSetNum().resultSetNumWeibo(visitor.getId()));
		label1.setFont(Constant.FONT2);
		JLabel label2 = new JLabel("关注：" + new ResultSetNum().resultSetNumAttention(visitor.getId()));
		label2.setFont(Constant.FONT2);
		JLabel label3 = new JLabel("粉丝：" + new ResultSetNum().resultSetNumFans(visitor.getId()));
		label3.setFont(Constant.FONT2);
		panel1.add(label, new AfMargin(10, 30, -1, -1));
		panel1.add(label1, new AfMargin(-1, 50, 20, -1));
		panel1.add(label2, new AfMargin(-1, 150, 20, -1));
		panel1.add(label3, new AfMargin(-1, 250, 20, -1));
		button1.setFont(Constant.FONT2);
		panel1.add(button1, new AfMargin(-1, -1, 20, 150));
		button2.setFont(Constant.FONT2);
		panel1.add(button2, new AfMargin(-1, -1, 20, 50));

		// panel2设置
		panel2.setLayout(new AfAnyWhere());
		BackgroundPanel p1 = new BackgroundPanel(new ImageIcon("images\\背景_3.jpg").getImage());
		p1.setLayout(new AfAnyWhere());
		p1.setPreferredSize(new Dimension(300, Constant.NUM * new ResultSetNum().resultSetNumWeibo(visitor.getId())));
		Weibo[] weibos = new Select().select_Writer_id(visitor.getId());
		for (int i = 0; i < weibos.length; i++) {
			p1.add(new PanelTXT(weibos[i]), new AfMargin(Constant.NUM * i, 0, -1, 0));
		}
		JScrollPane scrollPane = new JScrollPane(p1);
		panel2.add(scrollPane);

		if (visitor.getId().equals(GlobalVar.login_visitor.getId()) == true) {
			button1.setVisible(false);
			button2.setVisible(false);
		}

		// 按钮事件处理
		/* 关注 */
		button1.addActionListener(Event -> {
			AttentionFans attentionFans = new AttentionFans();
			attentionFans.setAttention_id(GlobalVar.login_visitor.getId());
			attentionFans.setFans_id(visitor.getId());
			if (new Select().select(attentionFans) == true) {
				JOptionPane.showMessageDialog(null, "已经关注过了", "警告", JOptionPane.INFORMATION_MESSAGE);
				this.setVisible(false);
			} else if (new Insert().insert(attentionFans)) {
				JOptionPane.showMessageDialog(null, "关注成功");
				this.setVisible(false);
			}

		});
		/* 取消关注 */
		button2.addActionListener(Event -> {
			AttentionFans attentionFans = new AttentionFans();
			attentionFans.setAttention_id(GlobalVar.login_visitor.getId());
			attentionFans.setFans_id(visitor.getId());
			if (new Select().select(attentionFans) == false) {
				JOptionPane.showMessageDialog(null, "你还没有关注", "警告", JOptionPane.INFORMATION_MESSAGE);
				this.setVisible(false);
			} else if (new Delete().delete(attentionFans)) {
				JOptionPane.showMessageDialog(null, "取消关注完成");
				this.setVisible(false);
			}
		});
		splitPane.setLeftComponent(panel1);
		splitPane.setRightComponent(panel2);
		// 设置分割线大小
		splitPane.setDividerSize(0);
		// 设置分割线位置
		splitPane.setDividerLocation(100);
		// 设置分割线拖动
		splitPane.setEnabled(false);
		// 在分隔符上提供UI小部件以快速扩展/折叠分隔符
		splitPane.setOneTouchExpandable(false);
	}
}