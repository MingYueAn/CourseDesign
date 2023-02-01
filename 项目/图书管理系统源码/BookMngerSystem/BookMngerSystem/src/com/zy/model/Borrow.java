package com.zy.model;

import java.sql.Date;

/**
 * 借阅书籍表
 * 
 * @author 胡绵发
 *
 */
public class Borrow {
	private int id;// 借阅id
	private int userid;// 用户id
	private int bookid;// 书籍id
	private Date btime;// 借书日期
	private Date rtime;// 还书日期

	public Borrow() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Borrow(int id, int userid, int bookid, Date btime, Date rtime) {
		super();
		this.id = id;
		this.userid = userid;
		this.bookid = bookid;
		this.btime = btime;
		this.rtime = rtime;
	}

	public Borrow(int userid, int bookid) {
		this.userid = userid;
		this.bookid = bookid;
	}

	public Borrow(int userid, int bookid, Date btime) {
		this.userid = userid;
		this.bookid = bookid;
		this.btime = btime;
	}

	public Borrow(Date btime, Date rtime) {
		this.btime = btime;
		this.rtime = rtime;
	}

	public Borrow(int bookid) {
		this.bookid = bookid;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
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
		return "Borrow [id=" + id + ", userid=" + userid + ", bookid=" + bookid + ", btime=" + btime + ", rtime="
				+ rtime + "]";
	}
}
