package main;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import Objects.Book;
import Objects.Client;
import Objects.Gender;
import Utils.ManagementAndUse;

public class Main {

	public static void main(String arg[]) throws SQLException {

		String url = "jdbc:postgresql://localhost:5432/SQL_project";

		try (Connection conn = DriverManager.getConnection(url, "postgres", "postgres")) {

			//conn.setAutoCommit(false);

			try (Statement stmt = conn.createStatement()) {
				/**
				 * Creation des tables uniquement si celles ci n'existent pas
				 * Table book (id, title, author);
				 * Table client (id, lastname, firstname, gender, id_favourite_book);
				 * Table prefer (id_client, id_book); Attention cette table s'appelle prefer mais c'est une table
				 * relationnelle qui mériterait de s'appeler achat;
				 */
				stmt.executeUpdate("CREATE TABLE IF NOT EXISTS book(id bigserial PRIMARY KEY, "
						+ "title varchar(255) NOT NULL, " + "author varchar(255))");

				stmt.executeUpdate("CREATE TABLE IF NOT EXISTS client(id bigserial PRIMARY KEY, "
						+ "lastname varchar(255) NOT NULL, " + "firstname varchar(255), " + "gender varchar(255), "
						+ "id_favourite_book INTEGER CONSTRAINT fk_id_fav_book REFERENCES book(id))");

				stmt.executeUpdate(
						"CREATE TABLE IF NOT EXISTS prefer (id_book INTEGER CONSTRAINT fk_id_book REFERENCES book(id), "
								+ "id_client INTEGER CONSTRAINT fk_id_client REFERENCES client(id), "
								+ "PRIMARY KEY (id_book, id_client))");
				
				stmt.executeUpdate("TRUNCATE prefer CASCADE");
				stmt.executeUpdate("TRUNCATE book CASCADE");
				stmt.executeUpdate("TRUNCATE client CASCADE");
				
				
				Book book1 = new Book("What's New in Java 8", "Adam L. Davis");
				Book book2 =(new Book("Welcome to Java for Python Programmers", "Brad Miller"));
				Book book3 =(new Book("UML For Java Programmers", "Robert Cecil Martin"));
				Book book4 =(new Book("The Java EE7 Tutorial", null));
				ManagementAndUse.createBook(book1, book2, book3, book4);

				Client client1 = new Client ("AUPETIT", "Sapho", Gender.F);
				Client client2 = new Client ("LEPROPRE", "Florian", Gender.M);
				Client client3 = new Client ("LAPROPRE", "Anais", Gender.F);
				ManagementAndUse.createClient(client1, client2, client3);
				
				ManagementAndUse.prefer(client1, book1);
				ManagementAndUse.buy(client1, book1);
				
				//conn.commit();
				stmt.close();
				
				
				

			} catch (Exception e) {
				System.out.println(e.getMessage());
				//conn.rollback();
			} finally {
				//conn.setAutoCommit(true);

				if (conn != null) {
					conn.close();
				}

			}
		}
	}

}
