package role;

import javax.swing.JOptionPane;

import map.Cell;

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
	protected String roleName;

	public abstract double attack();

	public abstract boolean Dodge();

	public abstract double defense(double damage);

	public abstract void setRoleName();

	public String getRoleName() {
		return this.roleName;
	}

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

	public void setHP(double HP) {
		this.HP = HP;
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

	public String getName() {
		return this.name;
	}

	public String getContent() {
		return this.content;
	}

	public String getKeyword() {
		return this.keyword;
	}

	public double getHP() {
		return this.HP;
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

	// public abstract void meetEvent(Cell cell);
	public void meetEvent(Cell cell) {
		this.setATK(cell.getEvent().getStrength());
		this.setDEX(cell.getEvent().getAgility());
		this.setINT(cell.getEvent().getIntelligence());
		this.setDEF(cell.getEvent().getDefense());
		this.setSTA(cell.getEvent().getStability());
		this.setContent(cell.getEvent().getContent());
		this.setKeyword(cell.getEvent().getKeyword());
	}
}
