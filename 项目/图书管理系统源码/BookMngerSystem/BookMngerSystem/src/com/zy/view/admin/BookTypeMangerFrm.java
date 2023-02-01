package com.zy.view.admin;

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
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.table.DefaultTableModel;

import com.zy.dao.AdminDao;
import com.zy.model.BookType;
import com.zy.utils.StringUtil;

public class BookTypeMangerFrm extends JInternalFrame {
	private JTextField bookTypeName;
	private JTable table;
	private JTextField booktypeId;
	private JTextField bookTypeName2;
	private AdminDao adminDao = new AdminDao();
	private JTextArea bookTypeDesc;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					BookTypeMangerFrm frame = new BookTypeMangerFrm();
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
	public BookTypeMangerFrm() {
		setIconifiable(true);
		setClosable(true);
		setTitle("\u56FE\u4E66\u7C7B\u522B\u7EF4\u62A4");
		setBounds(100, 100, 450, 530);

		JLabel label = new JLabel("\u56FE\u4E66\u7C7B\u522B\u540D\u79F0");

		bookTypeName = new JTextField();
		bookTypeName.setColumns(10);

		JButton button = new JButton("\u67E5\u8BE2");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				SelectBookTypeActionPerformed(e);
			}
		});

		button.setIcon(new ImageIcon(BookTypeMangerFrm.class.getResource("/images/search.png")));

		JScrollPane scrollPane = new JScrollPane();

		JLabel lblNewLabel = new JLabel("\u7F16\u53F7");

		booktypeId = new JTextField();
		booktypeId.setEditable(false);
		booktypeId.setColumns(10);

		JLabel lblNewLabel_1 = new JLabel("\u56FE\u4E66\u540D\u79F0");

		bookTypeName2 = new JTextField();
		bookTypeName2.setColumns(10);

		JLabel label_1 = new JLabel("\u63CF\u53D9");

		bookTypeDesc = new JTextArea();

		JButton button_1 = new JButton("\u4FEE\u6539");
		button_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				UpdateBookTypeActionPerformed(e);
			}
		});

		button_1.setIcon(new ImageIcon(BookTypeMangerFrm.class.getResource("/images/modify.png")));

		JButton button_2 = new JButton("\u5220\u9664");
		button_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				DeleteBookTypeActionPerformed(e);
			}
		});

		button_2.setIcon(new ImageIcon(BookTypeMangerFrm.class.getResource("/images/delete.png")));
		GroupLayout groupLayout = new GroupLayout(getContentPane());
		groupLayout
				.setHorizontalGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(groupLayout.createSequentialGroup().addGap(33).addGroup(groupLayout
								.createParallelGroup(Alignment.LEADING)
								.addGroup(Alignment.TRAILING,
										groupLayout.createSequentialGroup().addGap(29).addComponent(button_1)
												.addPreferredGap(ComponentPlacement.RELATED, 213, Short.MAX_VALUE)
												.addComponent(button_2).addGap(33))
								.addGroup(
										groupLayout.createSequentialGroup().addComponent(label_1)
												.addPreferredGap(ComponentPlacement.UNRELATED)
												.addComponent(bookTypeDesc, GroupLayout.DEFAULT_SIZE, 343,
														Short.MAX_VALUE)
												.addContainerGap())
								.addGroup(groupLayout.createSequentialGroup().addGroup(groupLayout
										.createParallelGroup(Alignment.TRAILING)
										.addComponent(scrollPane, Alignment.LEADING)
										.addGroup(Alignment.LEADING, groupLayout.createSequentialGroup()
												.addComponent(label).addPreferredGap(ComponentPlacement.UNRELATED)
												.addComponent(bookTypeName, GroupLayout.PREFERRED_SIZE, 124,
														GroupLayout.PREFERRED_SIZE)
												.addGap(53).addComponent(button)))
										.addContainerGap(37, Short.MAX_VALUE))
								.addGroup(groupLayout.createSequentialGroup().addComponent(lblNewLabel)
										.addPreferredGap(ComponentPlacement.UNRELATED)
										.addComponent(booktypeId, GroupLayout.PREFERRED_SIZE, 102,
												GroupLayout.PREFERRED_SIZE)
										.addGap(67).addComponent(lblNewLabel_1)
										.addPreferredGap(ComponentPlacement.RELATED)
										.addComponent(bookTypeName2, GroupLayout.DEFAULT_SIZE, 108, Short.MAX_VALUE)
										.addContainerGap()))));
		groupLayout.setVerticalGroup(groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup().addGap(34)
						.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE).addComponent(label)
								.addComponent(bookTypeName, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
										GroupLayout.PREFERRED_SIZE)
								.addComponent(button))
						.addGap(29)
						.addComponent(scrollPane, GroupLayout.PREFERRED_SIZE, 165, GroupLayout.PREFERRED_SIZE)
						.addGap(33)
						.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
								.addComponent(booktypeId, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
										GroupLayout.PREFERRED_SIZE)
								.addComponent(lblNewLabel_1)
								.addComponent(bookTypeName2, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
										GroupLayout.PREFERRED_SIZE)
								.addComponent(lblNewLabel))
						.addGap(28)
						.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE).addComponent(label_1)
								.addComponent(bookTypeDesc, GroupLayout.PREFERRED_SIZE, 81, GroupLayout.PREFERRED_SIZE))
						.addGap(18).addGroup(groupLayout.createParallelGroup(Alignment.BASELINE).addComponent(button_2)
								.addComponent(button_1))
						.addContainerGap(28, Short.MAX_VALUE)));

		table = new JTable();
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				BookTypeTableMousePressed(e);
			}
		});

		table.setModel(new DefaultTableModel(new Object[][] {}, new String[] { "\u7F16\u53F7",
				"\u56FE\u4E66\u522B\u7C7B\u540D\u79F0", "\u56FE\u4E66\u522B\u7C7B\u63CF\u53D9" }));
		table.getColumnModel().getColumn(1).setPreferredWidth(124);
		table.getColumnModel().getColumn(2).setPreferredWidth(146);
		scrollPane.setViewportView(table);
		getContentPane().setLayout(groupLayout);
		fillTable("");
	}

	protected void DeleteBookTypeActionPerformed(ActionEvent e) {
		String id = this.booktypeId.getText();
		int bookTypeid = Integer.parseInt(id);
		// 首先要删除书籍表中对应的类别id的书籍
		int i = adminDao.deleteBook(bookTypeid);
		int j = adminDao.deleteBookType(bookTypeid);
		if (j > 0) {
			JOptionPane.showMessageDialog(null, "删除成功");
			reset();
			fillTable("");
		} else {
			JOptionPane.showMessageDialog(null, "删除失败");
			reset();
		}
	}

	protected void UpdateBookTypeActionPerformed(ActionEvent e) {
		String bookTypeName = this.bookTypeName2.getText();
		String bookTypeDesc = this.bookTypeDesc.getText();
		String id = booktypeId.getText();
		int btid = Integer.parseInt(id);
		if (StringUtil.isEmpty(bookTypeName)) {
			JOptionPane.showMessageDialog(null, "书籍类别名不能为空");
			return;
		}
		if (StringUtil.isEmpty(bookTypeDesc)) {
			JOptionPane.showMessageDialog(null, "书籍类别描叙不能为空");
			return;
		}
		int i = adminDao.udpateBookType(bookTypeName, bookTypeDesc, btid);
		if (i > 0) {
			JOptionPane.showMessageDialog(null, "修改成功");
			reset();
			fillTable("");
		} else {
			JOptionPane.showMessageDialog(null, "修改失败");
			reset();
		}
	}

	protected void BookTypeTableMousePressed(MouseEvent e) {
		int row = table.getSelectedRow();// 获得行号
		booktypeId.setText(String.valueOf(table.getValueAt(row, 0)));// 获取第一列的信息
		bookTypeName2.setText((String) table.getValueAt(row, 1));// 获取第一列的信息
		bookTypeDesc.setText((String) table.getValueAt(row, 2));// 获取第一列的信息
	}

	protected void SelectBookTypeActionPerformed(ActionEvent e) {
		String bookTypeName = this.bookTypeName.getText();
		fillTable(bookTypeName);
	}

	public void fillTable(String bookTypeName) {
		DefaultTableModel dtm = (DefaultTableModel) table.getModel();
		dtm.setRowCount(0);// 设置成0行
		// 查询出书籍
		List<BookType> b = adminDao.listBookType(bookTypeName);
		System.out.println(b);
		for (BookType book2 : b) {
			Vector v = new Vector<>();
			v.add(book2.getId());
			v.add(book2.getBookTypeName());
			v.add(book2.getBookTypeDesc());
			dtm.addRow(v);
		}
	}

	public void reset() {
		this.booktypeId.setText("");
		this.bookTypeName2.setText("");
		this.bookTypeDesc.setText("");
	}
}
