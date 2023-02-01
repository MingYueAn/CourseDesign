package view_visitor;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.Map;

import javax.sound.sampled.AudioFileFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.SourceDataLine;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;

import control.Insert;
import data.Constant;
import data.GlobalVar;
import model.Music;
import tools.FileIconView;

/**
 * @Author 作者
 * @Description 说明：设置菜单栏
 * @Date 时间：2020-12-4
 */
@SuppressWarnings("serial")
public class SetupJMenuBar extends JMenuBar {
	public SourceDataLine sourceDataLine;// 输出设备
	// 其他变量
	public static String oldValue;// 存放编辑区原来的内容，用于比较文本是否有改动
	private boolean isNewFile = true; // 是否新文件(未保存过的)
	private File currentFile;// 当前文件名
	private String string = Constant.PATH;

	// 菜单+菜单项
	private JMenu fileMenu = new JMenu("文件");
	private JMenuItem fileMenu_OpenMP3 = new JMenuItem("打开音乐");
	private JMenuItem fileMenu_OpenMP3List = new JMenuItem("打开文件夹");
	private JMenuItem fileMenu_OpenTXT = new JMenuItem("打开文本");
	private JMenuItem fileMenu_SaveTXT = new JMenuItem("保存文本");
	private JMenuItem fileMenu_SaveTXTAs = new JMenuItem("另存为...");
	private JMenu helpMenu = new JMenu("帮助");
	private JMenuItem helpMenu_About = new JMenuItem("关于");
	private JMenuItem helpMenu_Con = new JMenuItem("帮助内容");

	SetupJMenuBar() {
		// 文件（打开音乐、打开文件夹 打开文本、保存文本、另存为）
		fileMenu.add(fileMenu_OpenMP3);
		fileMenu.add(fileMenu_OpenMP3List);
		fileMenu.addSeparator();
		fileMenu.add(fileMenu_OpenTXT);
		fileMenu.add(fileMenu_SaveTXT);
		fileMenu.add(fileMenu_SaveTXTAs);
		// 帮助（关于、帮助内容）
		helpMenu.add(helpMenu_About);
		helpMenu.add(helpMenu_Con);
		this.add(fileMenu);// 添加菜单
		this.add(helpMenu);// 添加帮助
		// 事件监听
		addListener();
	}

	/**
	 * 事件监听
	 */
	public void addListener() {
		fileMenu_OpenMP3.addActionListener((e) -> OpenMusicFile()); // 打开音乐
		fileMenu_OpenMP3List.addActionListener((e) -> OpenFolder()); // 打开文件夹
		fileMenu_OpenTXT.addActionListener((e) -> OpenTextFile()); // 打开文本
		fileMenu_SaveTXT.addActionListener((e) -> SaveTextFile()); // 保存文本
		fileMenu_SaveTXTAs.addActionListener((e) -> SaveTextFileAs()); // 另存为
		helpMenu_About.addActionListener((e) -> About()); // 关于
		helpMenu_Con.addActionListener((e) -> HelpContent()); // 帮助内容
	}

	/**
	 * @Description 说明：帮助，帮助内容
	 */
	private Object HelpContent() {
		// TODO 自动生成的方法存根
		Panel2_EditorialPublished.editArea.requestFocus();
		JOptionPane.showMessageDialog(this, "路漫漫其修远兮，吾将上下而求索。", "帮助主题", JOptionPane.INFORMATION_MESSAGE);
		return null;
	}

	/**
	 * @Description 说明：帮助，关于
	 */
	private Object About() {
		// TODO 自动生成的方法存根
		Panel2_EditorialPublished.editArea.requestFocus();
		JOptionPane.showMessageDialog(this,
				"&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&\n" + " 编写者：谢晓艳\n" + "\n" + "&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&\n", "桌面微博",
				JOptionPane.INFORMATION_MESSAGE);
		return null;
	}

