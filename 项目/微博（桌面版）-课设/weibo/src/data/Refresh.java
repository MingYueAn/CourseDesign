package data;

import javax.swing.JOptionPane;

import control.LoginRegister;
import model.Personal;

public class Refresh {
	public Refresh() {
		// 登录
		Personal login = new Personal();
		login = new LoginRegister().login(GlobalVar.login_personal.getId(), GlobalVar.login_personal.getPassword());
		if (login.getLogin_success().equals(Constant.YES_STRING) == true) {
			// 保存当前登录个人对象
			GlobalVar.login_personal = login;
			// 获取个人权限并判断界面
			switch (login.getPower()) {
			case "访客":
				new view_visitor.Interface_MainFrame();
				break;
			case "管理员":
				new view_administrator.Interface_MainFrame();
				break;
			default:
				System.out.println("完成");
			}
		} else {
			JOptionPane.showMessageDialog(null, "登录失败，请重新登录！", "登录失败", JOptionPane.INFORMATION_MESSAGE);
		}
	}
}
