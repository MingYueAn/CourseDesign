package tools;

import java.io.File;

import javax.swing.Icon;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileView;

/**
 * @Author ����
 * @Description ˵���������ļ�ѡ��������Ϊ�ļ�ѡ������ʾ��ÿ���ļ��ṩ�ض���ͼ����ļ�����
 * @Date ʱ�䣺2020-11-20
 */
public class FileIconView extends FileView {
	private FileFilter filter;
	private Icon icon;

	public FileIconView(FileFilter filter, Icon icon) {
		super();
		this.filter = filter;
		this.icon = icon;
	}

	public Icon getIcon(File f) {
		if (!f.isDirectory() && filter.accept(f)) {
			return icon;
		} else {
			return null;
		}

	}

}
