package com.bigdata.spider.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DBUtil {
	public static List<Map<String, String>> getMailList() {
		List<Map<String, String>> list = new ArrayList<Map<String, String>>();
		try {
			Connection conn = getConnectionByJDBC(
					LoadPropertyUtil.getDB("mysql_ip"),
					LoadPropertyUtil.getDB("mysql_db"),
					LoadPropertyUtil.getDB("mysql_user"),
					LoadPropertyUtil.getDB("mysql_pswd"));

			String sql = "select * from mail_list where status=1 and result=9 or (result=0 and timestampdiff(second, sendtime,current_timestamp)>=1200 and timestampdiff(second, createtime,current_timestamp)<=3600)";

			PreparedStatement stmt = conn.prepareStatement(sql);
			ResultSet result = stmt.executeQuery();

			while (result.next()) {
				Map<String, String>  map = new HashMap<String, String>();
				map.put("mlid", result.getString("mlid"));
				map.put("to", result.getString("receiver"));
				map.put("subject", result.getString("subject"));
				map.put("content", result.getString("content"));
				list.add(map);
			}

			result.close();
			stmt.close();
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}

	public static void setMailStatus(String mlid, int result) {
		try {
			Connection conn = getConnectionByJDBC(LoadPropertyUtil.getDB("mysql_ip"),
					LoadPropertyUtil.getDB("mysql_db"),
					LoadPropertyUtil.getDB("mysql_user"),
					LoadPropertyUtil.getDB("mysql_pswd"));

			String sql = "update mail_list set result=?,sendtime=? where mlid=?";
			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setInt(1, result);
			stmt.setTimestamp(2, TimeUtil.getCurSQLTimestamp());
			stmt.setInt(3, Integer.parseInt(mlid));
			int res = stmt.executeUpdate();
			
			System.out.println("更新了"+res+"行");

			stmt.close();
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public static Connection getConnectionByJDBC(String ip, String dbName,
			String userName, String pwd) {
		Connection conn = null;
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		try {
			conn = DriverManager
					.getConnection(
							"jdbc:mysql://"
									+ ip
									+ ":3306/"
									+ dbName
									+ "?seUnicode=true&characterEncoding=UTF-8&rewriteBatchedStatements=true",
							userName, pwd);
		} catch (SQLException e) {
			System.out.println("连接出现异常");
			e.printStackTrace();
		}

		return conn;
	}

	public static void main(String[] args) {
	}
}