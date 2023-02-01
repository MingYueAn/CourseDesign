package tools;

import java.awt.*;
import javax.swing.JPanel;

/**
 * @Author 作者
 * @Description 说明：有背景图片的Panel类
 * @Date 时间：2020-11-20
 */
@SuppressWarnings("serial")
public class BackgroundPanel extends JPanel {

	private Image image = null;

	public BackgroundPanel() {
	}

	/**
	 * 背景
	 * 
	 * @param image
	 */
	public BackgroundPanel(Image image) {
		this.image = image;
	}

	// 固定背景图片，允许这个JPanel可以在图片上添加其他组件
	protected void paintComponent(Graphics g) {
		g.drawImage(image, 0, 0, this.getWidth(), this.getHeight(), this);
	}
}