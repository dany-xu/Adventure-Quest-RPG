package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;

import event.Event;

public class eventdb {
	private Connection connection;
	private Statement statement;
	private String tableName;
	private double strength;
	private double intelligence;
	private double agility;
	private double stability; // Changed from charisma
	private double defense; // Changed from wisdom

	public eventdb(String table, double strength, double intelligence, double agility, double stability,
			double defense) {
		this.tableName = table;
		this.strength = strength;
		this.intelligence = intelligence;
		this.agility = agility;
		this.stability = stability;
		this.defense = defense;
		try {
			// connect to the SQLite database
			connection = DriverManager.getConnection("jdbc:sqlite:game.db");
			statement = connection.createStatement();

			// create the specified table if not exists
			String createTableQuery = "CREATE TABLE IF NOT EXISTS " + tableName
					+ " (id INTEGER PRIMARY KEY AUTOINCREMENT, content TEXT, keyword TEXT, Strength DOUBLE, Intelligence DOUBLE, Agility DOUBLE, Stability DOUBLE, Defense DOUBLE)";
			statement.executeUpdate(createTableQuery);

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	// insert an event into the db if not exists
	public void insertEventIfNotExists(String content, String keyword, double Strength, double Intelligence,
			double Agility, double Stability, double Defense) {
		try {
			// Check if the event with content already exists
			PreparedStatement checkContentStatement = connection
					.prepareStatement("SELECT COUNT(*) AS count FROM " + tableName + " WHERE content = ?");
			checkContentStatement.setString(1, content);
			ResultSet contentResultSet = checkContentStatement.executeQuery();
			if (contentResultSet.next() && contentResultSet.getInt("count") > 0) {
				System.out.println("Inserting event already exists with content:  '" + content + "'");
				return;
			}

			// Check if the event with keyword already exists
			PreparedStatement checkKeywordStatement = connection
					.prepareStatement("SELECT COUNT(*) AS count FROM " + tableName + " WHERE keyword = ?");
			checkKeywordStatement.setString(1, keyword);
			ResultSet keywordResultSet = checkKeywordStatement.executeQuery();
			if (keywordResultSet.next() && keywordResultSet.getInt("count") > 0) {
				System.out.println("Inserting event already exists with keyword: '" + keyword + "'");
				return;
			}

			// Event does not exist, insert it
			PreparedStatement insertStatement = connection.prepareStatement("INSERT INTO " + tableName
					+ " (content, keyword, Strength, Intelligence, Agility, Stability, Defense) VALUES (?, ?, ?, ?, ?, ?, ?)");
			insertStatement.setString(1, content);
			insertStatement.setString(2, keyword);
			insertStatement.setDouble(3, Strength);
			insertStatement.setDouble(4, Intelligence);
			insertStatement.setDouble(5, Agility);
			insertStatement.setDouble(6, Stability);
			insertStatement.setDouble(7, Defense);
			insertStatement.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	// retrieve a random event from game db
	public Event getRandomEvent() {
		try {
			// consider both character table and common events table
			String selectedTable = Math.random() < 0.5 ? tableName : "common_event";
			String query = "SELECT * FROM " + selectedTable + " ORDER BY RANDOM() LIMIT 1";
			ResultSet resultSet = statement.executeQuery(query);

			if (resultSet.next()) {
				String content = resultSet.getString("content");
				String keyword = resultSet.getString("keyword");
				double strength = 0;
				double intelligence = 0;
				double agility = 0;
				double stability = 0;
				double defense = 0;

				// if the keyword is "Mysterious Stranger's Offer," generate random values for
				// attributes value between -20 and 20
				if (keyword.equals("Mysterious Stranger's Offer")) {
					strength = (double) (Math.random() * 41) - 20;
					intelligence = (double) (Math.random() * 41) - 20;
					agility = (double) (Math.random() * 41) - 20;
					stability = (double) (Math.random() * 41) - 20; // Changed from charisma
					defense = (double) (Math.random() * 41) - 20; // Changed from wisdom
				}

				return new Event(content, keyword, strength, intelligence, agility, stability, defense);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
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

	public static void main(String[] args) {
		eventdb gameDB = new eventdb("event", 10, 15, 20, 12, 18);

		// Example usage: inserting an event if not exists
		gameDB.insertEventIfNotExists(
				"The player encounters a seemingly ordinary old man who offers a choice of one of three different boxes, each containing a different fantastic item.",
				"Gift from the mysterious old man - 1", 5, 8, 10, 7, 9);

		// Example usage: retrieving a random event
		Event randomEvent = gameDB.getRandomEvent();
		if (randomEvent != null) {
			System.out.println("Random event: " + randomEvent.getContent() + randomEvent.getKeyword());
		}

		// Example usage: querying the event table
		gameDB.queryEventTable("SELECT \"keyword\" FROM " + gameDB.tableName + ";");
	}
}
