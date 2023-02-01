package view_administrator;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import control.Delete;
import control.Select;
import data.Constant;
import data.GlobalVar;
import data.Refresh;
import model.Personal;
import model.Visitor;
import tools.AfAnyWhere;
import tools.AfMargin;
import tools.BackgroundPanel;
import tools.StringUtil;

/**
 * @Author 作者：物联19102 谢晓艳 190611201
 * @Description 说明：删除
 * @Date 时间：2020-12-17
 */
@SuppressWarnings("serial")
public class PanelDelete extends BackgroundPanel {
	// 按钮
	private JButton deleteButton = new JButton("确定删除"), showButton = new JButton("查看信息");
	// 文本框（输入账号）
	private JTextField idTextField = new JTextField();

	public PanelDelete() {
		super(new ImageIcon("images\\背景_2.jpg").getImage());
		this.setLayout(new AfAnyWhere());
	}

	/**
	 * @Description 说明：删除
	 */
	public void init(int w, int h) {
		JLabel Title = new JLabel("删除访客");
		Title.setFont(Constant.FONT1);
		this.add(Title, AfMargin.TOP_CENTER);

		JLabel label = new JLabel("输入需要删除的访客的账号：");
		label.setFont(Constant.FONT2);
		this.add(label, new AfMargin(60, -1, -1, -1));

		idTextField = new JTextField(15);
		idTextField.setToolTipText("账号");
		idTextField.setFont(Constant.FONT2);
		this.add(idTextField, new AfMargin(85, -1, -1, -1));

		deleteButton.setFont(Constant.FONT2);
		showButton.setFont(Constant.FONT2);
		this.add(deleteButton, new AfMargin(-1, 120, 150, -1));
		this.add(showButton, new AfMargin(-1, -1, 150, 120));

		// 删除
		deleteButton.addActionListener((e) -> {
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
				new Delete().delete(personal);
				new Delete().delete(visitor);
				new Delete().deleteWeibo(visitor.getId());
				new Delete().deleteWeibo(visitor.getId());
				new Delete().delete1(visitor.getId());
				new Delete().delete2(visitor.getId());
				this.getRootPane().getParent().setVisible(false);
				new Refresh();
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
 * @Description 说明：搜索结果面板
 * @Date 时间：2020-12-17
 */
@SuppressWarnings("serial")
class FindVisitor extends JDialog {
	FindVisitor(Visitor operator, JFrame parent, boolean modal) {
		super(parent, modal);
		this.setTitle("搜索结果");
		this.setSize(200, 250);
		this.setResizable(false);
		this.setLocationRelativeTo(null);
		this.setLayout(new AfAnyWhere());

		JLabel label = new JLabel("访客的信息");
		label.setFont(Constant.FONT2);
		this.add(label, new AfMargin(10, -1, -1, -1));
		this.add(new JLabel("账号：" + operator.getId()), new AfMargin(50, 20, -1, -1));
		this.add(new JLabel("昵称：" + operator.getVisitor_name()), new AfMargin(75, 20, -1, -1));
		this.add(new JLabel("性别：" + operator.getVisitor_sex()), new AfMargin(100, 20, -1, -1));
		this.add(new JLabel("生日：" + operator.getVisitor_birthday()), new AfMargin(125, 20, -1, -1));
		this.add(new JLabel("微博数：" + GlobalVar.weibo_num1), new AfMargin(150, 20, -1, -1));
		this.add(new JLabel("关注数：" + GlobalVar.attention_num), new AfMargin(175, 20, -1, -1));
		this.add(new JLabel("粉丝数：" + GlobalVar.fans_num), new AfMargin(200, 20, -1, -1));
	}
}