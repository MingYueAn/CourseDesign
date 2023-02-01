package tools;

import java.awt.*;
import javax.swing.JPanel;

/**
 * @Author ����
 * @Description ˵�����б���ͼƬ��Panel��
 * @Date ʱ�䣺2020-11-20
 */
@SuppressWarnings("serial")
public class BackgroundPanel extends JPanel {

	private Image image = null;

	public BackgroundPanel() {
	}

	/**
	 * ����
	 * 
	 * @param image
	 */
	public BackgroundPanel(Image image) {
		this.image = image;
	}

	// �̶�����ͼƬ���������JPanel������ͼƬ������������
	protected void paintComponent(Graphics g) {
		g.drawImage(image, 0, 0, this.getWidth(), this.getHeight(), this);
	}
}