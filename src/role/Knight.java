package role;

import java.util.Random;

import database.eventdb;

public class Knight extends Role {

	public Knight(String name, Double HP) {
		super(name, HP, 4, 13, 5, 18, 10, "Shield Block");
	}

	@Override
	public double attack() {
		return this.ATK * 1.1;
	}

	@Override
	public boolean Dodge() {
		double dodgeChance = this.DEX * 0.02;
		Random randomGenerator = new Random();
		double randomValue = randomGenerator.nextDouble();
		return randomValue <= dodgeChance;
	}

	@Override
	public double defense(double damage) {
		return (35 / (this.DEF + 35)) * damage;
	}

	public void useSkill(int damage) {

		this.setDEF(5);
		this.setDEX(-3);
		this.setSTA(-5);

	}

	@Override
	public void meetEvent(double strength, double intelligence, double agility, double stability, double defense) {
		eventdb eventObj = new eventdb("knight_event", this.getATK(), this.getINT(), this.getDEX(), this.getDEF(),
				this.getSTA());
		event.Event event = eventObj.getRandomEvent();
		this.setATK(event.getStrength());
		this.setDEX(event.getAgility());
		this.setINT(event.getIntelligence());
		this.setDEF(event.getDefense());
		this.setSTA(event.getStability());
		this.setContent(event.getContent());
		this.setKeyword(event.getKeyword());
	}

}
