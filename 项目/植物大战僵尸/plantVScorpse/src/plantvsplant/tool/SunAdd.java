package plantvsplant.tool;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JLabel;

import plantvsplant.Controller;

public class SunAdd extends Thread
{
	Controller controller;
	private JLabel sun;
	private int a;
	private int b;
	public static boolean stop = false;

	public SunAdd(int x, int y, Controller controller, JLabel sun)
	{
		this.controller = controller;
		this.a = x;
		this.b = y;
		this.sun = sun;
	}

	public void run()
	{
		sun.addMouseListener(new MouseAdapter()
		{

			public void mouseClicked(MouseEvent e)
			{
				sun.setVisible(false);
				controller.putSunValue(25);
			}

		});
		while (true)
		{
			if (stop)
			{
				sun.setVisible(false);
				break;
			}
			try
			{
				sleep(200);
			} catch (InterruptedException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			b += 3;
			if (a < 200)
				a += 2;
			sun.setBounds(a, b, 80, 80);
			if (b >= 650)
			{
				sun.setVisible(false);
				break;
			}
		}
	}
}
