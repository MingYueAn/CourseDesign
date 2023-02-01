package com.zy.view;

import java.awt.EventQueue;
import java.util.List;
import java.util.Vector;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JInternalFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import com.zy.dao.UserDao;
import com.zy.model.BookAndBorrow;
import com.zy.model.User;
import com.zy.utils.LoginConfig;

public class BookHistoryFrm extends JInternalFrame {
	private JTable table;
	private UserDao userDao = new UserDao();

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					BookHistoryFrm frame = new BookHistoryFrm();
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
	public BookHistoryFrm() {
		setIconifiable(true);
		setClosable(true);
		setTitle("\u501F\u4E66\u5386\u53F2");
		setBounds(100, 100, 450, 300);

		JScrollPane scrollPane = new JScrollPane();
		GroupLayout groupLayout = new GroupLayout(getContentPane());
		groupLayout.setHorizontalGroup(groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup().addGap(25)
						.addComponent(scrollPane, GroupLayout.PREFERRED_SIZE, 377, GroupLayout.PREFERRED_SIZE)
						.addContainerGap(32, Short.MAX_VALUE)));
		groupLayout.setVerticalGroup(groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup().addGap(29)
						.addComponent(scrollPane, GroupLayout.PREFERRED_SIZE, 188, GroupLayout.PREFERRED_SIZE)
						.addContainerGap(47, Short.MAX_VALUE)));

		table = new JTable();
		table.setModel(new DefaultTableModel(new Object[][] {}, new String[] { "\u7F16\u53F7", "\u4E66\u540D",
				"\u4F5C\u8005", "\u501F\u4E66\u65E5\u671F", "\u8FD8\u4E66\u65E5\u671F" }));
		scrollPane.setViewportView(table);
		getContentPane().setLayout(groupLayout);
		fillTable();
	}

	public void fillTable() {
		DefaultTableModel dtm = (DefaultTableModel) table.getModel();
		dtm.setRowCount(0);// 设置成0行
		// 查询出书籍
		List<String> reader = LoginConfig.reader();
		String name = reader.get(0);
		User u = userDao.selectUname(name);
		int userid = u.getId();
		List<BookAndBorrow> b = userDao.listBookHistory(userid);
		for (BookAndBorrow book2 : b) {
			Vector v = new Vector<>();
			v.add(dtm.getRowCount() + 1);
			v.add(book2.getBookName());
			v.add(book2.getAuthor());
			v.add(book2.getBtime().toString());
			v.add(book2.getRtime().toString());
			dtm.addRow(v);
		}
	}
}
