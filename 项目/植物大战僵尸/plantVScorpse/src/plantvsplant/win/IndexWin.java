package plantvsplant.win;

import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JTextArea;

import plantvsplant.Controller;

public class IndexWin extends JFrame
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	Controller controller;
	private String name;

	public IndexWin(Controller controller)
	{
		this.controller = controller;
		name = "百鬼夜行";
		init();
	}

	private void init()
	{
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(740, 590);
		setContentPane(createPanel());
		Toolkit toolkit = Toolkit.getDefaultToolkit();
		Dimension d = toolkit.getScreenSize();
		setLocation((d.width - getWidth()) / 2, (d.height - getHeight()) / 2);
	}

	private Container createPanel()
	{
		JLayeredPane main = new JLayeredPane();
		main.setVisible(true);
		// 背景层
		ImageIcon background = new ImageIcon(IndexWin.class.getResource("/plantvsplant/index/SelectorScreen_BG_Right.jpg"));
		JLabel lbl = new JLabel(background);
		lbl.setBounds(0, 0, background.getIconWidth(), background.getIconHeight());
		main.add(lbl, 20);
		// 填写名字层
		ImageIcon wood = new ImageIcon(IndexWin.class.getResource("/plantvsplant/index/SelectorScreen_WoodSign1.png"));
		final ImageIcon woodsign1 = new ImageIcon(IndexWin.class.getResource("/plantvsplant/index/SelectorScreen_WoodSign2.png"));
		final ImageIcon woodsign2 = new ImageIcon(IndexWin.class.getResource("/plantvsplant/index/SelectorScreen_WoodSign2_press.png"));
		JLabel lblnorth = new JLabel(wood);
		final JLabel namer = new JLabel(name);
		final JTextArea namer1 = new JTextArea();
		namer.setFont(new Font("Mousespace", 1, 20));
		namer1.setVisible(false);
		final JLabel woodsign = new JLabel(woodsign1);
		woodsign.addMouseListener(new MouseAdapter()
		{

			public void mouseClicked(MouseEvent e)
			{
				namer1.setVisible(true);
			}

			public void mouseEntered(MouseEvent e)
			{
				woodsign.setIcon(woodsign2);

			}

			public void mouseExited(MouseEvent e)
			{
				woodsign.setIcon(woodsign1);

			}
		});
		namer1.addMouseListener(new MouseAdapter()
		{
			public void mouseExited(MouseEvent e)
			{
				if (!"".equals(namer1.getText()))
				{
					name = namer1.getText();
					namer.setText(name);
					namer1.setVisible(false);
				}
			}
		});
		lblnorth.setBounds(0, 0, wood.getIconWidth(), wood.getIconHeight());
		woodsign.setBounds(0, 140, woodsign1.getIconWidth(), woodsign1.getIconHeight());
		namer.setBounds(100, 95, 100, 20);
		namer1.setBounds(100, 95, 100, 20);
		main.add(lblnorth, 0);
		main.add(woodsign, 0);
		main.add(namer, 0);
		main.add(namer1, 0);
		// 游戏模式选项层
		final ImageIcon img1 = new ImageIcon(IndexWin.class.getResource("/plantvsplant/index/SelectorScreen_Adventure_highlight.png"));
		final ImageIcon img11 = new ImageIcon(IndexWin.class.getResource("/plantvsplant/index/SelectorScreen_StartAdventure_Highlight.png"));
		ImageIcon img2 = new ImageIcon(IndexWin.class.getResource("/plantvsplant/index/SelectorScreen_Vasebreaker_button.png"));
		ImageIcon img3 = new ImageIcon(IndexWin.class.getResource("/plantvsplant/index/SelectorScreen_Challenges_button.png"));
		final JLabel lbl1 = new JLabel(img1);
		lbl1.addMouseListener(new MouseAdapter()
		{

			public void mouseClicked(MouseEvent e)
			{
				controller.start();

			}

			public void mouseEntered(MouseEvent e)
			{
				lbl1.setIcon(img11);

			}

			public void mouseExited(MouseEvent e)
			{
				lbl1.setIcon(img1);

			}
		});
		JLabel lbl2 = new JLabel(img2);
		JLabel lbl3 = new JLabel(img3);
		lbl1.setBounds(330, 50, img1.getIconWidth(), img1.getIconHeight());
		lbl2.setBounds(340, 150, img2.getIconWidth(), img2.getIconHeight());
		lbl3.setBounds(340, 250, img3.getIconWidth(), img3.getIconHeight());
		main.add(lbl1, 0);
		main.add(lbl2, 0);
		main.add(lbl3, 0);
		// 选项，帮助，退出
		final ImageIcon option1 = new ImageIcon(IndexWin.class.getResource("/plantvsplant/index/SelectorScreen_Options1.png"));
		final ImageIcon help1 = new ImageIcon(IndexWin.class.getResource("/plantvsplant/index/SelectorScreen_Help1.png"));
		final ImageIcon quit1 = new ImageIcon(IndexWin.class.getResource("/plantvsplant/index/SelectorScreen_Quit1.png"));
		final ImageIcon option2 = new ImageIcon(IndexWin.class.getResource("/plantvsplant/index/SelectorScreen_Options2.png"));
		final ImageIcon help2 = new ImageIcon(IndexWin.class.getResource("/plantvsplant/index/SelectorScreen_Help2.png"));
		final ImageIcon quit2 = new ImageIcon(IndexWin.class.getResource("/plantvsplant/index/SelectorScreen_Quit2.png"));
		final JLabel option = new JLabel(option1);
		final JLabel help = new JLabel(help1);
		final JLabel quit = new JLabel(quit1);
		option.setBounds(490, 450, option1.getIconWidth(), option1.getIconHeight());
		help.setBounds(580, 480, help1.getIconWidth(), help1.getIconHeight());
		quit.setBounds(650, 470, quit1.getIconWidth(), quit1.getIconHeight());
		option.addMouseListener(new MouseAdapter()
		{

			public void mouseClicked(MouseEvent e)
			{
				// TODO Auto-generated method stub

			}

			public void mouseEntered(MouseEvent e)
			{
				option.setIcon(option2);

			}

			public void mouseExited(MouseEvent e)
			{
				option.setIcon(option1);

			}
		});
		help.addMouseListener(new MouseAdapter()
		{

			public void mouseClicked(MouseEvent e)
			{
				// TODO Auto-generated method stub

			}

			public void mouseEntered(MouseEvent e)
			{
				help.setIcon(help2);

			}

			public void mouseExited(MouseEvent e)
			{
				help.setIcon(help1);

			}
		});
		quit.addMouseListener(new MouseAdapter()
		{

			public void mouseClicked(MouseEvent e)
			{
				System.exit(0);

			}

			public void mouseEntered(MouseEvent e)
			{
				quit.setIcon(quit2);

			}

			public void mouseExited(MouseEvent e)
			{
				quit.setIcon(quit1);

			}
		});
		main.add(option, 0);
		main.add(help, 0);
		main.add(quit, 0);
		return main;
	}

	public void ToShow()
	{
		setVisible(true);
	}
}
