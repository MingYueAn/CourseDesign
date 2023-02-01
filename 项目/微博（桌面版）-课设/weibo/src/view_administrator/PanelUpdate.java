package view_administrator;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import control.Delete;
import control.Insert;
import control.Select;
import data.Constant;
import data.Refresh;
import model.Personal;
import model.Visitor;
import tools.AfAnyWhere;
import tools.AfMargin;
import tools.BackgroundPanel;
import tools.StringUtil;

/**
 * @Author 作者：物联19102 谢晓艳 190611201
 * @Description 说明：修改
 * @Date 时间：2020-12-17
 */
@SuppressWarnings("serial")
public class PanelUpdate extends BackgroundPanel {
	// 按钮
	private JButton updataButton = new JButton("编辑信息"), showButton = new JButton("查看信息");
	// 文本框（输入账号）
	private JTextField idTextField = new JTextField();

	public PanelUpdate() {
		super(new ImageIcon("images\\背景_2.jpg").getImage());
		this.setLayout(new AfAnyWhere());
	}

	/**
	 * @Description 说明：修改
	 */
	public void init(int w, int h) {
		JLabel Title = new JLabel("修改访客");
		Title.setFont(Constant.FONT1);
		this.add(Title, AfMargin.TOP_CENTER);

		JLabel label = new JLabel("输入需要修改的访客的账号：");
		label.setFont(Constant.FONT2);
		this.add(label, new AfMargin(60, -1, -1, -1));

		idTextField = new JTextField(15);
		idTextField.setToolTipText("账号");
		idTextField.setFont(Constant.FONT2);
		this.add(idTextField, new AfMargin(85, -1, -1, -1));

		updataButton.setFont(Constant.FONT2);
		showButton.setFont(Constant.FONT2);
		this.add(updataButton, new AfMargin(-1, 120, 150, -1));
		this.add(showButton, new AfMargin(-1, -1, 150, 120));

		// 修改
		updataButton.addActionListener((e) -> {
			String id = this.idTextField.getText();
			if (StringUtil.isEmpty(id)) {
				JOptionPane.showMessageDialog(null, "ID不能为空");
				return;
			}

			Personal personal = new Personal(id);
			new Select().select(personal);
			Visitor visitor = new Visitor(id);
			new Select().select(visitor);
			if (visitor.getVisitor_yes_no().equals(Constant.YES_STRING) == true) {
				UpdataVisitor dialog = new UpdataVisitor(personal, (JFrame) this.getRootPane().getParent(), true);
				dialog.setVisible(true);
			} else {
				System.out.println("不可查看");
				JOptionPane.showMessageDialog(null, "账号不存在", "警告", JOptionPane.INFORMATION_MESSAGE);
			}
		});
		// 查看
		showButton.addActionListener((e) -> {
			String id = this.idTextField.getText();
			if (StringUtil.isEmpty(id)) {
				JOptionPane.showMessageDialog(null, "ID不能为空");
				return;
			}

			Visitor visitor = new Visitor(id);
			new Select().select(visitor);
			if (visitor.getVisitor_yes_no().equals(Constant.YES_STRING) == true) {
				FindVisitor dialog = new FindVisitor(visitor, (JFrame) this.getRootPane().getParent(), true);
				dialog.setVisible(true);
			} else {
				System.out.println("不可查看");
				JOptionPane.showMessageDialog(null, "账号不存在", "警告", JOptionPane.INFORMATION_MESSAGE);
			}
		});
	}
}

/**
 * @Author 作者：物联19102 谢晓艳 190611201
 * @Description 说明：搜索结果
 * @Date 时间：2020-12-17
 */
@SuppressWarnings("serial")
class UpdataVisitor extends JDialog {
	UpdataVisitor(Personal personal, JFrame parent, boolean modal) {
		super(parent, modal);
		this.setTitle("搜索结果");
		this.setSize(200, 250);
		this.setResizable(false);
		this.setLocationRelativeTo(null);
		this.setLayout(new AfAnyWhere());

		JLabel label = new JLabel("修改访客的信息");
		label.setFont(Constant.FONT2);
		this.add(label, new AfMargin(10, -1, -1, -1));
		this.add(new JLabel("账号："), new AfMargin(50, 15, -1, -1));
		this.add(new JLabel("密码："), new AfMargin(80, 15, -1, -1));

		JTextField TextField1 = new JTextField(personal.getId(), 6);
		JTextField TextField2 = new JTextField(personal.getPassword(), 6);

		this.add(TextField1, new AfMargin(45, -1, -1, 15));
		this.add(TextField2, new AfMargin(75, -1, -1, 15));

		JButton button1 = new JButton("确定编辑");
		JButton button2 = new JButton("清空数据");
		button1.setFont(Constant.FONT2);
		button2.setFont(Constant.FONT2);
		this.add(button1, new AfMargin(-1, 10, 50, -1));
		this.add(button2, new AfMargin(-1, -1, 50, 10));
		// 修改
		button1.addActionListener((e) -> {
			if (StringUtil.isEmpty(TextField1.getText()) || StringUtil.isEmpty(TextField2.getText())) {
				JOptionPane.showMessageDialog(null, "输入框不能为空");
				return;
			}

			new Delete().delete(personal);
			personal.setId(TextField1.getText());
			personal.setPassword(TextField2.getText());
			personal.setPower(Constant.POWER[0]);
			new Insert().insert(personal);
			new Refresh();
		});
		// 清空
		button2.addActionListener((e) -> {
			TextField1.setText("");
			TextField2.setText("");
		});
	}
}