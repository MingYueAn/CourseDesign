package view_visitor;

import java.applet.Applet;
import java.awt.Checkbox;
import java.awt.CheckboxGroup;
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.net.URL;

import javax.sound.sampled.SourceDataLine;
import javax.swing.DefaultListCellRenderer;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;

import control.Update;
import data.Constant;
import data.GlobalVar;
import data.Refresh;
import model.Visitor;
import tools.AfAnyWhere;
import tools.AfMargin;
import tools.BackgroundPanel;
import tools.ChooserDate;
import tools.PaintButton;

/**
 * @Author ����
 * @Description ˵����������Ϣ��ʾ���
 * @Date ʱ�䣺2020-11-20
 */
@SuppressWarnings("serial")
public class Panel1_PersonalInformation extends BackgroundPanel {

	// ��ǩ����ע������˿����΢������
	private JLabel jLabel1, jLabel2, jLabel3;
	// ��ǩ���ǳƣ�
	private JLabel name;
	// ͷ��ť
	private PaintButton imageButton;
	SourceDataLine sourceDataLine;// ����豸

	/**
	 * @Description ˵�������췽��
	 */
	public Panel1_PersonalInformation() {
		super(new ImageIcon("images\\����_1.jpg").getImage());
		this.setLayout(new AfAnyWhere());
		init();
		addListener();
	}

	/**
	 * @Description ˵������ʼ��
	 */
	private void init() {
		// �û�ͷ��
		imageButton = new PaintButton(Constant.HEADIMAGE, 90, 90);
		// �û��ǳ�
		name = new JLabel(GlobalVar.login_visitor.getVisitor_name(), JLabel.CENTER);
		name.setFont(new Font("����", 1, 20));
		// ��ע����˿��΢��
		Font font = new Font("����", 1, 20);
		jLabel1 = new JLabel("΢����" + Integer.toString(GlobalVar.weibo_num1), JLabel.CENTER);
		jLabel1.setFont(font);
		jLabel1.setForeground(Color.white);
		jLabel2 = new JLabel("��ע��" + Integer.toString(GlobalVar.attention_num), JLabel.CENTER);
		jLabel2.setFont(font);
		jLabel2.setForeground(Color.white);
		jLabel3 = new JLabel("��˿��" + Integer.toString(GlobalVar.fans_num), JLabel.CENTER);
		jLabel3.setFont(font);
		jLabel3.setForeground(Color.white);

		// �����б�����Ĭ��ѡ����
		GlobalVar.MUSIC_LIST.setSelectedIndex(0);
		// �����б�����ѡ��ģʽ��������ģʽ: ֻ�ܵ�ѡ���ɼ����ѡ����������ѡ
		GlobalVar.MUSIC_LIST.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		// �����б���ɫ�ı���¼�����
		GlobalVar.MUSIC_LIST.setCellRenderer(new BaseListRenderer());
		// ���б�����������
		JScrollPane jsp = new JScrollPane(GlobalVar.MUSIC_LIST);
		// ��С
		jsp.setPreferredSize(new Dimension(300, 70));
		GlobalVar.MUSIC_LABEL_NAME.setFont(Constant.FONT2);
		GlobalVar.MUSIC_LABEL_NAME.setForeground(Color.yellow);
		this.add(GlobalVar.MUSIC_LABEL_NAME, new AfMargin(-1, 0, 70, -1));
		this.add(jsp, AfMargin.BOTTOM_CENTER);

		// panel1������
		this.add(imageButton, new AfMargin(10, -1, -1, -1));
		this.add(name, new AfMargin(100, -1, -1, -1));
		this.add(jLabel1, new AfMargin(180, 0, -1, -1));
		this.add(jLabel2, new AfMargin(180, -1, -1, -1));
		this.add(jLabel3, new AfMargin(180, -1, -1, 0));
	}

