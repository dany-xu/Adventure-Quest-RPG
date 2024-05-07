package event;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class RoleEvent extends AbstractEvent {
	private Connection connection;
	private Statement statement;
	private String tableName;

	public RoleEvent(String tableName) {
		super(); // content, keyword, strength, intelligence, agility, stability, defense
		// connect to the SQLite database
		try {
			// connect to the SQLite database
			connection = DriverManager.getConnection("jdbc:sqlite:game.db");
			statement = connection.createStatement();

		} catch (SQLException e) {
			e.printStackTrace();
		}
		this.tableName = tableName;
		this.eventFlag = "R";
	}

	@Override
	// get event explicitly use certain table
	public void getRandomEvent() {
		try {
			String query = "SELECT * FROM " + tableName + " ORDER BY RANDOM() LIMIT 1";
			ResultSet resultSet = statement.executeQuery(query);

			if (resultSet.next()) {
				this.content = resultSet.getString("content");
				this.keyword = resultSet.getString("keyword");
				this.strength = resultSet.getDouble("strength");
				// System.out.println("Strength: " + getStrength());
				this.intelligence = resultSet.getDouble("intelligence");
				this.agility = resultSet.getDouble("agility");
				this.stability = resultSet.getDouble("stability");
				this.defense = resultSet.getDouble("defense");
				return;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return;
	}

}
