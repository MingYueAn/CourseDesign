package com.zy.model;

import java.sql.Date;

public class BookAndBorrow {
	private int id;
	private String bookName;
	private String author;
	private String sex;
	private float price;
	private String bookDesc;
	private Integer bookTypeId;
	private int isflag;
	private int id2;// 借阅id
	private int userid;// 用户id
	private int bookid;// 书籍id
	private Date btime;// 借书日期
	private Date rtime;// 还书日期

	public BookAndBorrow() {
		super();
		// TODO Auto-generated constructor stub
	}

	public BookAndBorrow(int id, String bookName, String author, String sex, float price, String bookDesc,
			Integer bookTypeId, int isflag, int id2, int userid, int bookid, Date btime, Date rtime) {
		super();
		this.id = id;
		this.bookName = bookName;
		this.author = author;
		this.sex = sex;
		this.price = price;
		this.bookDesc = bookDesc;
		this.bookTypeId = bookTypeId;
		this.isflag = isflag;
		this.id2 = id2;
		this.userid = userid;
		this.bookid = bookid;
		this.btime = btime;
		this.rtime = rtime;
	}

	public BookAndBorrow(int id, String bookName, String author, Date btime) {
		this.id = id;
		this.bookName = bookName;
		this.author = author;
		this.btime = btime;
	}

	public BookAndBorrow(String bookName, String author, Date btime, Date rtime) {
		this.bookName = bookName;
		this.author = author;
		this.btime = btime;
		this.rtime = rtime;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getBookName() {
		return bookName;
	}

	public void setBookName(String bookName) {
		this.bookName = bookName;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public float getPrice() {
		return price;
	}

	public void setPrice(float price) {
		this.price = price;
	}

	public String getBookDesc() {
		return bookDesc;
	}

	public void setBookDesc(String bookDesc) {
		this.bookDesc = bookDesc;
	}

	public Integer getBookTypeId() {
		return bookTypeId;
	}

	public void setBookTypeId(Integer bookTypeId) {
		this.bookTypeId = bookTypeId;
	}

	public int getIsflag() {
		return isflag;
	}

	public void setIsflag(int isflag) {
		this.isflag = isflag;
	}

	public int getId2() {
		return id2;
	}

	public void setId2(int id2) {
		this.id2 = id2;
	}

	public int getUserid() {
		return userid;
	}

	public void setUserid(int userid) {
		this.userid = userid;
	}

	public int getBookid() {
		return bookid;
	}

	public void setBookid(int bookid) {
		this.bookid = bookid;
	}

	public Date getBtime() {
		return btime;
	}

	public void setBtime(Date btime) {
		this.btime = btime;
	}

	public Date getRtime() {
		return rtime;
	}

	public void setRtime(Date rtime) {
		this.rtime = rtime;
	}

	@Override
	public String toString() {
		return "BookAndBorrow [id=" + id + ", bookName=" + bookName + ", author=" + author + ", sex=" + sex + ", price="
				+ price + ", bookDesc=" + bookDesc + ", bookTypeId=" + bookTypeId + ", isflag=" + isflag + ", id2="
				+ id2 + ", userid=" + userid + ", bookid=" + bookid + ", btime=" + btime + ", rtime=" + rtime + "]";
	}

}
