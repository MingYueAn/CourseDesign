package model;

/**
 * @Author ����
 * @Description ˵����������Ϣģ��
 * @Date ʱ�䣺2020-12-4
 */
public class Music {

	private String id;
	private String name;
	private String path;
	private String length;
	private long time;

	/**
	 * @Description ˵������ȡ����id
	 * @return id
	 */
	public String getId() {
		return id;
	}

	/**
	 * @Description ˵�������ø���id
	 * @param id Ҫ���õ� id
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * @Description ˵������ȡ����
	 * @return name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @Description ˵�������ø���
	 * @param name Ҫ���õ� name
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @Description ˵������ȡ������ַ
	 * @return path
	 */
	public String getPath() {
		return path;
	}

	/**
	 * @Description ˵�������ø�����ַ
	 * @param path Ҫ���õ� path
	 */
	public void setPath(String path) {
		this.path = path;
	}

	/**
	 * @Description ˵������ȡ����ʱ��
	 * @return length
	 */
	public String getLength() {
		return length;
	}

	/**
	 * @Description ˵�������ø���ʱ��
	 * @param length Ҫ���õ� length
	 */
	public void setLength(String length) {
		this.length = length;
	}

	/**
	 * @Description ˵������ȡʱ��
	 * @return time
	 */
	public long getTime() {
		return time;
	}

	/**
	 * @Description ˵��������ʱ��
	 * @param time Ҫ���õ� time
	 */
	public void setTime(long time) {
		this.time = time;
	}

}
