package role;

import javax.swing.JOptionPane;

import map.Cell;

public abstract class Role {
	protected String name;
	protected Double HP;
	protected double INT; 
	protected double ATK; 
	protected double DEX; 
	protected double DEF; 
	protected double STA; 
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
		// this.content = "";
		// this.keyword = "";
	}

	public void setHP(double HP) {
		this.HP = HP;
	}

	public void setINT(double change) {
		this.INT += change;
	}

	public void setATK(double change) {
		// System.out.println("setATK: " + change + ", " + ATK);
		this.ATK += change;
		// System.out.println("setATK_Added: " + ATK);
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

	/*
	 * public void setContent(String change) { this.content = change; }
	 * 
	 * public void setKeyword(String change) { this.keyword = change; }
	 */
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
	public String viewStatus() {
		StringBuilder status = new StringBuilder();
		status.append("Character Status Screen").append("\n");
		status.append("Name: ").append(this.name).append("\n");
		status.append("HP: ").append(this.HP).append("\n");
		status.append("Intelligence (INT): ").append(this.INT).append("\n");
		status.append("Strength (ATK): ").append(this.ATK).append("\n");
		status.append("Dexterity (DEX): ").append(this.DEX).append("\n");
		status.append("Defense (DEF): ").append(this.DEF).append("\n");
		status.append("Stamina (STA): ").append(this.STA).append("\n");
		status.append("Skill: ").append(this.SKILL).append("\n");
		return status.toString();
		
	}

	// public abstract void meetEvent(Cell cell);
	public void meetEvent(Cell cell) {
		// System.out.println(ATK + "," + cell.getEvent().getStrength());
		this.setATK(cell.getEvent().getStrength());
		// System.out.println("AFTER:" + ATK);
		this.setDEX(cell.getEvent().getAgility());
		this.setINT(cell.getEvent().getIntelligence());
		this.setDEF(cell.getEvent().getDefense());
		this.setSTA(cell.getEvent().getStability());
		// this.setContent(cell.getEvent().getContent());
		// this.setKeyword(cell.getEvent().getKeyword());
	}

	public abstract void useSkill();
}
