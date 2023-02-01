package view_visitor;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.IOException;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JSplitPane;
import javax.swing.JTextField;
import javax.swing.JToolBar;
import javax.swing.border.BevelBorder;

import control.Delete;
import control.Select;
import data.Constant;
import data.GlobalVar;
import data.Refresh;
import model.Visitor;

/**
 * @Author ����
 * @Description ˵�����ÿ�������
 * @Date ʱ�䣺2020-11-20
 */
@SuppressWarnings("serial")
public class Interface_MainFrame extends JFrame {

	// ������
	private JToolBar jToolBar = new JToolBar();

	private JButton button1, button2, button3, button;
	private JTextField idTextField;

	// �����м�����[���ò���]
	private Panel1_PersonalInformation panel1;
	private Panel2_EditorialPublished panel2;
	private Panel3_MainFunction panel3;
	private Panel4_PlaybackControls panel4;

	// �ָ����VERTICAL\HORIZONTAL
	private JSplitPane splitPane1 = new JSplitPane(JSplitPane.VERTICAL_SPLIT, true);
	private JSplitPane splitPane2 = new JSplitPane(JSplitPane.VERTICAL_SPLIT, true);
	private JSplitPane splitPane3 = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, true);

	/**
	 * ���췽��
	 */
	public Interface_MainFrame() {
		System.out.println("-----��¼�ÿͶ���Ĳ�����Ϣ");
		System.out.println("�ÿ�ID��" + GlobalVar.login_visitor.getId());
		System.out.println("�ÿ��ǳƣ�" + GlobalVar.login_visitor.getVisitor_name());
		System.out.println("�ɼ�΢���ܸ�����" + GlobalVar.weibo_num2);
		System.out.println("΢��������" + GlobalVar.weibo_num1);
		System.out.println("��ע������" + GlobalVar.attention_num);
		System.out.println("��˿������" + GlobalVar.fans_num);
		// ��������������
		// ���ô��ڱ���
		this.setTitle("΢��");
		// ���ô��ڴ�С
		this.setSize(900, 600);
		// ������ʾ
		this.setLocationRelativeTo(null);

		// ����ͼ��
		this.setIconImage(new ImageIcon("images\\ͼ��_΢��.png").getImage());

		// ���õ���رմ��ں������Ĵ���
		// JFrame.DO_NOTHING_ON_CLOSE ʲôҲ����
		// JFrame.HIDE_ON_CLOSE ���ص�ǰ����
		// JFrame.DISPOSE_ON_CLOSE ���ص�ǰ���ڣ����ͷŴ���ռ�е�������Դ
		// JFrrame.EXIT_ON_CLOSE ������������Ӧ�ó���
//		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		/* ������������ݴ��� */
		this.panelSettings();
		/* �¼����� */
		this.addListener();

		// ��ӹ�����
		this.add(jToolBar, BorderLayout.NORTH);
		// ���ָ������ӵ�����
		this.add(splitPane3);
		// ���ô����Ƿ������
		this.setResizable(false);
		// ���ô����Ƿ�ɼ�
		this.setVisible(true);
		// ��Ӵ��ڼ�����
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				Panel2_EditorialPublished.editArea.requestFocus();
				String currentValue = Panel2_EditorialPublished.editArea.getText();
				if (currentValue.equals(SetupJMenuBar.oldValue) == true || Panel2_EditorialPublished.editArea.getText().equals("")) {
					System.exit(0);
				} else {
					int exitChoose = JOptionPane.showConfirmDialog(panel2, "�����ļ���δ���棬�Ƿ񱣴棿", "�˳���ʾ", JOptionPane.YES_NO_CANCEL_OPTION);
					if (exitChoose == JOptionPane.YES_OPTION) {
						new SetupJMenuBar().SaveTextFileAs();
					} else if (exitChoose == JOptionPane.NO_OPTION) {
						System.exit(0);
					}
				}
			}
		});

	}

	/**
	 * @Description ˵����������������ݴ���
	 */
	private void panelSettings() {

		/* ���ò˵��� */
		this.setJMenuBar(new SetupJMenuBar());
		/* ���ù����� */
		this.setJToolBar();
		/* ���panel1���á���������Ϣ */
		panel1 = new Panel1_PersonalInformation();
		/* ���panel2���á����༭������΢�� */
		panel2 = new Panel2_EditorialPublished();
		/* ���panel3���á������΢�� */
		panel3 = new Panel3_MainFunction();
		/* ���panel4���á������ֲ��ſؼ� */
		panel4 = new Panel4_PlaybackControls();
		/* ���÷ָ���� */
		this.setupSplitPane();
	}

	/**
	 * @Description ˵�������ù�����
	 */
	private void setJToolBar() {
		// ���ù��������ɸ���
		jToolBar.setFloatable(false);
		// ���ù������߿򵼽Ƿ�ʽ
		jToolBar.setBorder(new BevelBorder(BevelBorder.LOWERED));
		jToolBar.setBackground(Color.yellow);

		button = new JButton(new ImageIcon("images/1_��ͼ.png"));
		button.setToolTipText("��ͼ");
		idTextField = new JTextField("��������Ҫ���ҵ��˺�", 25);
		button1 = new JButton(new ImageIcon("images/1_����.png"));
		button1.setToolTipText("����");
		button2 = new JButton(new ImageIcon("images/1_ˢ��.png"));
		button2.setToolTipText("ˢ��");
		button3 = new JButton(new ImageIcon("images/1_ע��.png"));
		button3.setToolTipText("ע��");
		jToolBar.add(idTextField);
		jToolBar.add(button1);
		jToolBar.addSeparator();
		jToolBar.add(button);
		jToolBar.add(button2);
		jToolBar.add(button3);
	}

	/**
	 * ���÷ָ����
	 */
	private void setupSplitPane() {
		splitPane1.setLeftComponent(panel1);
		splitPane1.setRightComponent(panel2);
		// ���÷ָ��ߴ�С
		splitPane1.setDividerSize(0);
		// ���÷ָ���λ��
		splitPane1.setDividerLocation(300);
		// ���÷ָ����϶�
		splitPane1.setEnabled(false);
		// �ڷָ������ṩUIС�����Կ�����չ/�۵��ָ���
		splitPane1.setOneTouchExpandable(false);

		splitPane2.setLeftComponent(panel3);
		splitPane2.setRightComponent(panel4);
		// ���÷ָ��ߴ�С
		splitPane2.setDividerSize(0);
		// ���÷ָ���λ��
		splitPane2.setDividerLocation(420);
		// ���÷ָ����϶�
		splitPane2.setEnabled(false);
		// �ڷָ������ṩUIС�����Կ�����չ/�۵��ָ���
		splitPane2.setOneTouchExpandable(false);

		splitPane3.setLeftComponent(splitPane1);
		splitPane3.setRightComponent(splitPane2);
		// ���÷ָ��ߴ�С
		splitPane3.setDividerSize(10);
		// ���÷ָ���λ��
		splitPane3.setDividerLocation(300);
		// ���÷ָ����϶�
		splitPane3.setEnabled(false);
		// �ڷָ������ṩUIС�����Կ�����չ/�۵��ָ���
		splitPane3.setOneTouchExpandable(true);
	}

	/**
	 * �¼�����
	 */
	private void addListener() {
		/* ��ͼ */
		button.addActionListener((e) -> {
			// ��Ե�ַ
			File file = new File("database", "��Ļ��ͼ.exe");
			Runtime runtime = Runtime.getRuntime();
			System.out.println("��Ļ��ͼ.exe�ľ��Ե�ַ��" + file.getAbsolutePath());
			try {
				runtime.exec(file.getAbsolutePath());
			} catch (IOException e1) {
				System.out.println(e1);
			}
		});
		/* ���� */
		button1.addActionListener((e) -> {
			/* �ÿ� */
			Visitor visitor = new Visitor(idTextField.getText());
			new Select().select(visitor);
			if (visitor.getVisitor_yes_no().equals(Constant.YES_STRING) == true) {
				new ShowFram(visitor, this);
			} else {
				System.out.println("���ɲ鿴");
				JOptionPane.showMessageDialog(null, "�˺Ų�����", "����", JOptionPane.INFORMATION_MESSAGE);
			}
		});
		/* ˢ�� */
		button2.addActionListener((e) -> {
			this.setVisible(false);
			new Refresh();
		});
		/* ע�� */
		button3.addActionListener((e) -> {
			// �Ի���ѯ���Ƿ���Ҫע����
			int buttonChoose = JOptionPane.showConfirmDialog(this, "������ע�����˺ţ���ȷ��ע��", "��ʾ", JOptionPane.YES_NO_OPTION);
			if (buttonChoose == JOptionPane.NO_OPTION) {
				return;
			} else if (buttonChoose == JOptionPane.YES_OPTION) {
				new Delete().delete(GlobalVar.login_personal);
				new Delete().delete(GlobalVar.login_visitor);
				new Delete().deleteWeibo(GlobalVar.login_visitor.getId());
				new Delete().delete1(GlobalVar.login_visitor.getId());
				new Delete().delete2(GlobalVar.login_visitor.getId());
				System.exit(0);
			}
		});
	}
}