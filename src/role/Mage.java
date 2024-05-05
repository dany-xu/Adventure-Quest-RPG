package role;
import java.util.Random;


public class Mage extends Role {
	 public Mage(String name, Double HP) {
	        super(name, HP, 20, 3, 5, 5, 7, "Ancient Knowledge");
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
	    public double defense(int damage) {
	        return (70 / (this.DEF + 70)) * damage; // 基础减伤计算
	    }
	    
	    public void useSkill(int damage) {
	        
	        this.setINT(5);
	        this.setSTA(-3);
	        this.setDEX(-3);
	        
	        
	    }
	    
   
}
