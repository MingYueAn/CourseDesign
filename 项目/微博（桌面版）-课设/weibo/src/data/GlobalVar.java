package data;

import java.applet.AudioClip;
import javax.swing.JLabel;
import javax.swing.JList;

import model.Music;
import model.Personal;
import model.Visitor;

/**
 * @Author 作者
 * @Description 说明：全局变量
 * @Date 时间：2020-11-28
 */
public class GlobalVar {
	public static Personal login_personal = null;// 当前登录个人
	public static Visitor login_visitor = null;// 当前登录访客
	public static int weibo_num1 = 0;// 自己写的微博数
	public static int weibo_num2 = 0;// 总共可见的微博数
	public static int attention_num = 0;// 关注数
	public static int fans_num = 0;// 粉丝数
//	音乐播放列表
	public static JList<Music> MUSIC_LIST = new JList<Music>();
	public static Music[] MUSIC = new Music[] {};
//	音乐文件名称
	public static String MUSIC_FILE_NAME = new String("请选择音乐文件");
//	音乐文件路径
	public static String MUSIC_FILE_PATH = new String();
//	音乐时长
	public static long MUSIC_FILE_TIME = 0;
//	音乐标签
	public static JLabel MUSIC_LABEL_NAME = new JLabel("正在播放：" + MUSIC_FILE_NAME);
//	声音音频剪辑对象
	public static AudioClip music;
}
