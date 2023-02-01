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
 * @Author ����
 * @Description ˵�����༭����΢�����
 * @Date ʱ�䣺2020-11-20
 */
@SuppressWarnings("serial")
public class Panel2_EditorialPublished extends BackgroundPanel {

	// ��ť����ա�������
	private JButton buttonClear, buttonPush;
	// �༭����
	public static JTextArea editArea;
	// �Ҽ������˵���
	JPopupMenu popupMenu;
	JMenuItem popupMenu_Undo, popupMenu_Cut, popupMenu_Copy, popupMenu_Paste, popupMenu_Delete, popupMenu_SelectAll;
	// ������������������(�볷�������й�)
	protected UndoManager undo = new UndoManager();
	protected UndoableEditListener undoHandler = new UndoHandler();
	// ϵͳ������
	Toolkit toolkit = Toolkit.getDefaultToolkit();
	Clipboard clipBoard = toolkit.getSystemClipboard();

	/**
	 * @Description ˵�������췽��
	 */
	public Panel2_EditorialPublished() {
		super(new ImageIcon("images\\����_2.jpg").getImage());
		this.setLayout(new AfAnyWhere());
		init();
		addListener();
	}

	/**
	 * @Description ˵������ʼ��
	 */
	private void init() {
		// ��ť
		buttonClear = new JButton("���");
		buttonPush = new JButton("����");

		// �ı���
		editArea = new JTextArea();
		editArea.setRows(5);
		editArea.setLineWrap(true);// �����ı����е��ı�Ϊ�Զ�����
		editArea.setFont(new Font("����", 0, 12));// �����ı����е�����
		editArea.setBackground(new Color(158, 226, 198));// ���ñ���ɫ
		// ���ı�������������
		JScrollPane jsp = new JScrollPane(editArea);

		// ������
		this.add(buttonClear, new AfMargin(-1, -1, 30, 160));
		this.add(buttonPush, new AfMargin(-1, -1, 30, 80));
		this.add(jsp, new AfMargin(10, 5, -1, 5));// ��JScrollPane��ӵ�JPanel������

		// �����Ҽ������˵�
		popupMenu = new JPopupMenu();
		popupMenu_Undo = new JMenuItem("����(U)");
		popupMenu_Cut = new JMenuItem("����(T)");
		popupMenu_Copy = new JMenuItem("����(C)");
		popupMenu_Paste = new JMenuItem("ճ��(P)");
		popupMenu_Delete = new JMenuItem("ɾ��(D)");
		popupMenu_SelectAll = new JMenuItem("ȫѡ(A)");
		popupMenu_Undo.setEnabled(false);

		// ���Ҽ��˵���Ӳ˵���ͷָ���
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
	 * @Description ˵�����¼�����
	 */
	private void addListener() {
		// �ı��༭��ע���Ҽ��˵��¼�
		setPopupMenuListener();
		// ��հ�ť
		buttonClear.addActionListener((e) -> {
			editArea.setText("");
		});
		// ������ť
		buttonPush.addActionListener((e) -> {
			Weibo w = new Weibo();
			// ����΢��������
			GlobalVar.weibo_num1=GlobalVar.weibo_num1+1;
			w.setWeibo_id(GlobalVar.login_visitor.getId() + "." + GlobalVar.weibo_num1);
			w.setWriter_id(GlobalVar.login_visitor.getId());
			w.setResder_id(GlobalVar.login_visitor.getId());
			w.setWeibo_content(editArea.getText());
			new Insert().insert(w);// ����΢������
			// ˢ��
			this.getRootPane().getParent().setVisible(false);
			new Interface_MainFrame();
		});
	}

	/**
	 * @Description ˵�����ı��༭��ע���Ҽ��˵��¼�
	 */
	private void setPopupMenuListener() {
		// TODO �Զ����ɵķ������
		editArea.addMouseListener(new MouseAdapter() {
			/**
			 * ���ش�����¼��Ƿ�Ϊ��ƽ̨�ĵ����˵������¼�
			 */
			public void mousePressed(MouseEvent e) {
				if (e.isPopupTrigger()) {
					// ����������ߵ�����ռ��е�λ�� X��Y ��ʾ�����˵�
					popupMenu.show(e.getComponent(), e.getX(), e.getY());
				}
				checkMenuItemEnabled();// ���ü��У����ƣ�ճ����ɾ���ȹ��ܵĿ�����
				editArea.requestFocus();// �༭����ȡ����
			}

			/**
			 * ���ش�����¼��Ƿ�Ϊ��ƽ̨�ĵ����˵������¼�
			 */
			public void mouseReleased(MouseEvent e) {
				if (e.isPopupTrigger()) {
					// ����������ߵ�����ռ��е�λ�� X��Y ��ʾ�����˵�
					popupMenu.show(e.getComponent(), e.getX(), e.getY());
				}
				checkMenuItemEnabled();// ���ü��У����ƣ�ճ����ɾ���ȹ��ܵĿ�����
				editArea.requestFocus();// �༭����ȡ����
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
		});// ��������
		popupMenu_Cut.addActionListener((e) -> {
			editArea.requestFocus();
			String text = editArea.getSelectedText();
			StringSelection selection = new StringSelection(text);
			clipBoard.setContents(selection, null);
			editArea.replaceRange("", editArea.getSelectionStart(), editArea.getSelectionEnd());
			checkMenuItemEnabled();// ���ü��У����ƣ�ճ����ɾ�����ܵĿ�����
		});// ���н���
		popupMenu_Copy.addActionListener((e) -> {
			editArea.requestFocus();
			String text = editArea.getSelectedText();
			StringSelection selection = new StringSelection(text);
			clipBoard.setContents(selection, null);
			checkMenuItemEnabled();// ���ü��У����ƣ�ճ����ɾ�����ܵĿ�����
		});// ���ƽ���
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
		});// ճ������
		popupMenu_Delete.addActionListener((e) -> {
			editArea.requestFocus();
			editArea.replaceRange("", editArea.getSelectionStart(), editArea.getSelectionEnd());
			checkMenuItemEnabled(); // ���ü��С����ơ�ճ����ɾ���ȹ��ܵĿ�����
		});// ɾ������
		popupMenu_SelectAll.addActionListener((e) -> {
			editArea.selectAll();
		});// ȫѡ����
	}

	/**
	 * @Description ˵�������ò˵���Ŀ����ԣ����У����ƣ�ճ����ɾ������
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
		// ճ�����ܿ������ж�
		Transferable contents = clipBoard.getContents(this);
		if (contents == null) {
			popupMenu_Paste.setEnabled(false);
		} else {
			popupMenu_Paste.setEnabled(true);
		}
	}

	// ʵ�ֽӿ�UndoableEditListener����UndoHandler(�볷�������й�)
	class UndoHandler implements UndoableEditListener {
		public void undoableEditHappened(UndoableEditEvent uee) {
			undo.addEdit(uee.getEdit());
		}
	}
}
