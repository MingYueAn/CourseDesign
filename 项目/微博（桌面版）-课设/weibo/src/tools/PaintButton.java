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
 * @Author ����
 * @Description ˵����Բ�ΰ�ť����ͷ��
 * @Date ʱ�䣺2020-11-20
 */
@SuppressWarnings("serial")
public class PaintButton extends JButton {

//	����һ: ͨ�� java.awt.Toolkit ����������ȡ���ء����� �� �ڴ��� �� ͼƬ��֧�� GIF��JPEG �� PNG��
//	Image image = Toolkit.getDefaultToolkit().getImage(String filename);
//	Image image = Toolkit.getDefaultToolkit().getImage(URL url);
//	Image image = Toolkit.getDefaultToolkit().createImage(byte[] imageData);
//
//	������: ͨ�� javax.imageio.ImageIO �������ȡ���ء����� �� �ڴ��� �� ͼƬ��BufferedImage �̳��� Image��
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
		this.setContentAreaFilled(false);// JButton�����������Զ���һ��Բ�ı���
	}

	@Override
	protected void paintComponent(Graphics g) {
		// TODO �Զ����ɵķ������
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

	// ������¼�
	Shape shape;

	public boolean contains(int x, int y) {
		// �����ť�ı��С������һ���µ���״����
		if (shape == null || !shape.getBounds().equals(getBounds())) {
			shape = new Ellipse2D.Float(0, 0, w, h);
		}
		return shape.contains(x, y);
	}

}