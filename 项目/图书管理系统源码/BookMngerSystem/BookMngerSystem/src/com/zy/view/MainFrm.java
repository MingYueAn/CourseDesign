package com.zy.view;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.ImageIcon;
import javax.swing.JDesktopPane;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import com.zy.utils.LoginConfig;

public class MainFrm extends JFrame {

	private JPanel contentPane;
	private JDesktopPane table;
	private JMenu UserNameTxt2;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainFrm frame = new MainFrm();
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
	public MainFrm() {
		setTitle("\u4E3B\u754C\u9762");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);

		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);

		UserNameTxt2 = new JMenu("\u7528\u6237\u540D");
		UserNameTxt2.setIcon(new ImageIcon(MainFrm.class.getResource("/images/userName.png")));
		menuBar.add(UserNameTxt2);

		JMenuItem menuItem = new JMenuItem("\u6211\u7684\u4E66\u67B6");
		menuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				MyBookCar userupdatePwd = new MyBookCar();
				userupdatePwd.setVisible(true);
				table.add(userupdatePwd);
			}
		});
		menuItem.setIcon(new ImageIcon(MainFrm.class.getResource("/images/\u6211\u7684-\u6536\u85CF.png")));
		UserNameTxt2.add(menuItem);

		JMenuItem mntmNewMenuItem = new JMenuItem("\u4FEE\u6539\u5BC6\u7801");
		mntmNewMenuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// 修改密码
				UpdatePwdFrm userupdatePwd = new UpdatePwdFrm();
				userupdatePwd.setVisible(true);
				table.add(userupdatePwd);
			}
		});
		mntmNewMenuItem.setIcon(new ImageIcon(MainFrm.class.getResource("/images/password.png")));
		UserNameTxt2.add(mntmNewMenuItem);

		JMenuItem menuItem_1 = new JMenuItem("\u7528\u6237\u9000\u51FA");
		menuItem_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// 是 0 否 1
				int x = JOptionPane.showConfirmDialog(null, "是否退出系统");
				if (x == 0) {// 退出
					dispose();
					LoginConfig.clean();
				}
			}
		});
		menuItem_1.setIcon(new ImageIcon(MainFrm.class.getResource("/images/exit.png")));
		UserNameTxt2.add(menuItem_1);

		JMenu mnNewMenu_1 = new JMenu("\u56FE\u4E66\u7BA1\u7406");
		mnNewMenu_1.setIcon(new ImageIcon(MainFrm.class.getResource("/images/\u56FE\u4E66\u7BA1\u7406.png")));
		menuBar.add(mnNewMenu_1);

		JMenuItem menuItem_2 = new JMenuItem("\u56FE\u4E66\u67E5\u8BE2");
		menuItem_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// 图书查询
				BookSelectFrm userupdatePwd = new BookSelectFrm();
				userupdatePwd.setVisible(true);
				table.add(userupdatePwd);
			}
		});
		menuItem_2.setIcon(new ImageIcon(MainFrm.class.getResource("/images/\u56FE\u4E66\u67E5\u8BE2.png")));
		mnNewMenu_1.add(menuItem_2);

		JMenuItem menuItem_3 = new JMenuItem("\u501F\u9605\u56FE\u4E66");
		menuItem_3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// 图书借阅
				BookJyFrm userupdatePwd = new BookJyFrm();
				userupdatePwd.setVisible(true);
				table.add(userupdatePwd);
			}
		});
		menuItem_3.setIcon(new ImageIcon(MainFrm.class.getResource("/images/\u501F\u9605\u56FE\u4E66.png")));
		mnNewMenu_1.add(menuItem_3);

		JMenuItem menuItem_4 = new JMenuItem("\u501F\u4E66\u5386\u53F2");
		menuItem_4.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// 借书历史
				BookHistoryFrm userupdatePwd = new BookHistoryFrm();
				userupdatePwd.setVisible(true);
				table.add(userupdatePwd);
			}
		});
		menuItem_4.setIcon(new ImageIcon(MainFrm.class.getResource("/images/icon-\u501F\u4E66\u5386\u53F2.png")));
		mnNewMenu_1.add(menuItem_4);
		contentPane = new JPanel();
		contentPane.setForeground(Color.WHITE);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);

		table = new JDesktopPane();
		table.setBackground(Color.WHITE);
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(gl_contentPane.createParallelGroup(Alignment.LEADING).addComponent(table,
				GroupLayout.DEFAULT_SIZE, 422, Short.MAX_VALUE));
		gl_contentPane.setVerticalGroup(
				gl_contentPane.createParallelGroup(Alignment.LEADING).addGroup(gl_contentPane.createSequentialGroup()
						.addGap(1).addComponent(table, GroupLayout.DEFAULT_SIZE, 216, Short.MAX_VALUE)));
		contentPane.setLayout(gl_contentPane);
		this.setLocationRelativeTo(null);// 居中显示
		fillName();
	}

	/**
	 * 初始化用户名
	 */
	public void fillName() {
		List<String> reader = LoginConfig.reader();
		this.UserNameTxt2.setText(reader.get(0));
	}
}