	/**
	 * @Description 说明：文件，另存为
	 */
	public Object SaveTextFileAs() {
		// TODO 自动生成的方法存根
		Panel2_EditorialPublished.editArea.requestFocus();
		// 创建文件选择器
		JFileChooser fileChooser = new JFileChooser();
		// 定义按钮
		fileChooser.setApproveButtonText("另存为");
		// 定义文件选择框标题
		fileChooser.setDialogTitle("另存为。。");
		// 设置默认目录
		fileChooser.setCurrentDirectory(new File(string));
		// 设置默认文件名
		fileChooser.setSelectedFile(new File(".txt"));
		// 文件过滤器
		fileChooser.setFileFilter(new FileNameExtensionFilter("文本文件", "txt"));
		// 禁用all files过滤器
		fileChooser.setAcceptAllFileFilterUsed(false);
		// 为文件选择器显示的每个文件提供特定的图标和文件描述
		fileChooser.setFileView(new FileIconView(fileChooser.getFileFilter(), new ImageIcon("images\\txt.png")));
		// 显示打开对话框
		int result = fileChooser.showOpenDialog(this.getComponent());

		result = fileChooser.showSaveDialog(this.getComponent());// 显示保存对话框
		if (result == JFileChooser.APPROVE_OPTION) {
			File saveFileName = fileChooser.getSelectedFile();
			if (saveFileName == null || saveFileName.getName().equals(".txt")) {
				JOptionPane.showMessageDialog(this, "不合法的文件名", "不合法的文件名", JOptionPane.ERROR_MESSAGE);
			} else {
				System.out.println("另存为的文件名称为：" + saveFileName.getName());
				try {
					OutputStreamWriter writer = new OutputStreamWriter(new FileOutputStream(saveFileName), "UTF-8");
					BufferedWriter bufferedWriter = new BufferedWriter(writer);
					bufferedWriter.write(Panel2_EditorialPublished.editArea.getText(), 0, Panel2_EditorialPublished.editArea.getText().length());
					bufferedWriter.flush();
					writer.close();
					oldValue = Panel2_EditorialPublished.editArea.getText();
				} catch (IOException ioException) {
				}
			}
		} else if (result == JFileChooser.CANCEL_OPTION) {
			System.out.println("没有保存任何文件");
		}
		return null;
	}

	/**
	 * @Description 说明：文件，保存
	 */
	private Object SaveTextFile() {
		// TODO 自动生成的方法存根
		Panel2_EditorialPublished.editArea.requestFocus();
		if (isNewFile) {
			// 创建文件选择器
			JFileChooser fileChooser = new JFileChooser();
			// 定义按钮
			fileChooser.setApproveButtonText("保存");
			// 定义文件选择框标题
			fileChooser.setDialogTitle("保存文本");
			// 设置默认目录
			fileChooser.setCurrentDirectory(new File(string));
			// 设置默认文件名
			fileChooser.setSelectedFile(new File(".txt"));
			// 文件过滤器
			fileChooser.setFileFilter(new FileNameExtensionFilter("文本文件", "txt"));
			// 禁用all files过滤器
			fileChooser.setAcceptAllFileFilterUsed(false);
			// 为文件选择器显示的每个文件提供特定的图标和文件描述
			fileChooser.setFileView(new FileIconView(fileChooser.getFileFilter(), new ImageIcon("images\\txt.png")));
			// 显示打开对话框
			int result = fileChooser.showOpenDialog(this.getComponent());

			if (result == JFileChooser.APPROVE_OPTION) {
				File saveFileName = fileChooser.getSelectedFile();
				if (saveFileName == null || saveFileName.getName().equals(".txt")) {
					JOptionPane.showMessageDialog(this, "不合法的文件名", "不合法的文件名", JOptionPane.ERROR_MESSAGE);
				} else {
					System.out.println("保存的文件名称为：" + saveFileName.getName());
					try {
						OutputStreamWriter writer = new OutputStreamWriter(new FileOutputStream(saveFileName), "UTF-8");
						BufferedWriter bufferedWriter = new BufferedWriter(writer);
						bufferedWriter.write(Panel2_EditorialPublished.editArea.getText(), 0, Panel2_EditorialPublished.editArea.getText().length());
						bufferedWriter.flush();
						bufferedWriter.close();
						isNewFile = false;
						currentFile = saveFileName;
						oldValue = Panel2_EditorialPublished.editArea.getText();
					} catch (Exception e) {
						System.out.println("读取文件内容出错");
						e.printStackTrace();
					}
				}
			} else if (result == JFileChooser.CANCEL_OPTION) {
				System.out.println("没有保存任何文件");
			}
		} else {
			System.out.println("保存的文件名称为：" + currentFile.getName());
			try {
				OutputStreamWriter writer = new OutputStreamWriter(new FileOutputStream(currentFile), "UTF-8");
				BufferedWriter bufferedWriter = new BufferedWriter(writer);
				bufferedWriter.write(Panel2_EditorialPublished.editArea.getText(), 0, Panel2_EditorialPublished.editArea.getText().length());
				bufferedWriter.flush();
				writer.close();
			} catch (Exception e) {
				System.out.println("读取文件内容出错");
				e.printStackTrace();
			}
		}
		return null;
	}

