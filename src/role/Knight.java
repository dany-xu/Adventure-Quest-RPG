package role;
import java.util.Random;

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
    public double defense(int damage) {
        return (35 / (this.DEF + 35)) * damage; 
    }
    
    public void useSkill(int damage) {
        
        this.setDEF(5);
        this.setDEX(-3);
        this.setSTA(-5);
        
    }
    
    
}
