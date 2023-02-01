package model;

/**
 * @Author 作者
 * @Description 说明：音乐信息模板
 * @Date 时间：2020-12-4
 */
public class Music {

	private String id;
	private String name;
	private String path;
	private String length;
	private long time;

	/**
	 * @Description 说明：获取歌曲id
	 * @return id
	 */
	public String getId() {
		return id;
	}

	/**
	 * @Description 说明：设置歌曲id
	 * @param id 要设置的 id
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * @Description 说明：获取歌名
	 * @return name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @Description 说明：设置歌名
	 * @param name 要设置的 name
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @Description 说明：获取歌曲地址
	 * @return path
	 */
	public String getPath() {
		return path;
	}

	/**
	 * @Description 说明：设置歌曲地址
	 * @param path 要设置的 path
	 */
	public void setPath(String path) {
		this.path = path;
	}

	/**
	 * @Description 说明：获取歌曲时长
	 * @return length
	 */
	public String getLength() {
		return length;
	}

	/**
	 * @Description 说明：设置歌曲时长
	 * @param length 要设置的 length
	 */
	public void setLength(String length) {
		this.length = length;
	}

	/**
	 * @Description 说明：获取时间
	 * @return time
	 */
	public long getTime() {
		return time;
	}

	/**
	 * @Description 说明：设置时间
	 * @param time 要设置的 time
	 */
	public void setTime(long time) {
		this.time = time;
	}

}