	/**
	 * @Description 说明：打开文本功能实现
	 */
	private void open() {
		// 创建文件选择器
		JFileChooser fileChooser = new JFileChooser();
		// 定义文件选择框标题
		fileChooser.setDialogTitle("打开文本");
		// 设置默认目录
		fileChooser.setCurrentDirectory(new File(string));
		// 文件过滤器
		fileChooser.setFileFilter(new FileNameExtensionFilter("文本文件", "txt"));
		// 禁用all files过滤器
		fileChooser.setAcceptAllFileFilterUsed(false);
		// 为文件选择器显示的每个文件提供特定的图标和文件描述
		fileChooser.setFileView(new FileIconView(fileChooser.getFileFilter(), new ImageIcon("images\\txt.png")));
		// 显示打开对话框
		int result = fileChooser.showOpenDialog(this.getComponent());

		if (result == JFileChooser.APPROVE_OPTION) {
			File fileName = fileChooser.getSelectedFile();
			if (fileName == null || fileName.getName().equals(".txt")) {
				JOptionPane.showMessageDialog(this, "不合法的文件名", "不合法的文件名", JOptionPane.ERROR_MESSAGE);
			} else {
				System.out.println("打开的文件名称为：" + fileName.getName());
				try {
					// 判断文件是否存在
					if (fileName.isFile() && fileName.exists()) {
						InputStreamReader read = new InputStreamReader(new FileInputStream(fileName), "UTF-8");
						BufferedReader bufferedReader = new BufferedReader(read);
						String lineTxt = null;
						while ((lineTxt = bufferedReader.readLine()) != null) {
							Panel2_EditorialPublished.editArea.append(lineTxt + "\n");
						}
						read.close();
						isNewFile = false;
						currentFile = fileName;
						oldValue = Panel2_EditorialPublished.editArea.getText();
					} else {
						System.out.println("找不到指定的文件");
					}
				} catch (Exception e) {
					System.out.println("读取文件内容出错");
					e.printStackTrace();
				}
			}
		} else if (result == JFileChooser.CANCEL_OPTION) {
			System.out.println("没有打开任何文件");
		}
	}

	/**
	 * @Description 说明：文件，打开文本
	 */
	private Object OpenTextFile() {
		// TODO 自动生成的方法存根
		Panel2_EditorialPublished.editArea.requestFocus();
		String currentValue = Panel2_EditorialPublished.editArea.getText();
		boolean isTextChange = (currentValue.equals(oldValue)) ? false : true;
		// 判断是否改变文本
		if (isTextChange && !Panel2_EditorialPublished.editArea.getText().equals("")) {
			// 对话框（询问是否需要保存）
			int saveChoose = JOptionPane.showConfirmDialog(this, "您的文件尚未保存，是否保存？", "提示", JOptionPane.YES_NO_OPTION);
			if (saveChoose == JOptionPane.NO_OPTION) {
				open();// 打开文本功能
			} else if (saveChoose == JOptionPane.YES_OPTION) {
				SaveTextFileAs();// 另存为功能
			}
		} else {
			open();// 打开文本功能
		}
		return null;
	}

