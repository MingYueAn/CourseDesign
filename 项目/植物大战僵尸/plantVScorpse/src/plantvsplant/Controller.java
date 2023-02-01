package plantvsplant;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.swing.JLabel;
import javax.swing.JOptionPane;

import plantvsplant.entity.Cell;
import plantvsplant.entity.Corpse;
import plantvsplant.entity.Grid;
import plantvsplant.entity.Plant;
import plantvsplant.tool.BulletMoveThread;
import plantvsplant.tool.CarThread;
import plantvsplant.tool.CorpseMoveThread;
import plantvsplant.tool.MoneyEnoughThread;
import plantvsplant.tool.ProduceBullet;
import plantvsplant.tool.SunAdd;
import plantvsplant.tool.SunAutoProduceThread;
import plantvsplant.tool.SunflowerProduceThread;
import plantvsplant.tool.Timer;
import plantvsplant.tool.plantTrainThread;
import plantvsplant.win.IndexWin;
import plantvsplant.win.MyLabel;
import plantvsplant.win.Win;

public class Controller
{
	private Win win;
	private Grid grid;
	private IndexWin indexwin;
	private int x;
	private int y;
	private int length = 28;
	private String plantType = "00111";
	private Timer timer;
	private int sunValue;
	private int corpseNum;
	private boolean uproot = false;// 断定 能够挖除植物 0可以挖除
	private int operation = 0;// 0添加植物 1铲除植物
	private MoneyEnoughThread moneyEnoughThread;
	private Map<Integer, String> carRunList = new HashMap<Integer, String>();
	private Map<Integer, String> bulletList = new HashMap<Integer, String>();
	private SunAutoProduceThread sunAutoProduceThread;
	private Map<Integer, Plant> plantList = new HashMap<Integer, Plant>();
	private Map<Integer, CarThread> carList = new HashMap<Integer, CarThread>();
	private Map<String, CorpseMoveThread> corpseList = new HashMap<String, CorpseMoveThread>();
	private Map<Integer, ProduceBullet> produceBulletTimer = new HashMap<Integer, ProduceBullet>();

	public Controller()
	{
		indexwin = new IndexWin(this);

	}

	public void start()
	{
		SunAdd.stop = false;
		this.x = 15;
		this.y = 80;
		grid = new Grid(this.x, this.y);
		sunValue = 50;
		win = Win.getWin(this);
		timer = new Timer(this);
		// grid.start();
		indexwin.setVisible(false);
		sunAutoProduceThread = new SunAutoProduceThread(this);
		moneyEnoughThread = new MoneyEnoughThread(this);
		win.toShow();
		putSunValue(0);
		timer.start();
		sunAutoProduceThread.start();
		moneyEnoughThread.start();
		grid.getBoard()[1][0] = "31111";
		grid.getBoard()[4][0] = "31111";
		grid.getBoard()[7][0] = "31111";
		grid.getBoard()[10][0] = "31111";
		grid.getBoard()[13][0] = "31111";
		corpseNum = grid.getNum();

	}

	public void newCorpseThread()
	{
		for (int i = 0; i < grid.getBoard().length; i++)
		{
			if ("11,12,13,14".contains(grid.getBoard()[i][length - 1].substring(0, 2)) && corpseList.get(grid.getBoard()[i][length - 1]) == null)
			{
				corpseList.put(grid.getBoard()[i][length - 1], new CorpseMoveThread(this, i, length - 1));
				corpseList.get(grid.getBoard()[i][length - 1]).start();
			}
		}
	}

