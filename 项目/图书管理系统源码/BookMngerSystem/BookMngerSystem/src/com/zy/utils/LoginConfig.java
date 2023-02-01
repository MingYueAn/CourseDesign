package com.zy.utils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.FileUtils;

import com.zy.model.User;

/**
 * 用于保存账号和密码
 * 
 * @author 胡绵发
 *
 */
public class LoginConfig {
	/**
	 * 把账号密码写入文件
	 * 
	 * @param user
	 */
	public static void save(User user) {
		List<String> list = new ArrayList<>();
		list.add("name" + user.getUsername());
		list.add("pwd" + user.getPassword());
		list.add("uid" + user.getId());
		try {
			FileUtils.writeLines(new File("password.txt"), list);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 读取账号和密码 和用户id
	 */
	public static List<String> reader() {

		String str = "";
		try {
			str = FileUtils.readFileToString(new File("password.txt"), "GBK");
		} catch (IOException e) {
			e.printStackTrace();
		}
		String userName = subString(str, "name", "pwd");// 账号
		String passwprd = subString(str, "pwd", "uid");// 密码
		// 拿用户id
		int indexOf2 = str.indexOf("uid");
		String userid = str.substring(indexOf2);
		String uid = userid.substring(3);
		List<String> list = new ArrayList<>();
		list.add(userName.trim());
		list.add(passwprd.trim());
		list.add(uid.trim());
		return list;
	}

	public static String subString(String str, String strStart, String strEnd) {
		/* 找出指定的2个字符在 该字符串里面的 位置 */
		int strStartIndex = str.indexOf(strStart);
		int strEndIndex = str.indexOf(strEnd);

		/* index 为负数 即表示该字符串中 没有该字符 */
		if (strStartIndex < 0) {
			return "字符串 :---->" + str + "<---- 中不存在 " + strStart + ", 无法截取目标字符串";
		}
		if (strEndIndex < 0) {
			return "字符串 :---->" + str + "<---- 中不存在 " + strEnd + ", 无法截取目标字符串";
		}
		/* 开始截取 */
		String result = str.substring(strStartIndex, strEndIndex).substring(strStart.length());
		return result;
	}

	/**
	 * 清空文件
	 */
	public static void clean() {
		File file = new File("password.txt");
		if (!file.exists()) {
			System.out.println("文件不存在");
		} else {
			file.delete();
		}

	}

	public static void main(String[] args) {
		List<String> list = reader();

		System.out.println("用户名:" + list.get(0));
		System.out.println("密码:" + list.get(1));
		System.out.println("用户id:" + list.get(2));
		// save(new User("胡绵发", "123456", 1));
	}
}
