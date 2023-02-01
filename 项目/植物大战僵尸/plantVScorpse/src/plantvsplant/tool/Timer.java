package plantvsplant.tool;

import plantvsplant.Controller;

public class Timer extends Thread
{

	private boolean flag = true;
	private Controller controller;

	public Timer(Controller controller)
	{
		this.controller = controller;
	}

	public void run()
	{
		try
		{
			while (flag)
			{
				controller.moveCorpse();
				controller.newCorpseThread();
				controller.showGame();
				sleep(200);
				controller.showGame();
				sleep(200);
				controller.showGame();
				sleep(200);

				controller.showGame();
				sleep(200);
				controller.showGame();
				sleep(200);
				controller.showGame();
				sleep(200);
				controller.showGame();
				sleep(200);
				controller.showGame();
				sleep(200);
				controller.showGame();
				sleep(200);
				controller.showGame();
				sleep(200);
				controller.showGame();
				sleep(200);
				controller.showGame();
				sleep(200);
				controller.showGame();
				sleep(200);
				controller.showGame();
				sleep(200);
				controller.showGame();
				sleep(200);
				controller.showGame();
				sleep(200);
				controller.showGame();
				sleep(200);
				controller.showGame();
				sleep(200);
				controller.showGame();
				sleep(200);
				controller.showGame();
				sleep(200);
			}
		} catch (Exception e)
		{
			e.printStackTrace();
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

	public Controller getController()
	{
		return controller;
	}

	public void setController(Controller controller)
	{
		this.controller = controller;
	}

}
