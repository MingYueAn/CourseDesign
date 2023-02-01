package com.zy.view;

import java.awt.Color;
import java.awt.EventQueue;
import java.util.List;
import java.util.Vector;

import javax.swing.JInternalFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import com.zy.dao.UserDao;
import com.zy.model.BooKAndBookType;

public class BookSelectFrm extends JInternalFrame {
	private JTable table;
	private UserDao userDao = new UserDao();

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					BookSelectFrm frame = new BookSelectFrm();
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
	public BookSelectFrm() {
		getContentPane().setForeground(Color.WHITE);
		setClosable(true);
		setIconifiable(true);
		setTitle("\u56FE\u4E66\u67E5\u8BE2");
		setBounds(100, 100, 495, 300);
		getContentPane().setLayout(null);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(14, 34, 451, 194);
		getContentPane().add(scrollPane);

		table = new JTable();
		table.setModel(new DefaultTableModel(new Object[][] {},
				new String[] { "\u56FE\u4E66\u7F16\u53F7", "\u56FE\u4E66\u540D\u79F0", "\u56FE\u4E66\u7C7B\u578B",
						"\u56FE\u4E66\u4F5C\u8005", "\u56FE\u4E66\u4EF7\u683C", "\u56FE\u4E66\u7B80\u4ECB" }));
		scrollPane.setViewportView(table);
		fillTable();
	}

	public void fillTable() {
		DefaultTableModel dtm = (DefaultTableModel) table.getModel();
		dtm.setRowCount(0);// 设置成0行
		// 查询出书籍
		List<BooKAndBookType> b = userDao.listBook2();
		System.out.println(b);
		for (BooKAndBookType book2 : b) {
			Vector v = new Vector<>();
			v.add(book2.getId());
			v.add(book2.getBookName());
			v.add(book2.getBookTypeName());
			v.add(book2.getAuthor());
			v.add(book2.getPrice());
			v.add(book2.getBookDesc());
			dtm.addRow(v);
		}
	}
}
