
package event;

public abstract class AbstractEvent implements EventInterface {
	protected String content;
	protected String keyword;
	protected double strength;
	protected double intelligence;
	protected double agility;
	protected double stability;
	protected double defense;
	protected boolean triggered;
	protected String eventFlag;

	public AbstractEvent() {
		this.content = "No content";
		this.keyword = "No keyword";
		this.strength = 0;
		this.intelligence = 0;
		this.agility = 0;
		this.stability = 0;
		this.defense = 0;
		this.eventFlag = "";
		// this.triggered = false;
	}

	/*
	 * public boolean isTriggered() { return triggered; }
	 * 
	 * public void setTriggered(boolean triggered) { this.triggered = triggered; }
	 */
	public String getEventFlag() {
		return this.eventFlag;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}

	public void setStrength(double strength) {
		this.strength = strength;
	}

	public void setIntelligence(double intelligence) {
		this.intelligence = intelligence;
	}

	public void setAgility(double agility) {
		this.agility = agility;
	}

	public void setStability(double stability) {
		this.stability = stability;
	}

	public void setDefense(double defense) {
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
