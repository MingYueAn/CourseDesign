package com.zy.view;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;
import java.util.Vector;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.table.DefaultTableModel;

import com.zy.dao.UserDao;
import com.zy.model.Book;
import com.zy.model.BookAndBorrow;
import com.zy.model.User;
import com.zy.utils.LoginConfig;

public class MyBookCar extends JInternalFrame {
	private UserDao userDao = new UserDao();
	private JTable table;
	private JTextField bookNameTXT;
	private JTextField authorTxt;
	private JTextField btimeTXT;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MyBookCar frame = new MyBookCar();
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
	public MyBookCar() {
		setClosable(true);
		setIconifiable(true);
		setTitle("\u6211\u7684\u4E66\u67B6");
		setBounds(100, 100, 450, 462);

		JScrollPane scrollPane = new JScrollPane();

		JLabel label = new JLabel("\u4E66\u540D");

		bookNameTXT = new JTextField();
		bookNameTXT.setColumns(10);

		JLabel label_1 = new JLabel("\u4F5C\u8005");

		authorTxt = new JTextField();
		authorTxt.setColumns(10);

		JLabel label_2 = new JLabel("\u501F\u4E66\u65E5\u671F");

		btimeTXT = new JTextField();
		btimeTXT.setColumns(10);

		JButton button = new JButton("\u5F52\u8FD8");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// 归还书籍
				ReturnBookActionformed(e);
			}
		});

		button.setIcon(new ImageIcon(MyBookCar.class.getResource("/images/\u5F52\u8FD8.png")));
		GroupLayout groupLayout = new GroupLayout(getContentPane());
		groupLayout.setHorizontalGroup(groupLayout.createParallelGroup(Alignment.LEADING).addGroup(groupLayout
				.createSequentialGroup()
				.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(
								groupLayout.createSequentialGroup()
										.addContainerGap()
										.addComponent(scrollPane, GroupLayout.PREFERRED_SIZE, 400,
												GroupLayout.PREFERRED_SIZE))
						.addGroup(groupLayout.createSequentialGroup().addGap(25).addGroup(groupLayout
								.createParallelGroup(Alignment.TRAILING).addComponent(button)
								.addGroup(groupLayout.createSequentialGroup()
										.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING)
												.addComponent(label).addComponent(label_2))
										.addPreferredGap(ComponentPlacement.RELATED)
										.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
												.addComponent(bookNameTXT, Alignment.TRAILING,
														GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
														GroupLayout.PREFERRED_SIZE)
												.addComponent(btimeTXT, Alignment.TRAILING, GroupLayout.PREFERRED_SIZE,
														GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
										.addGap(35).addComponent(label_1)))
								.addGap(18).addComponent(authorTxt, GroupLayout.PREFERRED_SIZE,
										GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)))
				.addContainerGap(20, Short.MAX_VALUE)));
		groupLayout.setVerticalGroup(groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup().addGap(32)
						.addComponent(scrollPane, GroupLayout.PREFERRED_SIZE, 205, GroupLayout.PREFERRED_SIZE)
						.addGap(28)
						.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE).addComponent(label_1)
								.addComponent(authorTxt, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
										GroupLayout.PREFERRED_SIZE)
								.addComponent(bookNameTXT, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
										GroupLayout.PREFERRED_SIZE)
								.addComponent(label))
						.addGap(38)
						.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
								.addComponent(btimeTXT, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
										GroupLayout.PREFERRED_SIZE)
								.addComponent(label_2))
						.addPreferredGap(ComponentPlacement.RELATED, 26, Short.MAX_VALUE).addComponent(button)
						.addGap(22)));

		table = new JTable();
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				MyBookMousePressed(e);
			}
		});
		table.setModel(new DefaultTableModel(new Object[][] {},
				new String[] { "\u7F16\u53F7", "\u4E66\u540D", "\u4F5C\u8005", "\u501F\u4E66\u65E5\u671F" }));
		scrollPane.setViewportView(table);
		getContentPane().setLayout(groupLayout);
		fillTable();
	}

	/**
	 * 归还书籍的事件处理
	 * 
	 * @param e
	 */
	/**
	 * 归还书籍的逻辑 1. 把book表中的isflag改成0
	 * 
	 * 2. 在借书表中对应插入一个还书日期
	 */

	protected void ReturnBookActionformed(ActionEvent e) {
		String bookName = this.bookNameTXT.getText();
		Book b = userDao.selectBookId(bookName);
		int bookid = b.getId();
		List<String> reader = LoginConfig.reader();
		String name = reader.get(0);
		User u = userDao.selectUname(name);
		int userid = u.getId();
		int i = userDao.updateIsFlag(bookid);
		if (i > 0) {
			int j = userDao.updateRtime(userid, bookid);
			if (j > 0) {
				JOptionPane.showMessageDialog(null, "归还成功");
				fillTable();
				reset();
			} else {
				JOptionPane.showMessageDialog(null, "归还失败");
			}
		} else {
			JOptionPane.showMessageDialog(null, "归还失败");
		}
	}

	/**
	 * 点击表格行数据显示事件处理
	 * 
	 * @param e
	 */
	protected void MyBookMousePressed(MouseEvent e) {
		int row = table.getSelectedRow();// 获得行号
		bookNameTXT.setText((String) table.getValueAt(row, 1));// 获取第一列的信息
		authorTxt.setText((String) table.getValueAt(row, 2));// 获取第一列的信息
		btimeTXT.setText((String) table.getValueAt(row, 3));// 获取第一列的信息
	}

	/**
	 * 初始化表格
	 */
	public void fillTable() {
		DefaultTableModel dtm = (DefaultTableModel) table.getModel();
		dtm.setRowCount(0);// 设置成0行
		// 查询出书籍
		List<String> reader = LoginConfig.reader();
		String uname = reader.get(0);
		User u = userDao.selectUname(uname);
		List<BookAndBorrow> b = userDao.listBook(u.getId());
		for (BookAndBorrow book2 : b) {
			Vector v = new Vector<>();
			v.add(dtm.getRowCount() + 1);
			v.add(book2.getBookName());
			v.add(book2.getAuthor());
			v.add(book2.getBtime().toString());
			dtm.addRow(v);
		}
	}

	public void reset() {
		this.bookNameTXT.setText("");
		this.authorTxt.setText("");
		this.btimeTXT.setText("");
	}
}
