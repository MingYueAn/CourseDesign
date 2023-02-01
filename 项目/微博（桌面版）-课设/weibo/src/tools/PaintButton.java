package tools;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.geom.Ellipse2D;

import javax.swing.ImageIcon;
import javax.swing.JButton;

/**
 * @Author 作者
 * @Description 说明：圆形按钮设置头像
 * @Date 时间：2020-11-20
 */
@SuppressWarnings("serial")
public class PaintButton extends JButton {

//	方法一: 通过 java.awt.Toolkit 工具类来读取本地、网络 或 内存中 的 图片（支持 GIF、JPEG 或 PNG）
//	Image image = Toolkit.getDefaultToolkit().getImage(String filename);
//	Image image = Toolkit.getDefaultToolkit().getImage(URL url);
//	Image image = Toolkit.getDefaultToolkit().createImage(byte[] imageData);
//
//	方法二: 通过 javax.imageio.ImageIO 工具类读取本地、网络 或 内存中 的 图片（BufferedImage 继承自 Image）
//	BufferedImage bufImage = ImageIO.read(File input);
//	BufferedImage bufImage = ImageIO.read(URL input);
//	BufferedImage bufImage = ImageIO.read(InputStream input);

	private ImageIcon image;
	private int w, h;

	/**
	 * @param w
	 * @param h
	 */
	public PaintButton(String filename, int w, int h) {
		super();
		@SuppressWarnings("unused")
		CutHeadImages cut = new CutHeadImages(filename, w + 10);
		this.w = w;
		this.h = h;
		image = new ImageIcon(filename);
		this.setPreferredSize(new Dimension(w, h));
		this.setContentAreaFilled(false);// JButton不画背景，自定义一个圆的背景
	}

	@Override
	protected void paintComponent(Graphics g) {
		// TODO 自动生成的方法存根
		Graphics2D g2d = (Graphics2D) g;
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g2d.setColor(Color.white);
		g2d.drawImage(image.getImage(), 0, 0, w, h, this);
		g2d.drawOval(0, 0, w, h);
		super.paintComponent(g);
	}

	@Override
	protected void paintBorder(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;
		g2d.drawOval(0, 0, w, h);
	}

	// 侦测点击事件
	Shape shape;

	public boolean contains(int x, int y) {
		// 如果按钮改变大小，产生一个新的形状对象。
		if (shape == null || !shape.getBounds().equals(getBounds())) {
			shape = new Ellipse2D.Float(0, 0, w, h);
		}
		return shape.contains(x, y);
	}

}