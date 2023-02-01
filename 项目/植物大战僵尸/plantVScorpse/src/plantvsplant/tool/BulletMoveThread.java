package plantvsplant.tool;

import javax.swing.JLabel;

import plantvsplant.Controller;

public class BulletMoveThread extends Thread
{
	private boolean flag = true;
	private int x;
	private int y;
	private JLabel bullet;
	private Controller controller;

	public BulletMoveThread(int x, Controller controller, int y)
	{
		this.controller = controller;
		this.x = x;
		this.y = y;
		controller.getWin().putBomp(x, y, 0);
		bullet = controller.getWin().getBulletMap().get(100 * x + y);
	}

	public void run()
	{
		while (flag)
		{
			int aa = controller.moveBullet(x, y);
			bullet.setVisible(false);
			if (aa != -1)
			{
				// controller.getWin().getBulletMap().remove(100*x+y);
				controller.getWin().putBomp(x, aa, 0);
				bullet = controller.getWin().getBulletMap().get(100 * x + aa);
				if (bullet == null)
				{
					controller.fail();
				}
				y = aa;
			} else
			{
				flag = false;
			}
			try
			{
				sleep(300);
			} catch (Exception e)
			{
				e.printStackTrace();
			}

		}
		if (bullet != null)
		{
			bullet.setVisible(false);
		}
	}

	public boolean isFlag()
	{
		return flag;
	}

	public void setFlag(boolean flag)
	{
		this.flag = flag;
	}

	public JLabel getBullet()
	{
		return bullet;
	}

	public void setBullet(JLabel bullet)
	{
		this.bullet = bullet;
	}

}
