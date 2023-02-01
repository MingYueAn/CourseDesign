package com.zy.view;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.LayoutStyle.ComponentPlacement;

import com.zy.dao.UserDao;
import com.zy.utils.LoginConfig;

public class UpdatePwdFrm extends JInternalFrame {
	private JTextField UserNameTxt;
	private JPasswordField pwdTxt;
	private JPasswordField NewPwdTxt;
	private JPasswordField NewPwdTxt2;
	private UserDao userDao = new UserDao();

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					UpdatePwdFrm frame = new UpdatePwdFrm();
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
	public UpdatePwdFrm() {
		setClosable(true);
		setIconifiable(true);
		setTitle("\u4FEE\u6539\u5BC6\u7801");
		setBounds(100, 100, 450, 300);

		JLabel lblNewLabel = new JLabel("\u7528\u6237\u540D");
		lblNewLabel.setIcon(new ImageIcon(UpdatePwdFrm.class.getResource("/images/userName.png")));

		JLabel lblNewLabel_1 = new JLabel("\u539F\u5BC6\u7801");
		lblNewLabel_1.setIcon(new ImageIcon(UpdatePwdFrm.class.getResource("/images/\u539F\u5BC6\u7801.png")));

		JLabel label = new JLabel("\u65B0\u5BC6\u7801");
		label.setIcon(new ImageIcon(UpdatePwdFrm.class.getResource("/images/\u65B0\u5BC6\u7801.png")));

		JLabel label_1 = new JLabel("\u786E\u8BA4\u5BC6\u7801");
		label_1.setIcon(new ImageIcon(UpdatePwdFrm.class.getResource("/images/\u786E\u8BA4\u5BC6\u7801.png")));

		UserNameTxt = new JTextField();
		UserNameTxt.setEditable(false);
		UserNameTxt.setColumns(10);

		pwdTxt = new JPasswordField();

		NewPwdTxt = new JPasswordField();

		NewPwdTxt2 = new JPasswordField();

		JButton button = new JButton("\u786E\u8BA4");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				UpdateActionPerformed(e);
			}
		});
		button.setIcon(new ImageIcon(UpdatePwdFrm.class.getResource("/images/\u63D0\u4EA4.png")));

		JButton button_1 = new JButton("\u53D6\u6D88");
		button_1.setIcon(new ImageIcon(UpdatePwdFrm.class.getResource("/images/reset.png")));
		GroupLayout groupLayout = new GroupLayout(getContentPane());
		groupLayout.setHorizontalGroup(groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup().addGap(40)
						.addGroup(
								groupLayout.createParallelGroup(Alignment.TRAILING)
										.addComponent(lblNewLabel).addComponent(lblNewLabel_1).addComponent(label)
										.addGroup(
												groupLayout.createParallelGroup(Alignment.LEADING).addComponent(button)
														.addComponent(label_1)))
						.addGroup(groupLayout.createParallelGroup(Alignment.LEADING).addGroup(groupLayout
								.createSequentialGroup().addGap(18).addGroup(groupLayout
										.createParallelGroup(Alignment.LEADING, false).addComponent(NewPwdTxt2)
										.addComponent(NewPwdTxt).addComponent(pwdTxt)
										.addComponent(UserNameTxt, GroupLayout.DEFAULT_SIZE, 178,
												Short.MAX_VALUE))
								.addContainerGap(138, Short.MAX_VALUE)).addGroup(Alignment.TRAILING,
										groupLayout.createSequentialGroup().addPreferredGap(ComponentPlacement.RELATED)
												.addComponent(button_1).addGap(68)))));
		groupLayout.setVerticalGroup(groupLayout.createParallelGroup(Alignment.LEADING).addGroup(groupLayout
				.createSequentialGroup().addGap(39)
				.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE).addComponent(lblNewLabel).addComponent(
						UserNameTxt, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
				.addGap(18)
				.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE).addComponent(lblNewLabel_1).addComponent(
						pwdTxt, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
				.addGap(18)
				.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING).addComponent(label).addComponent(
						NewPwdTxt, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
				.addGap(18)
				.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE).addComponent(label_1).addComponent(
						NewPwdTxt2, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
				.addPreferredGap(ComponentPlacement.RELATED, 35, Short.MAX_VALUE)
				.addGroup(
						groupLayout.createParallelGroup(Alignment.BASELINE).addComponent(button).addComponent(button_1))
				.addContainerGap()));
		getContentPane().setLayout(groupLayout);
		fillName();
	}

	/**
	 * 修改密码事件的处理
	 * 
	 * @param e
	 */
	protected void UpdateActionPerformed(ActionEvent e) {
		// 拿到输入的值
		List<String> reader = LoginConfig.reader();
		String pwd = new String(pwdTxt.getPassword());
		String Newpwd = new String(NewPwdTxt.getPassword());
		String Newpwd2 = new String(NewPwdTxt2.getPassword());
		String name = reader.get(0);
		// 判断原密码是否相同

		String oldPwd = reader.get(1);
		if (oldPwd.equals(pwd)) {// 原密码相同
			if (Newpwd.equals(Newpwd2)) {
				int i = userDao.updatePwd(Newpwd, name);
				if (i > 0) {
					JOptionPane.showMessageDialog(null, "修改成功");
					reset();
				}
			} else {
				JOptionPane.showMessageDialog(null, "两次密码不一致");
				reset();
			}
		} else {// 原密码不一致
			JOptionPane.showMessageDialog(null, "与原密码不相同");
			reset();
		}
	}

	/**
	 * 初始化用户名
	 */
	public void fillName() {
		List<String> reader = LoginConfig.reader();
		this.UserNameTxt.setText(reader.get(0));
	}

	/**
	 * 置空
	 */
	public void reset() {
		pwdTxt.setText("");
		NewPwdTxt.setText("");
		NewPwdTxt2.setText("");
	}
}
