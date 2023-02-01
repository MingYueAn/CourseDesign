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
 * @Author ����
 * @Description ˵�������ò˵���
 * @Date ʱ�䣺2020-12-4
 */
@SuppressWarnings("serial")
public class SetupJMenuBar extends JMenuBar {
	public SourceDataLine sourceDataLine;// ����豸
	// ��������
	public static String oldValue;// ��ű༭��ԭ�������ݣ����ڱȽ��ı��Ƿ��иĶ�
	private boolean isNewFile = true; // �Ƿ����ļ�(δ�������)
	private File currentFile;// ��ǰ�ļ���
	private String string = Constant.PATH;

	// �˵�+�˵���
	private JMenu fileMenu = new JMenu("�ļ�");
	private JMenuItem fileMenu_OpenMP3 = new JMenuItem("������");
	private JMenuItem fileMenu_OpenMP3List = new JMenuItem("���ļ���");
	private JMenuItem fileMenu_OpenTXT = new JMenuItem("���ı�");
	private JMenuItem fileMenu_SaveTXT = new JMenuItem("�����ı�");
	private JMenuItem fileMenu_SaveTXTAs = new JMenuItem("���Ϊ...");
	private JMenu helpMenu = new JMenu("����");
	private JMenuItem helpMenu_About = new JMenuItem("����");
	private JMenuItem helpMenu_Con = new JMenuItem("��������");

	SetupJMenuBar() {
		// �ļ��������֡����ļ��� ���ı��������ı������Ϊ��
		fileMenu.add(fileMenu_OpenMP3);
		fileMenu.add(fileMenu_OpenMP3List);
		fileMenu.addSeparator();
		fileMenu.add(fileMenu_OpenTXT);
		fileMenu.add(fileMenu_SaveTXT);
		fileMenu.add(fileMenu_SaveTXTAs);
		// ���������ڡ��������ݣ�
		helpMenu.add(helpMenu_About);
		helpMenu.add(helpMenu_Con);
		this.add(fileMenu);// ��Ӳ˵�
		this.add(helpMenu);// ��Ӱ���
		// �¼�����
		addListener();
	}

	/**
	 * �¼�����
	 */
	public void addListener() {
		fileMenu_OpenMP3.addActionListener((e) -> OpenMusicFile()); // ������
		fileMenu_OpenMP3List.addActionListener((e) -> OpenFolder()); // ���ļ���
		fileMenu_OpenTXT.addActionListener((e) -> OpenTextFile()); // ���ı�
		fileMenu_SaveTXT.addActionListener((e) -> SaveTextFile()); // �����ı�
		fileMenu_SaveTXTAs.addActionListener((e) -> SaveTextFileAs()); // ���Ϊ
		helpMenu_About.addActionListener((e) -> About()); // ����
		helpMenu_Con.addActionListener((e) -> HelpContent()); // ��������
	}

	/**
	 * @Description ˵������������������
	 */
	private Object HelpContent() {
		// TODO �Զ����ɵķ������
		Panel2_EditorialPublished.editArea.requestFocus();
		JOptionPane.showMessageDialog(this, "·��������Զ�⣬�Ὣ���¶�������", "��������", JOptionPane.INFORMATION_MESSAGE);
		return null;
	}

	/**
	 * @Description ˵��������������
	 */
	private Object About() {
		// TODO �Զ����ɵķ������
		Panel2_EditorialPublished.editArea.requestFocus();
		JOptionPane.showMessageDialog(this,
				"&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&\n" + " ��д�ߣ�л����\n" + "\n" + "&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&\n", "����΢��",
				JOptionPane.INFORMATION_MESSAGE);
		return null;
	}

