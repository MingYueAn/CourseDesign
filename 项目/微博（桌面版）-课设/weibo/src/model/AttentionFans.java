package model;

/**
 * @Author ����
 * @Description ˵�����������á���ȡ��ע���˿�����������Ϣ
 * @Date ʱ�䣺2020-12-10
 */
public class AttentionFans {
	/** ��Ա���� **/
	// ��ע���˺�
	private String attention_id;
	// ��˿�˺�
	private String fans_id;

	/**
	 * @Description ˵������ȡ
	 * @return attention_id
	 */
	public String getAttention_id() {
		return attention_id;
	}

	/**
	 * @Description ˵��������
	 * @param attention_id Ҫ���õ� attention_id
	 */
	public void setAttention_id(String attention_id) {
		this.attention_id = attention_id;
	}

	/**
	 * @Description ˵������ȡ
	 * @return fans_id
	 */
	public String getFans_id() {
		return fans_id;
	}

	/**
	 * @Description ˵��������
	 * @param fans_id Ҫ���õ� fans_id
	 */
	public void setFans_id(String fans_id) {
		this.fans_id = fans_id;
	}

}
