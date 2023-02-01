package com.zy.view.admin;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.LayoutStyle.ComponentPlacement;

import com.zy.dao.AdminDao;
import com.zy.model.BookType;
import com.zy.utils.StringUtil;

public class BookTypeAddFrm extends JInternalFrame {
	private JTextField bookTypeName;
	private JTextArea bookTypeDesc;
	private AdminDao adminDao = new AdminDao();

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					BookTypeAddFrm frame = new BookTypeAddFrm();
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
	public BookTypeAddFrm() {
		setIconifiable(true);
		setClosable(true);
		setTitle("\u56FE\u4E66\u7C7B\u522B\u6DFB\u52A0");
		setBounds(100, 100, 450, 300);

		JLabel label = new JLabel("\u56FE\u4E66\u7C7B\u522B\u540D\u79F0");

		bookTypeName = new JTextField();
		bookTypeName.setColumns(10);

		JLabel label_1 = new JLabel("\u56FE\u4E66\u7C7B\u522B\u63CF\u8FF0");

		bookTypeDesc = new JTextArea();

		JButton button = new JButton("\u6DFB\u52A0");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				BookTypeAddActionPerformed(e);
			}
		});

		JButton button_1 = new JButton("\u91CD\u7F6E");
		GroupLayout groupLayout = new GroupLayout(getContentPane());
		groupLayout.setHorizontalGroup(groupLayout.createParallelGroup(Alignment.LEADING).addGroup(groupLayout
				.createSequentialGroup().addGap(79)
				.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING).addComponent(label_1).addComponent(label))
				.addPreferredGap(ComponentPlacement.UNRELATED)
				.addGroup(groupLayout.createParallelGroup(Alignment.LEADING).addComponent(bookTypeDesc)
						.addComponent(bookTypeName, GroupLayout.PREFERRED_SIZE, 141, GroupLayout.PREFERRED_SIZE))
				.addContainerGap(110, Short.MAX_VALUE))
				.addGroup(groupLayout.createSequentialGroup().addGap(68).addComponent(button)
						.addPreferredGap(ComponentPlacement.RELATED, 111, Short.MAX_VALUE).addComponent(button_1)
						.addGap(79)));
		groupLayout.setVerticalGroup(groupLayout.createParallelGroup(Alignment.LEADING).addGroup(groupLayout
				.createSequentialGroup().addGap(45)
				.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE).addComponent(label).addComponent(
						bookTypeName, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
				.addGap(18)
				.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE).addComponent(label_1)
						.addComponent(bookTypeDesc, GroupLayout.PREFERRED_SIZE, 71, GroupLayout.PREFERRED_SIZE))
				.addPreferredGap(ComponentPlacement.RELATED, 52, Short.MAX_VALUE)
				.addGroup(
						groupLayout.createParallelGroup(Alignment.BASELINE).addComponent(button).addComponent(button_1))
				.addGap(27)));
		getContentPane().setLayout(groupLayout);

	}

	/**
	 * 书籍类别添加的事件处理
	 * 
	 * @param e
	 */
	protected void BookTypeAddActionPerformed(ActionEvent e) {
		String bookTypeName = this.bookTypeName.getText();
		String bookTypeDesc = this.bookTypeDesc.getText();
		if (StringUtil.isEmpty(bookTypeName)) {
			JOptionPane.showMessageDialog(null, "类别名称不能为空");
			return;
		}
		if (StringUtil.isEmpty(bookTypeDesc)) {
			JOptionPane.showMessageDialog(null, "类别描叙不能为空");
			return;
		}
		BookType b = adminDao.SelectBookTypeName(bookTypeName);
		if (b != null) {
			JOptionPane.showMessageDialog(null, "此类型已经添加,请重新添加");
			reset();
		} else {
			int i = adminDao.AddBookType(bookTypeName, bookTypeDesc);
			if (i > 0) {
				JOptionPane.showMessageDialog(null, "添加成功");
				reset();
			} else {
				JOptionPane.showMessageDialog(null, "添加失败");
			}
		}
	}

	/**
	 * 重置事件的处理
	 */
	private void reset() {
		this.bookTypeName.setText("");
		this.bookTypeDesc.setText("");
	}
}
