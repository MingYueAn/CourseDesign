package com.zy.view.admin;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

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
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.LayoutStyle.ComponentPlacement;

import com.zy.dao.AdminDao;
import com.zy.model.BookType;
import com.zy.utils.StringUtil;

public class BookAddFrm extends JInternalFrame {
	private JTextField bookName;
	private JTextField author;
	private JTextField price;
	private AdminDao adminDao = new AdminDao();
	private JComboBox bookTypeNameJcb;
	private JTextArea bookDesc;
	private JRadioButton nan;
	private JRadioButton nv;
	private final ButtonGroup buttonGroup = new ButtonGroup();

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					BookAddFrm frame = new BookAddFrm();
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
	public BookAddFrm() {
		setClosable(true);
		setIconifiable(true);
		setTitle("\u4E66\u7C4D\u6DFB\u52A0");
		setBounds(100, 100, 450, 402);

		JLabel label = new JLabel("\u4E66\u7C4D\u540D\u79F0");

		bookName = new JTextField();
		bookName.setColumns(10);

		JLabel label_1 = new JLabel("\u4F5C\u8005");

		author = new JTextField();
		author.setColumns(10);

		JLabel lblNewLabel = new JLabel("\u6027\u522B");

		nan = new JRadioButton("\u7537");
		buttonGroup.add(nan);
		nan.setSelected(true);

		nv = new JRadioButton("\u5973");
		buttonGroup.add(nv);

		JLabel label_2 = new JLabel("\u4EF7\u683C");

		price = new JTextField();
		price.setColumns(10);

		JLabel lblNewLabel_1 = new JLabel("\u7C7B\u522B");

		bookTypeNameJcb = new JComboBox();

		JLabel label_3 = new JLabel("\u4E66\u7C4D\u63CF\u53D9");

		bookDesc = new JTextArea();

		JButton button = new JButton("\u6DFB\u52A0");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				BookAddActionPerformed(e);
			}
		});

		button.setIcon(new ImageIcon(BookAddFrm.class.getResource("/images/add.png")));

		JButton button_1 = new JButton("\u91CD\u7F6E");
		button_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				reset();
			}
		});
		button_1.setIcon(new ImageIcon(BookAddFrm.class.getResource("/images/reset.png")));
		GroupLayout groupLayout = new GroupLayout(getContentPane());
		groupLayout
				.setHorizontalGroup(
						groupLayout
								.createParallelGroup(
										Alignment.LEADING)
								.addGroup(groupLayout.createSequentialGroup().addGroup(groupLayout
										.createParallelGroup(Alignment.LEADING)
										.addGroup(groupLayout.createSequentialGroup().addGap(20)
												.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING)
														.addComponent(lblNewLabel).addComponent(label).addComponent(
																lblNewLabel_1))
												.addPreferredGap(ComponentPlacement.UNRELATED)
												.addGroup(groupLayout.createParallelGroup(Alignment.LEADING).addGroup(
														groupLayout.createSequentialGroup().addGroup(groupLayout
																.createParallelGroup(Alignment.LEADING, false)
																.addGroup(Alignment.TRAILING, groupLayout
																		.createSequentialGroup().addComponent(nan)
																		.addPreferredGap(ComponentPlacement.RELATED,
																				GroupLayout.DEFAULT_SIZE,
																				Short.MAX_VALUE)
																		.addComponent(nv))
																.addComponent(bookName, GroupLayout.PREFERRED_SIZE, 115,
																		GroupLayout.PREFERRED_SIZE))
																.addGap(43)
																.addGroup(groupLayout
																		.createParallelGroup(Alignment.LEADING, false)
																		.addGroup(groupLayout.createSequentialGroup()
																				.addComponent(label_1)
																				.addPreferredGap(
																						ComponentPlacement.RELATED)
																				.addComponent(author,
																						GroupLayout.PREFERRED_SIZE, 118,
																						GroupLayout.PREFERRED_SIZE))
																		.addGroup(groupLayout.createSequentialGroup()
																				.addComponent(label_2)
																				.addPreferredGap(
																						ComponentPlacement.RELATED)
																				.addComponent(price))))
														.addComponent(bookTypeNameJcb, GroupLayout.PREFERRED_SIZE, 94,
																GroupLayout.PREFERRED_SIZE)))
										.addGroup(groupLayout.createSequentialGroup().addContainerGap()
												.addComponent(label_3).addGap(18).addComponent(bookDesc)))
										.addContainerGap(28, Short.MAX_VALUE))
								.addGroup(groupLayout.createSequentialGroup().addGap(30).addComponent(button)
										.addPreferredGap(ComponentPlacement.RELATED, 152, Short.MAX_VALUE)
										.addComponent(button_1).addGap(76)));
		groupLayout.setVerticalGroup(groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup().addGap(29)
						.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE).addComponent(label)
								.addComponent(bookName, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
										GroupLayout.PREFERRED_SIZE)
								.addComponent(label_1).addComponent(author, GroupLayout.PREFERRED_SIZE,
										GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
						.addGap(41)
						.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE).addComponent(label_2)
								.addComponent(price, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
										GroupLayout.PREFERRED_SIZE)
								.addComponent(nv).addComponent(nan).addComponent(lblNewLabel))
						.addGap(18)
						.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
								.addComponent(bookTypeNameJcb, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
										GroupLayout.PREFERRED_SIZE)
								.addComponent(lblNewLabel_1))
						.addGap(30)
						.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE).addComponent(label_3)
								.addComponent(bookDesc, GroupLayout.PREFERRED_SIZE, 77, GroupLayout.PREFERRED_SIZE))
						.addPreferredGap(ComponentPlacement.RELATED, 40, Short.MAX_VALUE).addGroup(groupLayout
								.createParallelGroup(Alignment.BASELINE).addComponent(button).addComponent(button_1))
						.addGap(29)));
		getContentPane().setLayout(groupLayout);
		fillBookType();

	}

	protected void BookAddActionPerformed(ActionEvent e) {
		// 拿值
		String bookName = this.bookName.getText();
		String author = this.author.getText();
		Float price = Float.parseFloat(this.price.getText());
		String bookDesc = this.bookDesc.getText();
		if (StringUtil.isEmpty(bookName)) {
			JOptionPane.showMessageDialog(null, "书籍名称不能为空");
			return;
		}
		if (StringUtil.isEmpty(author)) {
			JOptionPane.showMessageDialog(null, "作者不能为空");
			return;
		}
		if (StringUtil.isEmpty(this.price.getText())) {
			JOptionPane.showMessageDialog(null, "价格不能为空");
			return;
		}
		if (StringUtil.isEmpty(bookDesc)) {
			JOptionPane.showMessageDialog(null, "书籍描叙不能为空");
			return;
		}
		// 拿单选按钮
		String sex = "";
		if (nan.isSelected()) {
			sex = "男";
		} else {
			sex = "女";
		}
		// 拿下拉框
		BookType b = (BookType) bookTypeNameJcb.getSelectedItem();
		int id = b.getId();
		int j = adminDao.addBook(bookName, author, price, bookDesc, sex, id);
		if (j > 0) {
			JOptionPane.showMessageDialog(null, "添加成功");
			reset();
		} else {
			JOptionPane.showMessageDialog(null, "添加失败");
		}
	}

	private void reset() {
		this.bookName.setText("");
		this.bookDesc.setText("");
		this.price.setText("");
		this.author.setText("");
	}

	public void fillBookType() {
		List<BookType> listBookType = adminDao.listBookType("");
		for (BookType bookType : listBookType) {
			String bookTypeName = bookType.getBookTypeName();
			int id = bookType.getId();
			BookType b = new BookType(bookTypeName, id);
			this.bookTypeNameJcb.addItem(b);
		}
	}
}
