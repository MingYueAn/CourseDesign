package com.zy.view;

import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.border.EmptyBorder;

import com.zy.dao.UserDao;
import com.zy.model.User;
import com.zy.utils.StringUtil;

public class RegFrm extends JFrame {

	private JPanel contentPane;
	private JTextField nameTxt;
	private JPasswordField pwdTxt;
	private UserDao userDao = new UserDao();

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					RegFrm frame = new RegFrm();
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
	public RegFrm() {
		setTitle("\u6CE8\u518C");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);

		JLabel label = new JLabel("\u7528\u6237\u6CE8\u518C");
		label.setFont(new Font("宋体", Font.PLAIN, 25));

		JLabel label_1 = new JLabel("\u7528\u6237\u540D");
		label_1.setIcon(new ImageIcon(RegFrm.class.getResource("/images/userName.png")));

		nameTxt = new JTextField();
		nameTxt.setColumns(10);

		JLabel label_2 = new JLabel("\u5BC6\u7801");
		label_2.setIcon(new ImageIcon(RegFrm.class.getResource("/images/password.png")));

		JButton button = new JButton("\u6CE8\u518C");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				RegActionPerformed(e);
			}
		});
		button.setIcon(new ImageIcon(RegFrm.class.getResource("/images/reg.png")));

		pwdTxt = new JPasswordField();
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(gl_contentPane.createParallelGroup(Alignment.LEADING).addGroup(gl_contentPane
				.createSequentialGroup()
				.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_contentPane.createSequentialGroup().addGap(145).addComponent(label))
						.addGroup(gl_contentPane.createSequentialGroup().addGap(67)
								.addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING).addComponent(label_2)
										.addComponent(label_1))
								.addGap(28)
								.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING, false)
										.addComponent(pwdTxt)
										.addComponent(nameTxt, GroupLayout.DEFAULT_SIZE, 150, Short.MAX_VALUE)))
						.addGroup(gl_contentPane.createSequentialGroup().addGap(149).addComponent(button)))
				.addContainerGap(132, Short.MAX_VALUE)));
		gl_contentPane.setVerticalGroup(gl_contentPane.createParallelGroup(Alignment.LEADING).addGroup(gl_contentPane
				.createSequentialGroup().addGap(44).addComponent(label).addGap(32)
				.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE).addComponent(label_1).addComponent(
						nameTxt, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
				.addGap(18)
				.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE).addComponent(label_2).addComponent(
						pwdTxt, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
				.addPreferredGap(ComponentPlacement.RELATED, 31, Short.MAX_VALUE).addComponent(button)
				.addContainerGap()));
		contentPane.setLayout(gl_contentPane);
		this.setLocationRelativeTo(null);// 居中显示
	}

	/**
	 * 注册事件的处理
	 * 
	 * @param e
	 */
	protected void RegActionPerformed(ActionEvent e) {
		String name = this.nameTxt.getText();
		String pwd = new String(this.pwdTxt.getPassword());
		User user = userDao.selectUname(name);
		if (user != null) {
			JOptionPane.showConfirmDialog(null, "用户名已经注册");
			reset();
		} else {
			int i = userDao.reg(name, pwd);
			if (StringUtil.isEmpty(name)) {
				JOptionPane.showMessageDialog(null, "用户名不能为空");
				return;
			}
			if (StringUtil.isEmpty(pwd)) {
				JOptionPane.showMessageDialog(null, "密码不能为空");
				return;
			}
			if (i > 0) {// 注册成功
				dispose();
				new LoginFrm().setVisible(true);
			} else {// 注册失败
				JOptionPane.showMessageDialog(null, "注册失败");
			}
		}

	}

	public void reset() {
		this.nameTxt.setText("");
		this.pwdTxt.setText("");
	}
}