	/**
	 * @Description 说明：文件，打开文件夹
	 */
	private Object OpenFolder() {
		// TODO 自动生成的方法存根
		int result = 0;
		// 创建文件选择器
		JFileChooser fileChooser = new JFileChooser();
		// 定义文件选择框标题
		fileChooser.setDialogTitle("打开文件夹");
		// 设置默认目录
		fileChooser.setCurrentDirectory(new File(string));
		// 只打开目录
		fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		// 文件过滤器
		fileChooser.setFileFilter(new FileNameExtensionFilter("音乐文件", "mp3"));
		// 禁用all files过滤器
		fileChooser.setAcceptAllFileFilterUsed(false);
		// 为文件选择器显示的每个文件提供特定的图标和文件描述
		fileChooser.setFileView(new FileIconView(fileChooser.getFileFilter(), new ImageIcon("images\\mp3.png")));
		// 显示打开对话框
		result = fileChooser.showOpenDialog(this.getComponent());

		if (result == JFileChooser.APPROVE_OPTION) {

			String path = fileChooser.getSelectedFile().getPath();
			File file = new File(path);
			String[] filelist = file.list();
			for (int i = 0; i < filelist.length; i++) {
				// 清空音乐列表
				GlobalVar.MUSIC_LIST.removeAll();
				// 判断文件名称尾部
				if (filelist[i].endsWith(".mp3") || filelist[i].endsWith(".wav")) {
					// 获取音频时长
					int s = 0;
					long total = 0;
					try {
						File f = new File(path, filelist[i]);
						AudioFileFormat aff = AudioSystem.getAudioFileFormat(f);
						@SuppressWarnings("rawtypes")
						Map props = aff.properties();
						if (props.containsKey("duration")) {
							total = (long) Math.round((((Long) props.get("duration")).longValue()) / 1000);
						}
						s = (int) total / 1000;
//						System.out.println("打开的文件名称为："+filelist[i]);
//						System.out.println("打开的文件夹路径为：" + fileChooser.getSelectedFile().getPath());
//						System.out.println("音频时长：" + s / 60 + ":" + s % 60);
					} catch (Exception e) {
						e.printStackTrace();
					}
					// 添加列表
					Music music = new Music();
					music.setName(filelist[i]);
					music.setPath(path + "\\" + filelist[i]);
					music.setLength(s / 60 + ":" + s % 60);
					GlobalVar.MUSIC = Insert.insert(GlobalVar.MUSIC, music);
					GlobalVar.MUSIC_LIST.setListData(GlobalVar.MUSIC);
				}
			}
		} else if (result == JFileChooser.CANCEL_OPTION) {
			System.out.println("没有打开任何文件夹");
		}
		return null;
	}

	/**
	 * @Description 说明：文件，打开音乐
	 */
	private Object OpenMusicFile() {
		// TODO 自动生成的方法存根
		File file = null;
		int result = 0;
		// 创建文件选择器
		JFileChooser fileChooser = new JFileChooser();
		// 定义文件选择框标题
		fileChooser.setDialogTitle("打开音乐");
		// 设置默认目录
		fileChooser.setCurrentDirectory(new File(string));
		// 文件过滤器
		fileChooser.setFileFilter(new FileNameExtensionFilter("音乐文件", "mp3"));
		// 禁用all files过滤器
		fileChooser.setAcceptAllFileFilterUsed(false);
		// 为文件选择器显示的每个文件提供特定的图标和文件描述
		fileChooser.setFileView(new FileIconView(fileChooser.getFileFilter(), new ImageIcon("images\\mp3.png")));
		// 显示打开对话框
		result = fileChooser.showOpenDialog(this.getComponent());

		if (result == JFileChooser.APPROVE_OPTION) {
			file = fileChooser.getSelectedFile();
			// 获取音乐文件名
			String filename = file.getName();
			// 清空音乐列表
			GlobalVar.MUSIC_LIST.removeAll();
			// 判断文件名称尾部
			if (filename.endsWith(".mp3") || filename.endsWith(".wav")) {
				// 获取音频时长
				int s = 0;
				long total = 0;
				try {
					AudioFileFormat aff = AudioSystem.getAudioFileFormat(file);
					@SuppressWarnings("rawtypes")
					Map props = aff.properties();
					if (props.containsKey("duration")) {
						total = (long) Math.round((((Long) props.get("duration")).longValue()) / 1000);
					}
					s = (int) total / 1000;
//					System.out.println("打开的文件名称为：" + file.getName());
//					System.out.println("打开的文件的绝对路径为：" + file.getAbsolutePath());
//					System.out.println("音频时长：" + s / 60 + ":" + s % 60);
				} catch (Exception e) {
					e.printStackTrace();
				}
				// 添加列表
				Music music = new Music();
				music.setName(filename);
				music.setPath(file.getAbsolutePath());
				music.setLength(s / 60 + ":" + s % 60);
				music.setTime(total);
				GlobalVar.MUSIC = Insert.insert(GlobalVar.MUSIC, music);
				GlobalVar.MUSIC_LIST.setListData(GlobalVar.MUSIC);
			}
		} else if (result == JFileChooser.CANCEL_OPTION) {
			System.out.println("没有打开任何文件");
		}
		return null;
	}

}
