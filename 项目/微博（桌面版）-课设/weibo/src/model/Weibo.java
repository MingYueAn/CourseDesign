package model;

/**
 * @Author 作者
 * @Description 说明：用于设置、获取微博对象的属性信息
 * @Date 时间：2020-12-10
 */
public class Weibo {
	/** 成员变量 **/
	// 微博编号
	private String weibo_id;
	// 作者账号
	private String writer_id;
	// 读者账号
	private String resder_id;
	// 微博内容
	private String weibo_content;

	/**
	 * @Description 说明：获取
	 * @return weibo_id
	 */
	public String getWeibo_id() {
		return weibo_id;
	}

	/**
	 * @Description 说明：设置
	 * @param weibo_id 要设置的 weibo_id
	 */
	public void setWeibo_id(String weibo_id) {
		this.weibo_id = weibo_id;
	}

	/**
	 * @Description 说明：获取
	 * @return writer_id
	 */
	public String getWriter_id() {
		return writer_id;
	}

	/**
	 * @Description 说明：设置
	 * @param writer_id 要设置的 writer_id
	 */
	public void setWriter_id(String writer_id) {
		this.writer_id = writer_id;
	}

	/**
	 * @Description 说明：获取
	 * @return resder_id
	 */
	public String getResder_id() {
		return resder_id;
	}

	/**
	 * @Description 说明：设置
	 * @param resder_id 要设置的 resder_id
	 */
	public void setResder_id(String resder_id) {
		this.resder_id = resder_id;
	}

	/**
	 * @Description 说明：获取
	 * @return weibo_content
	 */
	public String getWeibo_content() {
		return weibo_content;
	}

	/**
	 * @Description 说明：设置
	 * @param weibo_content 要设置的 weibo_content
	 */
	public void setWeibo_content(String weibo_content) {
		this.weibo_content = weibo_content;
	}

}
