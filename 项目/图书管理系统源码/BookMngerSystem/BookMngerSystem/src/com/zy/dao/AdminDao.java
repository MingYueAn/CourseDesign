package com.zy.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.zy.model.BooKAndBookType;
import com.zy.model.Book;
import com.zy.model.BookType;
import com.zy.utils.DButils;
import com.zy.utils.JDBCUtils;

public class AdminDao {

	public BookType SelectBookTypeName(String bookTypeName) {
		String sql = "select * from t_booktype where bookTypeName=?";
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection conn = null;
		conn = JDBCUtils.getConnection();
		BookType b = null;
		try {
			ps = conn.prepareStatement(sql);
			ps.setString(1, bookTypeName);
			rs = ps.executeQuery();
			while (rs.next()) {
				int id = rs.getInt("id");
				String bookTypeName2 = rs.getString("bookTypeName");
				String booktypeDesc = rs.getString("booktypeDesc");
				b = new BookType(id, bookTypeName2, booktypeDesc);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JDBCUtils.close(rs, null, ps, conn);
		}
		return b;

	}

	public int AddBookType(String bookTypeName, String bookTypeDesc) {
		String sql = "insert into t_booktype(bookTypeName,bookTypeDesc) value(?,?)";
		return DButils.jdbc_update(sql, bookTypeName, bookTypeDesc);
	}

	public List<BookType> listBookType(String bookTypeName) {
		String sql = "select * from t_booktype where bookTypeName like ?";
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection conn = null;
		conn = JDBCUtils.getConnection();
		BookType b = null;
		List<BookType> list = new ArrayList<BookType>();
		try {
			ps = conn.prepareStatement(sql);
			ps.setString(1, "%" + bookTypeName + "%");
			rs = ps.executeQuery();
			while (rs.next()) {
				int id = rs.getInt("id");
				String bookTypeName2 = rs.getString("bookTypeName");
				String booktypeDesc = rs.getString("booktypeDesc");
				b = new BookType(id, bookTypeName2, booktypeDesc);
				list.add(b);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JDBCUtils.close(rs, null, ps, conn);
		}
		return list;

	}

	public int udpateBookType(String bookTypeName, String bookTypeDesc, int bid) {
		String sql = "update t_booktype set bookTypeName=?,bookTypeDesc=? where id=?";
		return DButils.jdbc_update(sql, bookTypeName, bookTypeDesc, bid);
	}

	public int deleteBookType(int id) {
		String sql = "delete from t_booktype where id=?";
		return DButils.jdbc_update(sql, id);
	}

	public int deleteBook(int id) {
		String sql = "delete from t_book where bookTypeid=?";
		return DButils.jdbc_update(sql, id);
	}

	public int addBook(String bookName, String author, Float price, String bookDesc, String sex, int bookTypeId) {
		String sql = "insert into t_book(bookName,author,sex,price,bookDesc,bookTypeId,isflag) value(?,?,?,?,?,?,?)";
		return DButils.jdbc_update(sql, bookName, author, sex, price, bookDesc, bookTypeId, 0);
	}

	public List<BooKAndBookType> listBook(String bookName, String author, String bookTypeName2) {
		String sql = "select * from t_book,t_booktype where t_book.bookTypeId=t_booktype.id and t_book.bookName LIKE ? and t_book.author LIKE ? and t_booktype.bookTypeName LIKE ?";
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection conn = null;
		conn = JDBCUtils.getConnection();
		BooKAndBookType b = null;
		List<BooKAndBookType> list = new ArrayList<BooKAndBookType>();
		try {
			ps = conn.prepareStatement(sql);
			ps.setString(1, "%" + bookName + "%");
			ps.setString(2, "%" + author + "%");
			ps.setString(3, "%" + bookTypeName2 + "%");
			rs = ps.executeQuery();
			while (rs.next()) {
				int id = rs.getInt("id");
				String bookName2 = rs.getString("bookName");
				String author2 = rs.getString("author");
				String sex = rs.getString("sex");
				Float price = rs.getFloat("price");
				String bookDesc = rs.getString("bookDesc");
				String bookTypeName = rs.getString("bookTypeName");
				int isFalg = rs.getInt("isflag");
				b = new BooKAndBookType(id, bookName2, author2, sex, price, bookDesc, bookTypeName, isFalg);
				list.add(b);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JDBCUtils.close(rs, null, ps, conn);
		}
		return list;
	}

	public int updateBook(Book book) {
		String sql = "update t_book set bookName=?,author=?,price=?,bookDesc=?,sex=?,bookTypeId=? where id=?";
		return DButils.jdbc_update(sql, book.getBookName(), book.getAuthor(), book.getPrice(), book.getBookDesc(),
				book.getSex(), book.getBookTypeId(), book.getId());
	}

	public int deleteBook2(int bookid) {
		String sql = "delete from t_book where id=?";
		return DButils.jdbc_update(sql, bookid);
	}

}