	/**
	 * @Description ˵�����ļ������Ϊ
	 */
	public Object SaveTextFileAs() {
		// TODO �Զ����ɵķ������
		Panel2_EditorialPublished.editArea.requestFocus();
		// �����ļ�ѡ����
		JFileChooser fileChooser = new JFileChooser();
		// ���尴ť
		fileChooser.setApproveButtonText("���Ϊ");
		// �����ļ�ѡ������
		fileChooser.setDialogTitle("���Ϊ����");
		// ����Ĭ��Ŀ¼
		fileChooser.setCurrentDirectory(new File(string));
		// ����Ĭ���ļ���
		fileChooser.setSelectedFile(new File(".txt"));
		// �ļ�������
		fileChooser.setFileFilter(new FileNameExtensionFilter("�ı��ļ�", "txt"));
		// ����all files������
		fileChooser.setAcceptAllFileFilterUsed(false);
		// Ϊ�ļ�ѡ������ʾ��ÿ���ļ��ṩ�ض���ͼ����ļ�����
		fileChooser.setFileView(new FileIconView(fileChooser.getFileFilter(), new ImageIcon("images\\txt.png")));
		// ��ʾ�򿪶Ի���
		int result = fileChooser.showOpenDialog(this.getComponent());

		result = fileChooser.showSaveDialog(this.getComponent());// ��ʾ����Ի���
		if (result == JFileChooser.APPROVE_OPTION) {
			File saveFileName = fileChooser.getSelectedFile();
			if (saveFileName == null || saveFileName.getName().equals(".txt")) {
				JOptionPane.showMessageDialog(this, "���Ϸ����ļ���", "���Ϸ����ļ���", JOptionPane.ERROR_MESSAGE);
			} else {
				System.out.println("���Ϊ���ļ�����Ϊ��" + saveFileName.getName());
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
			System.out.println("û�б����κ��ļ�");
		}
		return null;
	}

	/**
	 * @Description ˵�����ļ�������
	 */
	private Object SaveTextFile() {
		// TODO �Զ����ɵķ������
		Panel2_EditorialPublished.editArea.requestFocus();
		if (isNewFile) {
			// �����ļ�ѡ����
			JFileChooser fileChooser = new JFileChooser();
			// ���尴ť
			fileChooser.setApproveButtonText("����");
			// �����ļ�ѡ������
			fileChooser.setDialogTitle("�����ı�");
			// ����Ĭ��Ŀ¼
			fileChooser.setCurrentDirectory(new File(string));
			// ����Ĭ���ļ���
			fileChooser.setSelectedFile(new File(".txt"));
			// �ļ�������
			fileChooser.setFileFilter(new FileNameExtensionFilter("�ı��ļ�", "txt"));
			// ����all files������
			fileChooser.setAcceptAllFileFilterUsed(false);
			// Ϊ�ļ�ѡ������ʾ��ÿ���ļ��ṩ�ض���ͼ����ļ�����
			fileChooser.setFileView(new FileIconView(fileChooser.getFileFilter(), new ImageIcon("images\\txt.png")));
			// ��ʾ�򿪶Ի���
			int result = fileChooser.showOpenDialog(this.getComponent());

			if (result == JFileChooser.APPROVE_OPTION) {
				File saveFileName = fileChooser.getSelectedFile();
				if (saveFileName == null || saveFileName.getName().equals(".txt")) {
					JOptionPane.showMessageDialog(this, "���Ϸ����ļ���", "���Ϸ����ļ���", JOptionPane.ERROR_MESSAGE);
				} else {
					System.out.println("������ļ�����Ϊ��" + saveFileName.getName());
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
						System.out.println("��ȡ�ļ����ݳ���");
						e.printStackTrace();
					}
				}
			} else if (result == JFileChooser.CANCEL_OPTION) {
				System.out.println("û�б����κ��ļ�");
			}
		} else {
			System.out.println("������ļ�����Ϊ��" + currentFile.getName());
			try {
				OutputStreamWriter writer = new OutputStreamWriter(new FileOutputStream(currentFile), "UTF-8");
				BufferedWriter bufferedWriter = new BufferedWriter(writer);
				bufferedWriter.write(Panel2_EditorialPublished.editArea.getText(), 0, Panel2_EditorialPublished.editArea.getText().length());
				bufferedWriter.flush();
				writer.close();
			} catch (Exception e) {
				System.out.println("��ȡ�ļ����ݳ���");
				e.printStackTrace();
			}
		}
		return null;
	}

