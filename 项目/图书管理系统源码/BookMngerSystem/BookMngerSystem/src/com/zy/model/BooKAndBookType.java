package com.zy.model;

public class BooKAndBookType {
	private int id;
	private String bookName;
	private String author;
	private String sex;
	private float price;
	private String bookDesc;
	private Integer bookTypeId;
	private int isflag;
	private int id2;
	private String bookTypeName;
	private String bookTypeDesc;

	public BooKAndBookType() {
		super();
		// TODO Auto-generated constructor stub
	}

	public BooKAndBookType(int id, String bookName, String author, String sex, float price, String bookDesc,
			Integer bookTypeId, int isflag, int id2, String bookTypeName, String bookTypeDesc) {
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
		this.bookTypeName = bookTypeName;
		this.bookTypeDesc = bookTypeDesc;
	}

	public BooKAndBookType(int id, String bookName, String bookTypeName, String author, Float price, String bookDesc) {
		this.id = id;
		this.bookName = bookName;
		this.bookTypeName = bookTypeName;
		this.author = author;
		this.price = price;
		this.bookDesc = bookDesc;
	}

	public BooKAndBookType(int id, String bookName, String author, Float price, String bookDesc) {
		this.id = id;
		this.bookName = bookName;
		this.author = author;
		this.price = price;
		this.bookDesc = bookDesc;
	}

	public BooKAndBookType(int id, String bookName, String author, String sex, Float price, String bookDesc,
			String bookTypeName, int isflag) {
		this.id = id;
		this.bookName = bookName;
		this.author = author;
		this.sex = sex;
		this.price = price;
		this.bookDesc = bookDesc;
		this.bookTypeName = bookTypeName;
		this.isflag = isflag;
	}

	public BooKAndBookType(int id, String bookName, String author, Float price, String bookDesc, String bookTypeName) {
		this.id = id;
		this.bookName = bookName;
		this.author = author;
		this.price = price;
		this.bookDesc = bookDesc;
		this.bookTypeName = bookTypeName;
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

	public String getBookTypeName() {
		return bookTypeName;
	}

	public void setBookTypeName(String bookTypeName) {
		this.bookTypeName = bookTypeName;
	}

	public String getBookTypeDesc() {
		return bookTypeDesc;
	}

	public void setBookTypeDesc(String bookTypeDesc) {
		this.bookTypeDesc = bookTypeDesc;
	}

	@Override
	public String toString() {
		return "BooKAndBookType [id=" + id + ", bookName=" + bookName + ", author=" + author + ", sex=" + sex
				+ ", price=" + price + ", bookDesc=" + bookDesc + ", bookTypeId=" + bookTypeId + ", isflag=" + isflag
				+ ", id2=" + id2 + ", bookTypeName=" + bookTypeName + ", bookTypeDesc=" + bookTypeDesc + "]";
	}

}
