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

	public static Client createClient(String lastName, String firstName, Gender gender, int idFav) throws SQLException {

		Client client = new Client(lastName, firstName, gender, idFav);

		try (Connection conn = DriverManager.getConnection(url, "postgres", "postgres")) {

			try (PreparedStatement stmt = conn.prepareStatement(
					"INSERT INTO client(lastname, firstname, gender, id_favourite_book) VALUES(?, ?, ?, ?)")) {
				stmt.setString(1, client.getLastName());
				stmt.setString(2, client.getFirstName());
				stmt.setString(3, client.getGender().name());
				stmt.setLong(4, client.getIdFav());
				stmt.addBatch();

				stmt.close();

			} catch (Exception e) {
				System.out.println(e.getMessage());
				conn.rollback();

			} finally {
				conn.setAutoCommit(true);

				conn.close();
			}
		}

		return client;

	}

	public static Book createBook(String title, String author) throws SQLException {

		Book book = new Book(title, author);
		try (Connection conn = DriverManager.getConnection(url, "postgres", "postgres")) {

			try (PreparedStatement stmt = conn.prepareStatement("INSERT INTO book(title, author) VALUES(?, ?)")) {
				stmt.setString(1, book.getTitle());
				stmt.setString(2, book.getAuthor());
				stmt.addBatch();

				stmt.close();

			} catch (Exception e) {
				System.out.println(e.getMessage());
				conn.rollback();

			} finally {
				conn.setAutoCommit(true);

				conn.close();
			}
		}

		return book;
	}

	public static List<Book> clientsBook(Client client) {
		return null;
	}

	public static List<Client> bookByClient(Book book) {
		return null;
	}

}