	/**
	 * @Description ˵�����¼�����
	 */
	private void addListener() {
		// ͷ��ť
		imageButton.addActionListener((e) -> {
			ShowPersonal dialog = new ShowPersonal(GlobalVar.login_visitor, (JFrame) this.getRootPane().getParent(), true);
			dialog.setVisible(true);
		});
		GlobalVar.MUSIC_LIST.addMouseListener(new MouseAdapter() {
			// �����
			public void mouseClicked(MouseEvent e) {
				// ����ʱ����
				if (e.getClickCount() == 2) {
					// ��ȡ��ѡ�е�ѡ������
					int indices = GlobalVar.MUSIC_LIST.getSelectedIndex();
					GlobalVar.MUSIC[indices].setId(String.valueOf(indices));
					GlobalVar.MUSIC_FILE_NAME = new String(GlobalVar.MUSIC[indices].getName());
					GlobalVar.MUSIC_FILE_PATH = new String(GlobalVar.MUSIC[indices].getPath());
					GlobalVar.MUSIC_FILE_TIME = new Long(GlobalVar.MUSIC[indices].getTime());
					GlobalVar.MUSIC_LABEL_NAME.setText("���ڲ��ţ�" + GlobalVar.MUSIC_FILE_NAME);
					try {
						if (GlobalVar.music != null)
							GlobalVar.music.stop();
						URL url = new URL("file:" + GlobalVar.MUSIC_FILE_PATH);// ������Դ��λ
						GlobalVar.music = Applet.newAudioClip(url);
						GlobalVar.music.play();
					} catch (Exception e2) {
						// TODO: handle exception
					}
				}
			}
		});
	}
}

/**
 * @Author ����
 * @Description ˵������ʾ������Ϣ
 * @Date ʱ�䣺2020-12-3
 */
@SuppressWarnings("serial")
class ShowPersonal extends JDialog {
	private JButton okButton = new JButton("�༭");
	private JButton cancelButton = new JButton("ȡ��");

	ShowPersonal(Visitor visitor, JFrame parent, boolean modal) {
		super(parent, modal);
		this.setTitle("������Ϣ��ʾ");
		this.setSize(300, 450);
		this.setResizable(false);
		this.setLocationRelativeTo(null);
		this.setLayout(new AfAnyWhere());

		Font font = new Font("΢���ź�", 0, 20);
		JLabel la1, la2, la3, la4;
		la1 = new JLabel();
		la1.setText("ͷ��");
		la1.setIcon(new ImageIcon(Constant.HEADIMAGE));
		la1.setHorizontalTextPosition(10);
		la2 = new JLabel("�ǳƣ�" + visitor.getVisitor_name());
		la3 = new JLabel("�Ա�" + visitor.getVisitor_sex());
		la4 = new JLabel("���գ�" + visitor.getVisitor_birthday());
		la1.setFont(font);
		la2.setFont(font);
		la3.setFont(font);
		la4.setFont(font);
		this.add(la1, new AfMargin(10, 20, -1, -1));
		this.add(la2, new AfMargin(150, 20, -1, -1));
		this.add(la3, new AfMargin(200, 20, -1, -1));
		this.add(la4, new AfMargin(250, 20, -1, -1));

		okButton.setFont(new Font("΢���ź�", 0, 16));
		cancelButton.setFont(new Font("΢���ź�", 0, 16));
		this.add(okButton, new AfMargin(-1, 50, 30, -1));
		this.add(cancelButton, new AfMargin(-1, -1, 30, 50));
		/* �¼����� */
		okButton.addActionListener(Event -> {
			EditPersonal dialog = new EditPersonal(visitor, parent, true);
			dialog.setVisible(true);
			this.setVisible(false);
		});
		cancelButton.addActionListener(Event -> {
			this.setVisible(false);
		});
	}
}

/**
 * @Author ����
 * @Description ˵�����༭������Ϣ
 * @Date ʱ�䣺2020-12-3
 */
@SuppressWarnings("serial")
class EditPersonal extends JDialog {
//	private JButton editImage;
	private JTextField editName;
	private Checkbox editSex1;
	private Checkbox editSex2;
	private ChooserDate editBirthday = new ChooserDate();

	private JButton okButton = new JButton("ȷ��");
	private JButton cancelButton = new JButton("ȡ��");