	/**
	 * @Description ˵�������ı�����ʵ��
	 */
	private void open() {
		// �����ļ�ѡ����
		JFileChooser fileChooser = new JFileChooser();
		// �����ļ�ѡ������
		fileChooser.setDialogTitle("���ı�");
		// ����Ĭ��Ŀ¼
		fileChooser.setCurrentDirectory(new File(string));
		// �ļ�������
		fileChooser.setFileFilter(new FileNameExtensionFilter("�ı��ļ�", "txt"));
		// ����all files������
		fileChooser.setAcceptAllFileFilterUsed(false);
		// Ϊ�ļ�ѡ������ʾ��ÿ���ļ��ṩ�ض���ͼ����ļ�����
		fileChooser.setFileView(new FileIconView(fileChooser.getFileFilter(), new ImageIcon("images\\txt.png")));
		// ��ʾ�򿪶Ի���
		int result = fileChooser.showOpenDialog(this.getComponent());

		if (result == JFileChooser.APPROVE_OPTION) {
			File fileName = fileChooser.getSelectedFile();
			if (fileName == null || fileName.getName().equals(".txt")) {
				JOptionPane.showMessageDialog(this, "���Ϸ����ļ���", "���Ϸ����ļ���", JOptionPane.ERROR_MESSAGE);
			} else {
				System.out.println("�򿪵��ļ�����Ϊ��" + fileName.getName());
				try {
					// �ж��ļ��Ƿ����
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
						System.out.println("�Ҳ���ָ�����ļ�");
					}
				} catch (Exception e) {
					System.out.println("��ȡ�ļ����ݳ���");
					e.printStackTrace();
				}
			}
		} else if (result == JFileChooser.CANCEL_OPTION) {
			System.out.println("û�д��κ��ļ�");
		}
	}

	/**
	 * @Description ˵�����ļ������ı�
	 */
	private Object OpenTextFile() {
		// TODO �Զ����ɵķ������
		Panel2_EditorialPublished.editArea.requestFocus();
		String currentValue = Panel2_EditorialPublished.editArea.getText();
		boolean isTextChange = (currentValue.equals(oldValue)) ? false : true;
		// �ж��Ƿ�ı��ı�
		if (isTextChange && !Panel2_EditorialPublished.editArea.getText().equals("")) {
			// �Ի���ѯ���Ƿ���Ҫ���棩
			int saveChoose = JOptionPane.showConfirmDialog(this, "�����ļ���δ���棬�Ƿ񱣴棿", "��ʾ", JOptionPane.YES_NO_OPTION);
			if (saveChoose == JOptionPane.NO_OPTION) {
				open();// ���ı�����
			} else if (saveChoose == JOptionPane.YES_OPTION) {
				SaveTextFileAs();// ���Ϊ����
			}
		} else {
			open();// ���ı�����
		}
		return null;
	}

	/**
	 * @Description ˵�����ļ������ļ���
	 */
	private Object OpenFolder() {
		// TODO �Զ����ɵķ������
		int result = 0;
		// �����ļ�ѡ����
		JFileChooser fileChooser = new JFileChooser();
		// �����ļ�ѡ������
		fileChooser.setDialogTitle("���ļ���");
		// ����Ĭ��Ŀ¼
		fileChooser.setCurrentDirectory(new File(string));
		// ֻ��Ŀ¼
		fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		// �ļ�������
		fileChooser.setFileFilter(new FileNameExtensionFilter("�����ļ�", "mp3"));
		// ����all files������
		fileChooser.setAcceptAllFileFilterUsed(false);
		// Ϊ�ļ�ѡ������ʾ��ÿ���ļ��ṩ�ض���ͼ����ļ�����
		fileChooser.setFileView(new FileIconView(fileChooser.getFileFilter(), new ImageIcon("images\\mp3.png")));
		// ��ʾ�򿪶Ի���
		result = fileChooser.showOpenDialog(this.getComponent());

		if (result == JFileChooser.APPROVE_OPTION) {

			String path = fileChooser.getSelectedFile().getPath();
			File file = new File(path);
			String[] filelist = file.list();
			for (int i = 0; i < filelist.length; i++) {
				// ��������б�
				GlobalVar.MUSIC_LIST.removeAll();
				// �ж��ļ�����β��
				if (filelist[i].endsWith(".mp3") || filelist[i].endsWith(".wav")) {
					// ��ȡ��Ƶʱ��
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
//						System.out.println("�򿪵��ļ�����Ϊ��"+filelist[i]);
//						System.out.println("�򿪵��ļ���·��Ϊ��" + fileChooser.getSelectedFile().getPath());
//						System.out.println("��Ƶʱ����" + s / 60 + ":" + s % 60);
					} catch (Exception e) {
						e.printStackTrace();
					}
					// ����б�
					Music music = new Music();
					music.setName(filelist[i]);
					music.setPath(path + "\\" + filelist[i]);
					music.setLength(s / 60 + ":" + s % 60);
					GlobalVar.MUSIC = Insert.insert(GlobalVar.MUSIC, music);
					GlobalVar.MUSIC_LIST.setListData(GlobalVar.MUSIC);
				}
			}
		} else if (result == JFileChooser.CANCEL_OPTION) {
			System.out.println("û�д��κ��ļ���");
		}
		return null;
	}

	/**
	 * @Description ˵�����ļ���������
	 */
	private Object OpenMusicFile() {
		// TODO �Զ����ɵķ������
		File file = null;
		int result = 0;
		// �����ļ�ѡ����
		JFileChooser fileChooser = new JFileChooser();
		// �����ļ�ѡ������
		fileChooser.setDialogTitle("������");
		// ����Ĭ��Ŀ¼
		fileChooser.setCurrentDirectory(new File(string));
		// �ļ�������
		fileChooser.setFileFilter(new FileNameExtensionFilter("�����ļ�", "mp3"));
		// ����all files������
		fileChooser.setAcceptAllFileFilterUsed(false);
		// Ϊ�ļ�ѡ������ʾ��ÿ���ļ��ṩ�ض���ͼ����ļ�����
		fileChooser.setFileView(new FileIconView(fileChooser.getFileFilter(), new ImageIcon("images\\mp3.png")));
		// ��ʾ�򿪶Ի���
		result = fileChooser.showOpenDialog(this.getComponent());

		if (result == JFileChooser.APPROVE_OPTION) {
			file = fileChooser.getSelectedFile();
			// ��ȡ�����ļ���
			String filename = file.getName();
			// ��������б�
			GlobalVar.MUSIC_LIST.removeAll();
			// �ж��ļ�����β��
			if (filename.endsWith(".mp3") || filename.endsWith(".wav")) {
				// ��ȡ��Ƶʱ��
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
//					System.out.println("�򿪵��ļ�����Ϊ��" + file.getName());
//					System.out.println("�򿪵��ļ��ľ���·��Ϊ��" + file.getAbsolutePath());
//					System.out.println("��Ƶʱ����" + s / 60 + ":" + s % 60);
				} catch (Exception e) {
					e.printStackTrace();
				}
				// ����б�
				Music music = new Music();
				music.setName(filename);
				music.setPath(file.getAbsolutePath());
				music.setLength(s / 60 + ":" + s % 60);
				music.setTime(total);
				GlobalVar.MUSIC = Insert.insert(GlobalVar.MUSIC, music);
				GlobalVar.MUSIC_LIST.setListData(GlobalVar.MUSIC);
			}
		} else if (result == JFileChooser.CANCEL_OPTION) {
			System.out.println("û�д��κ��ļ�");
		}
		return null;
	}

}
