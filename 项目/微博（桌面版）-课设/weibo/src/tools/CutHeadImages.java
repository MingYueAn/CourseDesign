package tools;

import javax.imageio.ImageIO;

import data.Constant;

import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;

/**
 * @Author 作者
 * @Description 说明：将方形图片改变为圆形（头像）
 * @Date 时间：2020-11-21
 */
public class CutHeadImages {

	private String filepath;
	private int width;

	/**
	 * @param filepath
	 * @param width
	 */
	public CutHeadImages(String filepath, int width) {
		super();
		this.filepath = filepath;
		this.width = width;
		HeadImages();
	}

	private BufferedImage HeadImages() {

		try {
			// 读取图片
			BufferedImage bufImage = ImageIO.read(new File(filepath));
			// 缩小Image，此方法返回源图像按给定宽度、高度限制下缩放后的图像
			bufImage = scaleByPercentage(bufImage, bufImage.getWidth(), bufImage.getWidth());
			// 透明底的图片
			BufferedImage formatAvatarImage = new BufferedImage(width, width, BufferedImage.TYPE_4BYTE_ABGR);

			/////////////////////////
			Graphics2D graphics = formatAvatarImage.createGraphics();
			// 把图片切成一个园
			graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
			// 留一个像素的空白区域，这个很重要，画圆的时候把这个覆盖
			int border = 0;
			// 图片是一个圆型
			Ellipse2D.Double shape = new Ellipse2D.Double(border, border, width - border * 2, width - border * 2);
			// 需要保留的区域
			graphics.setClip(shape);
			graphics.drawImage(bufImage, border, border, width - border * 2, width - border * 2, null);
			graphics.dispose();
			// 在圆图外面再画一个圆
			// 新创建一个graphics，这样画的圆不会有锯齿
			graphics = formatAvatarImage.createGraphics();
			graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
			int border1 = 3;
			// 画笔是4.5个像素，BasicStroke的使用可以查看下面的参考文档
			// 使画笔时基本会像外延伸一定像素，具体可以自己使用的时候测试
			Stroke s = new BasicStroke(5F, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND);
			graphics.setStroke(s);
			graphics.setColor(Color.WHITE);
			graphics.drawOval(border1, border1, width - border1 * 2, width - border1 * 2);
			graphics.dispose();
			/////////////////////////

			// 保存图片
			OutputStream os = new FileOutputStream(Constant.HEADIMAGE);
			ImageIO.write(formatAvatarImage, "jpg", os);
			return formatAvatarImage;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 缩小Image，此方法返回源图像按给定宽度、高度限制下缩放后的图像
	 *
	 * @param inputImage ：压缩后宽度 ：压缩后高度
	 * @throws java.io.IOException return
	 */
	private BufferedImage scaleByPercentage(BufferedImage inputImage, int newWidth, int newHeight) {
		// 获取原始图像透明度类型
		try {
			int type = inputImage.getColorModel().getTransparency();
			int width = inputImage.getWidth();
			int height = inputImage.getHeight();
			// 开启抗锯齿
			RenderingHints renderingHints = new RenderingHints(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
			// 使用高质量压缩
			renderingHints.put(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
			BufferedImage img = new BufferedImage(newWidth, newHeight, type);
			Graphics2D graphics2d = img.createGraphics();
			graphics2d.setRenderingHints(renderingHints);
			graphics2d.drawImage(inputImage, 0, 0, newWidth, newHeight, 0, 0, width, height, null);
			graphics2d.dispose();
			return img;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

}
