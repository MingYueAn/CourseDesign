package tools;

/**
 * @Author ����
 * @Description ˵�����ַ���������
 * @Date ʱ�䣺2020-11-20
 */
public class StringUtil {
	/**
	 * �ж��Ƿ�Ϊ��
	 * 
	 * @param str
	 * @return
	 */
	public static boolean isEmpty(String str) {
		if (str == null || "".equals(str.trim())) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * �ж��Ƿ��ǿ�
	 * 
	 * @param str
	 * @return
	 */
	public static boolean isNoEmpty(String str) {
		if (str != null && "".equals(str.trim())) {
			return true;
		} else {
			return false;
		}
	}

}
