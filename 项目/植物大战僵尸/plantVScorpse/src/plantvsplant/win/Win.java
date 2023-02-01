
package plantvsplant.win;

import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;

import plantvsplant.Controller;
import plantvsplant.entity.Plant;

public class Win extends JFrame
{
	private static final long serialVersionUID = 1L;
	private Controller controller;
	private JLayeredPane mainwin = new JLayeredPane();
	private int plantid;
	private JLabel bombBloom;
	private MyLabel[][] boardlbl;
	private JLabel sunnum;
	private JLabel card0;
	private JLabel card1;
	private JLabel card2;
	private JLabel card3;
	private JLabel card4;
	private MyLabel plant;
	private Plant psunflower = new Plant(1);
	private Plant pbomb = new Plant(2);
	private Plant ppease = new Plant(3);
	private Plant ppotato = new Plant(4);
	private Plant psnowpease = new Plant(5);
	private List<Plant> plantlist = new ArrayList<Plant>();
	private Map<Integer, JLabel> bulletMap = new HashMap<Integer, JLabel>();
	private MyLabel corpse;
	private JLabel car;
	private Map<Integer, JLabel> corpseMap = new HashMap<Integer, JLabel>();
	private Map<Integer, JLabel> carMap = new HashMap<Integer, JLabel>();
	private JLabel scoop;
	private ImageIcon carrun = new ImageIcon(Win.class.getResource("/plantvsplant/plant/car.png"));
	private ImageIcon bb = new ImageIcon(Win.class.getResource("/plantvsplant/plant/bb.png"));
	private ImageIcon sunner = new ImageIcon(Win.class.getResource("/plantvsplant/plant/sun.png"));
	private ImageIcon bombIco = new ImageIcon(Win.class.getResource("/plantvsplant/plant/bb.png"));
	private JLabel bomp;
	private JLabel sun;
	private int width;
	private int height;
	private static Win win;

	private Win(Controller controller)
	{
		this.controller = controller;
		plantid = 0;
		height = 700;
		width = 1000;
		plantlist.add(psunflower);
		plantlist.add(pbomb);
		plantlist.add(ppease);
		plantlist.add(ppotato);
		plantlist.add(psnowpease);
		init();
	}

	public static Win getWin(Controller controller)
	{
		if (win == null)
		{
			win = new Win(controller);
		}
		return win;
	}

	private void init()
	{
		sunner.setImage(sunner.getImage().getScaledInstance(80, 80, 1));
		bb.setImage(bb.getImage().getScaledInstance(25, 25, 1));
		setTitle("植物大战僵尸");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(width, height);
		setContentPane(createContent());
		Toolkit toolkit = Toolkit.getDefaultToolkit();
		Dimension d = toolkit.getScreenSize();
		setLocation((d.width - getWidth()) / 2, (d.height - getHeight()) / 2);
	}

	private Container createContent()
	{
		ImageIcon back = new ImageIcon(Win.class.getResource("/plantvsplant/plant/background.png"));
		ImageIcon wood = new ImageIcon(Win.class.getResource("/plantvsplant/plant/wood.jpg"));
		bombIco.setImage(bombIco.getImage().getScaledInstance(250, 250, 1));
		back.setImage(back.getImage().getScaledInstance(1320, 700, 1));

		JLabel background = new JLabel(back);
		background.setBounds(0, 0, width, height);
		// mainwin.add(background,JLayeredPane.DEFAULT_LAYER);
		mainwin.add(background, JLayeredPane.PALETTE_LAYER);
		JPanel board = new JPanel(new GridLayout(controller.getX(), 28));
		boardlbl = new MyLabel[controller.getX()][28];
		// 第一列堆放小车
		for (int i = 0; i < boardlbl.length; i++)
		{
			boardlbl[i][0] = new MyLabel(i, 0, controller);
			boardlbl[i][0].setIcon(wood);
		}
		// 草坪
		// wood.setImage(wood.getImage().getScaledInstance(width, height, hints))
		for (int i = 0; i < boardlbl.length; i++)
		{
			for (int j = 1; j < boardlbl[i].length; j++)
			{
				boardlbl[i][j] = new MyLabel(i, j, controller);
				boardlbl[i][j].setIcon(wood);
			}
		}
		// 地面
//		for(int i=0;i<boardlbl.length;i++){
//			int j=boardlbl[i].length-1;
//			boardlbl[i][j]=new MyLabel(i,j,controller);
//			boardlbl[i][j].setIcon(wood);
//		}
		for (int i = 0; i < boardlbl.length; i++)
		{
			for (int j = 0; j < boardlbl[i].length; j++)
			{
				board.add(boardlbl[i][j]);
			}
		}
		board.setBounds(40, 85, width - 100, height - 100);
		width = width - 100;
		height = height - 100;
		mainwin.add(board, JLayeredPane.DEFAULT_LAYER);
		// mainwin.add(board,JLayeredPane.PALETTE_LAYER);
		createCard();
		return mainwin;
	}

