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
 * @Author ���ߣ�����19102 л���� 190611201
 * @Description ˵����ɾ��
 * @Date ʱ�䣺2020-12-17
 */
@SuppressWarnings("serial")
public class PanelDelete extends BackgroundPanel {
	// ��ť
	private JButton deleteButton = new JButton("ȷ��ɾ��"), showButton = new JButton("�鿴��Ϣ");
	// �ı��������˺ţ�
	private JTextField idTextField = new JTextField();

	public PanelDelete() {
		super(new ImageIcon("images\\����_2.jpg").getImage());
		this.setLayout(new AfAnyWhere());
	}

	/**
	 * @Description ˵����ɾ��
	 */
	public void init(int w, int h) {
		JLabel Title = new JLabel("ɾ���ÿ�");
		Title.setFont(Constant.FONT1);
		this.add(Title, AfMargin.TOP_CENTER);

		JLabel label = new JLabel("������Ҫɾ���ķÿ͵��˺ţ�");
		label.setFont(Constant.FONT2);
		this.add(label, new AfMargin(60, -1, -1, -1));

		idTextField = new JTextField(15);
		idTextField.setToolTipText("�˺�");
		idTextField.setFont(Constant.FONT2);
		this.add(idTextField, new AfMargin(85, -1, -1, -1));

		deleteButton.setFont(Constant.FONT2);
		showButton.setFont(Constant.FONT2);
		this.add(deleteButton, new AfMargin(-1, 120, 150, -1));
		this.add(showButton, new AfMargin(-1, -1, 150, 120));

		// ɾ��
		deleteButton.addActionListener((e) -> {
			String id = this.idTextField.getText();
			if (StringUtil.isEmpty(id)) {
				JOptionPane.showMessageDialog(null, "ID����Ϊ��");
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
				System.out.println("���ɲ鿴");
				JOptionPane.showMessageDialog(null, "�˺Ų�����", "����", JOptionPane.INFORMATION_MESSAGE);
			}
		});
		// �鿴
		showButton.addActionListener((e) -> {
			String id = this.idTextField.getText();
			if (StringUtil.isEmpty(id)) {
				JOptionPane.showMessageDialog(null, "ID����Ϊ��");
				return;
			}
			Visitor visitor = new Visitor(id);
			new Select().select(visitor);
			if (visitor.getVisitor_yes_no().equals(Constant.YES_STRING) == true) {
				FindVisitor dialog = new FindVisitor(visitor, (JFrame) this.getRootPane().getParent(), true);
				dialog.setVisible(true);
			} else {
				System.out.println("���ɲ鿴");
				JOptionPane.showMessageDialog(null, "�˺Ų�����", "����", JOptionPane.INFORMATION_MESSAGE);
			}
		});
	}
}

/**
 * @Author ���ߣ�����19102 л���� 190611201
 * @Description ˵��������������
 * @Date ʱ�䣺2020-12-17
 */
@SuppressWarnings("serial")
class FindVisitor extends JDialog {
	FindVisitor(Visitor operator, JFrame parent, boolean modal) {
		super(parent, modal);
		this.setTitle("�������");
		this.setSize(200, 250);
		this.setResizable(false);
		this.setLocationRelativeTo(null);
		this.setLayout(new AfAnyWhere());

		JLabel label = new JLabel("�ÿ͵���Ϣ");
		label.setFont(Constant.FONT2);
		this.add(label, new AfMargin(10, -1, -1, -1));
		this.add(new JLabel("�˺ţ�" + operator.getId()), new AfMargin(50, 20, -1, -1));
		this.add(new JLabel("�ǳƣ�" + operator.getVisitor_name()), new AfMargin(75, 20, -1, -1));
		this.add(new JLabel("�Ա�" + operator.getVisitor_sex()), new AfMargin(100, 20, -1, -1));
		this.add(new JLabel("���գ�" + operator.getVisitor_birthday()), new AfMargin(125, 20, -1, -1));
		this.add(new JLabel("΢������" + GlobalVar.weibo_num1), new AfMargin(150, 20, -1, -1));
		this.add(new JLabel("��ע����" + GlobalVar.attention_num), new AfMargin(175, 20, -1, -1));
		this.add(new JLabel("��˿����" + GlobalVar.fans_num), new AfMargin(200, 20, -1, -1));
	}
}