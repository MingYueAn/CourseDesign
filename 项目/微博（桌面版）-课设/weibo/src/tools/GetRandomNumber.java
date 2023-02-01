package tools;

import java.util.Random;

/**
 * @Author ���ߣ�л����
 * @Description ˵������1-max֮�������ȡamount������
 * @Date ʱ�䣺2020-12-16
 */
public class GetRandomNumber {

	/**
	 * @Description ˵������1-max֮�������ȡamount������
	 * @param max
	 * @param amount
	 */
	public static int[] getRandomNumber(int max, int amount) {
		int[] randomNumber = new int[amount];// amount��С�����飨�������������飩
		randomNumber[0] = -1;// ��ʼ����һ������Ԫ��
		Random random = new Random();// �����������
		int index = 0;
		while (index < amount) {
			int number = random.nextInt(max) + 1;// ��ȡһ��1-max�������
			boolean isInArrays = false;// ��ʼ����ȡ�������number���ڴ�������������randomNumber��
			for (int i : randomNumber) {// i����ȡ��������������randomNumber�е�ֵ
				if (i == number)// ����������������randomNumber�е�ֵi���ȡ�������number��ͬʱ
					isInArrays = true;// ��ȡ�������number�ڴ�������������randomNumber��
			}
			if (isInArrays == false) {// ��ȡ�������number���ڴ�������������randomNumber��ʱ
				randomNumber[index] = number;
				index++;
			}
		}
		return randomNumber;
	}
}