	private void createCard()
	{// 卡片框
		ImageIcon cardboard = new ImageIcon(Win.class.getResource("/plantvsplant/card/cardboard.png"));
		JLabel cardboardl = new JLabel(cardboard);
		cardboardl.setBounds(0, 2, 650, 98);
		mainwin.add(cardboardl, JLayeredPane.MODAL_LAYER);
		ImageIcon pic0 = new ImageIcon(IndexWin.class.getResource("/plantvsplant/card/2.jpg"));
		ImageIcon pic1 = new ImageIcon(IndexWin.class.getResource("/plantvsplant/card/3.jpg"));
		ImageIcon pic2 = new ImageIcon(IndexWin.class.getResource("/plantvsplant/card/1.jpg"));
		ImageIcon pic3 = new ImageIcon(IndexWin.class.getResource("/plantvsplant/card/4.jpg"));
		ImageIcon pic4 = new ImageIcon(IndexWin.class.getResource("/plantvsplant/card/6.jpg"));
		// 阳光
		sunnum = new JLabel(Integer.toString(controller.getSunValue()));
		sunnum.setBounds(30, 60, 100, 50);
		mainwin.add(sunnum, JLayeredPane.POPUP_LAYER);
		// 卡片框
		card0 = new JLabel(pic0);
		card1 = new JLabel(pic1);
		card2 = new JLabel(pic2);
		card3 = new JLabel(pic3);
		card4 = new JLabel(pic4);
		card0.addMouseListener(new MouseAdapter()
		{

			public void mouseClicked(MouseEvent e)
			{
				if (card0.isEnabled() && 0 == plantid)
				{
					plantid = 1;
					controller.putSunValue(-50);
				} else
				{
					if (plantid == 1)
					{
						controller.putSunValue(50);
						plantid = 0;
					}
				}
			}
		});
		card1.addMouseListener(new MouseAdapter()
		{

			public void mouseClicked(MouseEvent e)
			{

				if (card1.isEnabled() && 0 == plantid)
				{
					plantid = 2;
					controller.putSunValue(-150);
				} else
				{
					if (!card1.isEnabled() && plantid == 2)
					{
						controller.putSunValue(150);
						plantid = 0;
					}
				}

			}
		});
		card2.addMouseListener(new MouseAdapter()
		{

			public void mouseClicked(MouseEvent e)
			{
				if (card2.isEnabled() && 0 == plantid)
				{
					plantid = 3;
					controller.putSunValue(-100);
				} else
				{
					if (!card2.isEnabled() && plantid == 3)
					{
						controller.putSunValue(100);
						plantid = 0;
					}
				}
			}
		});
		card3.addMouseListener(new MouseAdapter()
		{

			public void mouseClicked(MouseEvent e)
			{
				if (card3.isEnabled() && 0 == plantid)
				{
					plantid = 4;
					controller.putSunValue(-50);
				} else
				{
					if (!card3.isEnabled() && plantid == 4)
					{
						controller.putSunValue(50);
						plantid = 0;
					}
				}
			}
		});
		card4.addMouseListener(new MouseAdapter()
		{

			public void mouseClicked(MouseEvent e)
			{
				if (card4.isEnabled() && 0 == plantid)
				{
					plantid = 5;
					controller.putSunValue(-150);
				} else
				{
					if (!card4.isEnabled() && plantid == 5)
					{
						controller.putSunValue(150);
						plantid = 0;
					}
				}
			}
		});
		card0.setBounds(100, 3, 50, 90);
		mainwin.add(card0, JLayeredPane.POPUP_LAYER);
		card1.setBounds(200, 3, 50, 90);
		mainwin.add(card1, JLayeredPane.POPUP_LAYER);
		card2.setBounds(300, 3, 50, 90);
		mainwin.add(card2, JLayeredPane.POPUP_LAYER);
		card3.setBounds(400, 3, 50, 90);
		mainwin.add(card3, JLayeredPane.POPUP_LAYER);
		card4.setBounds(500, 3, 50, 90);
		mainwin.add(card4, JLayeredPane.POPUP_LAYER);
		// 铲子
		ImageIcon scoopr = new ImageIcon(Win.class.getResource("/plantvsplant/plant/chanzi.png"));
		scoop = new JLabel(scoopr);
		scoop.addMouseListener(new MouseAdapter()
		{

			public void mouseClicked(MouseEvent e)
			{
				controller.setUproot(true);
				scoop.setEnabled(false);
			}
		});
		scoop.setBounds(658, 0, 95, 95);
		mainwin.add(scoop, JLayeredPane.POPUP_LAYER);
	}

	public void putBomb(int i, int j)
	{
		bombBloom = new JLabel(bombIco);
		bombBloom.setVisible(true);
		bombBloom.setBounds(45 + 32 * (j - 3), 90 + 40 * (i - 3), 250, 250);
		mainwin.add(bombBloom, JLayeredPane.POPUP_LAYER);
	}

	public JLabel getSun(int x, int y)
	{// 产生阳光
		sun = new JLabel(sunner);
		sun.setBounds(x, y, 80, 80);
		mainwin.add(sun, JLayeredPane.POPUP_LAYER);
		return sun;
	}

