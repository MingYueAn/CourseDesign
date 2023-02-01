package plantvsplant.tool;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import plantvsplant.entity.Plant;

public class plantTrainThread
{
	private int mills;
	private Plant plant;
	private boolean flag = true;
	private static final ExecutorService pool = Executors.newCachedThreadPool();

	public plantTrainThread(int mills, final Plant plant)
	{
		this.mills = mills;
		this.plant = plant;
		plant.setTrainFinish(false);
		Runnable r = new Runnable()
		{
			public void run()
			{
				while (flag)
				{
					if (plant.isTrainFinish() == false)
					{
						// System.out.println("ssssssssssssssssssssssssssssssssssssssssssssssssss");
						new plantTrainHandler().start();
					}
					break;
				}
			}
		};
		pool.execute(r);
	}

	class plantTrainHandler extends Thread
	{
		public plantTrainHandler()
		{
		}

		public void run()
		{
			try
			{
				sleep(mills);
				// System.out.println(plant.getId()+" 培养完成,有木有钱?!");
				plant.setTrainFinish(true);
			} catch (InterruptedException e)
			{
				e.printStackTrace();
			}
		}
	}

	public int getMills()
	{
		return mills;
	}

	public void setMills(int mills)
	{
		this.mills = mills;
	}

}
