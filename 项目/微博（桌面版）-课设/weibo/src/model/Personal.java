package model;

import data.Constant;

/**
 * @Author ����
 * @Description ˵�����������á���ȡ���˶����������Ϣ
 * @Date ʱ�䣺2020-11-25
 */
public class Personal {
	/** ��Ա���� **/
	// �����˺�
	private String id;
	// ��������
	private String password;
	// ���˲���Ȩ��
	private String power;
	// �Ƿ��¼�ɹ�
	private String login_success;

	public Personal() {
	}

	public Personal(String id) {
		this.id = id;
	}

	public Personal(String id, String password) {
		this.id = id;
		this.password = password;
		this.power = Constant.POWER[0];
		this.login_success = Constant.NO_STRING;
	}

	/**
	 * @Description ˵������ȡ
	 * @return id
	 */
	public String getId() {
		return id;
	}

	/**
	 * @Description ˵��������
	 * @param id Ҫ���õ� id
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * @Description ˵������ȡ
	 * @return password
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * @Description ˵��������
	 * @param password Ҫ���õ� password
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * @Description ˵������ȡ
	 * @return power
	 */
	public String getPower() {
		return power;
	}

	/**
	 * @Description ˵��������
	 * @param power Ҫ���õ� power
	 */
	public void setPower(String power) {
		this.power = power;
	}

	/**
	 * @Description ˵������ȡ
	 * @return login_success
	 */
	public String getLogin_success() {
		return login_success;
	}

	/**
	 * @Description ˵��������
	 * @param login_success Ҫ���õ� login_success
	 */
	public void setLogin_success(String login_success) {
		this.login_success = login_success;
	}

}
