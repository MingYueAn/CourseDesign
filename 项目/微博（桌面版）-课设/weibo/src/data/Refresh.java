package data;

import javax.swing.JOptionPane;

import control.LoginRegister;
import model.Personal;

public class Refresh {
	public Refresh() {
		// ��¼
		Personal login = new Personal();
		login = new LoginRegister().login(GlobalVar.login_personal.getId(), GlobalVar.login_personal.getPassword());
		if (login.getLogin_success().equals(Constant.YES_STRING) == true) {
			// ���浱ǰ��¼���˶���
			GlobalVar.login_personal = login;
			// ��ȡ����Ȩ�޲��жϽ���
			switch (login.getPower()) {
			case "�ÿ�":
				new view_visitor.Interface_MainFrame();
				break;
			case "����Ա":
				new view_administrator.Interface_MainFrame();
				break;
			default:
				System.out.println("���");
			}
		} else {
			JOptionPane.showMessageDialog(null, "��¼ʧ�ܣ������µ�¼��", "��¼ʧ��", JOptionPane.INFORMATION_MESSAGE);
		}
	}
}
