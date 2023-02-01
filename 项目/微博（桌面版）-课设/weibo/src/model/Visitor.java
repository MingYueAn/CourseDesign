package model;

import data.Constant;

/**
 * @Author ����
 * @Description ˵�����������á���ȡ�ÿͶ����������Ϣ
 * @Date ʱ�䣺2020-11-25
 */
public class Visitor {
	/** ��Ա���� **/
	// �ÿ��˺�
	private String id;
	// �ÿ��ǳ�
	private String visitor_name;
	// �ÿ��Ա�
	private String visitor_sex;
	// �ÿ�����
	private String visitor_birthday;
	// �Ƿ�ɲ鿴
	private String visitor_yes_no;

	public Visitor() {
	}

	public Visitor(String id) {
		this.id = id;
		this.visitor_name = "�û�" + id;
		this.visitor_sex = "��༭";
		this.visitor_birthday = "��༭";
		this.visitor_yes_no = Constant.NO_STRING;
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
	 * @return visitor_name
	 */
	public String getVisitor_name() {
		return visitor_name;
	}

	/**
	 * @Description ˵��������
	 * @param visitor_name Ҫ���õ� visitor_name
	 */
	public void setVisitor_name(String visitor_name) {
		this.visitor_name = visitor_name;
	}

	/**
	 * @Description ˵������ȡ
	 * @return visitor_sex
	 */
	public String getVisitor_sex() {
		return visitor_sex;
	}

	/**
	 * @Description ˵��������
	 * @param visitor_sex Ҫ���õ� visitor_sex
	 */
	public void setVisitor_sex(String visitor_sex) {
		this.visitor_sex = visitor_sex;
	}

	/**
	 * @Description ˵������ȡ
	 * @return visitor_birthday
	 */
	public String getVisitor_birthday() {
		return visitor_birthday;
	}

	/**
	 * @Description ˵��������
	 * @param visitor_birthday Ҫ���õ� visitor_birthday
	 */
	public void setVisitor_birthday(String visitor_birthday) {
		this.visitor_birthday = visitor_birthday;
	}

	/**
	 * @Description ˵������ȡ
	 * @return visitor_yes_no
	 */
	public String getVisitor_yes_no() {
		return visitor_yes_no;
	}

	/**
	 * @Description ˵��������
	 * @param visitor_yes_no Ҫ���õ� visitor_yes_no
	 */
	public void setVisitor_yes_no(String visitor_yes_no) {
		this.visitor_yes_no = visitor_yes_no;
	}

}
