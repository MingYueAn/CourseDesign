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
 * @Author ����
 * @Description ˵����������ͼƬ�ı�ΪԲ�Σ�ͷ��
 * @Date ʱ�䣺2020-11-21
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
			// ��ȡͼƬ
			BufferedImage bufImage = ImageIO.read(new File(filepath));
			// ��СImage���˷�������Դͼ�񰴸�����ȡ��߶����������ź��ͼ��
			bufImage = scaleByPercentage(bufImage, bufImage.getWidth(), bufImage.getWidth());
			// ͸���׵�ͼƬ
			BufferedImage formatAvatarImage = new BufferedImage(width, width, BufferedImage.TYPE_4BYTE_ABGR);

			/////////////////////////
			Graphics2D graphics = formatAvatarImage.createGraphics();
			// ��ͼƬ�г�һ��԰
			graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
			// ��һ�����صĿհ������������Ҫ����Բ��ʱ����������
			int border = 0;
			// ͼƬ��һ��Բ��
			Ellipse2D.Double shape = new Ellipse2D.Double(border, border, width - border * 2, width - border * 2);
			// ��Ҫ����������
			graphics.setClip(shape);
			graphics.drawImage(bufImage, border, border, width - border * 2, width - border * 2, null);
			graphics.dispose();
			// ��Բͼ�����ٻ�һ��Բ
			// �´���һ��graphics����������Բ�����о��
			graphics = formatAvatarImage.createGraphics();
			graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
			int border1 = 3;
			// ������4.5�����أ�BasicStroke��ʹ�ÿ��Բ鿴����Ĳο��ĵ�
			// ʹ����ʱ��������������һ�����أ���������Լ�ʹ�õ�ʱ�����
			Stroke s = new BasicStroke(5F, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND);
			graphics.setStroke(s);
			graphics.setColor(Color.WHITE);
			graphics.drawOval(border1, border1, width - border1 * 2, width - border1 * 2);
			graphics.dispose();
			/////////////////////////

			// ����ͼƬ
			OutputStream os = new FileOutputStream(Constant.HEADIMAGE);
			ImageIO.write(formatAvatarImage, "jpg", os);
			return formatAvatarImage;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * ��СImage���˷�������Դͼ�񰴸�����ȡ��߶����������ź��ͼ��
	 *
	 * @param inputImage ��ѹ������ ��ѹ����߶�
	 * @throws java.io.IOException return
	 */
	private BufferedImage scaleByPercentage(BufferedImage inputImage, int newWidth, int newHeight) {
		// ��ȡԭʼͼ��͸��������
		try {
			int type = inputImage.getColorModel().getTransparency();
			int width = inputImage.getWidth();
			int height = inputImage.getHeight();
			// ���������
			RenderingHints renderingHints = new RenderingHints(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
			// ʹ�ø�����ѹ��
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
