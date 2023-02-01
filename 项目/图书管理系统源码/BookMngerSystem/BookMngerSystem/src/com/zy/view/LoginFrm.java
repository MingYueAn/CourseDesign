package com.zy.view;

import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.DefaultComboBoxModel;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import com.zy.dao.UserDao;
import com.zy.model.User;
import com.zy.utils.LoginConfig;
import com.zy.utils.StringUtil;

public class LoginFrm extends JFrame {

	private JPanel contentPane;
	private JTextField userNameTxt;
	private JPasswordField PwdTxt;
	private JComboBox UserBoxTxt;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					LoginFrm frame = new LoginFrm();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public LoginFrm() {
		setTitle("\u767B\u5F55");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 408, 361);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);

		JLabel label = new JLabel("\u56FE\u4E66\u7BA1\u7406\u7CFB\u7EDF");
		label.setIcon(new ImageIcon(LoginFrm.class.getResource("/images/logo.png")));
		label.setFont(new Font("宋体", Font.PLAIN, 25));

		JLabel label_1 = new JLabel("\u7528\u6237\u540D");
		label_1.setIcon(new ImageIcon(LoginFrm.class.getResource("/images/userName.png")));

		userNameTxt = new JTextField();
		userNameTxt.setColumns(10);

		JLabel label_2 = new JLabel("\u5BC6\u7801");
		label_2.setIcon(new ImageIcon(LoginFrm.class.getResource("/images/password.png")));

		PwdTxt = new JPasswordField();

		JLabel label_3 = new JLabel("\u6743\u9650");

		UserBoxTxt = new JComboBox();
		UserBoxTxt.setModel(new DefaultComboBoxModel(new String[] { "\u7528\u6237", "\u7BA1\u7406\u5458" }));

		JButton button = new JButton("\u767B\u5F55");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				LoginActionPerformed(e);
			}
		});
		button.setIcon(new ImageIcon(LoginFrm.class.getResource("/images/login.png")));

		JButton button_1 = new JButton("\u6CE8\u518C");
		button_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				RegActionPerformed(e);
			}
		});
		button_1.setIcon(new ImageIcon(LoginFrm.class.getResource("/images/reg.png")));

		JButton button_2 = new JButton("\u91CD\u7F6E");
		button_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ResetActionPerformed(e);
			}
		});
		button_2.setIcon(new ImageIcon(LoginFrm.class.getResource("/images/reset.png")));
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(gl_contentPane.createParallelGroup(Alignment.LEADING).addGroup(gl_contentPane
				.createSequentialGroup()
				.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_contentPane.createSequentialGroup().addGap(86).addComponent(label))
						.addGroup(gl_contentPane.createSequentialGroup().addGap(28)
								.addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING)
										.addComponent(button).addComponent(label_2).addComponent(label_3).addComponent(
												label_1))
								.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING).addGroup(gl_contentPane
										.createSequentialGroup().addGap(30)
										.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
												.addComponent(UserBoxTxt, GroupLayout.PREFERRED_SIZE,
														GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
												.addGroup(gl_contentPane.createSequentialGroup().addComponent(button_1)
														.addGap(49).addComponent(button_2))))
										.addGroup(gl_contentPane.createSequentialGroup().addGap(18)
												.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
														.addComponent(PwdTxt, GroupLayout.DEFAULT_SIZE, 227,
																Short.MAX_VALUE)
														.addComponent(userNameTxt, GroupLayout.DEFAULT_SIZE, 227,
																Short.MAX_VALUE))))))
				.addGap(66)));
		gl_contentPane.setVerticalGroup(gl_contentPane.createParallelGroup(Alignment.LEADING).addGroup(gl_contentPane
				.createSequentialGroup().addContainerGap().addComponent(label).addGap(26)
				.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE).addComponent(label_1).addComponent(
						userNameTxt, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
				.addGap(27)
				.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE).addComponent(label_2).addComponent(
						PwdTxt, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
				.addGap(27)
				.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(UserBoxTxt, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
								GroupLayout.PREFERRED_SIZE)
						.addComponent(label_3))
				.addGap(31).addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE).addComponent(button)
						.addComponent(button_1).addComponent(button_2))
				.addContainerGap()));
		contentPane.setLayout(gl_contentPane);
		this.setLocationRelativeTo(null);// 居中显示
	}

	protected void RegActionPerformed(ActionEvent e) {
		dispose();// 销毁当前窗体
		new RegFrm().setVisible(true);// 显示窗体
	}

	/**
	 * 重置的事件的处理
	 * 
	 * @param e
	 */
	protected void ResetActionPerformed(ActionEvent e) {
		reset();
	}

	/**
	 * 重置
	 */
	public void reset() {
		this.userNameTxt.setText("");
		this.PwdTxt.setText("");
	}

	/**
	 * 对登录事件的处理
	 * 
	 * @param e
	 */
	protected void LoginActionPerformed(ActionEvent e) {
		String name = this.userNameTxt.getText();
		String pwd = new String(this.PwdTxt.getPassword());
		if (StringUtil.isEmpty(name)) {
			JOptionPane.showMessageDialog(null, "用户名不能为空");
			return;
		}
		if (StringUtil.isEmpty(pwd)) {
			JOptionPane.showMessageDialog(null, "密码不能为空");
			return;
		}
		String userid = "";
		String qxValue = (String) this.UserBoxTxt.getSelectedItem();
		if (qxValue.equals("用户")) {// 用户登录
			userid = "0";
			List<User> list = UserDao.login(name, pwd, userid);
			if (list.size() != 0) {// 登录成功
				dispose();
				LoginConfig.save(new User(name, pwd, userid));
				new MainFrm().setVisible(true);
			} else {
				JOptionPane.showMessageDialog(null, "账号或密码错误");
			}
		} else {// 管理员登录
			userid = "1";
			List<User> list = UserDao.login(name, pwd, userid);
			if (list.size() != 0) {// 登录成功
				dispose();
				LoginConfig.save(new User(name, pwd, userid));
				new AdminFrm().setVisible(true);
			} else {
				JOptionPane.showMessageDialog(null, "账号或密码错误");
			}
		}

	}

}