	public void putPlant(int x, int y)
	{// 放置植物
		if (plantid == 0)
			return;
		plant = new MyLabel(controller);
		plant.setVisible(true);
		plant.setlable(plantid);
		plant.setBounds(45 + 32 * (y - 1), 90 + 40 * (x - 1), width / 28 * 2, height / 15 * 2);
		// plant.setBounds((width/31)*(y-1),70+(height/15)*(x-1),width/31*3,height/15*3);
		if (plantid == 2)
		{
			try
			{
				Thread.sleep(1000);
				// controller.bombScorpse(x, y);
				// plant.setVisible(false);
			} catch (Exception e)
			{
				e.printStackTrace();
			}
		}
		mainwin.add(plant, JLayeredPane.MODAL_LAYER);
	}

	public void putCorpse(int x, int y, int type)
	{// 放置僵尸
		corpse = new MyLabel(x, y, controller);
		corpse.setlable(11);
		corpseMap.put(100 * x + y, corpse);
		corpseMap.get(100 * x + y).setVisible(true);
		corpse.setBounds(45 + 32 * (y - 1), 90 + 40 * (x - 1), width / 28 * 2, height / 15 * 2);
		mainwin.add(corpse, JLayeredPane.POPUP_LAYER);
	}

	public void putBomp(int x, int y, int type)
	{// 发射炮弹
		switch (type)
		{
		case 0:
			bomp = new JLabel(bb);
			bulletMap.put(x * 100 + y, bomp);
			bulletMap.get(100 * x + y).setVisible(true);
			// System.out.println(100*x+y+"putbomp"+bulletMap.get(100*x+y));
			bomp.setBounds(45 + 32 * (y - 1), 90 + 40 * (x - 1), 25, 25);
			mainwin.add(bomp, JLayeredPane.POPUP_LAYER);
			break;
		case 1:
			car = new JLabel(carrun);
			carMap.put(x * 100 + y, car);
			carMap.get(100 * x + y).setVisible(true);
			car.setBounds(45 + 32 * (y - 1), 90 + 40 * (x - 1), width / 28 * 2, height / 15 * 2);
			mainwin.add(car, JLayeredPane.POPUP_LAYER);
			break;
		}
	}

	public Plant getPlant(int x)
	{
		return plantlist.get(x);
	}

	public Map<Integer, JLabel> getBulletMap()
	{
		return bulletMap;
	}

	public MyLabel getCorpse()
	{
		return corpse;
	}

	public void setCorpse(MyLabel corpse)
	{
		this.corpse = corpse;
	}

	public Map<Integer, JLabel> getCorpseMap()
	{
		return corpseMap;
	}

	public Plant getPsunflower()
	{
		return psunflower;
	}

	public Plant getPpotato()
	{
		return ppotato;
	}

	public Plant getPpease()
	{
		return ppease;
	}

	public Plant getPbomb()
	{
		return pbomb;
	}

	public Plant getPsnowpease()
	{
		return psnowpease;
	}

	public JLabel getScoop()
	{
		return scoop;
	}

	public JLabel getBomp()
	{
		return bomp;
	}

	public MyLabel getPlant()
	{
		return plant;
	}

	public void toShow()
	{
		setVisible(true);
	}

	public int getPlantid()
	{
		return plantid;
	}

	public void setPlantid(int plantid)
	{
		this.plantid = plantid;
	}

	public MyLabel[][] getBoardlbl()
	{
		return boardlbl;
	}

	public JLayeredPane getMainwin()
	{
		return mainwin;
	}

	public JLabel getSunnum()
	{
		return sunnum;
	}

	public JLabel getCard0()
	{
		return card0;
	}

	public void setCard0(JLabel card0)
	{
		this.card0 = card0;
	}

	public JLabel getCard1()
	{
		return card1;
	}

	public void setCard1(JLabel card1)
	{
		this.card1 = card1;
	}

	public JLabel getCard2()
	{
		return card2;
	}

	public void setCard2(JLabel card2)
	{
		this.card2 = card2;
	}

	public JLabel getCard3()
	{
		return card3;
	}

	public void setCard3(JLabel card3)
	{
		this.card3 = card3;
	}

	public JLabel getCard4()
	{
		return card4;
	}

	public void setCard4(JLabel card4)
	{
		this.card4 = card4;
	}

	public JLabel getBombBloom()
	{
		return bombBloom;
	}

	public void setBombBloom(JLabel bombBloom)
	{
		this.bombBloom = bombBloom;
	}

	public Map<Integer, JLabel> getCarMap()
	{
		return carMap;
	}

	public void setCarMap(Map<Integer, JLabel> carMap)
	{
		this.carMap = carMap;
	}

	public void setBulletMap(Map<Integer, JLabel> bulletMap)
	{
		this.bulletMap = bulletMap;
	}

	public void setCorpseMap(Map<Integer, JLabel> corpseMap)
	{
		this.corpseMap = corpseMap;
	}

}