package event;

public class Event {
	private String content;
	private String keyword;
	private double strength;
	private double intelligence;
	private double agility;
	private double stability;
	private double defense;

	public Event(String content, String keyword, double strength, double intelligence, double agility, double stability,
			double defense) {
		this.content = content;
		this.keyword = keyword;
		this.strength = strength;
		this.intelligence = intelligence;
		this.agility = agility;
		this.stability = stability;
		this.defense = defense;
	}

	public String getContent() {
		return content;
	}

	public String getKeyword() {
		return keyword;
	}

	public double getStrength() {
		return strength;
	}

	public double getIntelligence() {
		return intelligence;
	}

	public double getAgility() {
		return agility;
	}

	public double getStability() {
		return stability;
	}

	public double getDefense() {
		return defense;
	}
}
