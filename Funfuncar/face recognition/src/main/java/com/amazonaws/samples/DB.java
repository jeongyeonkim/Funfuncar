package com.amazonaws.samples;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public class DB {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		Connection con = null;
		String url = "jdbc:mysql://localhost:3306/funfuncar?serverTimezone=UTC";
		try {
			Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
			System.out.println("after forName");
			con = DriverManager.getConnection(url, "root", "as7095as");
			System.out.println("DBms connection success");
			System.out.println("DB load success");
		} catch (Exception e) {
			System.out.println("DB load fail " + e.toString());
		}

	}
}
