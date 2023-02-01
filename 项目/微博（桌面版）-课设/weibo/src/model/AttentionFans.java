package model;

/**
 * @Author 作者
 * @Description 说明：用于设置、获取关注与粉丝对象的属性信息
 * @Date 时间：2020-12-10
 */
public class AttentionFans {
	/** 成员变量 **/
	// 关注者账号
	private String attention_id;
	// 粉丝账号
	private String fans_id;

	/**
	 * @Description 说明：获取
	 * @return attention_id
	 */
	public String getAttention_id() {
		return attention_id;
	}

	/**
	 * @Description 说明：设置
	 * @param attention_id 要设置的 attention_id
	 */
	public void setAttention_id(String attention_id) {
		this.attention_id = attention_id;
	}

	/**
	 * @Description 说明：获取
	 * @return fans_id
	 */
	public String getFans_id() {
		return fans_id;
	}

	/**
	 * @Description 说明：设置
	 * @param fans_id 要设置的 fans_id
	 */
	public void setFans_id(String fans_id) {
		this.fans_id = fans_id;
	}

}
