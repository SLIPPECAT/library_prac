package com.example.caching.search.util;

import java.io.IOException;
import java.sql.SQLException;

public class Main {
	public static void main(String[] args) {

		String url = "jdbc:mysql://localhost:3306/library_service";
		String username = "root";
		String password = "wnsdud12!@";

		BookDataLoader loader = new BookDataLoader(url, username, password);

		try {
			loader.loadDataWithPaging(2000);

			} catch (IOException e) {
				e.printStackTrace();
			} catch (SQLException e) {
				e.printStackTrace();
			}
	}
}

