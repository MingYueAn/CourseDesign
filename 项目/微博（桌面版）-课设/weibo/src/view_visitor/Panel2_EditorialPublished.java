package view_visitor;

import java.awt.Color;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.Transferable;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.event.UndoableEditEvent;
import javax.swing.event.UndoableEditListener;
import javax.swing.undo.CannotUndoException;
import javax.swing.undo.UndoManager;

import control.Insert;
import data.GlobalVar;
import model.Weibo;
import tools.AfAnyWhere;
import tools.AfMargin;
import tools.BackgroundPanel;

/**
 * @Author 作者
 * @Description 说明：编辑发布微博面板
 * @Date 时间：2020-11-20
 */
@SuppressWarnings("serial")
public class Panel2_EditorialPublished extends BackgroundPanel {

	// 按钮（清空、发布）
	private JButton buttonClear, buttonPush;
	// 编辑区域
	public static JTextArea editArea;
	// 右键弹出菜单项
	JPopupMenu popupMenu;
	JMenuItem popupMenu_Undo, popupMenu_Cut, popupMenu_Copy, popupMenu_Paste, popupMenu_Delete, popupMenu_SelectAll;
	// 创建撤销操作管理器(与撤销操作有关)
	protected UndoManager undo = new UndoManager();
	protected UndoableEditListener undoHandler = new UndoHandler();
	// 系统剪贴板
	Toolkit toolkit = Toolkit.getDefaultToolkit();
	Clipboard clipBoard = toolkit.getSystemClipboard();

	/**
	 * @Description 说明：构造方法
	 */
	public Panel2_EditorialPublished() {
		super(new ImageIcon("images\\背景_2.jpg").getImage());
		this.setLayout(new AfAnyWhere());
		init();
		addListener();
	}

	/**
	 * @Description 说明：初始化
	 */
	private void init() {
		// 按钮
		buttonClear = new JButton("清空");
		buttonPush = new JButton("发布");

		// 文本域
		editArea = new JTextArea();
		editArea.setRows(5);
		editArea.setLineWrap(true);// 设置文本域中的文本为自动换行
		editArea.setFont(new Font("宋体", 0, 12));// 设置文本域中的字体
		editArea.setBackground(new Color(158, 226, 198));// 设置背景色
		// 将文本域放入滚动窗口
		JScrollPane jsp = new JScrollPane(editArea);

		// 添加组件
		this.add(buttonClear, new AfMargin(-1, -1, 30, 160));
		this.add(buttonPush, new AfMargin(-1, -1, 30, 80));
		this.add(jsp, new AfMargin(10, 5, -1, 5));// 将JScrollPane添加到JPanel容器中

		// 创建右键弹出菜单
		popupMenu = new JPopupMenu();
		popupMenu_Undo = new JMenuItem("撤销(U)");
		popupMenu_Cut = new JMenuItem("剪切(T)");
		popupMenu_Copy = new JMenuItem("复制(C)");
		popupMenu_Paste = new JMenuItem("粘帖(P)");
		popupMenu_Delete = new JMenuItem("删除(D)");
		popupMenu_SelectAll = new JMenuItem("全选(A)");
		popupMenu_Undo.setEnabled(false);

		// 向右键菜单添加菜单项和分隔符
		popupMenu.add(popupMenu_Undo);
		popupMenu.addSeparator();
		popupMenu.add(popupMenu_Cut);
		popupMenu.add(popupMenu_Copy);
		popupMenu.add(popupMenu_Paste);
		popupMenu.add(popupMenu_Delete);
		popupMenu.addSeparator();
		popupMenu.add(popupMenu_SelectAll);
	}

	/**
	 * @Description 说明：事件处理
	 */
	private void addListener() {
		// 文本编辑区注册右键菜单事件
		setPopupMenuListener();
		// 清空按钮
		buttonClear.addActionListener((e) -> {
			editArea.setText("");
		});
		// 发布按钮
		buttonPush.addActionListener((e) -> {
			Weibo w = new Weibo();
			// 设置微博的属性
			GlobalVar.weibo_num1=GlobalVar.weibo_num1+1;
			w.setWeibo_id(GlobalVar.login_visitor.getId() + "." + GlobalVar.weibo_num1);
			w.setWriter_id(GlobalVar.login_visitor.getId());
			w.setResder_id(GlobalVar.login_visitor.getId());
			w.setWeibo_content(editArea.getText());
			new Insert().insert(w);// 增加微博对象
			// 刷新
			this.getRootPane().getParent().setVisible(false);
			new Interface_MainFrame();
		});
	}

