package view_visitor;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JToolBar;
import javax.swing.border.BevelBorder;

import control.Delete;
import data.Constant;
import data.GlobalVar;
import data.Refresh;
import model.Weibo;
import tools.AfAnyWhere;
import tools.AfMargin;

/**
 * @Author ����
 * @Description ˵�����鿴΢������
 * @Date ʱ�䣺2020-12-10
 */
@SuppressWarnings("serial")
public class PanelTXT extends JPanel {
	private JTextArea textArea = new JTextArea();
	private JToolBar toolBar = new JToolBar();
	Weibo weibo;

	/**
	 * @Description ˵�������췽��
	 */
	public PanelTXT(Weibo w) {
		super();
		this.weibo = w;
		if (w.getWriter_id().equals(GlobalVar.login_visitor.getId()) == true) {
			init();// �Լ���΢��
		} else if (w.getWriter_id().equals(GlobalVar.login_visitor.getId()) == false) {
			initwr();// �����˵�΢��
		}
	}

	// �Լ���΢��
	private void init() {
		this.setLayout(new AfAnyWhere());
		this.setPreferredSize(new Dimension(500, 115));
		this.setBackground(new Color(149, 100, 219));
		// ΢��������ʾ
		textArea.setRows(4);// 4��
		textArea.setBackground(new Color(255, 249, 201));// ����ɫ
		textArea.setFont(Constant.FONT2);// ����
		textArea.setEditable(false);// ���ɱ༭
		textArea.setLineWrap(true);// �Զ�����
		textArea.setText(weibo.getWeibo_content());// ����

		// ɾ����ť
		JButton button = new JButton(new ImageIcon("images/2_ɾ��.png"));
		button.setToolTipText("ɾ��");
		button.addActionListener((e) -> {
			// ɾ��
			new Delete().delete(weibo);
			new Refresh();
		});

		// ������
		toolBar.setFloatable(false);
		toolBar.setBorder(new BevelBorder(BevelBorder.LOWERED));
		toolBar.add(button);
		toolBar.add(new JLabel("     ΢��id��" + weibo.getWeibo_id()));
		toolBar.add(new JLabel("     ����id��" + weibo.getWriter_id()));
		toolBar.add(new JLabel("     ����id��" + data.GlobalVar.login_visitor.getId()));

		JScrollPane scrollPane = new JScrollPane(textArea);
		this.add(scrollPane, new AfMargin(0, 0, -1, 0));
		this.add(toolBar, new AfMargin(-1, 0, 0, 0));
	}

	// �����˵�΢��
	private void initwr() {
		this.setLayout(new AfAnyWhere());
		this.setPreferredSize(new Dimension(500, 115));
		this.setBackground(new Color(149, 100, 219));
		// ΢��������ʾ
		textArea.setRows(4);// 4��
		textArea.setBackground(new Color(255, 249, 201));// ����ɫ
		textArea.setFont(Constant.FONT2);// ����
		textArea.setEditable(false);// ���ɱ༭
		textArea.setLineWrap(true);// �Զ�����
		textArea.setText(weibo.getWeibo_content());// ����

		// ������
		toolBar.setFloatable(false);
		toolBar.setBorder(new BevelBorder(BevelBorder.LOWERED));
		toolBar.add(new JLabel("     ΢��id��" + weibo.getWeibo_id()));
		toolBar.add(new JLabel("     ����id��" + weibo.getWriter_id()));
		toolBar.add(new JLabel("     ����id��" + data.GlobalVar.login_visitor.getId()));

		JScrollPane scrollPane = new JScrollPane(textArea);
		this.add(scrollPane, new AfMargin(0, 0, -1, 0));
		this.add(toolBar, new AfMargin(-1, 0, 0, 0));
	}
}
