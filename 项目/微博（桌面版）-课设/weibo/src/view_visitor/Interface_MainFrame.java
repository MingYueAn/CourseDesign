package view_visitor;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.IOException;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JSplitPane;
import javax.swing.JTextField;
import javax.swing.JToolBar;
import javax.swing.border.BevelBorder;

import control.Delete;
import control.Select;
import data.Constant;
import data.GlobalVar;
import data.Refresh;
import model.Visitor;

/**
 * @Author 作者
 * @Description 说明：访客主界面
 * @Date 时间：2020-11-20
 */
@SuppressWarnings("serial")
public class Interface_MainFrame extends JFrame {

	// 工具栏
	private JToolBar jToolBar = new JToolBar();

	private JButton button1, button2, button3, button;
	private JTextField idTextField;

	// 创建中间容器[设置布局]
	private Panel1_PersonalInformation panel1;
	private Panel2_EditorialPublished panel2;
	private Panel3_MainFunction panel3;
	private Panel4_PlaybackControls panel4;

	// 分割面板VERTICAL\HORIZONTAL
	private JSplitPane splitPane1 = new JSplitPane(JSplitPane.VERTICAL_SPLIT, true);
	private JSplitPane splitPane2 = new JSplitPane(JSplitPane.VERTICAL_SPLIT, true);
	private JSplitPane splitPane3 = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, true);

	/**
	 * 构造方法
	 */
	public Interface_MainFrame() {
		System.out.println("-----登录访客对象的部分信息");
		System.out.println("访客ID：" + GlobalVar.login_visitor.getId());
		System.out.println("访客昵称：" + GlobalVar.login_visitor.getVisitor_name());
		System.out.println("可见微博总个数：" + GlobalVar.weibo_num2);
		System.out.println("微博个数：" + GlobalVar.weibo_num1);
		System.out.println("关注个数：" + GlobalVar.attention_num);
		System.out.println("粉丝个数：" + GlobalVar.fans_num);
		// 顶层容器的设置
		// 设置窗口标题
		this.setTitle("微博");
		// 设置窗口大小
		this.setSize(900, 600);
		// 居中显示
		this.setLocationRelativeTo(null);

		// 设置图标
		this.setIconImage(new ImageIcon("images\\图标_微博.png").getImage());

		// 设置点击关闭窗口后做出的处理
		// JFrame.DO_NOTHING_ON_CLOSE 什么也不做
		// JFrame.HIDE_ON_CLOSE 隐藏当前窗口
		// JFrame.DISPOSE_ON_CLOSE 隐藏当前窗口，并释放窗体占有的其他资源
		// JFrrame.EXIT_ON_CLOSE 结束窗口所在应用程序
//		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		/* 面板设置与数据处理 */
		this.panelSettings();
		/* 事件监听 */
		this.addListener();

		// 添加工具栏
		this.add(jToolBar, BorderLayout.NORTH);
		// 将分割面板添加到窗口
		this.add(splitPane3);
		// 设置窗口是否可缩放
		this.setResizable(false);
		// 设置窗口是否可见
		this.setVisible(true);
		// 添加窗口监听器
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				Panel2_EditorialPublished.editArea.requestFocus();
				String currentValue = Panel2_EditorialPublished.editArea.getText();
				if (currentValue.equals(SetupJMenuBar.oldValue) == true || Panel2_EditorialPublished.editArea.getText().equals("")) {
					System.exit(0);
				} else {
					int exitChoose = JOptionPane.showConfirmDialog(panel2, "您的文件尚未保存，是否保存？", "退出提示", JOptionPane.YES_NO_CANCEL_OPTION);
					if (exitChoose == JOptionPane.YES_OPTION) {
						new SetupJMenuBar().SaveTextFileAs();
					} else if (exitChoose == JOptionPane.NO_OPTION) {
						System.exit(0);
					}
				}
			}
		});

	}

	/**
	 * @Description 说明：面板设置与数据处理
	 */
	private void panelSettings() {

		/* 设置菜单条 */
		this.setJMenuBar(new SetupJMenuBar());
		/* 设置工具栏 */
		this.setJToolBar();
		/* 面板panel1设置——个人信息 */
		panel1 = new Panel1_PersonalInformation();
		/* 面板panel2设置——编辑、发布微博 */
		panel2 = new Panel2_EditorialPublished();
		/* 面板panel3设置——浏览微博 */
		panel3 = new Panel3_MainFunction();
		/* 面板panel4设置——音乐播放控件 */
		panel4 = new Panel4_PlaybackControls();
		/* 设置分割面板 */
		this.setupSplitPane();
	}

	/**
	 * @Description 说明：设置工具栏
	 */
	private void setJToolBar() {
		// 设置工具栏不可浮动
		jToolBar.setFloatable(false);
		// 设置工具栏边框导角方式
		jToolBar.setBorder(new BevelBorder(BevelBorder.LOWERED));
		jToolBar.setBackground(Color.yellow);

		button = new JButton(new ImageIcon("images/1_截图.png"));
		button.setToolTipText("截图");
		idTextField = new JTextField("请输入需要查找的账号", 25);
		button1 = new JButton(new ImageIcon("images/1_搜索.png"));
		button1.setToolTipText("搜索");
		button2 = new JButton(new ImageIcon("images/1_刷新.png"));
		button2.setToolTipText("刷新");
		button3 = new JButton(new ImageIcon("images/1_注销.png"));
		button3.setToolTipText("注销");
		jToolBar.add(idTextField);
		jToolBar.add(button1);
		jToolBar.addSeparator();
		jToolBar.add(button);
		jToolBar.add(button2);
		jToolBar.add(button3);
	}

	/**
	 * 设置分割面板
	 */
	private void setupSplitPane() {
		splitPane1.setLeftComponent(panel1);
		splitPane1.setRightComponent(panel2);
		// 设置分割线大小
		splitPane1.setDividerSize(0);
		// 设置分割线位置
		splitPane1.setDividerLocation(300);
		// 设置分割线拖动
		splitPane1.setEnabled(false);
		// 在分隔符上提供UI小部件以快速扩展/折叠分隔符
		splitPane1.setOneTouchExpandable(false);

		splitPane2.setLeftComponent(panel3);
		splitPane2.setRightComponent(panel4);
		// 设置分割线大小
		splitPane2.setDividerSize(0);
		// 设置分割线位置
		splitPane2.setDividerLocation(420);
		// 设置分割线拖动
		splitPane2.setEnabled(false);
		// 在分隔符上提供UI小部件以快速扩展/折叠分隔符
		splitPane2.setOneTouchExpandable(false);

		splitPane3.setLeftComponent(splitPane1);
		splitPane3.setRightComponent(splitPane2);
		// 设置分割线大小
		splitPane3.setDividerSize(10);
		// 设置分割线位置
		splitPane3.setDividerLocation(300);
		// 设置分割线拖动
		splitPane3.setEnabled(false);
		// 在分隔符上提供UI小部件以快速扩展/折叠分隔符
		splitPane3.setOneTouchExpandable(true);
	}

	/**
	 * 事件监听
	 */
	private void addListener() {
		/* 截图 */
		button.addActionListener((e) -> {
			// 相对地址
			File file = new File("database", "屏幕截图.exe");
			Runtime runtime = Runtime.getRuntime();
			System.out.println("屏幕截图.exe的绝对地址：" + file.getAbsolutePath());
			try {
				runtime.exec(file.getAbsolutePath());
			} catch (IOException e1) {
				System.out.println(e1);
			}
		});
		/* 搜索 */
		button1.addActionListener((e) -> {
			/* 访客 */
			Visitor visitor = new Visitor(idTextField.getText());
			new Select().select(visitor);
			if (visitor.getVisitor_yes_no().equals(Constant.YES_STRING) == true) {
				new ShowFram(visitor, this);
			} else {
				System.out.println("不可查看");
				JOptionPane.showMessageDialog(null, "账号不存在", "警告", JOptionPane.INFORMATION_MESSAGE);
			}
		});
		/* 刷新 */
		button2.addActionListener((e) -> {
			this.setVisible(false);
			new Refresh();
		});
		/* 注销 */
		button3.addActionListener((e) -> {
			// 对话框（询问是否需要注销）
			int buttonChoose = JOptionPane.showConfirmDialog(this, "您即将注销该账号，请确认注销", "提示", JOptionPane.YES_NO_OPTION);
			if (buttonChoose == JOptionPane.NO_OPTION) {
				return;
			} else if (buttonChoose == JOptionPane.YES_OPTION) {
				new Delete().delete(GlobalVar.login_personal);
				new Delete().delete(GlobalVar.login_visitor);
				new Delete().deleteWeibo(GlobalVar.login_visitor.getId());
				new Delete().delete1(GlobalVar.login_visitor.getId());
				new Delete().delete2(GlobalVar.login_visitor.getId());
				System.exit(0);
			}
		});
	}
}