	public int moveBullet(int x, int y)
	{
		if (y + 1 == length - 1 && grid.getBoard()[x][y].equals("21bullet"))
		{
			grid.getBoard()[x][y] = "00111";
			return -1;
		}
		if ("11,12,13,14".contains(grid.getBoard()[x][y].substring(0, 2)))
		{
			return -1;
		}

		if (y + 1 != length - 1 && grid.getBoard()[x][y].equals("21bullet") && ("01,02,03,04,05".contains(grid.getBoard()[x][y + 1].substring(0, 2))))
		{
			bulletList.put(100 * x + y + 1, "21bullet");
			grid.getBoard()[x][y] = "00111";
			return y + 1;
		}
		if (y + 1 != length - 1 && ("01,02,03,04,05".contains(grid.getBoard()[x][y].substring(0, 2))) && ("11,12,13,14".contains(grid.getBoard()[x][y + 1].substring(0, 2))) && bulletList.get(100 * x + y) != null)
		{
			if (isHit(grid.getBoard()[x][y + 1]))
			{
				grid.getBoard()[x][y + 1] = "00111";
				return -1;
			}
			bulletList.remove(100 * x + y);
			return y + 1;
		}
		if (y + 1 != length - 1 && ("01,02,03,04,05".contains(grid.getBoard()[x][y].substring(0, 2))) && !("11,12,13,14".contains(grid.getBoard()[x][y + 1].substring(0, 2))) && bulletList.get(100 * x + y) != null)
		{
			grid.getBoard()[x][y + 1] = bulletList.get(100 * x + y);
			bulletList.remove(100 * x + y);
			return y + 1;
		}
		if (y + 1 != length - 1 && grid.getBoard()[x][y].equals("21bullet") && !("11,12,13,14".contains(grid.getBoard()[x][y + 1].substring(0, 2))))
		{
			grid.getBoard()[x][y + 1] = grid.getBoard()[x][y];
			grid.getBoard()[x][y] = "00111";
			return y + 1;
		}
		if (y + 1 != length - 1 && grid.getBoard()[x][y].equals("21bullet") && ("11,12,13,14".contains(grid.getBoard()[x][y + 1].substring(0, 2))))
		{
			if (isHit(grid.getBoard()[x][y + 1]))
			{
				grid.getBoard()[x][y + 1] = "00111";

				return -1;
			}
			grid.getBoard()[x][y] = "00111";
			return y + 1;
		}
		return -1;
	}

	public int moveCorpse(int x, int y)
	{
		if (y == 0 && ("11,12,13,14,15".contains(grid.getBoard()[x][y].substring(0, 2))))
		{
			fail();
			return -1;
		}
		if (y - 1 != -1 && !("01,02,03,04,05".contains(grid.getBoard()[x][y - 1].substring(0, 2))) && ("11,12,13,14,15".contains(grid.getBoard()[x][y].substring(0, 2))))
		{
			grid.getBoard()[x][y - 1] = grid.getBoard()[x][y];
			grid.getBoard()[x][y] = "00111";
			return y - 1;
		}
		if (y - 1 != -1 && ("01,02,03,04,05".contains(grid.getBoard()[x][y - 1].substring(0, 2))) && ("11,12,13,14,15".contains(grid.getBoard()[x][y].substring(0, 2))))
		{
			plantList.get(x * 100 + y - 1).setHp(plantList.get(x * 100 + y - 1).getHp() - 1);
			if (plantList.get(x * 100 + y - 1).getHp() == 0)
			{
				grid.getBoard()[x][y - 1] = "00111";
			}
			return y;
		}
		return -1;
	}

	public void moveCorpse()
	{
		for (int j = 0; j < grid.getBoard().length; j++)
		{
			for (int j2 = length - 1; j2 < grid.getBoard()[j].length; j2++)
			{
				if (("11,12,13,14,15".contains(grid.getBoard()[j][0].substring(0, 2))))
				{
					fail();
				}
				if (j2 + 1 != grid.getBoard()[j].length && !("01,02,03,04,05".contains(grid.getBoard()[j][j2].substring(0, 2))) && ("11,12,13,14,15".contains(grid.getBoard()[j][j2 + 1].substring(0, 2))))
				{
					grid.getBoard()[j][j2] = grid.getBoard()[j][j2 + 1];
					grid.getBoard()[j][j2 + 1] = "00111";
				}
				if (j2 + 1 != grid.getBoard()[j].length && ("01,02,03,04,05".contains(grid.getBoard()[j][j2].substring(0, 2))) && ("11,12,13,14,15".contains(grid.getBoard()[j][j2 + 1].substring(0, 2))))
				{
					plantList.get(j * 100 + j2).setHp(plantList.get(j * 100 + j2).getHp() - 1);
					if (plantList.get(j * 100 + j2).getHp() == 0)
					{
						grid.getBoard()[j][j2] = "00111";
					}
				}
			}
		}
	}

