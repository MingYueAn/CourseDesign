package view_visitor;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JTextField;

import data.GlobalVar;
import tools.AfAnyWhere;
import tools.AfMargin;
import tools.BackgroundPanel;

/**
 * @Author ����
 * @Description ˵�������ֲ��ſؼ����
 * @Date ʱ�䣺2020-11-20
 */
@SuppressWarnings("serial")
public class Panel4_PlaybackControls extends BackgroundPanel {

	private JTextField textField;
	boolean loop = false;

	public Panel4_PlaybackControls() {
		super(new ImageIcon("images\\����_4.jpg").getImage());
		this.setLayout(new AfAnyWhere());
		init();
	}

	/**
	 * ���������ʼ��
	 */
	public void init() {

		textField = new JTextField();
		textField.setColumns(20);
		textField.setText("��ӭʹ�ñ�������");

		JButton playButton = new JButton("\u64AD\u653E");
		playButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String flag = "���ڲ��ţ�" + GlobalVar.MUSIC_FILE_NAME;
				if (GlobalVar.music == null) {
					flag = "��ѡ�񲥷ŵ�����";
					textField.setText(flag);
					return;
				}
				GlobalVar.music.play();
				textField.setText(flag);
			}
		});

		JButton stopbtn = new JButton("\u6682\u505C");
		stopbtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				GlobalVar.music.stop();
				String flag = "ֹͣ��������:" + GlobalVar.MUSIC_FILE_NAME;
				textField.setText(flag);
			}
		});

		JButton againbtn = new JButton("\u5FAA\u73AF");
		againbtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				loop = !loop;
				String flag = "";
				;
				if (loop) {
					GlobalVar.music.play();
					GlobalVar.music.loop();// ѭ������
					flag = "ѭ������:" + GlobalVar.MUSIC_FILE_NAME;
				} else {
					GlobalVar.music.play();
					flag = "˳�򲥷�" + GlobalVar.MUSIC_FILE_NAME;
				}
				textField.setText(flag);
			}
		});

		this.add(textField, AfMargin.TOP_CENTER);
		this.add(playButton, AfMargin.CENTER);
		this.add(stopbtn, new AfMargin(-1, 50, -1, -1));
		this.add(againbtn, new AfMargin(-1, -1, -1, 50));
	}
}
