package role;
import java.util.Random;
public class Warrior extends Role {
    
	 public Warrior(String name, Double HP) {
	        super(name, HP, 5, 18, 10, 12, 5, "Bloodthirsty Instinct"); 
	    }

	    @Override
	    public double attack() {
	        
	        return this.ATK * 1.5; 
	    }

	    @Override
	    public boolean Dodge() {
	        
	        double dodgeChance = this.DEX * 0.05; 
	        Random randomGenerator = new Random();
	        double randomValue = randomGenerator.nextDouble();
	        return randomValue <= dodgeChance;
	    }

	    @Override
	    public double defense(int damage) {
	        
	        return (50/(this.DEF + 50)) * damage; 
	    }
	    
	    public void useSkill(int damage) {
	        
	        this.setATK(5);
	        this.setDEX(-3);
	        this.setINT(-5);
	        
	    }
	    
	    
}
