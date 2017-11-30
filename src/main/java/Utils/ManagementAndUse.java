package Utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import Objects.Book;
import Objects.Client;
import Objects.Gender;

public class ManagementAndUse {

	private static final String url = "jdbc:postgresql://localhost:5432/SQL_project";

	private ManagementAndUse() {

	}

	public static void buy(Client client, Book book) {

	}

	public static void prefer(Client client, Book book) {

	}

	public static Boolean createClient(Client... clients) throws SQLException {

		try (Connection conn = DriverManager.getConnection(url, "postgres", "postgres")) {

			try (PreparedStatement stmt = conn.prepareStatement(
					"INSERT INTO client(lastname, firstname, gender) VALUES(?, ?, ?)")) {
				
				for (Client client : clients) {
					stmt.setString(1, client.getLastName());
					stmt.setString(2, client.getFirstName());
					stmt.setString(3, client.getGender().name());
					stmt.addBatch();
				}

				stmt.executeBatch();
				stmt.close();

			} catch (Exception e) {
				System.out.println(e.getMessage());
				//conn.rollback();

			} finally {
				//conn.setAutoCommit(true);

				conn.close();
			}
		}

		return true;

	}

	public static Boolean createBook(Book... books) throws SQLException {

		try (Connection conn = DriverManager.getConnection(url, "postgres", "postgres")) {

			try (PreparedStatement stmt = conn.prepareStatement("INSERT INTO book(title, author) VALUES(?, ?)")) {

				for (Book book : books) {
					stmt.setString(1, book.getTitle());
					stmt.setString(2, book.getAuthor());
					stmt.addBatch();
				}

				stmt.executeBatch();
				stmt.close();

			} catch (Exception e) {
				System.out.println(e.getMessage());
				//conn.rollback();

			} finally {
				//conn.setAutoCommit(true);

				conn.close();
			}
		}

		return true;
	}

	public static List<Book> clientsBook(Client client) {
		return null;
	}

	public static List<Client> bookByClient(Book book) {
		return null;
	}

}
