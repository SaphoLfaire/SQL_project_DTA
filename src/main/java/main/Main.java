package main;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class Main {

	public static void main(String arg[]) throws SQLException {

		String url = "jdbc:postgresql://localhost:5432/SQL_project";

		try (Connection conn = DriverManager.getConnection(url, "postgres", "postgres")) {

			conn.setAutoCommit(false);

			try (Statement stmt = conn.createStatement()) {
				stmt.executeUpdate("CREATE TABLE IF NOT EXISTS book(id bigserial PRIMARY KEY, "
						+ "title varchar(255) NOT NULL, " + "author varchar(255))");

				stmt.executeUpdate("CREATE TABLE IF NOT EXISTS client(id bigserial PRIMARY KEY, "
						+ "lastname varchar(255) NOT NULL, " + "firstname varchar(255), " + "gender varchar(255), "
						+ "id_favourite_book INTEGER CONSTRAINT fk_id_fav_book REFERENCES book(id))");

				stmt.executeUpdate(
						"CREATE TABLE IF NOT EXISTS prefer (id_book INTEGER CONSTRAINT fk_id_book REFERENCES book(id), "
								+ "id_client INTEGER CONSTRAINT fk_id_client REFERENCES client(id), "
								+ "PRIMARY KEY (id_book, id_client))");

				conn.commit();
				stmt.close();
				
				

			} catch (Exception e) {
				System.out.println(e.getMessage());
				conn.rollback();
			} finally {
				conn.setAutoCommit(true);

				if (conn != null) {
					conn.close();
				}

			}
		}
	}

}
