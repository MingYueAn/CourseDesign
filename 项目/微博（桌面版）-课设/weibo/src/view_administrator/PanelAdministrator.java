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
 * @Author 作者
 * @Description 说明：管理员面板
 * @Date 时间：2020-12-2
 */
@SuppressWarnings("serial")
public class PanelAdministrator extends BackgroundPanel {
	// 按钮
	private JButton button1 = new JButton("确定修改"), button2 = new JButton("清空数据");
	// 文本框（输入账号）
	private JTextField idTextField = new JTextField();
	// 密码框（输入密码）
	private JPasswordField password1 = new JPasswordField();
	// 密码框（确认密码）
	private JPasswordField password2 = new JPasswordField();

	public PanelAdministrator() {
		super(new ImageIcon("images\\背景_2.jpg").getImage());
		this.setLayout(new AfAnyWhere());
	}

	/**
	 * @Description 说明：管理员面板初始化
	 */
	public void init(int w, int h) {
		JLabel Title = new JLabel("管理员");
		Title.setFont(Constant.FONT1);
		this.add(Title, AfMargin.TOP_CENTER);

		JLabel label1 = new JLabel("输入管理员的账号：（原账号：" + GlobalVar.login_personal.getId() + "）");
		label1.setFont(Constant.FONT2);
		JLabel label2 = new JLabel("输入管理员的密码：（原密码：" + GlobalVar.login_personal.getPassword() + "）");
		label2.setFont(Constant.FONT2);
		JLabel label3 = new JLabel("确认输入管理员的密码：");
		label3.setFont(Constant.FONT2);
		this.add(label1, new AfMargin(80, 115, -1, -1));
		this.add(label2, new AfMargin(130, 115, -1, -1));
		this.add(label3, new AfMargin(180, 115, -1, -1));

		idTextField = new JTextField(15);
		idTextField.setToolTipText("账号");
		idTextField.setFont(Constant.FONT2);
		password1 = new JPasswordField(15);
		password1.setToolTipText("密码");
		password1.setFont(Constant.FONT2);
		password2 = new JPasswordField(15);
		password2.setToolTipText("确认密码");
		password2.setFont(Constant.FONT2);
		this.add(idTextField, new AfMargin(100, 115, -1, -1));
		this.add(password1, new AfMargin(150, 115, -1, -1));
		this.add(password2, new AfMargin(200, 115, -1, -1));

		button1.setFont(Constant.FONT2);
		button2.setFont(Constant.FONT2);
		this.add(button1, new AfMargin(-1, 120, 150, -1));
		this.add(button2, new AfMargin(-1, -1, 150, 120));

		// 添加
		button1.addActionListener((e) -> {
			String id = this.idTextField.getText();
			String password1 = new String(this.password1.getPassword());
			String password2 = new String(this.password2.getPassword());

			if (StringUtil.isEmpty(id) || StringUtil.isEmpty(password1) || StringUtil.isEmpty(password2)) {
				JOptionPane.showMessageDialog(null, "输入框不能为空");
				return;
			}
			if (password1.equals(password2) == false) {
				JOptionPane.showMessageDialog(null, "两次密码输入不一致");
				return;
			}
			// 修改操作
			new Delete().delete(GlobalVar.login_personal);
			Personal personal = new Personal(id, password1);
			personal.setPower(Constant.POWER[1]);
			new Insert().insert(personal);
			GlobalVar.login_personal = personal;
			this.getRootPane().getParent().setVisible(false);
			new Interface_MainFrame();
		});
		// 清空
		button2.addActionListener((e) -> {
			idTextField.setText("");
			password1.setText("");
			password2.setText("");
		});
	}
}
