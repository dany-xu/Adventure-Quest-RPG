package event;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class CommonEvent extends AbstractEvent {
	private Connection connection;
	private Statement statement;

	public CommonEvent() {
		super(); // content, keyword, strength, intelligence, agility, stability, defense
		this.eventFlag = "C";
		try {
			// connect to the SQLite database
			connection = DriverManager.getConnection("jdbc:sqlite:game.db");
			statement = connection.createStatement();

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	// get event explicitly use certain table
	public void getRandomEvent() {
		try {
			String query = "SELECT * FROM common_event ORDER BY RANDOM() LIMIT 1";
			ResultSet resultSet = statement.executeQuery(query);

			if (resultSet.next()) {
				this.content = resultSet.getString("content");
				this.keyword = resultSet.getString("keyword");
				if (keyword.equals("Mysterious Stranger's Offer")) {
					this.strength = (double) (Math.random() * 41) - 20;
					this.intelligence = (double) (Math.random() * 41) - 20;
					this.agility = (double) (Math.random() * 41) - 20;
					this.stability = (double) (Math.random() * 41) - 20;
					this.defense = (double) (Math.random() * 41) - 20;
					return;
				}

				this.strength = resultSet.getDouble("strength");
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

	public static void main(String[] args) {
		// Testing database connection without external connection
		CommonEvent commonEvent = new CommonEvent();
		commonEvent.getRandomEvent();
		// Print the retrieved event
		System.out.println("Content: " + commonEvent.getContent());
		System.out.println("Keyword: " + commonEvent.getKeyword());
		System.out.println("Strength: " + commonEvent.getStrength());
		System.out.println("Intelligence: " + commonEvent.getIntelligence());
		System.out.println("Agility: " + commonEvent.getAgility());
		System.out.println("Stability: " + commonEvent.getStability());
		System.out.println("Defense: " + commonEvent.getDefense());
	}
}
