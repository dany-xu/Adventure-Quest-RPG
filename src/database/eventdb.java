package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;

public class eventdb {
	private Connection connection;
	private Statement statement;

	public eventdb() {
		try {
			// connect to the SQLite database
			connection = DriverManager.getConnection("jdbc:sqlite:game.db");
			statement = connection.createStatement();

			// create the 'event' table if not exists
			statement.executeUpdate(
					"CREATE TABLE IF NOT EXISTS event (id INTEGER PRIMARY KEY AUTOINCREMENT, content TEXT, keyword TEXT)");

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	// insert an event into the db if not exists
	public void insertEventIfNotExists(String content, String keyword) {
		try {
			// Check if the event with content already exists
			PreparedStatement checkContentStatement = connection
					.prepareStatement("SELECT COUNT(*) AS count FROM event WHERE content = ?");
			checkContentStatement.setString(1, content);
			ResultSet contentResultSet = checkContentStatement.executeQuery();
			if (contentResultSet.next() && contentResultSet.getInt("count") > 0) {
				System.out.println("Inserting event already exists with content:  '" + content + "'");
				return;
			}

			// Check if the event with keyword already exists
			PreparedStatement checkKeywordStatement = connection
					.prepareStatement("SELECT COUNT(*) AS count FROM event WHERE keyword = ?");
			checkKeywordStatement.setString(1, keyword);
			ResultSet keywordResultSet = checkKeywordStatement.executeQuery();
			if (keywordResultSet.next() && keywordResultSet.getInt("count") > 0) {
				System.out.println("Inserting event already exists with keyword: '" + keyword + "'");
				return;
			}

			// Event does not exist, insert it
			PreparedStatement insertStatement = connection
					.prepareStatement("INSERT INTO event (content, keyword) VALUES (?, ?)");
			insertStatement.setString(1, content);
			insertStatement.setString(2, keyword);
			insertStatement.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	// Function to retrieve a random event from the database
	public event getRandomEvent() {
		try {
			ResultSet resultSet = statement.executeQuery("SELECT * FROM event ORDER BY RANDOM() LIMIT 1");
			if (resultSet.next()) {
				return new event(resultSet.getString("content"), resultSet.getString("keyword"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static class event {
		private String content;
		private String keyword;

		public event(String content, String keyword) {
			this.content = content;
			this.keyword = keyword;
		}

		public String getContent() {
			return content;
		}

		public String getKeyword() {
			return keyword;
		}
	}

	public void updateEvent(String oldKeyword, String newContent, String newKeyword) {
		try {
			// Check if the new content already exists
			PreparedStatement checkContentStatement = connection
					.prepareStatement("SELECT COUNT(*) AS count FROM event WHERE content = ?");
			checkContentStatement.setString(1, newContent);
			ResultSet contentResultSet = checkContentStatement.executeQuery();
			if (contentResultSet.next() && contentResultSet.getInt("count") > 0) {
				System.out.println("Updating event already exists with content '" + newContent + "'");
				return;
			}

			// Check if the new keyword already exists
			PreparedStatement checkKeywordStatement = connection
					.prepareStatement("SELECT COUNT(*) AS count FROM event WHERE keyword = ?");
			checkKeywordStatement.setString(1, newKeyword);
			ResultSet keywordResultSet = checkKeywordStatement.executeQuery();
			if (keywordResultSet.next() && keywordResultSet.getInt("count") > 0) {
				System.out.println("Updating event already exists with keyword '" + newKeyword + "'");
				return;
			}

			// Update the event
			PreparedStatement updateStatement = connection
					.prepareStatement("UPDATE event SET content = ?, keyword = ? WHERE keyword = ?");
			updateStatement.setString(1, newContent);
			updateStatement.setString(2, newKeyword);
			updateStatement.setString(3, oldKeyword);
			updateStatement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	// Overloaded function to update an event without changing keywords
	public void updateEvent(String oldKeyword, String newContent) {
		updateEvent(oldKeyword, newContent, oldKeyword);
	}

	// Function to execute a SQL query on the event table
	public void queryEventTable(String sqlQuery) {
		try {
			ResultSet resultSet = statement.executeQuery(sqlQuery);
			ResultSetMetaData metaData = resultSet.getMetaData();
			int columnCount = metaData.getColumnCount();

			while (resultSet.next()) {
				for (int i = 1; i <= columnCount; i++) {
					String columnValue = resultSet.getString(i);
					System.out.print(columnValue + "\t");
				}
				System.out.println(); // Move to the next line for the next row
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	/*
	 * public static void main(String[] args) { eventdb gameDB = new eventdb();
	 * 
	 * // Example usage: inserting an event if not exists
	 * gameDB.insertEventIfNotExists(
	 * "The player encounters a seemingly ordinary old man who offers a choice of one of three different boxes, each containing a different fantastic item."
	 * , "Gift from the mysterious old man - 1");
	 * 
	 * gameDB.updateEvent(
	 * "The player encounters a seemingly ordinary old man who offers a choice of one of three different boxes, each containing a different fantastic item."
	 * , "Gift from the mysterious old man - 1");
	 * 
	 * // Example usage: retrieving a random event event randomEvent =
	 * gameDB.getRandomEvent(); System.out.println("Random event: " +
	 * randomEvent.getContent() + randomEvent.getKeyword());
	 * 
	 * // Example usage: querying the event table
	 * gameDB.queryEventTable("SELECT \"keyword\" FROM event;");
	 * gameDB.queryEventTable("SELECT COUNT(*) AS count FROM event;");
	 * 
	 * }
	 */
}
