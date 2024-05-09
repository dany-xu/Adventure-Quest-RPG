package role;

import java.util.Random;

public class Ranger extends Role {
	public Ranger(String name, Double HP) {
		super(name, HP, 12, 8, 6, 7, 7, "Heal");
		setRoleName();
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

	public void useSkill() {
		double healAmount = 30; // Amount to heal
		if (this.HP + healAmount > 75) { // Check if healing would exceed 100 HP
			this.HP = 75.0; // Set HP to maximum if the heal would exceed 100 HP
		} else {
			this.HP += healAmount; // Otherwise, just add the heal amount
		}

	}

	@Override
	public void setRoleName() {
		this.roleName = "ranger";
	}
}
