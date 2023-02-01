package tools;

import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.Polygon;
import java.awt.Stroke;
import java.awt.Toolkit;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.Popup;
import javax.swing.PopupFactory;
import javax.swing.SwingUtilities;
import javax.swing.event.AncestorEvent;
import javax.swing.event.AncestorListener;

/**
 * @Author 作者
 * @Description 说明：日历选择器
 * @Date 时间：2020-11-21
 */
@SuppressWarnings("serial")
public class ChooserDate extends JPanel {
	// 设置日期显示格式
	private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy年MM月dd日");
	// 设置字体
	private Font font = new Font("宋体", Font.PLAIN, 12);
	// 面板
	private JPanel monthPanel;// 月历
	private JP1 jp1;
	private JP2 jp2;

	//////////////////
	public JLabel showDate;
	private boolean isShow = false;
	private Popup pop;
	private Date initDate;
	private final LabelManager labelManager = new LabelManager();

	/*
	 * 1 创建 Calendar对象不能使用 new 关键字，
	 * 
	 * 2 因为 Calendar类是一个抽象类，
	 * 
	 * 3 但是它提供了一个getInstance()方法来获得Calendar 类的对象。
	 * 
	 * getInstance()方法返回一个 Calendar的对象，其日历字段已由当前日期和时间初始化。
	 * 
	 * Calendar对象可以调用set()方法将日历翻到任何一个时间，当参数year取负数时表示公前。
	 * 
	 * Calendar对象调用get()方法还可以获取有关年、月、日等时间信息。
	 */
	private Calendar select;
	private Calendar now = Calendar.getInstance();

	public ChooserDate() {
		this(new Date());
	}

	public ChooserDate(Date date) {
		initDate = date;
		select = Calendar.getInstance();
		select.setTime(initDate);
		initPanel();
		initLabel();
	}

	public void setEnabled(boolean b) {
		super.setEnabled(b);
		showDate.setEnabled(b);
	}

	/**
	 * 得到当前选择框的日期
	 */
	public Date getDate() {
		return select.getTime();
	}

	// 根据初始化的日期,初始化面板
	private void initPanel() {

		monthPanel = new JPanel(new BorderLayout());
		monthPanel.setBorder(BorderFactory.createLineBorder(Color.red));

		// 上方
		JPanel topPanel = new JPanel(new AfAnyWhere());
		topPanel.setPreferredSize(new Dimension(295, 50));
		topPanel.setBackground(Color.red);
		JLabel label = new JLabel("星期日 星期一 星期二 星期三 星期四 星期五 星期六");
		label.setFont(font);
		label.setForeground(Color.white);
		topPanel.add(label, new AfMargin(25, -1, -1, -1));
		topPanel.add(jp1 = new JP1(), new AfMargin(0, -1, -1, -1));

		// 下方
		JPanel bottomPanel = new JPanel();
		bottomPanel.setPreferredSize(new Dimension(295, 20));
		bottomPanel.setBackground(new Color(160, 185, 215));
		// 设置日期格式
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy年MM月dd日");
		final JLabel today = new JLabel("今天: " + dateFormat.format(new Date()));
		today.setToolTipText("点击回到今天日期");
		bottomPanel.add(today, BorderLayout.CENTER);

		// 事件处理
		today.addMouseListener(new MouseAdapter() {
			// 鼠标进入
			public void mouseEntered(MouseEvent me) {
				// 设置光标类型为手状光标类型
				today.setCursor(new Cursor(Cursor.HAND_CURSOR));
				today.setForeground(Color.RED);
			}

			// 鼠标退出
			public void mouseExited(MouseEvent me) {
				today.setForeground(Color.BLACK);
			}

			// 鼠标按下
			public void mousePressed(MouseEvent me) {
				today.setForeground(Color.BLACK);
				select.setTime(new Date());
				refresh();
				commit();
			}

			// 鼠标释放
			public void mouseReleased(MouseEvent me) {
				today.setForeground(Color.BLACK);
			}
		});

		monthPanel.add(jp2 = new JP2(), BorderLayout.CENTER);
		monthPanel.add(topPanel, BorderLayout.NORTH);
		monthPanel.add(bottomPanel, BorderLayout.SOUTH);

		this.addAncestorListener(new AncestorListener() {
			public void ancestorAdded(AncestorEvent event) {
			}

			public void ancestorRemoved(AncestorEvent event) {
			}

			// 只要祖先组件一移动,马上就让popup消失
			public void ancestorMoved(AncestorEvent event) {
				if (pop != null) {
					isShow = false;
					pop.hide();
					pop = null;
				}
			}
		});
	}

