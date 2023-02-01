package view_visitor;

import java.awt.Dimension;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;

import control.Delete;
import control.Insert;
import control.ResultSetNum;
import control.Select;
import data.Constant;
import data.GlobalVar;
import model.AttentionFans;
import model.Visitor;
import model.Weibo;
import tools.AfAnyWhere;
import tools.AfMargin;
import tools.BackgroundPanel;

/**
 * @Author ����
 * @Description ˵�����鿴
 * @Date ʱ�䣺2020-12-15
 */
@SuppressWarnings("serial")
public class ShowFram extends JFrame {

	private JButton button1 = new JButton("��ע");
	private JButton button2 = new JButton("ȡ����ע");
	private BackgroundPanel panel1, panel2;
	private JSplitPane splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT, true);
	private Visitor visitor;

	public ShowFram(Visitor visitor, JFrame frame) {
		this.visitor = visitor;
		// ���ô��ڱ���
		this.setTitle("�鿴");
		// ���ô��ڴ�С
		this.setSize(600, 450);
		// ������ʾ
		this.setLocationRelativeTo(null);
		// ����ͼ��
		this.setIconImage(new ImageIcon("images\\ͼ��_΢��.png").getImage());
		// �������
		init();
		// ���ָ������ӵ�����
		this.add(splitPane);
		// ���ô����Ƿ������
		this.setResizable(false);
		// ���ô����Ƿ�ɼ�
		this.setVisible(true);
	}

	private void init() {
		panel1 = new BackgroundPanel(new ImageIcon("images\\����_3.jpg").getImage());
		panel2 = new BackgroundPanel(new ImageIcon("images\\����_3.jpg").getImage());

		// panel1����
		panel1.setLayout(new AfAnyWhere());
		JLabel label = new JLabel(visitor.getVisitor_name());
		label.setFont(Constant.FONT1);
		JLabel label1 = new JLabel("΢����" + new ResultSetNum().resultSetNumWeibo(visitor.getId()));
		label1.setFont(Constant.FONT2);
		JLabel label2 = new JLabel("��ע��" + new ResultSetNum().resultSetNumAttention(visitor.getId()));
		label2.setFont(Constant.FONT2);
		JLabel label3 = new JLabel("��˿��" + new ResultSetNum().resultSetNumFans(visitor.getId()));
		label3.setFont(Constant.FONT2);
		panel1.add(label, new AfMargin(10, 30, -1, -1));
		panel1.add(label1, new AfMargin(-1, 50, 20, -1));
		panel1.add(label2, new AfMargin(-1, 150, 20, -1));
		panel1.add(label3, new AfMargin(-1, 250, 20, -1));
		button1.setFont(Constant.FONT2);
		panel1.add(button1, new AfMargin(-1, -1, 20, 150));
		button2.setFont(Constant.FONT2);
		panel1.add(button2, new AfMargin(-1, -1, 20, 50));

		// panel2����
		panel2.setLayout(new AfAnyWhere());
		BackgroundPanel p1 = new BackgroundPanel(new ImageIcon("images\\����_3.jpg").getImage());
		p1.setLayout(new AfAnyWhere());
		p1.setPreferredSize(new Dimension(300, Constant.NUM * new ResultSetNum().resultSetNumWeibo(visitor.getId())));
		Weibo[] weibos = new Select().select_Writer_id(visitor.getId());
		for (int i = 0; i < weibos.length; i++) {
			p1.add(new PanelTXT(weibos[i]), new AfMargin(Constant.NUM * i, 0, -1, 0));
		}
		JScrollPane scrollPane = new JScrollPane(p1);
		panel2.add(scrollPane);

		if (visitor.getId().equals(GlobalVar.login_visitor.getId()) == true) {
			button1.setVisible(false);
			button2.setVisible(false);
		}

		// ��ť�¼�����
		/* ��ע */
		button1.addActionListener(Event -> {
			AttentionFans attentionFans = new AttentionFans();
			attentionFans.setAttention_id(GlobalVar.login_visitor.getId());
			attentionFans.setFans_id(visitor.getId());
			if (new Select().select(attentionFans) == true) {
				JOptionPane.showMessageDialog(null, "�Ѿ���ע����", "����", JOptionPane.INFORMATION_MESSAGE);
				this.setVisible(false);
			} else if (new Insert().insert(attentionFans)) {
				JOptionPane.showMessageDialog(null, "��ע�ɹ�");
				this.setVisible(false);
			}

		});
		/* ȡ����ע */
		button2.addActionListener(Event -> {
			AttentionFans attentionFans = new AttentionFans();
			attentionFans.setAttention_id(GlobalVar.login_visitor.getId());
			attentionFans.setFans_id(visitor.getId());
			if (new Select().select(attentionFans) == false) {
				JOptionPane.showMessageDialog(null, "�㻹û�й�ע", "����", JOptionPane.INFORMATION_MESSAGE);
				this.setVisible(false);
			} else if (new Delete().delete(attentionFans)) {
				JOptionPane.showMessageDialog(null, "ȡ����ע���");
				this.setVisible(false);
			}
		});
		splitPane.setLeftComponent(panel1);
		splitPane.setRightComponent(panel2);
		// ���÷ָ��ߴ�С
		splitPane.setDividerSize(0);
		// ���÷ָ���λ��
		splitPane.setDividerLocation(100);
		// ���÷ָ����϶�
		splitPane.setEnabled(false);
		// �ڷָ������ṩUIС�����Կ�����չ/�۵��ָ���
		splitPane.setOneTouchExpandable(false);
	}
}