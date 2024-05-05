package role;

import javax.swing.JOptionPane;

public abstract class Role {
	protected String name;
	protected Double HP;
	protected double INT; // 智力
	protected double ATK; // 力量
	protected double DEX; // 敏捷
	protected double DEF; // 防御
	protected double STA; // 耐力
	protected String SKILL;
	protected String content;
	protected String keyword;

	public abstract double attack();

	public abstract boolean Dodge();

	public abstract double defense(double damage);

	public abstract void meetEvent(double strength, double intelligence, double agility, double stability,
			double defense);

	public Role(String name, Double HP, int INT, int ATK, int DEX, int DEF, int STA, String SKILL) {
		this.name = name;
		this.HP = HP;
		this.INT = INT;
		this.ATK = ATK;
		this.DEX = DEX;
		this.DEF = DEF;
		this.STA = STA;
		this.SKILL = SKILL;
		this.content = "";
		this.keyword = "";
	}

	public void setINT(double change) {
		this.INT += change;
	}

	public void setATK(double change) {
		this.ATK += change;
	}

	public void setDEX(double change) {
		this.DEX += change;
	}

	public void setDEF(double change) {
		this.DEF += change;
	}

	public void setSTA(double change) {
		this.STA += change;
	}

	public void setContent(String change) {
		this.content = change;
	}

	public void setKeyword(String change) {
		this.keyword = change;
	}

	public double getINT() {
		return this.INT;
	}

	public double getATK() {
		return this.ATK;
	}

	public double getDEX() {
		return this.DEX;
	}

	public double getDEF() {
		return this.DEF;
	}

	public double getSTA() {
		return this.STA;
	}

	public String getContent() {
		return this.content;
	}

	public String getKeyword() {
		return this.keyword;
	}

	// Method to view the character's current status in a GUI window
	public void viewStatus() {
		StringBuilder status = new StringBuilder();
		status.append("<html>Name: " + name + "<br/>");
		status.append("HP: " + HP + "<br/>");
		status.append("Intelligence (INT): " + INT + "<br/>");
		status.append("Strength (ATK): " + ATK + "<br/>");
		status.append("Dexterity (DEX): " + DEX + "<br/>");
		status.append("Defense (DEF): " + DEF + "<br/>");
		status.append("Stamina (STA): " + STA + "<br/></html>");
		status.append("Skill:" + SKILL + "<br/></html>");

		JOptionPane.showMessageDialog(null, status.toString(), "Character Status", JOptionPane.INFORMATION_MESSAGE);
	}

}
