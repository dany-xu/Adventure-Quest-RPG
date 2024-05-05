package role;

import java.util.Random;

import database.eventdb;

public class Thief extends Role {

	public Thief(String name, Double HP) {
		super(name, HP, 8, 7, 15, 5, 5, "Into Shadows");
	}

	@Override
	public double attack() {
		return this.ATK + this.DEX * 0.5; // 盗贼攻击力受敏捷影响
	}

	@Override
	public boolean Dodge() {
		double dodgeChance = this.DEX * 0.08; // 盗贼有更高的闪避率
		Random randomGenerator = new Random();
		double randomValue = randomGenerator.nextDouble();
		return randomValue <= dodgeChance;
	}

	@Override
	public double defense(double damage) {
		return (60 / (this.DEF + 60)) * damage;
	}

	public void useSkill(double damage) {

		this.setDEX(5);
		this.setATK(-3);
		this.setDEF(-3);

	}

	@Override
	public void meetEvent(double strength, double intelligence, double agility, double stability, double defense) {
		eventdb eventObj = new eventdb("thief_event", this.getATK(), this.getINT(), this.getDEX(), this.getDEF(),
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
