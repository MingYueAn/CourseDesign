package com.zy.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JDesktopPane;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import com.zy.utils.LoginConfig;
import com.zy.view.admin.BookAddFrm;
import com.zy.view.admin.BookMangerFrm;
import com.zy.view.admin.BookTypeAddFrm;
import com.zy.view.admin.BookTypeMangerFrm;
import com.zy.view.admin.InfoFrm;

public class AdminFrm extends JFrame {

	private JPanel cc;
	private JDesktopPane table;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					AdminFrm frame = new AdminFrm();
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
	public AdminFrm() {
		setTitle("\u7BA1\u7406\u5458");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);

		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);

		JMenu menu = new JMenu("\u57FA\u672C\u6570\u636E\u7EF4\u62A4");
		menu.setIcon(new ImageIcon(AdminFrm.class.getResource("/images/base.png")));
		menuBar.add(menu);

		JMenu mnNewMenu = new JMenu("\u56FE\u4E66\u7C7B\u522B\u7BA1\u7406");
		mnNewMenu.setIcon(new ImageIcon(AdminFrm.class.getResource("/images/bookTypeManager.png")));
		menu.add(mnNewMenu);

		JMenuItem mntmNewMenuItem = new JMenuItem("\u56FE\u4E66\u7C7B\u522B\u6DFB\u52A0");
		mntmNewMenuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				BookTypeAddFrm b = new BookTypeAddFrm();
				b.setVisible(true);
				table.add(b);
			}
		});

		mntmNewMenuItem.setIcon(new ImageIcon(AdminFrm.class.getResource("/images/add.png")));
		mnNewMenu.add(mntmNewMenuItem);

		JMenuItem menuItem = new JMenuItem("\u56FE\u4E66\u522B\u7C7B\u7EF4\u62A4");
		menuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// 图书类别维护
				BookTypeMangerFrm bmf = new BookTypeMangerFrm();
				bmf.setVisible(true);
				table.add(bmf);
			}
		});
		menuItem.setIcon(new ImageIcon(AdminFrm.class.getResource("/images/edit.png")));
		mnNewMenu.add(menuItem);

		JMenu mnNewMenu_1 = new JMenu("\u56FE\u4E66\u7BA1\u7406");
		mnNewMenu_1.setIcon(new ImageIcon(AdminFrm.class.getResource("/images/bookManager.png")));
		menu.add(mnNewMenu_1);

		JMenuItem menuItem_1 = new JMenuItem("\u56FE\u4E66\u6DFB\u52A0");
		menuItem_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				BookAddFrm b = new BookAddFrm();
				b.setVisible(true);
				table.add(b);
			}
		});
		menuItem_1.setIcon(new ImageIcon(AdminFrm.class.getResource("/images/add.png")));
		mnNewMenu_1.add(menuItem_1);

		JMenuItem menuItem_2 = new JMenuItem("\u56FE\u4E66\u7EF4\u62A4");
		menuItem_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				BookMangerFrm bm = new BookMangerFrm();
				bm.setVisible(true);
				table.add(bm);
			}
		});
		menuItem_2.setIcon(new ImageIcon(AdminFrm.class.getResource("/images/base.png")));
		mnNewMenu_1.add(menuItem_2);

		JMenuItem menuItem_4 = new JMenuItem("\u9000\u51FA\u7CFB\u7EDF");
		menuItem_4.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ExitActionPerformed(e);
			}
		});
		menuItem_4.setIcon(new ImageIcon(AdminFrm.class.getResource("/images/exit.png")));
		menu.add(menuItem_4);

		JMenu menu_1 = new JMenu("\u5173\u4E8E\u6211\u4EEC");
		menu_1.setIcon(new ImageIcon(AdminFrm.class.getResource("/images/about.png")));
		menuBar.add(menu_1);

		JMenuItem menuItem_3 = new JMenuItem("\u5173\u4E8E\u5353\u5E94");
		menuItem_3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				InfoFrm i = new InfoFrm();
				i.setVisible(true);
				table.add(i);
			}
		});
		menuItem_3.setIcon(new ImageIcon(AdminFrm.class.getResource("/images/about.png")));
		menu_1.add(menuItem_3);
		cc = new JPanel();
		cc.setForeground(Color.WHITE);
		cc.setBorder(new EmptyBorder(5, 5, 5, 5));
		cc.setLayout(new BorderLayout(0, 0));
		setContentPane(cc);

		table = new JDesktopPane();
		table.setBackground(Color.WHITE);
		cc.add(table, BorderLayout.CENTER);
		this.setLocationRelativeTo(null);// 居中显示
	}

	protected void ExitActionPerformed(ActionEvent e) {
		dispose();
		LoginConfig.clean();
	}
}
