package role;

import java.util.Random;

public class Witch extends Role {

	public Witch(String name, Double HP) {
		super(name, HP, 15, 5, 5, 10, 5, "Ancient Knowledge");
		setRoleName();
	}

	@Override
	public double attack() {
		return this.INT * 0.9; // Attack power is based on intelligence
	}

	@Override
	public boolean Dodge() {
		double dodgeChance = this.DEX * 0.04; // Calculate dodge chance based on dexterity
		Random randomGenerator = new Random();
		double randomValue = randomGenerator.nextDouble();
		return randomValue <= dodgeChance; // Determine if the dodge was successful
	}

	@Override
	public double defense(double damage) {
		return (65 / (this.DEF + 65)) * damage; // Defense reduces damage based on the defense stat
	}

	/**
	 * Use skill to heal the priest. The priest heals up to 30 HP but cannot exceed
	 * a maximum of 100 HP.
	 */
	public void useSkill() {
		double healAmount = 30; // Amount to heal
		if (this.HP + healAmount > 100) { // Check if healing would exceed 100 HP
			this.HP = 100.0; // Set HP to maximum if the heal would exceed 100 HP
		} else {
			this.HP += healAmount; // Otherwise, just add the heal amount
		}
	}

	@Override
	public void setRoleName() {
		this.roleName = "witch";
	}

}
