package role;

import java.util.Random;

import database.eventdb;

public class Ranger extends Role {
	public Ranger(String name, Double HP) {
		super(name, HP, 20, 3, 5, 5, 7, "Heal");
	}

	@Override
	public double attack() {

		return this.INT * 1.2;
	}

	@Override
	public boolean Dodge() {
		double dodgeChance = this.DEX * 0.03;
		Random randomGenerator = new Random();
		double randomValue = randomGenerator.nextDouble();
		return randomValue <= dodgeChance;
	}

	@Override
	public double defense(double damage) {
		return (70 / (this.DEF + 70)) * damage; // 基础减伤计算
	}

	public void useSkill(int damage) {

		this.setINT(5);
		this.setSTA(-3);
		this.setDEX(-3);

	}

	@Override
	public void meetEvent(double strength, double intelligence, double agility, double stability, double defense) {
		eventdb eventObj = new eventdb("ranger_event", this.getATK(), this.getINT(), this.getDEX(), this.getDEF(),
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
