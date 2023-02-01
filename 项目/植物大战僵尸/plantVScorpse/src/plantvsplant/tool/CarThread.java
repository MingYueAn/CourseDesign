package plantvsplant.tool;

import javax.swing.JLabel;

import plantvsplant.Controller;

public class CarThread extends Thread
{

	private boolean flag = true;
	private int x;
	private int y;
	private JLabel car;
	private Controller controller;

	public CarThread(Controller controller, int x, int y)
	{
		this.x = x;
		this.y = y;
		this.controller = controller;
		controller.getWin().putBomp(x, y, 1);
		car = controller.getWin().getCarMap().get(100 * x + y);
	}

	public void run()
	{
		while (flag)
		{
			car.setVisible(false);
			int aa = controller.runCar(x, y);
			if (aa != -1)
			{
				car.setVisible(false);
				controller.getWin().putBomp(x, aa, 1);
				car = controller.getWin().getCarMap().get(100 * x + aa);
				controller.getWin().getCarMap().remove(100 * x + y);
				y = aa;
			} else
			{
				flag = false;
			}
			try
			{
				sleep(200);
			} catch (Exception e)
			{
				e.printStackTrace();
			}
			controller.showGame();
			controller.stopRnuCar(x);
		}
		if (!flag)
		{
			car.setVisible(false);
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

	public JLabel getCar()
	{
		return car;
	}

	public void setCar(JLabel car)
	{
		this.car = car;
	}

}
