package view;

import javax.swing.*;

import control.LoginRegister;
import data.Constant;
import data.GlobalVar;
import model.Personal;
import tools.BackgroundPanel;
import tools.StringUtil;

/**
 * @Author ���ߣ�����19102 л���� 190611201
 * @Description ˵������¼����
 * @Date ʱ�䣺2020-12-18
 */
@SuppressWarnings("serial")
public class Interface_Login extends JFrame {

	// ��ť����¼��ע�ᣩ
	private JButton login = new JButton("��¼"), register = new JButton("ע��");
	// ��ǩ���˺ţ������룺����¼���棩
	private JLabel jLabel1 = new JLabel("�˺ţ�"), jLabel2 = new JLabel("���룺"), jLabelTitle = new JLabel("��¼����");
	// �ı��������˺ţ�
	private JTextField id = new JTextField();
	// ������������룩
	private JPasswordField password = new JPasswordField();
	// �����м�����[���ò���]
	private JPanel panel;

	/**
	 * @Description ˵�������췽��
	 */
	public Interface_Login() {
		// ��������������
		// ���ô��ڱ���
		this.setTitle("��¼����");
		// ���ô��ڴ�С
		this.setSize(600, 500);
		// ���ô���λ�þ�����ʾ
		this.setLocationRelativeTo(null);
		// ������Image������Ķ���
		// JFmageIconʵ�ֵ���Icon�ӿ� ����û��ʵ��Image�����ࡣ
		// ��Ҫʹ��getImage()��ȡImage����
		// ����ͼ��
		this.setIconImage(new ImageIcon("images\\ͼ��_΢��.png").getImage());
		// ���õ���رմ��ں������Ĵ���
		// ���еĲ���operationȡJFrame���еľ�̬����
		// window.setDefaultCloseOperation(operation);
		// JFrame.DO_NOTHING_ON_CLOSE ʲôҲ����
		// JFrame.HIDE_ON_CLOSE ���ص�ǰ����
		// JFrame.DISPOSE_ON_CLOSE ���ص�ǰ���ڣ����ͷŴ���ռ�е�������Դ
		// JFrrame.EXIT_ON_CLOSE ������������Ӧ�ó���
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		// ���������ʼ��
		this.init();
		// �¼�����
		this.addListener();
		// ���м����������ӵ�����
		this.setContentPane(panel);
		// ���ô����Ƿ������
		this.setResizable(false);
		// ���ô����Ƿ�ɼ�
		this.setVisible(true);
	}

	/**
	 * @Description ˵�������������ʼ��
	 */
	private void init() {
		panel = new BackgroundPanel(new ImageIcon("images\\����_��¼ע��.png").getImage());
		panel.setLayout(null);
		// ��¼�����ǩ
		jLabelTitle.setFont(Constant.FONT1);
		jLabelTitle.setBounds(235, 90, 150, 30);
		panel.add(jLabelTitle);
		// �˺ţ���ǩ
		jLabel1.setFont(Constant.FONT2);
		jLabel1.setBounds(230, 155, 70, 25);
		panel.add(jLabel1);
		// ���룺��ǩ
		jLabel2.setFont(Constant.FONT2);
		jLabel2.setBounds(230, 210, 70, 25);
		panel.add(jLabel2);
		// �˺�ͼ��
		ImageIcon iconUserID = new ImageIcon("images/ͼ��_�ҵ�.png");
		iconUserID.setImage(iconUserID.getImage().getScaledInstance(15, 15, 15));
		JLabel iconJLabel1 = new JLabel();
		iconJLabel1.setIcon(iconUserID);
		iconJLabel1.setBounds(230, 180, 20, 25);
		panel.add(iconJLabel1);
		// ����ͼ��
		ImageIcon iconPassword = new ImageIcon("images/ͼ��_����.png");
		iconPassword.setImage(iconPassword.getImage().getScaledInstance(15, 15, 15));
		JLabel iconJLabel2 = new JLabel();
		iconJLabel2.setIcon(iconPassword);
		iconJLabel2.setBounds(230, 235, 20, 25);
		panel.add(iconJLabel2);
		// �˺������
		id.setBounds(250, 180, 120, 25);
		panel.add(id);
		// ���������
		password.setBounds(250, 235, 120, 25);
		panel.add(password);
		// ��¼��ť
		login.setBounds(220, 300, 60, 30);
		panel.add(login);
		// ע�ᰴť
		register.setBounds(320, 300, 60, 30);
		panel.add(register);
	}

	/**
	 * @Description ˵�����¼�����
	 */
	private void addListener() {
		// ��¼����
		login.addActionListener((e) -> forLogin());
		// ע�᷽��
		register.addActionListener((e) -> forRegister());
	}

	/**
	 * @Description ˵������¼
	 */
	private void forLogin() {
		String id = this.id.getText();
		String password = new String(this.password.getPassword());
		if (StringUtil.isEmpty(id)) {
			JOptionPane.showMessageDialog(null, "�û�������Ϊ��");
			return;
		}
		if (StringUtil.isEmpty(password)) {
			JOptionPane.showMessageDialog(null, "���벻��Ϊ��");
			return;
		}
		// ��¼
		Personal login = new Personal();
		login = new LoginRegister().login(id, password);
		if (login.getLogin_success().equals(Constant.YES_STRING) == true) {
			// ���浱ǰ��¼���˶���
			GlobalVar.login_personal = login;
			// ��ȡ����Ȩ�޲��жϽ���
			switch (login.getPower()) {
			case "�ÿ�":
//				System.out.println("�򿪷ÿ�������");
				JOptionPane.showMessageDialog(null, GlobalVar.login_visitor.getVisitor_name() + " ��ӭ���ĵ�����", "��¼�ɹ�", JOptionPane.INFORMATION_MESSAGE);
				new view_visitor.Interface_MainFrame();
				break;
			case "����Ա":
//				System.out.println("�򿪹���Ա������");
				JOptionPane.showMessageDialog(null, "����Ա" + " ��ӭ���ĵ�����", "��¼�ɹ�", JOptionPane.INFORMATION_MESSAGE);
				new view_administrator.Interface_MainFrame();
				break;
			default:
				System.out.println("���");
			}
			// ���ص�ǰ����
			this.setVisible(false);
		} else {
			JOptionPane.showMessageDialog(null, "��¼ʧ�ܣ������µ�¼��", "��¼ʧ��", JOptionPane.INFORMATION_MESSAGE);
		}
		System.out.println("-----��¼���˶������Ϣ");
		System.out.println("�˺ţ�" + GlobalVar.login_personal.getId());
		System.out.println("���룺" + GlobalVar.login_personal.getPassword());
		System.out.println("Ȩ�ޣ�" + GlobalVar.login_personal.getPower());
		System.out.println("Login_success��" + GlobalVar.login_personal.getLogin_success());
	}

	/**
	 * @Description ˵����ע��
	 */
	private void forRegister() {
		// ���ص�ǰ����
		this.setVisible(false);
		new Interface_Register();
	}

}
