package view_administrator;

import java.awt.Color;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JSplitPane;

import data.Constant;
import tools.BackgroundPanel;

/**
 * @Author 作者
 * @Description 说明：管理员窗口
 * @Date 时间：2020-12-2
 */
@SuppressWarnings("serial")
public class Interface_MainFrame extends JFrame {

	// 按钮（增、删、改、查）
	private JButton button1, button2, button3, button4;
	// 创建中间容器[设置布局]
	private BackgroundPanel panel1, panel2;
	// 分割面板VERTICAL\HORIZONTAL
	private JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, true);

	/**
	 * 构造方法
	 */
	public Interface_MainFrame() {
		this.setVisible(false);
		// 设置窗口标题
		this.setTitle("管理员界面");
		// 设置窗口大小
		this.setSize(600, 500);
		// 居中显示
		this.setLocationRelativeTo(null);

		// 设置图标
		this.setIconImage(new ImageIcon("images\\图标_微博.png").getImage());

		// 设置点击关闭窗口后做出的处理
		// JFrame.DO_NOTHING_ON_CLOSE 什么也不做
		// JFrame.HIDE_ON_CLOSE 隐藏当前窗口
		// JFrame.DISPOSE_ON_CLOSE 隐藏当前窗口，并释放窗体占有的其他资源
		// JFrrame.EXIT_ON_CLOSE 结束窗口所在应用程序
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		/* 面板设置 */
		this.panelSettings();
		/* 事件监听 */
		this.addListener();

		// 将分割面板添加到窗口
		this.add(splitPane);
		// 设置窗口是否可缩放
		this.setResizable(false);
		// 设置窗口是否可见
		this.setVisible(true);

	}

	/**
	 * @Description 说明：面板设置
	 */
	private void panelSettings() {

		/* 面板1设置 */
		panel1 = new BackgroundPanel(new ImageIcon("images\\背景_1.jpg").getImage());
		panel1.setLayout(null);
		// 按钮
		button1 = new JButton("删除访客");
		button1.setFont(Constant.FONT2);
		button1.setBounds(0, 50, 100, 45);
		button2 = new JButton("修改访客");
		button2.setFont(Constant.FONT2);
		button2.setBounds(0, 100, 100, 45);
		button3 = new JButton("查看访客");
		button3.setFont(Constant.FONT2);
		button3.setBounds(0, 150, 100, 45);
		button4 = new JButton("修改账号密码");
		button4.setForeground(Color.red);
		button4.setBounds(0, 200, 100, 45);
		// 添加组件
		panel1.add(button1);
		panel1.add(button2);
		panel1.add(button3);
		panel1.add(button4);

		/* 面板2设置 */
		setupPanel();

		/* 分割面板设置 */
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

	/**
	 * 面板panel2初始界面
	 */
	private void setupPanel() {
		// TODO 自动生成的方法存根
		panel2 = new BackgroundPanel(new ImageIcon("images\\背景_2.jpg").getImage());
		JLabel Title = new JLabel("欢迎来到管理员界面！");
		Title.setFont(Constant.FONT1);
		panel2.add(Title);
	}

	/**
	 * @Description 说明：事件监听
	 */
	private void addListener() {
		// TODO 自动生成的方法存根
		button1.addActionListener((e) -> setup_PanelDelete(panel2, new PanelDelete()));
		button2.addActionListener((e) -> setup_PanelUpdate(panel2, new PanelUpdate()));
		button3.addActionListener((e) -> setup_PanelSelect(panel2, new PanelShow()));
		button4.addActionListener((e) -> update(panel2, new PanelAdministrator()));
	}

	private Object update(BackgroundPanel panel, PanelAdministrator panelAdministrator) {
		// TODO 自动生成的方法存根
		panel.removeAll();// 移除面板中的所有组件
		panel.add(panelAdministrator);// 添加要切换的面板
		panel.repaint();// 刷新页面，重绘面板
		panel.validate();// 使重绘的面板确认生效

		// 初始化
		panelAdministrator.init(panel.getWidth(), panel.getHeight());

		// 更换分割面板
		splitPane.setLeftComponent(panel1);
		splitPane.setRightComponent(panelAdministrator);
		// 设置分割线大小
		splitPane.setDividerSize(0);
		// 设置分割线位置
		splitPane.setDividerLocation(100);
		// 设置分割线拖动
		splitPane.setEnabled(false);
		// 在分隔符上提供UI小部件以快速扩展/折叠分隔符
		splitPane.setOneTouchExpandable(false);
		return null;
	}

	private Object setup_PanelSelect(BackgroundPanel panel, PanelShow panelSelect) {
		// TODO 自动生成的方法存根
		panel.removeAll();// 移除面板中的所有组件
		panel.add(panelSelect);// 添加要切换的面板
		panel.repaint();// 刷新页面，重绘面板
		panel.validate();// 使重绘的面板确认生效

		// 初始化
		panelSelect.init(panel.getWidth(), panel.getHeight());

		// 更换分割面板
		splitPane.setLeftComponent(panel1);
		splitPane.setRightComponent(panelSelect);
		// 设置分割线大小
		splitPane.setDividerSize(0);
		// 设置分割线位置
		splitPane.setDividerLocation(100);
		// 设置分割线拖动
		splitPane.setEnabled(false);
		// 在分隔符上提供UI小部件以快速扩展/折叠分隔符
		splitPane.setOneTouchExpandable(false);
		return null;
	}

	private Object setup_PanelUpdate(BackgroundPanel panel, PanelUpdate panelUpdate) {
		// TODO 自动生成的方法存根
		panel.removeAll();// 移除面板中的所有组件
		panel.add(panelUpdate);// 添加要切换的面板
		panel.repaint();// 刷新页面，重绘面板
		panel.validate();// 使重绘的面板确认生效

		// 初始化
		panelUpdate.init(panel.getWidth(), panel.getHeight());

		// 更换分割面板
		splitPane.setLeftComponent(panel1);
		splitPane.setRightComponent(panelUpdate);
		// 设置分割线大小
		splitPane.setDividerSize(0);
		// 设置分割线位置
		splitPane.setDividerLocation(100);
		// 设置分割线拖动
		splitPane.setEnabled(false);
		// 在分隔符上提供UI小部件以快速扩展/折叠分隔符
		splitPane.setOneTouchExpandable(false);
		return null;
	}

	private Object setup_PanelDelete(BackgroundPanel panel, PanelDelete panelDelete) {
		// TODO 自动生成的方法存根
		panel.removeAll();// 移除面板中的所有组件
		panel.add(panelDelete);// 添加要切换的面板
		panel.repaint();// 刷新页面，重绘面板
		panel.validate();// 使重绘的面板确认生效

		// 初始化
		panelDelete.init(panel.getWidth(), panel.getHeight());

		// 更换分割面板
		splitPane.setLeftComponent(panel1);
		splitPane.setRightComponent(panelDelete);
		// 设置分割线大小
		splitPane.setDividerSize(0);
		// 设置分割线位置
		splitPane.setDividerLocation(100);
		// 设置分割线拖动
		splitPane.setEnabled(false);
		// 在分隔符上提供UI小部件以快速扩展/折叠分隔符
		splitPane.setOneTouchExpandable(false);
		return null;
	}
}
