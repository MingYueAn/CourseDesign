package tools;

import java.io.File;

import javax.swing.Icon;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileView;

/**
 * @Author 作者
 * @Description 说明：定制文件选择器——为文件选择器显示的每个文件提供特定的图标和文件描述
 * @Date 时间：2020-11-20
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
