package plantvsplant.tool;

import javax.swing.JLabel;

import plantvsplant.Controller;

public class CorpseMoveThread extends Thread
{

	private int x;
	private int y;
	private JLabel corpse;
	private boolean flag = true;
	private int second = 1500;
	private Controller controller;

	public CorpseMoveThread(Controller controller, int x, int y)
	{
		this.x = x;
		this.y = y;
		this.controller = controller;
		controller.getWin().putCorpse(x, y, Integer.parseInt(controller.getGrid().getBoard()[x][y].substring(0, 2)));
		corpse = controller.getWin().getCorpseMap().get(100 * x + y);
	}

	public void run()
	{

		while (flag)
		{
			int aa = controller.moveCorpse(x, y);
//			for (int i = 0; i < 15; i++) {
//				for (int j = controller.getLength()-3; j < controller.getLength(); j++) {
//					System.out.print(controller.getGrid().getBoard()[i][j]+"\t");
//				}
//				System.out.println();
//			}
//			System.out.println();
//			System.out.println();
//			System.out.println();
//			System.out.println();
			corpse.setVisible(false);
			if (aa == -1)
			{
				flag = false;
			} else
			{
				// controller.getWin().getCorpseMap().remove(100*x+y);
				controller.getWin().putCorpse(x, aa, Integer.parseInt(controller.getGrid().getBoard()[x][aa].substring(0, 2)));
				corpse = controller.getWin().getCorpseMap().get(100 * x + aa);
				y = aa;
			}
			try
			{
				sleep(second);

			} catch (Exception e)
			{
			}
		}
		if (flag == false)
		{
			corpse.setVisible(false);
		}
	}

	public int getX()
	{
		return x;
	}

	public void setX(int x)
	{
		this.x = x;
	}

	public int getY()
	{
		return y;
	}

	public void setY(int y)
	{
		this.y = y;
	}

	public boolean isFlag()
	{
		return flag;
	}

	public void setFlag(boolean flag)
	{
		this.flag = flag;
	}

	public int getSecond()
	{
		return second;
	}

	public void setSecond(int second)
	{
		this.second = second;
	}

	public Controller getController()
	{
		return controller;
	}

	public void setController(Controller controller)
	{
		this.controller = controller;
	}

	public JLabel getCorpse()
	{
		return corpse;
	}

	public void setCorpse(JLabel corpse)
	{
		this.corpse = corpse;
	}

}
