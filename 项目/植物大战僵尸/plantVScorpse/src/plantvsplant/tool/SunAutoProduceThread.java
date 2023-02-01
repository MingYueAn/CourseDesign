package plantvsplant.tool;

import plantvsplant.Controller;

public class SunAutoProduceThread extends Thread
{
	private Controller controller;
	private boolean stop = false;
	private long t = 0;

	public SunAutoProduceThread(Controller controller)
	{
		this.controller = controller;
	}

	public void run()
	{
		while (true)
		{
			try
			{
				sleep(100);
			} catch (InterruptedException e)
			{
				e.printStackTrace();
			}
			t += 100;
			if (stop)
			{
				break;
			}
			if (t % 12000 == 0)
				controller.putSun(50, 50);

		}
	}

	public Controller getController()
	{
		return controller;
	}

	public void setController(Controller controller)
	{
		this.controller = controller;
	}

	public void setStop(boolean stop)
	{
		this.stop = stop;
	}
}

//package plantvsplant;
//
//public class SunAutoProduceThread extends Thread {
//	private Controller controller;
//	private boolean goon = true;
//
//	public SunAutoProduceThread(Controller controller) {
//		this.controller = controller;
//	}
//	public void run() {
//		while (goon) {
//			try {
//				sleep(3000);
//				controller.getWin().getSunnum().setVisible(true);
//			} catch (InterruptedException e) {
//				e.printStackTrace();
//			}
//
//		}
//	}
//
//	public Controller getController() {
//		return controller;
//	}
//
//	public void setController(Controller controller) {
//		this.controller = controller;
//	}
//
//	public boolean isGoon() {
//		return goon;
//	}
//}