	public boolean isHit(String corpse)
	{
		int corpseId = Integer.parseInt(corpse.substring(0, 2));
		int corpseNum = Integer.parseInt(corpse.substring(2));
		Corpse c = grid.getList(corpseId).get(corpseNum);
		int hp = c.getHp() - 25;
		c.setHp(hp);
		if (hp <= 0)
		{
			return true;
		}
		return false;
	}

	public boolean isnull()
	{
		for (int i = 0; i < grid.getBoard().length; i++)
		{
			for (int j = 0; j < grid.getBoard()[i].length; j++)
			{
				if ("11,12,13,14".contains(grid.getBoard()[i][j].substring(0, 2)))
				{
					return false;
				}
			}
		}
		return true;
	}

	public void fail()
	{
		if (corpseList.size() > 0)
		{
			Set<Entry<String, CorpseMoveThread>> set = corpseList.entrySet();
			for (Entry<String, CorpseMoveThread> e : set)
			{
				CorpseMoveThread corpse = e.getValue();
				corpse.setFlag(false);
			}
			for (Iterator<Entry<String, CorpseMoveThread>> it = set.iterator(); it.hasNext();)
			{
				it.next();
				it.remove();
			}
		}
		if (carList.size() > 0)
		{
			Set<Entry<Integer, CarThread>> set = carList.entrySet();
			for (Entry<Integer, CarThread> e : set)
			{
				CarThread car = e.getValue();
				car.setFlag(false);
			}
		}
		if (produceBulletTimer.size() > 0)
		{
			Set<Entry<Integer, ProduceBullet>> set = produceBulletTimer.entrySet();
			for (Entry<Integer, ProduceBullet> e : set)
			{
				ProduceBullet bullet = e.getValue();
				bullet.setFlag(false);
			}
		}
		if (corpseList.size() > 0)
		{
			Set<Entry<String, CorpseMoveThread>> set = corpseList.entrySet();
			for (Entry<String, CorpseMoveThread> e : set)
			{
				CorpseMoveThread corpse = e.getValue();
				corpse.setFlag(false);
			}
		}
		timer.setFlag(false);
		sunAutoProduceThread.setStop(true);
		SunAdd.stop = true;
		int val = JOptionPane.showConfirmDialog(win, "失败，是否重新开始？");
		if (val == JOptionPane.YES_OPTION)
		{
			start();
		}
	}

	public void upGread()
	{
		if (carList.size() > 0)
		{
			Set<Entry<Integer, CarThread>> set = carList.entrySet();
			for (Entry<Integer, CarThread> e : set)
			{
				CarThread car = e.getValue();
				car.setFlag(false);
			}
		}
		if (produceBulletTimer.size() > 0)
		{
			Set<Entry<Integer, ProduceBullet>> set = produceBulletTimer.entrySet();
			for (Entry<Integer, ProduceBullet> e : set)
			{
				ProduceBullet bullet = e.getValue();
				bullet.setFlag(false);
			}
		}
		if (corpseList.size() > 0)
		{
			Set<Entry<String, CorpseMoveThread>> set = corpseList.entrySet();
			for (Entry<String, CorpseMoveThread> e : set)
			{
				CorpseMoveThread corpse = e.getValue();
				corpse.setFlag(false);
			}
		}
		timer.setFlag(false);
		sunAutoProduceThread.setStop(true);
		SunAdd.stop = true;
		if (y + 20 > 160)
		{
			JOptionPane.showMessageDialog(win, "您已经通关");
		}
		int val = JOptionPane.showConfirmDialog(win, "成功升级，是否继续？");
		if (val == JOptionPane.YES_OPTION)
		{
			y = y + 10;

			start();

		}
	}

	public void showGame()
	{
		isRunCar();
		isProduceButtle();
		if (isnull())
		{
			upGread();
		}
		// System.out.println(corpseNum);
	}

