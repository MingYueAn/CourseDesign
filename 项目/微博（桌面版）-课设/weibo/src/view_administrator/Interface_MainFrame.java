package view_administrator;

import java.awt.Color;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JSplitPane;

import data.Constant;
import tools.BackgroundPanel;

/**
 * @Author ����
 * @Description ˵��������Ա����
 * @Date ʱ�䣺2020-12-2
 */
@SuppressWarnings("serial")
public class Interface_MainFrame extends JFrame {

	// ��ť������ɾ���ġ��飩
	private JButton button1, button2, button3, button4;
	// �����м�����[���ò���]
	private BackgroundPanel panel1, panel2;
	// �ָ����VERTICAL\HORIZONTAL
	private JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, true);

	/**
	 * ���췽��
	 */
	public Interface_MainFrame() {
		this.setVisible(false);
		// ���ô��ڱ���
		this.setTitle("����Ա����");
		// ���ô��ڴ�С
		this.setSize(600, 500);
		// ������ʾ
		this.setLocationRelativeTo(null);

		// ����ͼ��
		this.setIconImage(new ImageIcon("images\\ͼ��_΢��.png").getImage());

		// ���õ���رմ��ں������Ĵ���
		// JFrame.DO_NOTHING_ON_CLOSE ʲôҲ����
		// JFrame.HIDE_ON_CLOSE ���ص�ǰ����
		// JFrame.DISPOSE_ON_CLOSE ���ص�ǰ���ڣ����ͷŴ���ռ�е�������Դ
		// JFrrame.EXIT_ON_CLOSE ������������Ӧ�ó���
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		/* ������� */
		this.panelSettings();
		/* �¼����� */
		this.addListener();

		// ���ָ������ӵ�����
		this.add(splitPane);
		// ���ô����Ƿ������
		this.setResizable(false);
		// ���ô����Ƿ�ɼ�
		this.setVisible(true);

	}

	/**
	 * @Description ˵�����������
	 */
	private void panelSettings() {

		/* ���1���� */
		panel1 = new BackgroundPanel(new ImageIcon("images\\����_1.jpg").getImage());
		panel1.setLayout(null);
		// ��ť
		button1 = new JButton("ɾ���ÿ�");
		button1.setFont(Constant.FONT2);
		button1.setBounds(0, 50, 100, 45);
		button2 = new JButton("�޸ķÿ�");
		button2.setFont(Constant.FONT2);
		button2.setBounds(0, 100, 100, 45);
		button3 = new JButton("�鿴�ÿ�");
		button3.setFont(Constant.FONT2);
		button3.setBounds(0, 150, 100, 45);
		button4 = new JButton("�޸��˺�����");
		button4.setForeground(Color.red);
		button4.setBounds(0, 200, 100, 45);
		// ������
		panel1.add(button1);
		panel1.add(button2);
		panel1.add(button3);
		panel1.add(button4);

		/* ���2���� */
		setupPanel();

		/* �ָ�������� */
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

	/**
	 * ���panel2��ʼ����
	 */
	private void setupPanel() {
		// TODO �Զ����ɵķ������
		panel2 = new BackgroundPanel(new ImageIcon("images\\����_2.jpg").getImage());
		JLabel Title = new JLabel("��ӭ��������Ա���棡");
		Title.setFont(Constant.FONT1);
		panel2.add(Title);
	}

	/**
	 * @Description ˵�����¼�����
	 */
	private void addListener() {
		// TODO �Զ����ɵķ������
		button1.addActionListener((e) -> setup_PanelDelete(panel2, new PanelDelete()));
		button2.addActionListener((e) -> setup_PanelUpdate(panel2, new PanelUpdate()));
		button3.addActionListener((e) -> setup_PanelSelect(panel2, new PanelShow()));
		button4.addActionListener((e) -> update(panel2, new PanelAdministrator()));
	}

	private Object update(BackgroundPanel panel, PanelAdministrator panelAdministrator) {
		// TODO �Զ����ɵķ������
		panel.removeAll();// �Ƴ�����е��������
		panel.add(panelAdministrator);// ���Ҫ�л������
		panel.repaint();// ˢ��ҳ�棬�ػ����
		panel.validate();// ʹ�ػ�����ȷ����Ч

		// ��ʼ��
		panelAdministrator.init(panel.getWidth(), panel.getHeight());

		// �����ָ����
		splitPane.setLeftComponent(panel1);
		splitPane.setRightComponent(panelAdministrator);
		// ���÷ָ��ߴ�С
		splitPane.setDividerSize(0);
		// ���÷ָ���λ��
		splitPane.setDividerLocation(100);
		// ���÷ָ����϶�
		splitPane.setEnabled(false);
		// �ڷָ������ṩUIС�����Կ�����չ/�۵��ָ���
		splitPane.setOneTouchExpandable(false);
		return null;
	}

	private Object setup_PanelSelect(BackgroundPanel panel, PanelShow panelSelect) {
		// TODO �Զ����ɵķ������
		panel.removeAll();// �Ƴ�����е��������
		panel.add(panelSelect);// ���Ҫ�л������
		panel.repaint();// ˢ��ҳ�棬�ػ����
		panel.validate();// ʹ�ػ�����ȷ����Ч

		// ��ʼ��
		panelSelect.init(panel.getWidth(), panel.getHeight());

		// �����ָ����
		splitPane.setLeftComponent(panel1);
		splitPane.setRightComponent(panelSelect);
		// ���÷ָ��ߴ�С
		splitPane.setDividerSize(0);
		// ���÷ָ���λ��
		splitPane.setDividerLocation(100);
		// ���÷ָ����϶�
		splitPane.setEnabled(false);
		// �ڷָ������ṩUIС�����Կ�����չ/�۵��ָ���
		splitPane.setOneTouchExpandable(false);
		return null;
	}

	private Object setup_PanelUpdate(BackgroundPanel panel, PanelUpdate panelUpdate) {
		// TODO �Զ����ɵķ������
		panel.removeAll();// �Ƴ�����е��������
		panel.add(panelUpdate);// ���Ҫ�л������
		panel.repaint();// ˢ��ҳ�棬�ػ����
		panel.validate();// ʹ�ػ�����ȷ����Ч

		// ��ʼ��
		panelUpdate.init(panel.getWidth(), panel.getHeight());

		// �����ָ����
		splitPane.setLeftComponent(panel1);
		splitPane.setRightComponent(panelUpdate);
		// ���÷ָ��ߴ�С
		splitPane.setDividerSize(0);
		// ���÷ָ���λ��
		splitPane.setDividerLocation(100);
		// ���÷ָ����϶�
		splitPane.setEnabled(false);
		// �ڷָ������ṩUIС�����Կ�����չ/�۵��ָ���
		splitPane.setOneTouchExpandable(false);
		return null;
	}

	private Object setup_PanelDelete(BackgroundPanel panel, PanelDelete panelDelete) {
		// TODO �Զ����ɵķ������
		panel.removeAll();// �Ƴ�����е��������
		panel.add(panelDelete);// ���Ҫ�л������
		panel.repaint();// ˢ��ҳ�棬�ػ����
		panel.validate();// ʹ�ػ�����ȷ����Ч

		// ��ʼ��
		panelDelete.init(panel.getWidth(), panel.getHeight());

		// �����ָ����
		splitPane.setLeftComponent(panel1);
		splitPane.setRightComponent(panelDelete);
		// ���÷ָ��ߴ�С
		splitPane.setDividerSize(0);
		// ���÷ָ���λ��
		splitPane.setDividerLocation(100);
		// ���÷ָ����϶�
		splitPane.setEnabled(false);
		// �ڷָ������ṩUIС�����Կ�����չ/�۵��ָ���
		splitPane.setOneTouchExpandable(false);
		return null;
	}
}
