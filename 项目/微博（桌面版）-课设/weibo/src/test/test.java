package test;

import java.awt.EventQueue;

import javax.swing.UIManager;

import view.Interface_Login;

/**
 * @Author ����
 * @Description ˵��
 * @Date ʱ�䣺2020-11-26
 */
public class test {

	/**
	 * @Description ˵��
	 */
	public static void main(String[] args) {

		// ������
		try {
			// Nimbus���
			UIManager.setLookAndFeel("com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel");
		} catch (Exception e) {
		}

		// ���е�swing����������¼������߳�����
		// ʵ��Runnable�ӿڣ���дrun����
		EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {
				// ��¼����
				new Interface_Login();
			}
		});
	}
}
