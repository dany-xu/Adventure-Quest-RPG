package role;
import java.util.Random;

public class Rogue extends Role {
    
    public Rogue(String name, Double HP) {
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
    public double defense(int damage) {
        return (60 / (this.DEF + 60)) * damage;
    }
    
    public void useSkill(int damage) {
        
    	this.setDEX(5);
        this.setATK(-3);
        this.setDEF(-3);
        
    }
}
