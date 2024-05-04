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
	private String tableName;
	private int strength;
	private int intelligence;
	private int agility;
	private int charisma;
	private int wisdom;
	private int dexterity;

	public eventdb(String table, int strength, int intelligence, int agility, int charisma, int wisdom, int dexterity) {
		this.tableName = table;
		this.strength = strength;
		this.intelligence = intelligence;
		this.agility = agility;
		this.charisma = charisma;
		this.wisdom = wisdom;
		this.dexterity = dexterity;
		try {
			// connect to the SQLite database
			connection = DriverManager.getConnection("jdbc:sqlite:game.db");
			statement = connection.createStatement();

			// create the specified table if not exists
			String createTableQuery = "CREATE TABLE IF NOT EXISTS " + tableName
					+ " (id INTEGER PRIMARY KEY AUTOINCREMENT, content TEXT, keyword TEXT, Strength INTEGER, Intelligence INTEGER, Agility INTEGER, Charisma INTEGER, Wisdom INTEGER, Dexterity INTEGER)";
			statement.executeUpdate(createTableQuery);

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	// insert an event into the db if not exists
	public void insertEventIfNotExists(String content, String keyword, int Strength, int Intelligence, int Agility,
			int Charisma, int Wisdom, int Dexterity) {
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
					+ " (content, keyword, Strength, Intelligence, Agility, Charisma, Wisdom, Dexterity) VALUES (?, ?, ?, ?, ?, ?, ?, ?)");
			insertStatement.setString(1, content);
			insertStatement.setString(2, keyword);
			insertStatement.setInt(3, Strength);
			insertStatement.setInt(4, Intelligence);
			insertStatement.setInt(5, Agility);
			insertStatement.setInt(6, Charisma);
			insertStatement.setInt(7, Wisdom);
			insertStatement.setInt(8, Dexterity);
			insertStatement.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	// Function to retrieve a random event from the database
	public event getRandomEvent() {
		try {
			ResultSet resultSet = statement.executeQuery("SELECT * FROM " + tableName + " ORDER BY RANDOM() LIMIT 1");
			if (resultSet.next()) {
				return new event(resultSet.getString("content"), resultSet.getString("keyword"),
						resultSet.getInt("Strength"), resultSet.getInt("Intelligence"), resultSet.getInt("Agility"),
						resultSet.getInt("Charisma"), resultSet.getInt("Wisdom"), resultSet.getInt("Dexterity"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static class event {
		private String content;
		private String keyword;
		private int strength;
		private int intelligence;
		private int agility;
		private int charisma;
		private int wisdom;
		private int dexterity;

		public event(String content, String keyword, int strength, int intelligence, int agility, int charisma,
				int wisdom, int dexterity) {
			this.content = content;
			this.keyword = keyword;
			this.strength = strength;
			this.intelligence = intelligence;
			this.agility = agility;
			this.charisma = charisma;
			this.wisdom = wisdom;
			this.dexterity = dexterity;
		}

		public String getContent() {
			return content;
		}

		public String getKeyword() {
			return keyword;
		}

		public int getStrength() {
			return strength;
		}

		public int getIntelligence() {
			return intelligence;
		}

		public int getAgility() {
			return agility;
		}

		public int getCharisma() {
			return charisma;
		}

		public int getWisdom() {
			return wisdom;
		}

		public int getDexterity() {
			return dexterity;
		}
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
		eventdb gameDB = new eventdb("event", 10, 15, 20, 12, 18, 14);

		// Example usage: inserting an event if not exists
		gameDB.insertEventIfNotExists(
				"The player encounters a seemingly ordinary old man who offers a choice of one of three different boxes, each containing a different fantastic item.",
				"Gift from the mysterious old man - 1", 5, 8, 10, 7, 9, 6);

		// Example usage: retrieving a random event
		eventdb.event randomEvent = gameDB.getRandomEvent();
		if (randomEvent != null) {
			System.out.println("Random event: " + randomEvent.getContent() + randomEvent.getKeyword());
		}

		// Example usage: querying the event table
		gameDB.queryEventTable("SELECT \"keyword\" FROM " + gameDB.tableName + ";");
	}
}
