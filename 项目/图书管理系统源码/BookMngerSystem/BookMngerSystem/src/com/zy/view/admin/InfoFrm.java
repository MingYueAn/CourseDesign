package com.zy.view.admin;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;

import javax.swing.ImageIcon;
import javax.swing.JDesktopPane;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;

public class InfoFrm extends JInternalFrame {

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					InfoFrm frame = new InfoFrm();
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
	public InfoFrm() {
		getContentPane().setForeground(Color.RED);

		JDesktopPane desktopPane = new JDesktopPane();
		desktopPane.setBackground(Color.RED);
		getContentPane().add(desktopPane, BorderLayout.CENTER);

		JLabel lblNewLabel = new JLabel("New label");
		lblNewLabel.setFont(new Font("ËÎÌו", Font.PLAIN, 25));
		lblNewLabel.setIcon(new ImageIcon(InfoFrm.class.getResource("/images/zy.png")));
		lblNewLabel.setBounds(115, 64, 149, 76);
		desktopPane.add(lblNewLabel);

		JLabel label = new JLabel("\u8054\u7CFB\u7535\u8BDD(\u53D1\u54E5)");
		label.setFont(new Font("ËÎÌו", Font.PLAIN, 20));
		label.setBounds(63, 174, 173, 18);
		desktopPane.add(label);

		JLabel label_1 = new JLabel("15574955212");
		label_1.setFont(new Font("ËÎÌו", Font.PLAIN, 20));
		label_1.setBounds(228, 169, 140, 28);
		desktopPane.add(label_1);
		setIconifiable(true);
		setClosable(true);
		setTitle("\u5173\u4E8E\u6211\u4EEC");
		setBounds(100, 100, 450, 300);

	}
}