	public void produceBullet(int x, int y)
	{
		if (grid.getBoard()[x][y].substring(0, 2).equals("03") || grid.getBoard()[x][y].substring(0, 2).equals("05"))
		{
			grid.getBoard()[x][y + 2] = "21bullet";
			new BulletMoveThread(x, this, y + 2).start();
		}
	}

	public void isProduceButtle()
	{
		for (int i = 1; i < grid.getBoard().length; i = i + 3)
		{
			for (int j = 0; j < grid.getBoard()[i].length; j++)
			{
				if (isHaveCorpse(i, j) && isHavePease(i, j) && produceBulletTimer.get(100 * i + j) == null)
				{
					produceBulletTimer.put(100 * i + j, new ProduceBullet(i, this, j));
					produceBulletTimer.get(100 * i + j).start();
				}
			}
		}
	}

	public boolean isStopProduceButtle(int x, int y)
	{
		if (produceBulletTimer.get(100 * x + y) != null && (!isHaveCorpse(x, y) || !isHavePease(x, y)))
		{
			produceBulletTimer.get(100 * x + y).setFlag(false);
			produceBulletTimer.remove(100 * x + y);
			return true;
		}
		return false;
	}

	public boolean isHaveCorpse(int x, int y)
	{
		for (int i = y + 1; i < length; i++)
		{
			if (("11,12,13,14,15".contains(grid.getBoard()[x][i].substring(0, 2))))
			{
				return true;
			}
		}
		return false;
	}

	public boolean isHavePease(int x, int y)
	{
		if (grid.getBoard()[x][y].substring(0, 2).equals("03") || grid.getBoard()[x][y].substring(0, 2).equals("05"))
		{
			return true;
		}
		return false;
	}

	public void isRunCar()
	{
		for (int i = 1; i < grid.getBoard().length; i += 3)
		{
			if (("11,12,13,14".contains(grid.getBoard()[i][1].substring(0, 2))) && grid.getBoard()[i][0].substring(0, 2).equals("31"))
			{
				carList.put(i, new CarThread(this, i, 0));
				carList.get(i).start();
			}
		}
	}

	public void stopRnuCar(int x)
	{
		for (int i = 0; i < length - 1; i++)
		{
			if (grid.getBoard()[x][i].substring(0, 2).equals("31"))
			{
				return;
			}
		}
		carList.get(x).setFlag(false);
	}

	public int runCar(int x, int y)
	{
		if (y == length - 1 && grid.getBoard()[x][y].substring(0, 2).equals("31"))
		{
			grid.getBoard()[x][y] = "00111";
			return -1;
		}
		if (grid.getBoard()[x][y].substring(0, 2).equals("31") && ("11,12,13,14".contains(grid.getBoard()[x][y + 1].substring(0, 2))))
		{
			System.out.println(grid.getBoard()[x][y + 1]);
			// corpseList.get(grid.getBoard()[x][y+1]).setFlag(false);
			grid.getBoard()[x][y + 1] = grid.getBoard()[x][y];
			grid.getBoard()[x][y] = "00111";
//				corpseNum--;
//				System.out.println(corpseNum);
//				if(corpseNum==0){
//					upGread();
//				}
			return y + 1;
		}
		if (grid.getBoard()[x][y].substring(0, 2).equals("31") && ("01,02,03,04,05".contains(grid.getBoard()[x][y + 1].substring(0, 2))))
		{
			carRunList.put(100 * x + y + 1, "31111");
			grid.getBoard()[x][y] = "00111";
			return y + 1;
		}
		if (grid.getBoard()[x][y].substring(0, 2).equals("31") && !"05,01,02,03,04".contains(grid.getBoard()[x][y + 1].substring(0, 2)))
		{
			grid.getBoard()[x][y + 1] = grid.getBoard()[x][y];
			grid.getBoard()[x][y] = "00111";
			return y + 1;
		}
		if (("01,02,03,04,05".contains(grid.getBoard()[x][y].substring(0, 2))) && carRunList.get(100 * x + y) != null)
		{
			grid.getBoard()[x][y + 1] = carRunList.get(100 * x + y);
//				carRunList.remove(100*x+y);
//				corpseNum--;
//				System.out.println(corpseNum);
//				if(corpseNum==0){
//					upGread();
//				}
			return y + 1;
		}
		return -1;
	}

