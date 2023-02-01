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
import javax.swing.JButton;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.table.DefaultTableModel;

import com.zy.dao.UserDao;
import com.zy.model.BooKAndBookType;
import com.zy.model.Book;
import com.zy.model.User;
import com.zy.utils.LoginConfig;

public class BookJyFrm extends JInternalFrame {
	private JTable table;
	private JTextField bookid;
	private JTextField bookName;
	private JTextField author;
	private JTextField price;
	private UserDao userDao = new UserDao();
	private JTextArea desc;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					BookJyFrm frame = new BookJyFrm();
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
	public BookJyFrm() {
		setIconifiable(true);
		setClosable(true);
		setTitle("\u56FE\u4E66\u501F\u9605");
		setBounds(100, 100, 450, 528);

		JScrollPane scrollPane = new JScrollPane();

		JLabel label = new JLabel("\u56FE\u4E66\u7F16\u53F7");

		bookid = new JTextField();
		bookid.setEditable(false);
		bookid.setColumns(10);

		JLabel label_1 = new JLabel("\u56FE\u4E66\u540D\u79F0");

		bookName = new JTextField();
		bookName.setColumns(10);

		JLabel lblNewLabel = new JLabel("\u56FE\u4E66\u4F5C\u8005");

		author = new JTextField();
		author.setColumns(10);

		JLabel label_2 = new JLabel("\u56FE\u4E66\u4EF7\u683C");

		price = new JTextField();
		price.setColumns(10);

		JLabel label_3 = new JLabel("\u56FE\u4E66\u7B80\u4ECB");

		desc = new JTextArea();

		JButton button = new JButton("\u501F\u9605");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				BookYJActionPerformed(e);
			}
		});

		JButton button_1 = new JButton("\u53D6\u6D88");
		GroupLayout groupLayout = new GroupLayout(getContentPane());
		groupLayout.setHorizontalGroup(
				groupLayout.createParallelGroup(Alignment.LEADING).addGroup(groupLayout.createSequentialGroup()
						.addContainerGap().addGroup(groupLayout.createParallelGroup(Alignment.LEADING).addComponent(
								scrollPane, GroupLayout.PREFERRED_SIZE, 408, GroupLayout.PREFERRED_SIZE).addGroup(
										groupLayout.createParallelGroup(Alignment.TRAILING, false).addGroup(groupLayout
												.createSequentialGroup()
												.addComponent(label_3).addPreferredGap(
														ComponentPlacement.RELATED)
												.addComponent(desc))
												.addGroup(Alignment.LEADING, groupLayout.createSequentialGroup()
														.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING)
																.addComponent(lblNewLabel).addComponent(label))
														.addPreferredGap(ComponentPlacement.RELATED)
														.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
																.addComponent(bookid, GroupLayout.PREFERRED_SIZE,
																		GroupLayout.DEFAULT_SIZE,
																		GroupLayout.PREFERRED_SIZE)
																.addComponent(author, GroupLayout.PREFERRED_SIZE,
																		GroupLayout.DEFAULT_SIZE,
																		GroupLayout.PREFERRED_SIZE))
														.addGap(54)
														.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
																.addGroup(groupLayout.createSequentialGroup()
																		.addComponent(label_1)
																		.addPreferredGap(ComponentPlacement.RELATED)
																		.addComponent(bookName,
																				GroupLayout.PREFERRED_SIZE,
																				GroupLayout.DEFAULT_SIZE,
																				GroupLayout.PREFERRED_SIZE))
																.addGroup(groupLayout.createSequentialGroup()
																		.addComponent(label_2)
																		.addPreferredGap(ComponentPlacement.RELATED)
																		.addComponent(price, GroupLayout.PREFERRED_SIZE,
																				GroupLayout.DEFAULT_SIZE,
																				GroupLayout.PREFERRED_SIZE))))))
						.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
						.addGroup(groupLayout.createSequentialGroup().addGap(32).addComponent(button)
								.addPreferredGap(ComponentPlacement.RELATED, 203, Short.MAX_VALUE)
								.addComponent(button_1).addGap(75)));
		groupLayout.setVerticalGroup(groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup().addGap(35)
						.addComponent(scrollPane, GroupLayout.PREFERRED_SIZE, 175, GroupLayout.PREFERRED_SIZE)
						.addGap(26)
						.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE).addComponent(label)
								.addComponent(bookid, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
										GroupLayout.PREFERRED_SIZE)
								.addComponent(label_1).addComponent(bookName, GroupLayout.PREFERRED_SIZE,
										GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
						.addGap(18)
						.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING).addGroup(groupLayout
								.createSequentialGroup()
								.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE).addComponent(lblNewLabel)
										.addComponent(label_2).addComponent(price, GroupLayout.PREFERRED_SIZE,
												GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
								.addGap(26))
								.addGroup(groupLayout.createSequentialGroup()
										.addComponent(author, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
												GroupLayout.PREFERRED_SIZE)
										.addPreferredGap(ComponentPlacement.UNRELATED)))
						.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE).addComponent(label_3)
								.addComponent(desc, GroupLayout.PREFERRED_SIZE, 78, GroupLayout.PREFERRED_SIZE))
						.addPreferredGap(ComponentPlacement.RELATED, 28, Short.MAX_VALUE).addGroup(groupLayout
								.createParallelGroup(Alignment.BASELINE).addComponent(button).addComponent(button_1))
						.addGap(31)));

		table = new JTable();
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				BookyjMousePressed(e);
			}
		});
		table.setModel(new DefaultTableModel(new Object[][] {},
				new String[] { "\u56FE\u4E66\u7F16\u53F7", "\u56FE\u4E66\u540D\u79F0", "\u56FE\u4E66\u4F5C\u8005",
						"\u56FE\u4E66\u4EF7\u683C", "\u56FE\u4E66\u7B80\u4ECB" }));
		scrollPane.setViewportView(table);
		getContentPane().setLayout(groupLayout);
		fillTable();
	}

	protected void BookYJActionPerformed(ActionEvent e) {
		int bookid = Integer.parseInt(this.bookid.getText());
		String bookName = this.bookName.getText();
		String author = this.author.getText();
		Float price = Float.parseFloat(this.price.getText());
		String bookDesc = this.desc.getText();
		// 书是否已经借出
		Book book = userDao.SelectBook(bookid);
		if (book != null) {// 此书已经借出
			JOptionPane.showMessageDialog(null, "这本书已经借出,请重新选一本");
			reset();
		} else {// 没有借出 可以借
			int i = userDao.borrowBook(bookid);
			if (i > 0) {
				List<String> reader = LoginConfig.reader();
				String name = reader.get(0);
				User u = userDao.selectUname(name);
				int id = u.getId();
				int j = userDao.insertBorrow(id, bookid);
				if (j > 0) {
					JOptionPane.showMessageDialog(null, "借书成功");
					fillTable();
					reset();
				}
			}
		}
	}

	protected void BookyjMousePressed(MouseEvent e) {
		int row = table.getSelectedRow();// 获得行号
		bookid.setText(String.valueOf(table.getValueAt(row, 0)));// 获取第一列的信息
		bookName.setText((String) table.getValueAt(row, 1));// 获取第一列的信息
		author.setText((String) table.getValueAt(row, 2));// 获取第一列的信息
		price.setText(String.valueOf(table.getValueAt(row, 3)));// 获取第一列的信息
		desc.setText((String) table.getValueAt(row, 4));// 获取第一列的信息
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
			v.add(book2.getAuthor());
			v.add(book2.getPrice());
			v.add(book2.getBookDesc());
			dtm.addRow(v);
		}
	}

	public void reset() {
		bookid.setText("");
		bookName.setText("");
		author.setText("");
		price.setText("");
		desc.setText("");
	}
}
