package plantvsplant.tool;

import plantvsplant.Controller;

public class MoneyEnoughThread extends Thread
{
	private Controller controller;
	private boolean goon = true;
	private int sunvalue = 0;

	public MoneyEnoughThread(Controller controller)
	{
		this.controller = controller;
	}

	@Override
	public void run()
	{

		while (goon)
		{
			try
			{
				sleep(300);
			} catch (InterruptedException e)
			{
			}
			sunvalue = controller.getSunValue();
			canSun();
		}
	}

	public void canSun()
	{
		if (sunvalue >= 50)
		{
			if (controller.getWin().getPsunflower().isTrainFinish() == true && !controller.getWin().getCard0().isEnabled())
			{
				controller.getWin().getCard0().setEnabled(true);
			}
			if (controller.getWin().getPpotato().isTrainFinish() == true && !controller.getWin().getCard3().isEnabled())
			{
				controller.getWin().getCard3().setEnabled(true);
			}
		}
		if (sunvalue >= 100)
		{
			if (controller.getWin().getPpease().isTrainFinish() == true && !controller.getWin().getCard2().isEnabled())
			{
				controller.getWin().getCard2().setEnabled(true);
			}
		}
		if (sunvalue >= 150)
		{
			if (controller.getWin().getPbomb().isTrainFinish() && !controller.getWin().getCard1().isEnabled())
			{
				controller.getWin().getCard1().setEnabled(true);
			}
		}
		if (sunvalue >= 175)
		{
			if (controller.getWin().getPsnowpease().isTrainFinish() && !controller.getWin().getCard4().isEnabled())
			{
				controller.getWin().getCard4().setEnabled(true);
			}
		}
		if (sunvalue < 175)
		{
			if (controller.getWin().getCard4().isEnabled())
			{
				controller.getWin().getCard4().setEnabled(false);
			}
		}
		if (sunvalue < 150)
		{
			if (controller.getWin().getCard1().isEnabled())
			{
				controller.getWin().getCard1().setEnabled(false);
			}
		}
		if (sunvalue < 100)
		{
			if (controller.getWin().getCard2().isEnabled())
			{
				controller.getWin().getCard2().setEnabled(false);
			}
		}
		if (sunvalue < 50)
		{
			if (controller.getWin().getCard0().isEnabled())
			{
				controller.getWin().getCard0().setEnabled(false);
			}
			if (controller.getWin().getCard3().isEnabled())
			{
				controller.getWin().getCard3().setEnabled(false);
			}
		}
	}
}

//package plantvsplant;
//
//public class MoneyEnoughThread extends Thread {
//	private Controller controller;
//	private boolean goon = true;
//	private int sunvalue = 0;
//	public MoneyEnoughThread(Controller controller) {
//		this.controller = controller;
//	}
//
//	@Override
//	public void run() {
//		
//		while (goon) {
//			try {
//					sleep(300);
//			} catch (InterruptedException e) {
//			}
//			sunvalue = controller.getSunValue();
//			for (int j = 0; j < 5; j++) {
//				isEnable(j);
//			}
//		}
//	}
//	public void isEnable(int j){
//		if (sunvalue < controller.getWin().getPlant(j).getPrice()
//				|| controller.getWin().getPlant(j).isTrainFinish() == false) {
//			controller.getWin().setCardsEnable(j, false);
//		}
//		if (sunvalue >= controller.getWin().getPlant(j).getPrice()
//				&& controller.getWin().getPlant(j).isTrainFinish() == true) {
//			controller.getWin().setCardsEnable(j, true);
//		}
//	}
//}