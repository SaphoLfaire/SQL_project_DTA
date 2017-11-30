package Utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import Objects.Book;
import Objects.Client;

public class ManagementAndUse {

	private static final String url = "jdbc:postgresql://localhost:5432/SQL_project";

	private ManagementAndUse() {

	}

	public static void buy(Client client, Book book) throws SQLException {

		try (Connection conn = DriverManager.getConnection(url, "postgres", "postgres")) {

			try (PreparedStatement stmt1 = conn
					.prepareStatement("SELECT client.id FROM client WHERE client.lastname = ?")) {

				stmt1.setString(1, client.getLastName());
				ResultSet resultSet = stmt1.executeQuery();
				while (resultSet.next()) {
					client.setId(resultSet.getInt("id")); // "id" = nom de la colonne concernee
				}

			}

			try (PreparedStatement stmt = conn
					.prepareStatement("INSERT INTO prefer (id_client, id_book) VALUES(?,?)")) {

				stmt.setLong(1, client.getId());
				stmt.setLong(2, book.getId());
				stmt.executeUpdate(); // NE PAS OUBLIER CETTE LIGNE
				stmt.close();

			} catch (Exception e) {
				System.out.println(e.getMessage());

			} finally {

				conn.close();
			}

		}
	}

	public static void prefer(Client client, Book book) throws SQLException {

		try (Connection conn = DriverManager.getConnection(url, "postgres", "postgres")) {

			try (PreparedStatement stmt1 = conn.prepareStatement("SELECT book.id FROM book WHERE book.title = ?")) {

				stmt1.setString(1, book.getTitle());
				ResultSet resultSet = stmt1.executeQuery();
				while (resultSet.next()) {
					book.setId(resultSet.getInt("id")); // "id" = nom de la colonne concernee
				}

			}

			try (PreparedStatement stmt = conn
					.prepareStatement("UPDATE client SET id_favourite_book = ? WHERE client.lastname = ?")) {

				stmt.setLong(1, book.getId());
				stmt.setString(2, client.getLastName());
				stmt.executeUpdate(); // NE PAS OUBLIER CETTE LIGNE
				stmt.close();

			} catch (Exception e) {
				System.out.println(e.getMessage());
				// conn.rollback();

			} finally {
				// conn.setAutoCommit(true);

				conn.close();
			}
		}

	}

	public static Boolean createClient(Client... clients) throws SQLException {

		try (Connection conn = DriverManager.getConnection(url, "postgres", "postgres")) {

			try (PreparedStatement stmt = conn
					.prepareStatement("INSERT INTO client(lastname, firstname, gender) VALUES(?, ?, ?)")) {

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
				// conn.rollback();

			} finally {
				// conn.setAutoCommit(true);

				conn.close();
			}
		}

		return true;

	}

	public static Boolean createBook(Book... books) throws SQLException {

		Connection conn = DriverManager.getConnection(url, "postgres", "postgres");

		PreparedStatement stmt = conn.prepareStatement("INSERT INTO book(title, author) VALUES(?, ?)");

		for (Book book : books) {
			stmt.setString(1, book.getTitle());
			stmt.setString(2, book.getAuthor());
			stmt.addBatch();
		}

		stmt.executeBatch();
		stmt.close();

		return true;
	}

	public static List<Book> clientsBook(Client client) {
		return null;
	}

	public static List<Client> bookByClient(Book book) {
		return null;
	}

}
