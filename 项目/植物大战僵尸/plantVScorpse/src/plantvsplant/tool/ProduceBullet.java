package plantvsplant.tool;

import plantvsplant.Controller;

public class ProduceBullet extends Thread
{
	private boolean flag = true;
	private int x;
	private int y;
//	private BulletMoveThread bulletMoveThread;
	private Controller controller;

	public ProduceBullet(int x, Controller controller, int y)
	{
		this.controller = controller;
		this.x = x;
		this.y = y;

	}

	public void run()
	{
//		if(!flag){			
//		}
		while (flag)
		{
			controller.produceBullet(x, y);
			controller.isStopProduceButtle(x, y);
			try
			{
				sleep(1500);
			} catch (Exception e)
			{
				e.printStackTrace();
			}
			// controller.showGame();
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

}