	/**
	 * @Description 说明：文本编辑区注册右键菜单事件
	 */
	private void setPopupMenuListener() {
		// TODO 自动生成的方法存根
		editArea.addMouseListener(new MouseAdapter() {
			/**
			 * 返回此鼠标事件是否为该平台的弹出菜单触发事件
			 */
			public void mousePressed(MouseEvent e) {
				if (e.isPopupTrigger()) {
					// 在组件调用者的坐标空间中的位置 X、Y 显示弹出菜单
					popupMenu.show(e.getComponent(), e.getX(), e.getY());
				}
				checkMenuItemEnabled();// 设置剪切，复制，粘帖，删除等功能的可用性
				editArea.requestFocus();// 编辑区获取焦点
			}

			/**
			 * 返回此鼠标事件是否为该平台的弹出菜单触发事件
			 */
			public void mouseReleased(MouseEvent e) {
				if (e.isPopupTrigger()) {
					// 在组件调用者的坐标空间中的位置 X、Y 显示弹出菜单
					popupMenu.show(e.getComponent(), e.getX(), e.getY());
				}
				checkMenuItemEnabled();// 设置剪切，复制，粘帖，删除等功能的可用性
				editArea.requestFocus();// 编辑区获取焦点
			}
		});
		popupMenu_Undo.addActionListener((e) -> {
			editArea.requestFocus();
			if (undo.canUndo()) {
				try {
					undo.undo();
				} catch (CannotUndoException ex) {
					System.out.println(ex);
				}
			}
		});// 撤销结束
		popupMenu_Cut.addActionListener((e) -> {
			editArea.requestFocus();
			String text = editArea.getSelectedText();
			StringSelection selection = new StringSelection(text);
			clipBoard.setContents(selection, null);
			editArea.replaceRange("", editArea.getSelectionStart(), editArea.getSelectionEnd());
			checkMenuItemEnabled();// 设置剪切，复制，粘帖，删除功能的可用性
		});// 剪切结束
		popupMenu_Copy.addActionListener((e) -> {
			editArea.requestFocus();
			String text = editArea.getSelectedText();
			StringSelection selection = new StringSelection(text);
			clipBoard.setContents(selection, null);
			checkMenuItemEnabled();// 设置剪切，复制，粘帖，删除功能的可用性
		});// 复制结束
		popupMenu_Paste.addActionListener((e) -> {
			editArea.requestFocus();
			Transferable contents = clipBoard.getContents(this);
			if (contents == null)
				return;
			String text = "";
			try {
				text = (String) contents.getTransferData(DataFlavor.stringFlavor);
			} catch (Exception exception) {
			}
			editArea.replaceRange(text, editArea.getSelectionStart(), editArea.getSelectionEnd());
			checkMenuItemEnabled();
		});// 粘帖结束
		popupMenu_Delete.addActionListener((e) -> {
			editArea.requestFocus();
			editArea.replaceRange("", editArea.getSelectionStart(), editArea.getSelectionEnd());
			checkMenuItemEnabled(); // 设置剪切、复制、粘贴、删除等功能的可用性
		});// 删除结束
		popupMenu_SelectAll.addActionListener((e) -> {
			editArea.selectAll();
		});// 全选结束
	}

	/**
	 * @Description 说明：设置菜单项的可用性：剪切，复制，粘帖，删除功能
	 */
	public void checkMenuItemEnabled() {
		String selectText = editArea.getSelectedText();
		if (selectText == null) {
			popupMenu_Cut.setEnabled(false);
			popupMenu_Copy.setEnabled(false);
			popupMenu_Delete.setEnabled(false);
		} else {
			popupMenu_Cut.setEnabled(true);
			popupMenu_Copy.setEnabled(true);
			popupMenu_Delete.setEnabled(true);
		}
		// 粘帖功能可用性判断
		Transferable contents = clipBoard.getContents(this);
		if (contents == null) {
			popupMenu_Paste.setEnabled(false);
		} else {
			popupMenu_Paste.setEnabled(true);
		}
	}

	// 实现接口UndoableEditListener的类UndoHandler(与撤销操作有关)
	class UndoHandler implements UndoableEditListener {
		public void undoableEditHappened(UndoableEditEvent uee) {
			undo.addEdit(uee.getEdit());
		}
	}
}
