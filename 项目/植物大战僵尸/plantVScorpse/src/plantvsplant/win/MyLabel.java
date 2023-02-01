package plantvsplant.win;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

import plantvsplant.Controller;

public class MyLabel extends JLabel
{
	int x;
	int y;
	private static final long serialVersionUID = 1L;
	private final static ImageIcon sunfllower = new ImageIcon(MyLabel.class.getResource("/plantvsplant/plant/2-2.png"));
	private final static ImageIcon pease = new ImageIcon(MyLabel.class.getResource("/plantvsplant/plant/1.png"));
	private final static ImageIcon snowpease = new ImageIcon(MyLabel.class.getResource("/plantvsplant/plant/6.png"));
	private final static ImageIcon bomb = new ImageIcon(MyLabel.class.getResource("/plantvsplant/plant/3.png"));
	private final static ImageIcon potato = new ImageIcon(MyLabel.class.getResource("/plantvsplant/plant/4.png"));
	private final static ImageIcon wood = new ImageIcon(IndexWin.class.getResource("/plantvsplant/plant/wood.jpg"));
	private ImageIcon corpse11 = new ImageIcon(MyLabel.class.getResource("/plantvsplant/corpsel/11.png"));
	private ImageIcon corpse12 = new ImageIcon(MyLabel.class.getResource("/plantvsplant/corpsel/22.png"));
	private ImageIcon corpse13 = new ImageIcon(MyLabel.class.getResource("/plantvsplant/corpsel/33.png"));
	// private ImageIcon corpse14 = new ImageIcon(MyLabel.class.getResource("14.png"));
	private ImageIcon bb = new ImageIcon(MyLabel.class.getResource("/plantvsplant/plant/bb.png"));
	private Controller controller;

	public MyLabel(final Controller controller)
	{
		this.controller = controller;
		// corpse11.setImage(corpse11.getImage().getScaledInstance(80, 80, 1));
		// corpse12.setImage(corpse12.getImage().getScaledInstance(80, 80, 1));
		// corpse13.setImage(corpse13.getImage().getScaledInstance(80, 80, 1));
		setHorizontalAlignment(JLabel.CENTER);
		setIcon(null);
	}

	public MyLabel(int i, int j, final Controller controller)
	{
		this(controller);
		this.x = i;
		this.y = j;
		addMouseListener(new MouseAdapter()
		{
			public void mouseClicked(MouseEvent e)
			{
				if (controller.isUproot())
				{
					controller.uprootPlant(x, y);
				} else
				{
					controller.addPlant(x, y);
				}
			}
		});

	}

	public void setlable(int x)
	{
		switch (x)
		{
		case 21:
			setIcon(bb);
			break;
//			case 31:
//				setIcon(bb);
//				break;
		case 1:
			setIcon(sunfllower);
			break;
		case 3:
			setIcon(pease);
			break;
		case 5:
			setIcon(snowpease);
			break;
		case 2:
			setIcon(bomb);
			break;
		case 4:
			setIcon(potato);
			break;
		case 11:
			setIcon(corpse11);
			break;
		case 12:
			setIcon(corpse12);
			break;
		case 13:
			setIcon(corpse13);
			break;
		case 14:
			setIcon(bomb);
			break;
		default:
			setIcon(wood);
			break;
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

}
