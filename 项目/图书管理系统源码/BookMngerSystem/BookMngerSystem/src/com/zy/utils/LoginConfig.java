package com.zy.utils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.FileUtils;

import com.zy.model.User;

/**
 * ���ڱ����˺ź�����
 * 
 * @author ���෢
 *
 */
public class LoginConfig {
	/**
	 * ���˺�����д���ļ�
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
	 * ��ȡ�˺ź����� ���û�id
	 */
	public static List<String> reader() {

		String str = "";
		try {
			str = FileUtils.readFileToString(new File("password.txt"), "GBK");
		} catch (IOException e) {
			e.printStackTrace();
		}
		String userName = subString(str, "name", "pwd");// �˺�
		String passwprd = subString(str, "pwd", "uid");// ����
		// ���û�id
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
		/* �ҳ�ָ����2���ַ��� ���ַ�������� λ�� */
		int strStartIndex = str.indexOf(strStart);
		int strEndIndex = str.indexOf(strEnd);

		/* index Ϊ���� ����ʾ���ַ����� û�и��ַ� */
		if (strStartIndex < 0) {
			return "�ַ��� :---->" + str + "<---- �в����� " + strStart + ", �޷���ȡĿ���ַ���";
		}
		if (strEndIndex < 0) {
			return "�ַ��� :---->" + str + "<---- �в����� " + strEnd + ", �޷���ȡĿ���ַ���";
		}
		/* ��ʼ��ȡ */
		String result = str.substring(strStartIndex, strEndIndex).substring(strStart.length());
		return result;
	}

	/**
	 * ����ļ�
	 */
	public static void clean() {
		File file = new File("password.txt");
		if (!file.exists()) {
			System.out.println("�ļ�������");
		} else {
			file.delete();
		}

	}

	public static void main(String[] args) {
		List<String> list = reader();

		System.out.println("�û���:" + list.get(0));
		System.out.println("����:" + list.get(1));
		System.out.println("�û�id:" + list.get(2));
		// save(new User("���෢", "123456", 1));
	}
}
