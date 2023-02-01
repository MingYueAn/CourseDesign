package model;

import data.Constant;

/**
 * @Author 作者
 * @Description 说明：用于设置、获取个人对象的属性信息
 * @Date 时间：2020-11-25
 */
public class Personal {
	/** 成员变量 **/
	// 个人账号
	private String id;
	// 个人密码
	private String password;
	// 个人操作权限
	private String power;
	// 是否登录成功
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
	 * @Description 说明：获取
	 * @return id
	 */
	public String getId() {
		return id;
	}

	/**
	 * @Description 说明：设置
	 * @param id 要设置的 id
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * @Description 说明：获取
	 * @return password
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * @Description 说明：设置
	 * @param password 要设置的 password
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * @Description 说明：获取
	 * @return power
	 */
	public String getPower() {
		return power;
	}

	/**
	 * @Description 说明：设置
	 * @param power 要设置的 power
	 */
	public void setPower(String power) {
		this.power = power;
	}

	/**
	 * @Description 说明：获取
	 * @return login_success
	 */
	public String getLogin_success() {
		return login_success;
	}

	/**
	 * @Description 说明：设置
	 * @param login_success 要设置的 login_success
	 */
	public void setLogin_success(String login_success) {
		this.login_success = login_success;
	}

}
