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
 * @Author ���ߣ�����19102 л���� 190611201
 * @Description ˵�����޸�
 * @Date ʱ�䣺2020-12-17
 */
@SuppressWarnings("serial")
public class PanelUpdate extends BackgroundPanel {
	// ��ť
	private JButton updataButton = new JButton("�༭��Ϣ"), showButton = new JButton("�鿴��Ϣ");
	// �ı��������˺ţ�
	private JTextField idTextField = new JTextField();

	public PanelUpdate() {
		super(new ImageIcon("images\\����_2.jpg").getImage());
		this.setLayout(new AfAnyWhere());
	}

	/**
	 * @Description ˵�����޸�
	 */
	public void init(int w, int h) {
		JLabel Title = new JLabel("�޸ķÿ�");
		Title.setFont(Constant.FONT1);
		this.add(Title, AfMargin.TOP_CENTER);

		JLabel label = new JLabel("������Ҫ�޸ĵķÿ͵��˺ţ�");
		label.setFont(Constant.FONT2);
		this.add(label, new AfMargin(60, -1, -1, -1));

		idTextField = new JTextField(15);
		idTextField.setToolTipText("�˺�");
		idTextField.setFont(Constant.FONT2);
		this.add(idTextField, new AfMargin(85, -1, -1, -1));

		updataButton.setFont(Constant.FONT2);
		showButton.setFont(Constant.FONT2);
		this.add(updataButton, new AfMargin(-1, 120, 150, -1));
		this.add(showButton, new AfMargin(-1, -1, 150, 120));

		// �޸�
		updataButton.addActionListener((e) -> {
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
				UpdataVisitor dialog = new UpdataVisitor(personal, (JFrame) this.getRootPane().getParent(), true);
				dialog.setVisible(true);
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
 * @Description ˵�����������
 * @Date ʱ�䣺2020-12-17
 */
@SuppressWarnings("serial")
class UpdataVisitor extends JDialog {
	UpdataVisitor(Personal personal, JFrame parent, boolean modal) {
		super(parent, modal);
		this.setTitle("�������");
		this.setSize(200, 250);
		this.setResizable(false);
		this.setLocationRelativeTo(null);
		this.setLayout(new AfAnyWhere());

		JLabel label = new JLabel("�޸ķÿ͵���Ϣ");
		label.setFont(Constant.FONT2);
		this.add(label, new AfMargin(10, -1, -1, -1));
		this.add(new JLabel("�˺ţ�"), new AfMargin(50, 15, -1, -1));
		this.add(new JLabel("���룺"), new AfMargin(80, 15, -1, -1));

		JTextField TextField1 = new JTextField(personal.getId(), 6);
		JTextField TextField2 = new JTextField(personal.getPassword(), 6);

		this.add(TextField1, new AfMargin(45, -1, -1, 15));
		this.add(TextField2, new AfMargin(75, -1, -1, 15));

		JButton button1 = new JButton("ȷ���༭");
		JButton button2 = new JButton("�������");
		button1.setFont(Constant.FONT2);
		button2.setFont(Constant.FONT2);
		this.add(button1, new AfMargin(-1, 10, 50, -1));
		this.add(button2, new AfMargin(-1, -1, 50, 10));
		// �޸�
		button1.addActionListener((e) -> {
			if (StringUtil.isEmpty(TextField1.getText()) || StringUtil.isEmpty(TextField2.getText())) {
				JOptionPane.showMessageDialog(null, "�������Ϊ��");
				return;
			}

			new Delete().delete(personal);
			personal.setId(TextField1.getText());
			personal.setPassword(TextField2.getText());
			personal.setPower(Constant.POWER[0]);
			new Insert().insert(personal);
			new Refresh();
		});
		// ���
		button2.addActionListener((e) -> {
			TextField1.setText("");
			TextField2.setText("");
		});
	}
}