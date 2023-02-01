package plantvsplant.entity;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Grid
{
	private int num11 = 0;// ct
	private int num12 = 0;// ct
	private int num13 = 0;// ct
	private int num14 = 0;// ct
	private String corpse11 = "11";
	private String corpse12 = "12";
	private String corpse13 = "13";
	private String corpse14 = "14";
	private int width;
	private int length;
	private int space;// ct
	private int sum;// ct
	private int maxLength;// ct
	private int minLength;// ct
	private String[][] board;
	private List<Corpse> corpseList11;
	private List<Corpse> corpseList12;
	private List<Corpse> corpseList13;
	private List<Corpse> corpseList14;

	public Grid(int width, int length)
	{
		this.length = length;
		this.width = width;
		board = new String[width][length];
		init();
	}

	public void init()
	{
		for (int i = 0; i < board.length; i++)
		{
			for (int j = 0; j < board[i].length; j++)
			{
				board[i][j] = "00111";
			}
		}
		space = (length - 30) / 4;
		start();
	}

	public void start()
	{
		switch (length)
		{
		case 80:
			sum = 40;
			break;
		case 90:
			sum = 60;
			break;
		case 100:
			sum = 70;
			break;
		case 110:
			sum = 80;
			break;

		}

		// 排列僵尸
		rank1();
		rank2();
		rank3();
		rank4();

		coepseList();

	}

	public void rank1()
	{
		minLength = 30;
		maxLength = 30 + 1 * space;
		Random r = new Random();
		int n = 0;
		for (int i = 35; i < maxLength; i++)
		{
			for (int j = 1; j < board.length; j = j + 3)
			{
				if (board[j][n].equals("00111") && num11 <= (sum >= 50 ? 1 : 2))
				{
					n = r.nextInt(space) + minLength;
					board[j][n] = corpse11 + num11;
					num11++;
				}
			}
		}
		minLength = 30 + 1 * space;
	}

	public void rank2()
	{
		maxLength = 30 + 2 * space;
		Random r = new Random();
		int n = 0;
		for (int i = minLength; i < maxLength; i++)
		{
			for (int j = 1; j < board.length; j = j + 3)
			{
				if (board[j][n].equals("00111") && num12 <= (sum >= 50 ? 1 : 2))
				{
					n = r.nextInt(space) + minLength;
					board[j][n] = corpse12 + num12;
					num12++;
				}
			}
		}
		for (int i = minLength; i < maxLength; i++)
		{
			for (int j = 1; j < board.length; j = j + 3)
			{
				if (board[j][n].equals("00111") && num11 <= (sum >= 50 ? 3 : 5))
				{
					n = r.nextInt(space) + minLength;
					board[j][n] = corpse11 + num11;
					num11++;
				}
			}
		}
		minLength = maxLength;
	}

	public void rank3()
	{
		maxLength = 30 + 3 * space;
		Random r = new Random();
		int n = 0;

		for (int j = 13; j >= 1; j = j - 3)
		{
			for (int i = minLength; i < maxLength; i++)
			{
				if (board[j][n].equals("00111") && num13 <= (sum >= 50 ? 1 : 2))
				{
					n = r.nextInt(space) + minLength;
					board[j][n] = corpse13 + num13;
					num13++;
				}
			}
		}

		for (int j = 13; j >= 1; j = j - 3)
		{
			for (int i = minLength; i < maxLength; i++)
			{
				if (board[j][n].equals("00111") && num12 <= (sum >= 50 ? 3 : 5))
				{
					n = r.nextInt(space) + minLength;
					board[j][n] = corpse12 + num12;
					num12++;
				}
			}
		}
		for (int j = 13; j >= 1; j = j - 3)
		{
			for (int i = minLength; i < maxLength; i++)
			{
				if (board[j][n].equals("00111") && num11 <= (sum >= 50 ? 7 : 10))
				{
					n = r.nextInt(space) + minLength;
					board[j][n] = corpse11 + num11;
					num11++;
				}
			}
		}
		minLength = maxLength;
	}

	private void rank4()
	{

		Random r = new Random();
		int n = 0;
		for (int i = length - 5; i < length; i++)
		{
			for (int j = 1; j < board.length; j = j + 3)
			{
				if (board[j][n].equals("00111") && num14 <= sum / 10)
				{
					n = r.nextInt(space) + minLength;
					board[j][n] = corpse14 + num14;
					num14++;
				}
			}
		}
		// System.out.println(num14);
		for (int i = length - 5; i < length; i++)
		{
			for (int j = 1; j < board.length; j = j + 3)
			{
				if (board[j][n].equals("00111") && num13 <= (sum / 5))
				{
					board[j][i] = corpse13 + num13;
					num13++;
				}
			}
		}
		// System.out.println(num13);
		for (int i = length - 5; i < length; i++)
		{
			for (int j = 1; j < board.length; j = j + 3)
			{
				if (board[j][i].equals("00111") && num12 <= (3 * sum / 10))
				{
					board[j][i] = corpse12 + num12;
					num12++;
				}
			}
		}
		// System.out.println(num12);
		for (int i = length - 5; i < length; i++)
		{
			for (int j = 1; j < board.length; j = j + 3)
			{
				if (board[j][i].equals("00111") && num11 <= (2 * sum / 5))
				{
					board[j][i] = corpse11 + num11;
					num11++;
				}
			}
		}
//		System.out.println(num11);
//		System.out.println(minLength+"       "+length);
	}

	public void coepseList()
	{
		corpseList11 = new ArrayList<Corpse>();
		for (int i = 0; i < num11; i++)
		{
			Corpse corpse = new Corpse(11);
			corpseList11.add(corpse);
		}
		corpseList12 = new ArrayList<Corpse>();
		for (int i = 0; i < num12; i++)
		{
			Corpse corpse = new Corpse(12);
			corpseList12.add(corpse);
		}
		corpseList13 = new ArrayList<Corpse>();
		for (int i = 0; i < num13; i++)
		{
			Corpse corpse = new Corpse(13);
			corpseList13.add(corpse);
		}
		corpseList14 = new ArrayList<Corpse>();
		for (int i = 0; i < num14; i++)
		{
			Corpse corpse = new Corpse(14);
			corpseList14.add(corpse);
		}
	}

	public int getWidth()
	{
		return width;
	}

	public void setWidth(int width)
	{
		this.width = width;
	}

	public int getLength()
	{
		return length;
	}

	public void setLength(int length)
	{
		this.length = length;
	}

	public String[][] getBoard()
	{
		return board;
	}

	public void setBoard(String[][] board)
	{
		this.board = board;
	}

	public String getCorpse11()
	{
		return corpse11;
	}

	public void setCorpse11(String corpse11)
	{
		this.corpse11 = corpse11;
	}

	public String getCorpse12()
	{
		return corpse12;
	}

	public void setCorpse12(String corpse12)
	{
		this.corpse12 = corpse12;
	}

	public String getCorpse13()
	{
		return corpse13;
	}

	public void setCorpse13(String corpse13)
	{
		this.corpse13 = corpse13;
	}

	public String getCorpse14()
	{
		return corpse14;
	}

	public void setCorpse14(String corpse14)
	{
		this.corpse14 = corpse14;
	}

	public List<Corpse> getCorpseList11()
	{
		return corpseList11;
	}

	public void setCorpseList11(List<Corpse> corpseList11)
	{
		this.corpseList11 = corpseList11;
	}

	public List<Corpse> getCorpseList12()
	{
		return corpseList12;
	}

	public void setCorpseList12(List<Corpse> corpseList12)
	{
		this.corpseList12 = corpseList12;
	}

	public List<Corpse> getCorpseList13()
	{
		return corpseList13;
	}

	public void setCorpseList13(List<Corpse> corpseList13)
	{
		this.corpseList13 = corpseList13;
	}

	public List<Corpse> getCorpseList14()
	{
		return corpseList14;
	}

	public void setCorpseList14(List<Corpse> corpseList14)
	{
		this.corpseList14 = corpseList14;
	}

	public List<Corpse> getList(int x)
	{
		switch (x)
		{
		case 11:
			return corpseList11;
		case 12:
			return corpseList12;
		case 13:
			return corpseList13;
		case 14:
			return corpseList14;
		default:
			return null;
		}
	}

	public int getNum11()
	{
		return num11;
	}

	public void setNum11(int num11)
	{
		this.num11 = num11;
	}

	public int getNum12()
	{
		return num12;
	}

	public void setNum12(int num12)
	{
		this.num12 = num12;
	}

	public int getNum13()
	{
		return num13;
	}

	public void setNum13(int num13)
	{
		this.num13 = num13;
	}

	public int getNum14()
	{
		return num14;
	}

	public void setNum14(int num14)
	{
		this.num14 = num14;
	}

	public int getNum()
	{
		return num11 + num12 + num13 + num14;
	}

	public boolean isKillAll()
	{
		return (num11 + num12 + num13 + num14) == 0 ? true : false;
	}

}