	EditPersonal(Visitor visitor, JFrame parent, boolean modal) {
		super(parent, modal);
		this.setTitle("������Ϣ�༭");
		this.setSize(300, 450);
		this.setResizable(false);
		this.setLocationRelativeTo(null);
		this.setLayout(new AfAnyWhere());

		JLabel la1 = new JLabel(), la2 = new JLabel("�ǳƣ�"), la3 = new JLabel("�Ա�"), la4 = new JLabel("���գ�");

		// ��������
		Font font = new Font("΢���ź�", 0, 20);
		la1.setFont(font);
		la2.setFont(font);
		la3.setFont(font);
		la4.setFont(font);
		// ���ñ�ǩλ��
		this.add(la1, new AfMargin(10, 20, -1, -1));
		this.add(la2, new AfMargin(150, 20, -1, -1));
		this.add(la3, new AfMargin(200, 20, -1, -1));
		this.add(la4, new AfMargin(250, 20, -1, -1));

		la1.setText("ͷ��");
		la1.setIcon(new ImageIcon(Constant.HEADIMAGE));
		la1.setHorizontalTextPosition(10);

		editName = new JTextField(visitor.getVisitor_name(), 10);
		editName.setFont(new Font("΢���ź�", 0, 15));
		this.add(editName, new AfMargin(150, 100, -1, -1));

		CheckboxGroup group = new CheckboxGroup();
		editSex1 = new Checkbox("Ů", false, group);
		editSex2 = new Checkbox("��", false, group);
		if (visitor.getVisitor_sex().equals("Ů")) {
			editSex1.setState(true);
		}
		if (visitor.getVisitor_sex().equals("��")) {
			editSex2.setState(true);
		}
		editSex1.setFont(new Font("΢���ź�", 0, 18));
		editSex2.setFont(new Font("΢���ź�", 0, 18));
		this.add(editSex1, new AfMargin(200, 100, -1, -1));
		this.add(editSex2, new AfMargin(200, 150, -1, -1));

		this.add(editBirthday, new AfMargin(250, 100, -1, -1));
		// ȷ����ȡ����ť
		okButton.setFont(new Font("΢���ź�", 0, 16));
		cancelButton.setFont(new Font("΢���ź�", 0, 16));
		this.add(okButton, new AfMargin(-1, 50, 30, -1));
		this.add(cancelButton, new AfMargin(-1, -1, 30, 50));
		/* �¼����� */
		okButton.addActionListener(Event -> {
			visitor.setVisitor_name(editName.getText());
			if (editSex1.getState()) {
				visitor.setVisitor_sex("Ů");
			} else if (editSex2.getState()) {
				visitor.setVisitor_sex("��");
			}
			visitor.setVisitor_birthday(editBirthday.toString());
			// ���²���
			new Update().update(visitor);
			this.setVisible(false);
			parent.setVisible(false);
			new Refresh();
		});
		cancelButton.addActionListener(Event -> {
			this.setVisible(false);
		});
	}
}

@SuppressWarnings("serial")
class BaseListRenderer extends DefaultListCellRenderer {
	private int hoverIndex = -1;

	@SuppressWarnings("rawtypes")
	public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
		super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
		setText(GlobalVar.MUSIC[index].getLength() + "   " + GlobalVar.MUSIC[index].getName());

		if (isSelected) {
			setBackground(new Color(26, 150, 202));
		} else {
			setBackground(index == hoverIndex ? new Color(248, 191, 80) : new Color(203, 232, 240));
		}

		list.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				list.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
			}

			@Override
			public void mouseExited(MouseEvent e) {
				list.setCursor(Cursor.getDefaultCursor());
			}
		});

		list.addMouseMotionListener(new MouseAdapter() {
			@Override
			public void mouseMoved(MouseEvent e) {
				int index = list.locationToIndex(e.getPoint());
				setHoverIndex(list.getCellBounds(index, index).contains(e.getPoint()) ? index : -1);
			}

			private void setHoverIndex(int index) {
				if (hoverIndex == index)
					return;
				hoverIndex = index;
				list.repaint();
			}
		});
		return this;
	}
}