	// 初始化标签
	private void initLabel() {
		showDate = new JLabel(dateFormat.format(initDate));
		showDate.setRequestFocusEnabled(true);
		showDate.setFont(new Font("微软雅黑", 0, 15));
		this.setBackground(Color.WHITE);
		this.add(showDate, BorderLayout.CENTER);
		this.setPreferredSize(new Dimension(150, 30));
		this.setBorder(BorderFactory.createLineBorder(Color.GRAY));

		showDate.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent me) {
				showDate.requestFocusInWindow();
			}
		});

		showDate.addMouseListener(new MouseAdapter() {
			public void mouseEntered(MouseEvent me) {
				if (showDate.isEnabled()) {
					showDate.setCursor(new Cursor(Cursor.HAND_CURSOR));
					showDate.setForeground(Color.RED);
				}
			}

			public void mouseExited(MouseEvent me) {
				if (showDate.isEnabled()) {
					showDate.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
					showDate.setForeground(Color.BLACK);
				}
			}

			public void mousePressed(MouseEvent me) {
				if (showDate.isEnabled()) {
					showDate.setForeground(Color.CYAN);
					if (isShow) {
						if (pop != null) {
							isShow = false;
							pop.hide();
							pop = null;
						}
					} else {
						showPanel(showDate);
					}
				}
			}

			public void mouseReleased(MouseEvent me) {
				if (showDate.isEnabled()) {
					showDate.setForeground(Color.BLACK);
				}
			}
		});
		showDate.addFocusListener(new FocusListener() {
			public void focusLost(FocusEvent e) {
				if (pop != null) {
					isShow = false;
					pop.hide();
					pop = null;
				}
			}

			public void focusGained(FocusEvent e) {

			}
		});
	}

	// 根据新的日期刷新
	private void refresh() {
		jp1.updateDate();
		jp2.updateDate();
		SwingUtilities.updateComponentTreeUI(this);
	}

	// 提交日期
	private void commit() {
		System.out.println("选中的日期是：" + dateFormat.format(select.getTime()));
		showDate.setText(dateFormat.format(select.getTime()));
		if (pop != null) {
			isShow = false;
			pop.hide();
			pop = null;
		}
	}

	@Override
	public String toString() {
		// TODO 自动生成的方法存根
		return dateFormat.format(select.getTime());
	}

	private void showPanel(Component owner) {
		if (pop != null) {
			pop.hide();
		}
		Point show = new Point(0, showDate.getHeight());
		SwingUtilities.convertPointToScreen(show, showDate);
		Dimension size = Toolkit.getDefaultToolkit().getScreenSize();
		int x = show.x;
		int y = show.y;
		if (x < 0) {
			x = 0;
		}
		if (x > size.width - 295) {
			x = size.width - 295;
		}
		if (y < size.height - 170) {
		} else {
			y -= 188;
		}
		pop = PopupFactory.getSharedInstance().getPopup(owner, monthPanel, x, y);
		pop.show();
		isShow = true;
	}

	/**
	 * @Author 作者
	 * @Description 说明：面板一
	 * @Date 时间：2020-11-21
	 */
	private class JP1 extends JPanel {

		JLabel left, right, center;

		public JP1() {
			super(new AfAnyWhere());
			this.setBackground(Color.pink);
			initJP1();
		}

		private void initJP1() {

			left = new JLabel(" << ", JLabel.CENTER);
			left.setToolTipText("上一月");
			right = new JLabel(" >> ", JLabel.CENTER);
			right.setToolTipText("下一月");

			center = new JLabel("", JLabel.CENTER);

			updateDate();

			this.add(left, new AfMargin(-1, 25, -1, -1));
			this.add(center, AfMargin.CENTER);
			this.add(right, new AfMargin(-1, -1, -1, 25));

			this.setPreferredSize(new Dimension(295, 25));

			left.addMouseListener(new MouseAdapter() {
				public void mouseEntered(MouseEvent me) {
					left.setCursor(new Cursor(Cursor.HAND_CURSOR));
					left.setForeground(Color.RED);
				}

				public void mouseExited(MouseEvent me) {
					left.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
					left.setForeground(Color.BLACK);
				}

				public void mousePressed(MouseEvent me) {
					select.add(Calendar.MONTH, -1);
					left.setForeground(Color.WHITE);
					refresh();
				}

				public void mouseReleased(MouseEvent me) {
					left.setForeground(Color.BLACK);
				}
			});
			right.addMouseListener(new MouseAdapter() {
				public void mouseEntered(MouseEvent me) {
					right.setCursor(new Cursor(Cursor.HAND_CURSOR));
					right.setForeground(Color.RED);
				}

				public void mouseExited(MouseEvent me) {
					right.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
					right.setForeground(Color.BLACK);
				}

				public void mousePressed(MouseEvent me) {
					select.add(Calendar.MONTH, 1);
					right.setForeground(Color.WHITE);
					refresh();
				}

				public void mouseReleased(MouseEvent me) {
					right.setForeground(Color.BLACK);
				}
			});
		}

		private void updateDate() {
			center.setText(select.get(Calendar.YEAR) + "年" + (select.get(Calendar.MONTH) + 1) + "月");
		}
	}

	/**
	 * @Author 作者
	 * @Description 说明：面板二
	 * @Date 时间：2020-11-21
	 */
	private class JP2 extends JPanel {
		// 构造方法
		public JP2() {
			super(new GridLayout(6, 7));
			this.setBackground(Color.yellow);
			this.setPreferredSize(new Dimension(295, 100));
			updateDate();// 更新日期
		}

		public void updateDate() {
			this.removeAll();// 移除面板所有组件
			labelManager.clear();// 标签管理器的清空list

			Date temp = select.getTime();// 通过使用Date实例获取此Calendar的时间值
			Calendar select = Calendar.getInstance();// 当前时间
			select.setTime(temp);

			select.set(Calendar.DAY_OF_MONTH, 1);
			int index = select.get(Calendar.DAY_OF_WEEK);
			int sum = (index == 1 ? 8 : index);
			select.add(Calendar.DAY_OF_MONTH, 0 - sum);

			for (int i = 0; i < 42; i++) {
				select.add(Calendar.DAY_OF_MONTH, 1);
				labelManager.addLabel(new MyLabel(select.get(Calendar.YEAR), select.get(Calendar.MONTH), select.get(Calendar.DAY_OF_MONTH)));
			}
			for (MyLabel my : labelManager.getLabels()) {
				this.add(my);
			}
			select.setTime(temp);
		}
	}

	/**
	 * @Author 作者
	 * @Description 说明：我的标签
	 * @Date 时间：2020-11-21
	 */
	private class MyLabel extends JLabel implements Comparator<MyLabel>, MouseListener, MouseMotionListener {

		private static final long serialVersionUID = 1L;

		private int year, month, day;// 年、月、日
		private boolean isSelected;

		public MyLabel(int year, int month, int day) {
			super("" + day, JLabel.CENTER);
			this.year = year;
			this.day = day;
			this.month = month;
			this.addMouseListener(this);
			this.addMouseMotionListener(this);
			this.setFont(font);
			if (month == select.get(Calendar.MONTH)) {
				this.setForeground(Color.BLACK);
			} else {
				this.setForeground(Color.LIGHT_GRAY);
			}
			if (day == select.get(Calendar.DAY_OF_MONTH)) {
				this.setBackground(new Color(160, 185, 215));
			} else {
				this.setBackground(Color.WHITE);
			}
		}

		public boolean getIsSelected() {
			return isSelected;
		}

		public void setSelected(boolean b, boolean isDrag) {
			isSelected = b;
			if (b && !isDrag) {
				int temp = select.get(Calendar.MONTH);
				select.set(year, month, day);
				if (temp == month) {
					SwingUtilities.updateComponentTreeUI(jp2);
				} else {
					refresh();
				}
			}
			this.repaint();
		}

		protected void paintComponent(Graphics g) {
			if (day == select.get(Calendar.DAY_OF_MONTH) && month == select.get(Calendar.MONTH)) {
				// 如果当前日期是选择日期,则高亮显示
				g.setColor(new Color(160, 185, 215));
				g.fillRect(0, 0, getWidth(), getHeight());
			}
			if (year == now.get(Calendar.YEAR) && month == now.get(Calendar.MONTH) && day == now.get(Calendar.DAY_OF_MONTH)) {
				// 如果日期和当前日期一样,则用红框
				Graphics2D gd = (Graphics2D) g;
				gd.setColor(Color.RED);
				Polygon p = new Polygon();
				p.addPoint(0, 0);
				p.addPoint(getWidth() - 1, 0);
				p.addPoint(getWidth() - 1, getHeight() - 1);
				p.addPoint(0, getHeight() - 1);
				gd.drawPolygon(p);
			}
			if (isSelected) {// 如果被选中了就画出一个虚线框出来
				Stroke s = new BasicStroke(1.0f, BasicStroke.CAP_SQUARE, BasicStroke.JOIN_BEVEL, 1.0f, new float[] { 2.0f, 2.0f }, 1.0f);
				Graphics2D gd = (Graphics2D) g;
				gd.setStroke(s);
				gd.setColor(Color.BLACK);
				Polygon p = new Polygon();
				p.addPoint(0, 0);
				p.addPoint(getWidth() - 1, 0);
				p.addPoint(getWidth() - 1, getHeight() - 1);
				p.addPoint(0, getHeight() - 1);
				gd.drawPolygon(p);
			}
			super.paintComponent(g);
		}

		public boolean contains(Point p) {
			return this.getBounds().contains(p);
		}

		private void update() {
			repaint();
		}

		public void mouseClicked(MouseEvent e) {
		}

		public void mousePressed(MouseEvent e) {
			isSelected = true;
			update();
		}

		public void mouseReleased(MouseEvent e) {
			Point p = SwingUtilities.convertPoint(this, e.getPoint(), jp2);
			labelManager.setSelect(p, false);
			commit();
		}

		public void mouseEntered(MouseEvent e) {
		}

		public void mouseExited(MouseEvent e) {
		}

		public void mouseDragged(MouseEvent e) {
			Point p = SwingUtilities.convertPoint(this, e.getPoint(), jp2);
			labelManager.setSelect(p, true);
		}

		public void mouseMoved(MouseEvent e) {
		}

		public int compare(MyLabel o1, MyLabel o2) {
			Calendar c1 = Calendar.getInstance();
			c1.set(o1.year, o2.month, o1.day);
			Calendar c2 = Calendar.getInstance();
			c2.set(o2.year, o2.month, o2.day);
			return c1.compareTo(c2);
		}
	}

	/**
	 * @Author 作者
	 * @Description 说明：标签管理器
	 * @Date 时间：2020-11-21
	 */
	private class LabelManager {
		private List<MyLabel> list;

		public LabelManager() {
			list = new ArrayList<MyLabel>();
		}

		public List<MyLabel> getLabels() {
			return list;
		}

		public void addLabel(MyLabel my) {
			list.add(my);
		}

		public void clear() {
			list.clear();
		}

//		public void setSelect(MyLabel my, boolean b) {
//			for (MyLabel m : list) {
//				if (m.equals(my)) {
//					m.setSelected(true, b);
//				} else {
//					m.setSelected(false, b);
//				}
//			}
//		}

		public void setSelect(Point p, boolean b) {
			// 如果是拖动,则要优化一下,以提高效率
			if (b) {
				// 表示是否能返回,不用比较完所有的标签,能返回的标志就是把上一个标签和
				// 将要显示的标签找到了就可以了
				boolean findPrevious = false, findNext = false;
				for (MyLabel m : list) {
					if (m.contains(p)) {
						findNext = true;
						if (m.getIsSelected()) {
							findPrevious = true;
						} else {
							m.setSelected(true, b);
						}
					} else if (m.getIsSelected()) {
						findPrevious = true;
						m.setSelected(false, b);
					}
					if (findPrevious && findNext) {
						return;
					}
				}
			} else {
				MyLabel temp = null;
				for (MyLabel m : list) {
					if (m.contains(p)) {
						temp = m;
					} else if (m.getIsSelected()) {
						m.setSelected(false, b);
					}
				}
				if (temp != null) {
					temp.setSelected(true, b);
				}
			}
		}
	}

//	public static void main(String[] args) {
//		final ChooserDate dateChooser = new ChooserDate();
//		JFrame f = new JFrame("test");
//		f.add(dateChooser);
//		f.pack();
//		f.setLocationRelativeTo(null);
//		f.setVisible(true);
//		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//	}
}