	public void addPlant(int x, int y)
	{// 放入植物
		// System.out.println("x"+x+" "+y);
		final Controller con = this;
		final int aa = win.getPlantid();
		if (aa > 0)
		{
			Plant plant = new Plant(aa);
			final int i = ((int) x / 3) * 3 + 1;
			final int j = ((int) (y - 1) / 3) * 3 + 2;
			if (grid.getBoard()[i][j].equals("00111"))
			{
				int key = i * 100 + j;
				plantList.put(key, plant);
				plantType = "0" + Integer.toString(aa) + "111";
				grid.getBoard()[i][j] = plantType;
				win.putPlant(i, j);
				switch (aa)
				{
				case 1:
					win.getPsunflower().setCanPlant(false);
					win.getCard0().setEnabled(false);
					new plantTrainThread(5000, win.getPsunflower());
					break;
				case 2:
					bombStop(i, j);
					win.getPbomb().setCanPlant(false);
					win.getCard1().setEnabled(false);
					new plantTrainThread(27000, win.getPbomb());
					break;
				case 3:
					win.getPpease().setCanPlant(false);
					win.getCard2().setEnabled(false);
					new plantTrainThread(17000, win.getPpease());
					break;
				case 4:
					win.getPpotato().setCanPlant(false);
					win.getCard3().setEnabled(false);
					new plantTrainThread(13000, win.getPpotato());
					break;
				case 5:
					win.getPsnowpease().setCanPlant(false);
					win.getCard4().setEnabled(false);
					new plantTrainThread(21000, win.getPsnowpease());
					break;
				}
				new Thread()
				{
					private String pp = plantType;
					private int i1 = i;
					private int j1 = j;
					private MyLabel plant = win.getPlant();

					public void run()
					{
						SunflowerProduceThread sunf = null;
						if (aa == 1)
						{
							sunf = new SunflowerProduceThread(i1, j1, con);
							sunf.start();
						}
						while (true)
						{
							try
							{
								sleep(100);
							} catch (InterruptedException e)
							{
								e.printStackTrace();
							}
							if (!pp.equals(grid.getBoard()[i1][j1]))
							{
								plant.setVisible(false);
								if (sunf != null)
								{
									sunf.setStop(true);
								}
								break;
							}
						}
					}
				}.start();
				plantType = "00111";
				win.setPlantid(0);
			} else
			{
				return;
			}
		}
	}

	public void uprootPlant(int x, int y)
	{
		if (uproot)
		{
			int i = ((int) x / 3) * 3 + 1;
			int j = ((int) (y - 1) / 3) * 3 + 2;
			if (!"01111,02111,03111,04111,05111".contains(grid.getBoard()[i][j]))
			{
				uproot = false;
				win.getScoop().setEnabled(true);
				uproot = false;
				return;
			}
			grid.getBoard()[i][j] = "00111";
			uproot = false;
			win.getScoop().setEnabled(true);
		}
	}

	public void bombStop(final int i, final int j)
	{
		new Thread()
		{
			@Override
			public void run()
			{
				try
				{
					sleep(3000);
					bombScorpse(i, j);
				} catch (InterruptedException e)
				{
					e.printStackTrace();
				}
			}
		}.start();
	}

	private void myBombThread(final int i, final int j)
	{
		new Thread()
		{
			@Override
			public void run()
			{
				try
				{
					win.getPlant().setVisible(false);
					win.putBomb(i, j);
					sleep(4000);
					win.getBombBloom().setVisible(false);
				} catch (InterruptedException e)
				{
					e.printStackTrace();
				}
			}
		}.start();
	}

