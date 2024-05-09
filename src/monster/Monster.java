package monster;

import map.Cell;
import role.Role;

public class Monster extends Role {
	private double ex;

	public Monster(String name, Double HP, int INT, int ATK, int DEX, int DEF, int STA, String SKILL) {
		super(name, HP, INT, ATK, DEX, DEF, STA, SKILL);
		this.setEx(ex);
	}

	public double attack() {
		return this.ATK;
	}

	public boolean Dodge() {
		return false;
	}

	public double defense(double damage) {
		return damage;
	}

	public double getEx() {
		return ex;
	}

	public void setEx(double ex) {
		this.ex = ex;
	}

	public double getHP() {
		return this.HP;
	}

	public void setHP(double HP) {
		this.HP = HP;
	}

	public String getName() {
		return this.name;
	}

	@Override
	public void meetEvent(Cell cell) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setRoleName() {
		// TODO Auto-generated method stub

	}

	@Override
	public void useSkill() {
		// TODO Auto-generated method stub
		
	}

}
