package com.zy.view.admin;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;
import java.util.Vector;

import javax.swing.ButtonGroup;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.table.DefaultTableModel;

import com.zy.dao.AdminDao;
import com.zy.model.BooKAndBookType;
import com.zy.model.Book;
import com.zy.model.BookType;

public class BookMangerFrm extends JInternalFrame {
	private JTextField bookName;
	private JTextField author;
	private JTable table;
	private JTextField bookId;
	private JTextField bookName2;
	private JTextField price;
	private JTextField author2;
	private final ButtonGroup buttonGroup = new ButtonGroup();
	private JComboBox bookTypeName;
	private AdminDao adminDao = new AdminDao();
	private JRadioButton nan;
	private JRadioButton nv;
	private JTextArea bookDesc;
	private JComboBox bookTypeNameJcb;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					BookMangerFrm frame = new BookMangerFrm();
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
	public BookMangerFrm() {
		setClosable(true);
		setIconifiable(true);
		setTitle("\u4E66\u7C4D\u7EF4\u62A4");
		setBounds(100, 100, 697, 547);

		JLabel label = new JLabel("\u56FE\u4E66\u540D\u79F0");

		bookName = new JTextField();
		bookName.setColumns(10);

		JLabel label_1 = new JLabel("\u4F5C\u8005");

		author = new JTextField();
		author.setColumns(10);

		JLabel lblNewLabel = new JLabel("\u7C7B\u522B");

		bookTypeName = new JComboBox();

		JScrollPane scrollPane = new JScrollPane();

		JLabel label_2 = new JLabel("\u7F16\u53F7");

		bookId = new JTextField();
		bookId.setEditable(false);
		bookId.setColumns(10);

		JLabel label_3 = new JLabel("\u56FE\u4E66\u540D\u79F0");

		bookName2 = new JTextField();
		bookName2.setColumns(10);

		JLabel label_4 = new JLabel("\u6027\u522B");

		nan = new JRadioButton("\u7537");
		nan.setSelected(true);
		buttonGroup.add(nan);

		JLabel label_5 = new JLabel("\u4EF7\u683C");

		price = new JTextField();
		price.setColumns(10);

		JLabel label_6 = new JLabel("\u56FE\u4E66\u4F5C\u8005");

		author2 = new JTextField();
		author2.setColumns(10);

		JLabel label_7 = new JLabel("\u7C7B\u522B");

		bookTypeNameJcb = new JComboBox();

		JLabel label_8 = new JLabel("\u56FE\u4E66\u63CF\u53D9");

		bookDesc = new JTextArea();

		nv = new JRadioButton("\u5973");
		buttonGroup.add(nv);

		JButton button = new JButton("\u641C\u67E5");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				SelectBookActionPerformed(e);
			}
		});

		button.setIcon(new ImageIcon(BookMangerFrm.class.getResource("/images/search.png")));

		JButton button_1 = new JButton("\u4FEE\u6539");
		button_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				updateBookActionPerformed(e);
			}
		});

		button_1.setIcon(new ImageIcon(BookMangerFrm.class.getResource("/images/modify.png")));

		JButton button_2 = new JButton("\u5220\u9664");
		button_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				DleteBookActionPerformed(e);
			}
		});

		button_2.setIcon(new ImageIcon(BookMangerFrm.class.getResource("/images/delete.png")));
		GroupLayout groupLayout = new GroupLayout(getContentPane());
		groupLayout
				.setHorizontalGroup(groupLayout.createParallelGroup(Alignment.LEADING).addGroup(groupLayout
						.createSequentialGroup().addGap(32).addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
								.addComponent(scrollPane, GroupLayout.PREFERRED_SIZE, 593, GroupLayout.PREFERRED_SIZE)
								.addGroup(groupLayout.createSequentialGroup().addGroup(
										groupLayout.createParallelGroup(Alignment.LEADING).addComponent(label_8)
												.addComponent(label_2).addComponent(label)
												.addComponent(label_4))
										.addPreferredGap(
												ComponentPlacement.RELATED)
										.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
												.addGroup(groupLayout.createSequentialGroup().addGroup(groupLayout
														.createParallelGroup(Alignment.LEADING)
														.addComponent(bookId, GroupLayout.PREFERRED_SIZE,
																GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
														.addGroup(groupLayout.createSequentialGroup().addGap(13)
																.addComponent(bookName, GroupLayout.PREFERRED_SIZE,
																		GroupLayout.DEFAULT_SIZE,
																		GroupLayout.PREFERRED_SIZE))
														.addGroup(groupLayout.createSequentialGroup().addComponent(nan)
																.addGap(10).addComponent(nv)))
														.addPreferredGap(ComponentPlacement.RELATED)
														.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
																.addGroup(groupLayout.createSequentialGroup().addGap(44)
																		.addComponent(label_1).addGap(18)
																		.addComponent(author,
																				GroupLayout.PREFERRED_SIZE,
																				GroupLayout.DEFAULT_SIZE,
																				GroupLayout.PREFERRED_SIZE)
																		.addGap(26).addComponent(lblNewLabel)
																		.addPreferredGap(ComponentPlacement.UNRELATED)
																		.addComponent(bookTypeName,
																				GroupLayout.PREFERRED_SIZE, 86,
																				GroupLayout.PREFERRED_SIZE)
																		.addGap(18).addComponent(button))
																.addGroup(groupLayout.createSequentialGroup().addGap(45)
																		.addGroup(groupLayout
																				.createParallelGroup(Alignment.TRAILING)
																				.addGroup(groupLayout
																						.createSequentialGroup()
																						.addComponent(label_3)
																						.addGap(18)
																						.addComponent(bookName2,
																								GroupLayout.PREFERRED_SIZE,
																								GroupLayout.DEFAULT_SIZE,
																								GroupLayout.PREFERRED_SIZE))
																				.addGroup(groupLayout
																						.createSequentialGroup()
																						.addComponent(label_5)
																						.addGap(18).addComponent(price,
																								GroupLayout.PREFERRED_SIZE,
																								GroupLayout.DEFAULT_SIZE,
																								GroupLayout.PREFERRED_SIZE)))
																		.addGap(49)
																		.addGroup(groupLayout
																				.createParallelGroup(Alignment.LEADING)
																				.addGroup(groupLayout
																						.createSequentialGroup()
																						.addComponent(label_7)
																						.addGap(35)
																						.addComponent(bookTypeNameJcb,
																								GroupLayout.PREFERRED_SIZE,
																								94,
																								GroupLayout.PREFERRED_SIZE))
																				.addGroup(groupLayout
																						.createSequentialGroup()
																						.addComponent(label_6)
																						.addPreferredGap(
																								ComponentPlacement.RELATED)
																						.addComponent(author2,
																								GroupLayout.PREFERRED_SIZE,
																								GroupLayout.DEFAULT_SIZE,
																								GroupLayout.PREFERRED_SIZE))))))
												.addComponent(bookDesc, GroupLayout.PREFERRED_SIZE, 507,
														GroupLayout.PREFERRED_SIZE)
												.addGroup(groupLayout.createSequentialGroup().addComponent(button_1)
														.addGap(243).addComponent(button_2)))))
						.addContainerGap(20, Short.MAX_VALUE)));
		groupLayout.setVerticalGroup(groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup().addGap(38)
						.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE).addComponent(label)
								.addComponent(bookName, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
										GroupLayout.PREFERRED_SIZE)
								.addComponent(label_1)
								.addComponent(author, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
										GroupLayout.PREFERRED_SIZE)
								.addComponent(lblNewLabel)
								.addComponent(bookTypeName, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
										GroupLayout.PREFERRED_SIZE)
								.addComponent(button))
						.addGap(18)
						.addComponent(scrollPane, GroupLayout.PREFERRED_SIZE, 144, GroupLayout.PREFERRED_SIZE)
						.addGap(57)
						.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
								.addComponent(bookId, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
										GroupLayout.PREFERRED_SIZE)
								.addComponent(label_2).addComponent(label_3)
								.addComponent(bookName2, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
										GroupLayout.PREFERRED_SIZE)
								.addComponent(label_6).addComponent(author2, GroupLayout.PREFERRED_SIZE,
										GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
						.addGap(18)
						.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE).addComponent(label_7)
								.addComponent(bookTypeNameJcb, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
										GroupLayout.PREFERRED_SIZE)
								.addComponent(price, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
										GroupLayout.PREFERRED_SIZE)
								.addComponent(label_5).addComponent(label_4).addComponent(nan).addComponent(nv))
						.addGap(33)
						.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE).addComponent(label_8)
								.addComponent(bookDesc, GroupLayout.PREFERRED_SIZE, 70, GroupLayout.PREFERRED_SIZE))
						.addPreferredGap(ComponentPlacement.RELATED, 20, Short.MAX_VALUE).addGroup(groupLayout
								.createParallelGroup(Alignment.BASELINE).addComponent(button_1).addComponent(button_2))
						.addContainerGap()));

		table = new JTable();
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				BookMousePressed(e);
			}
		});

		table.setModel(new DefaultTableModel(new Object[][] {},
				new String[] { "\u7F16\u53F7", "\u56FE\u4E66\u540D\u79F0", "\u56FE\u4E66\u4F5C\u8005", "\u6027\u522B",
						"\u56FE\u4E66\u4EF7\u683C", "\u56FE\u4E66\u63CF\u53D9", "\u56FE\u4E66\u7C7B\u522B",
						"\u662F\u5426\u501F\u51FA" }));
		table.getColumnModel().getColumn(0).setPreferredWidth(47);
		table.getColumnModel().getColumn(3).setPreferredWidth(53);
		scrollPane.setViewportView(table);
		getContentPane().setLayout(groupLayout);
		fill();
		fillTable("", "", "");
	}

	protected void DleteBookActionPerformed(ActionEvent e) {
		String id = this.bookId.getText();
		int bookid = Integer.parseInt(id);
		int i = adminDao.deleteBook2(bookid);
		if (i > 0) {
			JOptionPane.showMessageDialog(null, "删除成功");
			reset();
			fillTable("", "", "");// 更新表格
		} else {
			JOptionPane.showMessageDialog(null, "删除失败");
			reset();
		}
	}

	protected void updateBookActionPerformed(ActionEvent e) {
		String id = this.bookId.getText();
		String bookName = this.bookName2.getText();
		String author = this.author2.getText();
		String price = this.price.getText();
		String bookDesc = this.bookDesc.getText();
		// 拿单选按钮
		String sex = "";
		if (nan.isSelected()) {
			sex = "男";
		} else {
			sex = "女";
		}
		// 下拉框的拿值
		BookType b = (BookType) bookTypeNameJcb.getSelectedItem();
		int bookTypeId = b.getId();
		Book book = new Book(Integer.parseInt(id), bookName, author, Float.parseFloat(price), bookDesc, sex,
				bookTypeId);
		int i = adminDao.updateBook(book);
		if (i > 0) {
			JOptionPane.showMessageDialog(null, "修改成功");
			reset();
			fillTable("", "", "");// 更新表格
		} else {
			JOptionPane.showMessageDialog(null, "修改失败");
			reset();
		}
	}

	private void reset() {
		this.bookId.setText("");
		this.bookName2.setText("");
		this.author2.setText("");
		this.price.setText("");
		this.bookDesc.setText("");
	}

	protected void BookMousePressed(MouseEvent e) {
		int row = table.getSelectedRow();// 获得行号
		bookId.setText(String.valueOf(table.getValueAt(row, 0)));// 获取第一列的信息
		bookName2.setText((String) table.getValueAt(row, 1));// 获取第一列的信息
		author2.setText((String) table.getValueAt(row, 2));// 获取第一列的信息
		// 性别
		String sex = (String) table.getValueAt(row, 3);
		if ("男".equals(sex)) {
			nan.setSelected(true);
		} else {
			nv.setSelected(true);
		}
		price.setText(String.valueOf(table.getValueAt(row, 4)));// 获取第一列的信息
		bookDesc.setText((String) table.getValueAt(row, 5));// 获取第一列的信息
		// 类别
		String bookTypeNamejcb = (String) table.getValueAt(row, 6);
		int n = this.bookTypeNameJcb.getItemCount();
		for (int i = 0; i < n; i++) {
			BookType b = (BookType) this.bookTypeNameJcb.getItemAt(i);
			if (b.getBookTypeName().equals(bookTypeNamejcb)) {
				this.bookTypeNameJcb.setSelectedIndex(i);
			}
		}
	}

	protected void SelectBookActionPerformed(ActionEvent e) {
		String bookName = this.bookName.getText();
		String author = this.author.getText();
		BookType booktype = (BookType) this.bookTypeName.getSelectedItem();
		String bookTypeName2 = booktype.getBookTypeName();
		fillTable(bookName, author, bookTypeName2);
	}

	public void fillTable(String bookName, String author, String bookTypeName) {
		DefaultTableModel dtm = (DefaultTableModel) table.getModel();
		dtm.setRowCount(0);// 设置成0行
		List<BooKAndBookType> b = adminDao.listBook(bookName, author, bookTypeName);
		for (BooKAndBookType book2 : b) {
			Vector v = new Vector<>();
			v.add(book2.getId());
			v.add(book2.getBookName());
			v.add(book2.getAuthor());
			v.add(book2.getSex());
			v.add(book2.getPrice());
			v.add(book2.getBookDesc());
			v.add(book2.getBookTypeName());
			if (book2.getIsflag() == 0) {
				v.add("未借出");
			} else {
				v.add("未借出");
			}
			dtm.addRow(v);
		}
	}

	/**
	 * 初始化下拉框
	 */
	public void fill() {
		List<BookType> listBookType = adminDao.listBookType("");
		for (BookType bookType : listBookType) {
			String bookTypeName = bookType.getBookTypeName();
			int id = bookType.getId();
			BookType b = new BookType(bookTypeName, id);
			this.bookTypeName.addItem(b);
			this.bookTypeNameJcb.addItem(b);

		}
	}
}
