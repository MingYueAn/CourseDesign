package model;

/**
 * @Author ����
 * @Description ˵�����������á���ȡ΢�������������Ϣ
 * @Date ʱ�䣺2020-12-10
 */
public class Weibo {
	/** ��Ա���� **/
	// ΢�����
	private String weibo_id;
	// �����˺�
	private String writer_id;
	// �����˺�
	private String resder_id;
	// ΢������
	private String weibo_content;

	/**
	 * @Description ˵������ȡ
	 * @return weibo_id
	 */
	public String getWeibo_id() {
		return weibo_id;
	}

	/**
	 * @Description ˵��������
	 * @param weibo_id Ҫ���õ� weibo_id
	 */
	public void setWeibo_id(String weibo_id) {
		this.weibo_id = weibo_id;
	}

	/**
	 * @Description ˵������ȡ
	 * @return writer_id
	 */
	public String getWriter_id() {
		return writer_id;
	}

	/**
	 * @Description ˵��������
	 * @param writer_id Ҫ���õ� writer_id
	 */
	public void setWriter_id(String writer_id) {
		this.writer_id = writer_id;
	}

	/**
	 * @Description ˵������ȡ
	 * @return resder_id
	 */
	public String getResder_id() {
		return resder_id;
	}

	/**
	 * @Description ˵��������
	 * @param resder_id Ҫ���õ� resder_id
	 */
	public void setResder_id(String resder_id) {
		this.resder_id = resder_id;
	}

	/**
	 * @Description ˵������ȡ
	 * @return weibo_content
	 */
	public String getWeibo_content() {
		return weibo_content;
	}

	/**
	 * @Description ˵��������
	 * @param weibo_content Ҫ���õ� weibo_content
	 */
	public void setWeibo_content(String weibo_content) {
		this.weibo_content = weibo_content;
	}

}
