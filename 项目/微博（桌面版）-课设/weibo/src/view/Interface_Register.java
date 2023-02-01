package view;

import javax.swing.*;

import control.LoginRegister;
import data.Constant;
import tools.BackgroundPanel;
import tools.StringUtil;

/**
 * @Author ���ߣ�����19102 л���� 190611201
 * @Description ˵����ע�����
 * @Date ʱ�䣺2020-12-18
 */
@SuppressWarnings("serial")
public class Interface_Register extends JFrame {

	// ��ť�����ص�¼��ȷ��ע�ᣩ
	private JButton back = new JButton("���ص�¼"), ok = new JButton("ȷ��ע��");
	// ��ǩ���˺ţ������룺��ȷ�����룺��ע����棩
	private JLabel jLabel1 = new JLabel("�˺ţ�"), jLabel2 = new JLabel("���룺"), jLabel3 = new JLabel("ȷ�����룺"), jLabelTitle = new JLabel("ע�����");
	// �ı��������˺ţ�
	private JTextField idTextField = new JTextField();
	// ������������룩
	private JPasswordField password1 = new JPasswordField();
	// �����ȷ�����룩
	private JPasswordField password2 = new JPasswordField();
	// �����м�����[���ò���]
	private JPanel panel;

	/**
	 * @Description ˵�������췽��
	 */
	public Interface_Register() {
		// ��������������
		// ���ô��ڱ���
		this.setTitle("ע�����");
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
		// ���룺��ǩ
		jLabel3.setFont(Constant.FONT2);
		jLabel3.setBounds(230, 265, 70, 25);
		panel.add(jLabel3);
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
		// ȷ������ͼ��
		JLabel iconJLabel3 = new JLabel();
		iconJLabel3.setIcon(iconPassword);
		iconJLabel3.setBounds(230, 290, 20, 25);
		panel.add(iconJLabel3);
		// �˺������
		idTextField.setBounds(250, 180, 120, 25);
		panel.add(idTextField);
		// ���������
		password1.setBounds(250, 235, 120, 25);
		panel.add(password1);
		// ȷ�����������
		password2.setBounds(250, 290, 120, 25);
		panel.add(password2);
		// ���ص�¼��ť
		back.setBounds(200, 330, 90, 30);
		panel.add(back);
		// ȷ��ע�ᰴť
		ok.setBounds(340, 330, 90, 30);
		panel.add(ok);

	}

	/**
	 * @Description ˵�����¼�����
	 */
	private void addListener() {
		// ���ص�¼����
		back.addActionListener((e) -> backLogin());
		// ȷ��ע�᷽��
		ok.addActionListener((e) -> setRegister());
	}

	/**
	 * @Description ˵�������ص�¼����
	 */
	private void backLogin() {
		this.setVisible(false);
		new Interface_Login();
	}

	/**
	 * @Description ˵����ע��ÿ�
	 */
	private void setRegister() {
		String id = this.idTextField.getText();
		String password1 = new String(this.password1.getPassword());
		String password2 = new String(this.password2.getPassword());
		if (StringUtil.isEmpty(id)) {
			JOptionPane.showMessageDialog(null, "�û�������Ϊ��");
			return;
		}
		if (StringUtil.isEmpty(password1) || StringUtil.isEmpty(password2)) {
			JOptionPane.showMessageDialog(null, "���벻��Ϊ��");
			return;
		}
		if (password1.equals(password2) == false) {
			JOptionPane.showMessageDialog(null, "�����������벻һ��");
			return;
		}
		new LoginRegister().register(id, password1);
		// ���ص�ǰ����
		this.setVisible(false);
		new Interface_Login();
	}
}
