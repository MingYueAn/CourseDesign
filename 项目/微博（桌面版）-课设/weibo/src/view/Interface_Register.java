package view;

import javax.swing.*;

import control.LoginRegister;
import data.Constant;
import tools.BackgroundPanel;
import tools.StringUtil;

/**
 * @Author 作者：物联19102 谢晓艳 190611201
 * @Description 说明：注册界面
 * @Date 时间：2020-12-18
 */
@SuppressWarnings("serial")
public class Interface_Register extends JFrame {

	// 按钮（返回登录、确定注册）
	private JButton back = new JButton("返回登录"), ok = new JButton("确定注册");
	// 标签（账号：、密码：、确认密码：、注册界面）
	private JLabel jLabel1 = new JLabel("账号："), jLabel2 = new JLabel("密码："), jLabel3 = new JLabel("确认密码："), jLabelTitle = new JLabel("注册界面");
	// 文本框（输入账号）
	private JTextField idTextField = new JTextField();
	// 密码框（输入密码）
	private JPasswordField password1 = new JPasswordField();
	// 密码框（确认密码）
	private JPasswordField password2 = new JPasswordField();
	// 创建中间容器[设置布局]
	private JPanel panel;

	/**
	 * @Description 说明：构造方法
	 */
	public Interface_Register() {
		// 顶层容器的设置
		// 设置窗口标题
		this.setTitle("注册界面");
		// 设置窗口大小
		this.setSize(600, 500);
		// 设置窗口位置居中显示
		this.setLocationRelativeTo(null);
		// 参数是Image抽象类的对象。
		// JFmageIcon实现的是Icon接口 ，并没有实现Image抽象类。
		// 需要使用getImage()获取Image对象
		// 设置图标
		this.setIconImage(new ImageIcon("images\\图标_微博.png").getImage());
		// 设置点击关闭窗口后做出的处理
		// 其中的参数operation取JFrame类中的静态常量
		// window.setDefaultCloseOperation(operation);
		// JFrame.DO_NOTHING_ON_CLOSE 什么也不做
		// JFrame.HIDE_ON_CLOSE 隐藏当前窗口
		// JFrame.DISPOSE_ON_CLOSE 隐藏当前窗口，并释放窗体占有的其他资源
		// JFrrame.EXIT_ON_CLOSE 结束窗口所在应用程序
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		// 窗体组件初始化
		this.init();
		// 事件监听
		this.addListener();
		// 将中间容器面板添加到窗口
		this.setContentPane(panel);
		// 设置窗口是否可缩放
		this.setResizable(false);
		// 设置窗口是否可见
		this.setVisible(true);
	}

	/**
	 * @Description 说明：窗体组件初始化
	 */
	private void init() {
		panel = new BackgroundPanel(new ImageIcon("images\\背景_登录注册.png").getImage());
		panel.setLayout(null);
		// 登录界面标签
		jLabelTitle.setFont(Constant.FONT1);
		jLabelTitle.setBounds(235, 90, 150, 30);
		panel.add(jLabelTitle);
		// 账号：标签
		jLabel1.setFont(Constant.FONT2);
		jLabel1.setBounds(230, 155, 70, 25);
		panel.add(jLabel1);
		// 密码：标签
		jLabel2.setFont(Constant.FONT2);
		jLabel2.setBounds(230, 210, 70, 25);
		panel.add(jLabel2);
		// 密码：标签
		jLabel3.setFont(Constant.FONT2);
		jLabel3.setBounds(230, 265, 70, 25);
		panel.add(jLabel3);
		// 账号图标
		ImageIcon iconUserID = new ImageIcon("images/图标_我的.png");
		iconUserID.setImage(iconUserID.getImage().getScaledInstance(15, 15, 15));
		JLabel iconJLabel1 = new JLabel();
		iconJLabel1.setIcon(iconUserID);
		iconJLabel1.setBounds(230, 180, 20, 25);
		panel.add(iconJLabel1);
		// 密码图标
		ImageIcon iconPassword = new ImageIcon("images/图标_密码.png");
		iconPassword.setImage(iconPassword.getImage().getScaledInstance(15, 15, 15));
		JLabel iconJLabel2 = new JLabel();
		iconJLabel2.setIcon(iconPassword);
		iconJLabel2.setBounds(230, 235, 20, 25);
		panel.add(iconJLabel2);
		// 确认密码图标
		JLabel iconJLabel3 = new JLabel();
		iconJLabel3.setIcon(iconPassword);
		iconJLabel3.setBounds(230, 290, 20, 25);
		panel.add(iconJLabel3);
		// 账号输入框
		idTextField.setBounds(250, 180, 120, 25);
		panel.add(idTextField);
		// 密码输入框
		password1.setBounds(250, 235, 120, 25);
		panel.add(password1);
		// 确认密码输入框
		password2.setBounds(250, 290, 120, 25);
		panel.add(password2);
		// 返回登录按钮
		back.setBounds(200, 330, 90, 30);
		panel.add(back);
		// 确认注册按钮
		ok.setBounds(340, 330, 90, 30);
		panel.add(ok);

	}

	/**
	 * @Description 说明：事件监听
	 */
	private void addListener() {
		// 返回登录方法
		back.addActionListener((e) -> backLogin());
		// 确认注册方法
		ok.addActionListener((e) -> setRegister());
	}

	/**
	 * @Description 说明：返回登录界面
	 */
	private void backLogin() {
		this.setVisible(false);
		new Interface_Login();
	}

	/**
	 * @Description 说明：注册访客
	 */
	private void setRegister() {
		String id = this.idTextField.getText();
		String password1 = new String(this.password1.getPassword());
		String password2 = new String(this.password2.getPassword());
		if (StringUtil.isEmpty(id)) {
			JOptionPane.showMessageDialog(null, "用户名不能为空");
			return;
		}
		if (StringUtil.isEmpty(password1) || StringUtil.isEmpty(password2)) {
			JOptionPane.showMessageDialog(null, "密码不能为空");
			return;
		}
		if (password1.equals(password2) == false) {
			JOptionPane.showMessageDialog(null, "两次密码输入不一致");
			return;
		}
		new LoginRegister().register(id, password1);
		// 隐藏当前界面
		this.setVisible(false);
		new Interface_Login();
	}
}
