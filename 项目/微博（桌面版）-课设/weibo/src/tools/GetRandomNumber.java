package tools;

import java.util.Random;

/**
 * @Author 作者：谢晓艳
 * @Description 说明：从1-max之间随机获取amount个整数
 * @Date 时间：2020-12-16
 */
public class GetRandomNumber {

	/**
	 * @Description 说明：从1-max之间随机获取amount个整数
	 * @param max
	 * @param amount
	 */
	public static int[] getRandomNumber(int max, int amount) {
		int[] randomNumber = new int[amount];// amount大小的数组（存放随机数的数组）
		randomNumber[0] = -1;// 初始化第一个数组元素
		Random random = new Random();// 随机数生成器
		int index = 0;
		while (index < amount) {
			int number = random.nextInt(max) + 1;// 获取一个1-max的随机数
			boolean isInArrays = false;// 初始化获取的随机数number不在存放随机数的数组randomNumber中
			for (int i : randomNumber) {// i依次取存放随机数的数组randomNumber中的值
				if (i == number)// 当存放随机数的数组randomNumber中的值i与获取的随机数number相同时
					isInArrays = true;// 获取的随机数number在存放随机数的数组randomNumber中
			}
			if (isInArrays == false) {// 获取的随机数number不在存放随机数的数组randomNumber中时
				randomNumber[index] = number;
				index++;
			}
		}
		return randomNumber;
	}
}
