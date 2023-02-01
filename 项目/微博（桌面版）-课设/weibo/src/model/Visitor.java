package model;

import data.Constant;

/**
 * @Author 作者
 * @Description 说明：用于设置、获取访客对象的属性信息
 * @Date 时间：2020-11-25
 */
public class Visitor {
	/** 成员变量 **/
	// 访客账号
	private String id;
	// 访客昵称
	private String visitor_name;
	// 访客性别
	private String visitor_sex;
	// 访客生日
	private String visitor_birthday;
	// 是否可查看
	private String visitor_yes_no;

	public Visitor() {
	}

	public Visitor(String id) {
		this.id = id;
		this.visitor_name = "用户" + id;
		this.visitor_sex = "请编辑";
		this.visitor_birthday = "请编辑";
		this.visitor_yes_no = Constant.NO_STRING;
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
	 * @return visitor_name
	 */
	public String getVisitor_name() {
		return visitor_name;
	}

	/**
	 * @Description 说明：设置
	 * @param visitor_name 要设置的 visitor_name
	 */
	public void setVisitor_name(String visitor_name) {
		this.visitor_name = visitor_name;
	}

	/**
	 * @Description 说明：获取
	 * @return visitor_sex
	 */
	public String getVisitor_sex() {
		return visitor_sex;
	}

	/**
	 * @Description 说明：设置
	 * @param visitor_sex 要设置的 visitor_sex
	 */
	public void setVisitor_sex(String visitor_sex) {
		this.visitor_sex = visitor_sex;
	}

	/**
	 * @Description 说明：获取
	 * @return visitor_birthday
	 */
	public String getVisitor_birthday() {
		return visitor_birthday;
	}

	/**
	 * @Description 说明：设置
	 * @param visitor_birthday 要设置的 visitor_birthday
	 */
	public void setVisitor_birthday(String visitor_birthday) {
		this.visitor_birthday = visitor_birthday;
	}

	/**
	 * @Description 说明：获取
	 * @return visitor_yes_no
	 */
	public String getVisitor_yes_no() {
		return visitor_yes_no;
	}

	/**
	 * @Description 说明：设置
	 * @param visitor_yes_no 要设置的 visitor_yes_no
	 */
	public void setVisitor_yes_no(String visitor_yes_no) {
		this.visitor_yes_no = visitor_yes_no;
	}

}
