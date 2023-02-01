package com.zy.utils;

import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;

/**
 * 对crud进行封装
 * 
 * @author 胡绵发
 *
 */
public class DButils {
	/**
	 * 对任意表的一个非查询
	 * 
	 * @param sql
	 * @param objects
	 * @return
	 */
	public static int jdbc_update(String sql, Object... objects) {
		Connection conn = JDBCUtils.getConnection();
		PreparedStatement ps = null;
		int i = 0;
		try {
			// 预编译
			ps = conn.prepareStatement(sql);
			// 给sql赋值
			for (int j = 0; j < objects.length; j++) {
				ps.setObject(j + 1, objects[j]);
			}
			// 执行
			i = ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			// 关闭资源
			JDBCUtils.close(null, null, ps, conn);
		}
		return i;
	}

	/**
	 * 查询任意表中的任意字段
	 * 
	 * @param sql
	 * @param classes
	 * @param objects
	 * @return
	 */
	public static <T> List<T> jdbc_select(String sql, Class<T> classes, Object... objects) {
		Connection conn = JDBCUtils.getConnection();
		PreparedStatement ps = null;
		ResultSet rs = null;
		List<T> list = null;
		try {
			ps = conn.prepareStatement(sql);
			// 给sql赋值
			for (int i = 0; i < objects.length; i++) {
				ps.setObject(i + 1, objects[i]);
			}
			rs = ps.executeQuery();
			ResultSetMetaData rsmd = rs.getMetaData();// 数据表的结构
			int col_count = rsmd.getColumnCount();// 获取到查询结果 列数
			list = new ArrayList<T>();
			while (rs.next()) {
				T t = classes.newInstance();// 创建Javabean对象
				Map<String, Object> map = new HashMap<String, Object>();
				// 循环拿到当前行的数据
				for (int i = 0; i < col_count; i++) {
					String colName = rsmd.getColumnLabel(i + 1);// 拿到列名
					Object colValue = rs.getObject(colName);// 拿到值
					map.put(colName, colValue);
				}
				// 将map中key-value的值映射到对象
				BeanUtils.populate(t, map);
				list.add(t);
			}
			// System.out.println(rsmd);
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
		return list;
	}
}
