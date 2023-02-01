
package plantvsplant.tool;

import plantvsplant.Controller;

public class SunflowerProduceThread extends Thread
{
	private boolean stop = false;
	private int x;
	private int y;
	private Controller controller;

	public SunflowerProduceThread(int x, int y, Controller controller)
	{
		this.controller = controller;
		this.x = 80 + 32 * (y - 1);
		this.y = 60 + 40 * (x - 1);
	}

	public void run()
	{
		while (true)
		{
			try
			{
				sleep(10000);
			} catch (Exception e)
			{
				e.printStackTrace();
			}
			if (stop)
			{
				break;
			}
			controller.putSun(x, y);
		}
	}

	public void setStop(boolean stop)
	{
		this.stop = stop;
	}
}

//package plantvsplant;
//
//public class sunflowerProduceThread extends Thread{
//	private boolean flag=true;
//	private int x;
//	private Controller controller;
//	public sunflowerProduceThread(int x,Controller controller){
//		this.controller=controller;
//		this.x=x;
//	}
//	public void run() {
//		while(flag){
//			try {
//				sleep(2000);
//				controller.produceSun(x);
//			} catch (Exception e) {
//				e.printStackTrace();
//			}
//		}
//	}
//	public boolean isFlag() {
//		return flag;
//	}
//	public void setFlag(boolean flag) {
//		this.flag = flag;
//	}
//	
//}
