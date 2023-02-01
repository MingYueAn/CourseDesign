package view_administrator;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import control.Delete;
import control.Insert;
import data.Constant;
import data.GlobalVar;
import model.Personal;
import tools.AfAnyWhere;
import tools.AfMargin;
import tools.BackgroundPanel;
import tools.StringUtil;

/**
 * @Author ����
 * @Description ˵��������Ա���
 * @Date ʱ�䣺2020-12-2
 */
@SuppressWarnings("serial")
public class PanelAdministrator extends BackgroundPanel {
	// ��ť
	private JButton button1 = new JButton("ȷ���޸�"), button2 = new JButton("�������");
	// �ı��������˺ţ�
	private JTextField idTextField = new JTextField();
	// ������������룩
	private JPasswordField password1 = new JPasswordField();
	// �����ȷ�����룩
	private JPasswordField password2 = new JPasswordField();

	public PanelAdministrator() {
		super(new ImageIcon("images\\����_2.jpg").getImage());
		this.setLayout(new AfAnyWhere());
	}

	/**
	 * @Description ˵��������Ա����ʼ��
	 */
	public void init(int w, int h) {
		JLabel Title = new JLabel("����Ա");
		Title.setFont(Constant.FONT1);
		this.add(Title, AfMargin.TOP_CENTER);

		JLabel label1 = new JLabel("�������Ա���˺ţ���ԭ�˺ţ�" + GlobalVar.login_personal.getId() + "��");
		label1.setFont(Constant.FONT2);
		JLabel label2 = new JLabel("�������Ա�����룺��ԭ���룺" + GlobalVar.login_personal.getPassword() + "��");
		label2.setFont(Constant.FONT2);
		JLabel label3 = new JLabel("ȷ���������Ա�����룺");
		label3.setFont(Constant.FONT2);
		this.add(label1, new AfMargin(80, 115, -1, -1));
		this.add(label2, new AfMargin(130, 115, -1, -1));
		this.add(label3, new AfMargin(180, 115, -1, -1));

		idTextField = new JTextField(15);
		idTextField.setToolTipText("�˺�");
		idTextField.setFont(Constant.FONT2);
		password1 = new JPasswordField(15);
		password1.setToolTipText("����");
		password1.setFont(Constant.FONT2);
		password2 = new JPasswordField(15);
		password2.setToolTipText("ȷ������");
		password2.setFont(Constant.FONT2);
		this.add(idTextField, new AfMargin(100, 115, -1, -1));
		this.add(password1, new AfMargin(150, 115, -1, -1));
		this.add(password2, new AfMargin(200, 115, -1, -1));

		button1.setFont(Constant.FONT2);
		button2.setFont(Constant.FONT2);
		this.add(button1, new AfMargin(-1, 120, 150, -1));
		this.add(button2, new AfMargin(-1, -1, 150, 120));

		// ���
		button1.addActionListener((e) -> {
			String id = this.idTextField.getText();
			String password1 = new String(this.password1.getPassword());
			String password2 = new String(this.password2.getPassword());

			if (StringUtil.isEmpty(id) || StringUtil.isEmpty(password1) || StringUtil.isEmpty(password2)) {
				JOptionPane.showMessageDialog(null, "�������Ϊ��");
				return;
			}
			if (password1.equals(password2) == false) {
				JOptionPane.showMessageDialog(null, "�����������벻һ��");
				return;
			}
			// �޸Ĳ���
			new Delete().delete(GlobalVar.login_personal);
			Personal personal = new Personal(id, password1);
			personal.setPower(Constant.POWER[1]);
			new Insert().insert(personal);
			GlobalVar.login_personal = personal;
			this.getRootPane().getParent().setVisible(false);
			new Interface_MainFrame();
		});
		// ���
		button2.addActionListener((e) -> {
			idTextField.setText("");
			password1.setText("");
			password2.setText("");
		});
	}
}
