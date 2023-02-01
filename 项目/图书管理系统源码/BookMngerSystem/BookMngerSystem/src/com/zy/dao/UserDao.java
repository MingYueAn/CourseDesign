package com.zy.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.zy.model.BooKAndBookType;
import com.zy.model.Book;
import com.zy.model.BookAndBorrow;
import com.zy.model.User;
import com.zy.utils.DButils;
import com.zy.utils.JDBCUtils;

public class UserDao {

	public static List<User> login(String name, String pwd, String userid) {
		String sql = "select * from t_user where userName=? and password=? and userid=?";
		List<User> jdbc_select = DButils.jdbc_select(sql, User.class, name, pwd, userid);
		return jdbc_select;
	}

	public int reg(String name, String pwd) {
		String sql = "insert into t_user(userName,password,userid) value(?,?,?)";
		int jdbc_update = DButils.jdbc_update(sql, name, pwd, "0");
		return jdbc_update;
	}

	public List<BookAndBorrow> listBook(int id) {
		String sql = "select * from t_book,t_borrow where t_book.id=t_borrow.bookid and userid=? and btime is not null and rtime is null";
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection conn = null;
		conn = JDBCUtils.getConnection();
		List<BookAndBorrow> list = new ArrayList<BookAndBorrow>();
		try {
			ps = conn.prepareStatement(sql);
			ps.setInt(1, id);
			rs = ps.executeQuery();
			while (rs.next()) {
				int id2 = rs.getInt("id");
				String bookName = rs.getString("bookName");
				String author = rs.getString("author");
				Date rdate = rs.getDate("btime");
				BookAndBorrow book = new BookAndBorrow(id2, bookName, author, rdate);
				list.add(book);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JDBCUtils.close(rs, null, ps, conn);
		}
		return list;
	}

	public int updatePwd(String newpwd, String name) {
		String sql = "update t_user set password=? where userName=?";
		int i = DButils.jdbc_update(sql, newpwd, name);
		return i;
	}

	public List<BooKAndBookType> listBook2() {
		String sql = "select * from t_book,t_booktype  where t_book.bookTypeId=t_booktype.id and isflag=0";
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection conn = null;
		conn = JDBCUtils.getConnection();
		List<BooKAndBookType> list = new ArrayList<BooKAndBookType>();
		try {
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();
			while (rs.next()) {
				int Bookid = rs.getInt("id");
				String bookName = rs.getString("bookName");
				String bookTypeName = rs.getString("bookTypeName");
				String author = rs.getString("author");
				Float price = rs.getFloat("price");
				String bookDesc = rs.getString("bookDesc");
				BooKAndBookType book = new BooKAndBookType(Bookid, bookName, author, price, bookDesc, bookTypeName);
				list.add(book);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JDBCUtils.close(rs, null, ps, conn);
		}
		return list;
	}

	public User selectUname(String name) {
		String sql = "select * from t_user where userName=?";
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection conn = null;
		conn = JDBCUtils.getConnection();
		User user = null;
		try {
			ps = conn.prepareStatement(sql);
			ps.setString(1, name);
			rs = ps.executeQuery();

			while (rs.next()) {
				int id = rs.getInt("id");
				String username = rs.getString("userName");
				String password = rs.getString("password");
				user = new User(id, username, password);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JDBCUtils.close(rs, null, ps, conn);
		}
		return user;
	}

	public Book SelectBook(int bookid) {
		String sql = "select * from t_book where isFlag=1 and id=?";
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection conn = null;
		conn = JDBCUtils.getConnection();
		Book book = null;
		try {
			ps = conn.prepareStatement(sql);
			ps.setInt(1, bookid);
			rs = ps.executeQuery();
			while (rs.next()) {
				int id = rs.getInt("id");
				String bookName = rs.getString("bookName");
				String author = rs.getString("author");
				book = new Book(id, bookName, author);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JDBCUtils.close(rs, null, ps, conn);
		}
		return book;
	}

	public int borrowBook(int bookid) {
		String sql = "update t_book set isFlag=1 where id=?";
		return DButils.jdbc_update(sql, bookid);
	}

	public int insertBorrow(int id, int bookid) {
		String sql = "insert into t_borrow(userid,bookid,btime) value(?,?,?)";
		return DButils.jdbc_update(sql, id, bookid, new Date(System.currentTimeMillis()));
	}

	public int updateBookIsFlag(String bookName) {
		String sql = "update t_book set isflag=0 where bookName=?";
		return DButils.jdbc_update(sql, bookName);
	}

	public Book SelectBookId(String bookName) {
		String sql = "select * from t_book where isflag=1 and bookName=?";
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection conn = null;
		conn = JDBCUtils.getConnection();
		Book book = null;
		try {
			ps = conn.prepareStatement(sql);
			ps.setString(1, bookName);
			rs = ps.executeQuery();
			while (rs.next()) {
				int id = rs.getInt("id");
				String bookName2 = rs.getString("bookName");
				String author = rs.getString("author");
				book = new Book(id, bookName2, author);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JDBCUtils.close(rs, null, ps, conn);
		}
		return book;
	}

	public int insertRtime(int id, int bookid) {
		String sql = "update t_borrow set rtime=? where userid=? and bookid=?";
		return DButils.jdbc_update(sql, new Date(System.currentTimeMillis()), id, bookid);
	}

	public Book selectBookId(String bookName) {
		String sql = "select * from t_book where bookName=?";
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection conn = null;
		conn = JDBCUtils.getConnection();
		Book book = null;
		try {
			ps = conn.prepareStatement(sql);
			ps.setString(1, bookName);
			rs = ps.executeQuery();
			while (rs.next()) {
				int id = rs.getInt("id");
				String bookName2 = rs.getString("bookName");
				String author = rs.getString("author");
				book = new Book(id, bookName2, author);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JDBCUtils.close(rs, null, ps, conn);
		}
		return book;
	}

	public int updateIsFlag(int bookid) {
		String sql = "update t_book set isflag=0 where id=?";
		return DButils.jdbc_update(sql, bookid);
	}

	public int updateRtime(int userid, int bookid) {
		String sql = "update t_borrow set rtime=? where userid=? and bookid=?";
		return DButils.jdbc_update(sql, new Date(System.currentTimeMillis()), userid, bookid);
	}

	public List<BookAndBorrow> listBookHistory(int userid) {
		String sql = "select t_book.bookName ,t_book.author ,t_borrow.btime ,t_borrow.rtime  from  t_borrow,t_book where t_book.id=t_borrow.bookid and userid =? and rtime is not NULL";
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection conn = null;
		conn = JDBCUtils.getConnection();
		List<BookAndBorrow> list = new ArrayList<>();
		BookAndBorrow book = null;
		try {
			ps = conn.prepareStatement(sql);
			ps.setInt(1, userid);
			rs = ps.executeQuery();
			while (rs.next()) {
				String bookName = rs.getString("bookName");
				String author = rs.getString("author");
				Date btime = rs.getDate("btime");
				Date rtime = rs.getDate("rtime");
				book = new BookAndBorrow(bookName, author, btime, rtime);
				list.add(book);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JDBCUtils.close(rs, null, ps, conn);
		}
		return list;
	}

}