	public void bombScorpse(int i, int j)
	{
		int hp = 1000;
		List<Cell> lists = findRound81Point(i, j, 1);
		if ((hp = bombScorpseTip(hp, lists)) > 0)
		{
			List<Cell> lists1 = findRound81Point(i, j, 4);
			lists1.removeAll(lists);
			if ((hp = bombScorpseTip(hp, lists1)) > 0)
			{
				List<Cell> lists2 = findRound81Point(i, j, 9);
				lists2.removeAll(lists1);
				if ((hp = bombScorpseTip(hp, lists2)) > 0)
				{
					List<Cell> lists3 = findRound81Point(i, j, 16);
					lists3.removeAll(lists2);
					bombScorpseTip(hp, lists3);
				} else
				{
					myBombThread(i, j);
					return;
				}
			} else
			{
				myBombThread(i, j);
				return;
			}
		} else
		{
			myBombThread(i, j);
			return;
		}
		myBombThread(i, j);
	}

	private int bombScorpseTip(int hp, List<Cell> lists)
	{
		Corpse corpse;
		for (Cell cell : lists)
		{
			if ("11,12,13,14".contains(grid.getBoard()[cell.getX()][cell.getY()].substring(0, 2)))
			{
				corpse = new Corpse(Integer.parseInt(grid.getBoard()[cell.getX()][cell.getY()].substring(0, 2)));
				hp = hp - corpse.getHp();
				if (hp < 0)
					return 0;
				grid.getBoard()[cell.getX()][cell.getY()] = "00111";// 底层 为原来的数据
				corpseNum--;
				System.out.println(corpseNum);
				if (corpseNum == 0)
				{
					upGread();
				}

			}
		}
		return hp;
	}

	private List<Cell> findRound81Point(int m, int n, int value)
	{
		List<Cell> lists = new ArrayList<Cell>();
		for (int i = 0; i < 15; i++)
		{
			for (int j = 0; j < length; j++)
			{
				if (((m - i) * (m - i) + (n - j) * (n - j)) <= (2 * value))
				{
					lists.add(new Cell(i, j));
				}
			}
		}
		return lists;
	}

	public void putSun(int x, int y)
	{// 产生太阳并加太阳值
		JLabel sun = win.getSun(x, y);
		new SunAdd(x, y, this, sun).start();
	}

	public void putSunValue(int value)
	{
		sunValue = sunValue + value;
		win.getSunnum().setText(Integer.toString(sunValue));
	}

	public int getOperation()
	{
		return operation;
	}

	public void setOperation(int operation)
	{
		this.operation = operation;
	}

	public Win getWin()
	{
		return win;
	}

	public void setWin(Win win)
	{
		this.win = win;
	}

	public Grid getGrid()
	{
		return grid;
	}

	public void setGrid(Grid grid)
	{
		this.grid = grid;
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

	public void setPlantType(String plantType)
	{
		this.plantType = plantType;
	}

	public String getPlantType()
	{
		return plantType;
	}

	public void toShow()
	{
		indexwin.ToShow();
	}

	public int getSunValue()
	{
		return sunValue;
	}

	public void setSunValue(int sunValue)
	{
		this.sunValue = sunValue;
	}

	public boolean isUproot()
	{
		return uproot;
	}

	public void setUproot(boolean uproot)
	{
		this.uproot = uproot;
	}

	public int getLength()
	{
		return length;
	}

	public void setLength(int length)
	{
		this.length = length;
	}

	public Map<Integer, CarThread> getCarList()
	{
		return carList;
	}

	public void setCarList(Map<Integer, CarThread> carList)
	{
		this.carList = carList;
	}

	public Map<String, CorpseMoveThread> getCorpseList()
	{
		return corpseList;
	}

	public void setCorpseList(Map<String, CorpseMoveThread> corpseList)
	{
		this.corpseList = corpseList;
	}

	public Map<Integer, String> getCarRunList()
	{
		return carRunList;
	}

	public void setCarRunList(Map<Integer, String> carRunList)
	{
		this.carRunList = carRunList;
	}

	public Map<Integer, String> getBulletList()
	{
		return bulletList;
	}

	public void setBulletList(Map<Integer, String> bulletList)
	{
		this.bulletList = bulletList;
	}

}
