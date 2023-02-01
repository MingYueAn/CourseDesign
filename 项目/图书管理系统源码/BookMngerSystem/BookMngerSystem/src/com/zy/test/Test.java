package com.zy.test;

import java.util.List;

import com.zy.dao.AdminDao;
import com.zy.model.BooKAndBookType;

public class Test {
	public static void main(String[] args) {
		List<BooKAndBookType> listBook = new AdminDao().listBook("", "", "");
		System.out.println(listBook);
	}
}
