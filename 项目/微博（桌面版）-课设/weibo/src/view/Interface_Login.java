package view;

import javax.swing.*;

import control.LoginRegister;
import data.Constant;
import data.GlobalVar;
import model.Personal;
import tools.BackgroundPanel;
import tools.StringUtil;

/**
 * @Author 作者：物联19102 谢晓艳 190611201
 * @Description 说明：登录界面
 * @Date 时间：2020-12-18
 */
@SuppressWarnings("serial")
public class Interface_Login extends JFrame {

	// 按钮（登录、注册）
	private JButton login = new JButton("登录"), register = new JButton("注册");
	// 标签（账号：、密码：、登录界面）
	private JLabel jLabel1 = new JLabel("账号："), jLabel2 = new JLabel("密码："), jLabelTitle = new JLabel("登录界面");
	// 文本框（输入账号）
	private JTextField id = new JTextField();
	// 密码框（输入密码）
	private JPasswordField password = new JPasswordField();
	// 创建中间容器[设置布局]
	private JPanel panel;

	/**
	 * @Description 说明：构造方法
	 */
	public Interface_Login() {
		// 顶层容器的设置
		// 设置窗口标题
		this.setTitle("登录界面");
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
		// 账号输入框
		id.setBounds(250, 180, 120, 25);
		panel.add(id);
		// 密码输入框
		password.setBounds(250, 235, 120, 25);
		panel.add(password);
		// 登录按钮
		login.setBounds(220, 300, 60, 30);
		panel.add(login);
		// 注册按钮
		register.setBounds(320, 300, 60, 30);
		panel.add(register);
	}

	/**
	 * @Description 说明：事件监听
	 */
	private void addListener() {
		// 登录方法
		login.addActionListener((e) -> forLogin());
		// 注册方法
		register.addActionListener((e) -> forRegister());
	}

	/**
	 * @Description 说明：登录
	 */
	private void forLogin() {
		String id = this.id.getText();
		String password = new String(this.password.getPassword());
		if (StringUtil.isEmpty(id)) {
			JOptionPane.showMessageDialog(null, "用户名不能为空");
			return;
		}
		if (StringUtil.isEmpty(password)) {
			JOptionPane.showMessageDialog(null, "密码不能为空");
			return;
		}
		// 登录
		Personal login = new Personal();
		login = new LoginRegister().login(id, password);
		if (login.getLogin_success().equals(Constant.YES_STRING) == true) {
			// 保存当前登录个人对象
			GlobalVar.login_personal = login;
			// 获取个人权限并判断界面
			switch (login.getPower()) {
			case "访客":
//				System.out.println("打开访客主界面");
				JOptionPane.showMessageDialog(null, GlobalVar.login_visitor.getVisitor_name() + " 欢迎您的到来！", "登录成功", JOptionPane.INFORMATION_MESSAGE);
				new view_visitor.Interface_MainFrame();
				break;
			case "管理员":
//				System.out.println("打开管理员主界面");
				JOptionPane.showMessageDialog(null, "管理员" + " 欢迎您的到来！", "登录成功", JOptionPane.INFORMATION_MESSAGE);
				new view_administrator.Interface_MainFrame();
				break;
			default:
				System.out.println("完成");
			}
			// 隐藏当前界面
			this.setVisible(false);
		} else {
			JOptionPane.showMessageDialog(null, "登录失败，请重新登录！", "登录失败", JOptionPane.INFORMATION_MESSAGE);
		}
		System.out.println("-----登录个人对象的信息");
		System.out.println("账号：" + GlobalVar.login_personal.getId());
		System.out.println("密码：" + GlobalVar.login_personal.getPassword());
		System.out.println("权限：" + GlobalVar.login_personal.getPower());
		System.out.println("Login_success：" + GlobalVar.login_personal.getLogin_success());
	}

	/**
	 * @Description 说明：注册
	 */
	private void forRegister() {
		// 隐藏当前界面
		this.setVisible(false);
		new Interface_Register();
	}

}
