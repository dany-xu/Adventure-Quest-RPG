package role;

import java.util.Random;

public class Thief extends Role {

	public Thief(String name, Double HP) {
		super(name, HP, 8, 7, 15, 5, 5, "Into Shadows");
		setRoleName();
	}

	@Override
	public double attack() {
		return this.ATK + this.DEX * 0.5; 
	}

	@Override
	public boolean Dodge() {
		double dodgeChance = this.DEX * 0.08; 
		Random randomGenerator = new Random();
		double randomValue = randomGenerator.nextDouble();
		return randomValue <= dodgeChance;
	}

	@Override
	public double defense(double damage) {
		return (60 / (this.DEF + 60)) * damage;
	}

	public void useSkill() {

		this.setDEX(5);
		this.setATK(-3);
		this.setDEF(-3);

	}

	@Override
	public void setRoleName() {
		this.roleName = "thief";
	}
}
