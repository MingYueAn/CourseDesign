package test;

import java.awt.EventQueue;

import javax.swing.UIManager;

import view.Interface_Login;

/**
 * @Author 作者
 * @Description 说明
 * @Date 时间：2020-11-26
 */
public class test {

	/**
	 * @Description 说明
	 */
	public static void main(String[] args) {

		// 界面风格
		try {
			// Nimbus风格
			UIManager.setLookAndFeel("com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel");
		} catch (Exception e) {
		}

		// 所有的swing组件必须由事件分配线程配置
		// 实现Runnable接口，覆写run方法
		EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {
				// 登录界面
				new Interface_Login();
			}
		});
	}
}
