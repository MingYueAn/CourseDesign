package data;

import java.applet.AudioClip;
import javax.swing.JLabel;
import javax.swing.JList;

import model.Music;
import model.Personal;
import model.Visitor;

/**
 * @Author ����
 * @Description ˵����ȫ�ֱ���
 * @Date ʱ�䣺2020-11-28
 */
public class GlobalVar {
	public static Personal login_personal = null;// ��ǰ��¼����
	public static Visitor login_visitor = null;// ��ǰ��¼�ÿ�
	public static int weibo_num1 = 0;// �Լ�д��΢����
	public static int weibo_num2 = 0;// �ܹ��ɼ���΢����
	public static int attention_num = 0;// ��ע��
	public static int fans_num = 0;// ��˿��
//	���ֲ����б�
	public static JList<Music> MUSIC_LIST = new JList<Music>();
	public static Music[] MUSIC = new Music[] {};
//	�����ļ�����
	public static String MUSIC_FILE_NAME = new String("��ѡ�������ļ�");
//	�����ļ�·��
	public static String MUSIC_FILE_PATH = new String();
//	����ʱ��
	public static long MUSIC_FILE_TIME = 0;
//	���ֱ�ǩ
	public static JLabel MUSIC_LABEL_NAME = new JLabel("���ڲ��ţ�" + MUSIC_FILE_NAME);
//	������Ƶ��������
	public static AudioClip music;
}
