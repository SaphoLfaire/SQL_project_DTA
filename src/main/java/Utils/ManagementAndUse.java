package Utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import Objects.Book;
import Objects.Client;

public class ManagementAndUse {

	private static final String url = "jdbc:postgresql://localhost:5432/SQL_project";

	private ManagementAndUse() {

	}

	public static void buy(Client client, Book book) throws SQLException {

		try (Connection conn = DriverManager.getConnection(url, "postgres", "postgres")) {
			setIdCLient(client);

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

			setIdBook(book);

			try (PreparedStatement stmt = conn
					.prepareStatement("UPDATE client SET id_favourite_book = ? WHERE client.lastname = ?")) {

				stmt.setLong(1, book.getId());
				stmt.setString(2, client.getLastName());
				stmt.executeUpdate(); // NE PAS OUBLIER CETTE LIGNE
				stmt.close();

			} catch (Exception e) {
				System.out.println(e.getMessage());

			} finally {
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

			} finally {
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
		conn.close();

		return true;
	}

	public static List<Book> clientsBook(Client client) throws SQLException {
		setIdCLient(client);
		Connection conn = DriverManager.getConnection(url, "postgres", "postgres");
		PreparedStatement stmt = conn.prepareStatement(
				"SELECT book.* FROM book JOIN prefer ON prefer.id_book = book.id JOIN client ON client.id = prefer.id_client WHERE prefer.id_client = ?");
		stmt.setInt(1, client.getId());
		List<Book> bookList = new ArrayList<Book>();
		ResultSet resultSet = stmt.executeQuery();
		while (resultSet.next()) {
			bookList.add(new Book(resultSet.getString("title"), resultSet.getString("author")));
		}

		stmt.close();
		conn.close();

		return bookList;
	}

	public static List<Client> bookByClient(Book book) throws SQLException {
		setIdBook(book);
		Connection conn = DriverManager.getConnection(url, "postgres", "postgres");
		PreparedStatement stmt = conn.prepareStatement(
				"SELECT client.* FROM client JOIN prefer ON prefer.id_client = client.id JOIN book ON book.id = prefer.id_book WHERE prefer.id_book = ?");
		stmt.setInt(1, book.getId());
		List<Client> clientList = new ArrayList<Client>();
		ResultSet resultSet = stmt.executeQuery();
		while (resultSet.next()) {
			clientList.add(new Client(resultSet.getString("lastname"), resultSet.getString("firstname")));
		}

		stmt.close();
		conn.close();

		return clientList;
	}

	private static void setIdCLient(Client client) throws SQLException {

		Connection conn = DriverManager.getConnection(url, "postgres", "postgres");
		PreparedStatement stmt1 = conn.prepareStatement("SELECT client.id FROM client WHERE client.lastname = ?");

		stmt1.setString(1, client.getLastName());
		ResultSet resultSet = stmt1.executeQuery();
		while (resultSet.next()) {
			client.setId(resultSet.getInt("id")); // "id" = nom de la colonne concernee
		}
		stmt1.close();
		conn.close();

	}

	private static void setIdBook(Book book) throws SQLException {

		Connection conn = DriverManager.getConnection(url, "postgres", "postgres");
		PreparedStatement stmt1 = conn.prepareStatement("SELECT book.id FROM book WHERE book.title = ?");

		stmt1.setString(1, book.getTitle());
		ResultSet resultSet = stmt1.executeQuery();
		while (resultSet.next()) {
			book.setId(resultSet.getInt("id")); // "id" = nom de la colonne concernee

		}

		stmt1.close();
		conn.close();

	}

}
