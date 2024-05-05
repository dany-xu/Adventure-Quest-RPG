package role;
import javax.swing.JOptionPane;

public abstract class Role {
    protected String name;
    protected Double HP;
    protected int INT; // 智力
    protected int ATK; // 力量
    protected int DEX; // 敏捷
    protected int DEF; // 防御
    protected int STA; // 耐力
    protected String SKILL;
    
    public abstract double attack();
    
    public abstract boolean Dodge(); 
      
    public abstract double defense(int damage);
    

    public Role(String name, Double HP, int INT, int ATK, int DEX, int DEF, int STA, String SKILL) {
        this.name = name;
        this.HP = HP;
        this.INT = INT;
        this.ATK = ATK;
        this.DEX = DEX;
        this.DEF = DEF;
        this.STA = STA;
        this.SKILL = SKILL;
    }
    
    public void setINT(int change) {
        this.INT += change;
    }

    public void setATK(int change) {
        this.ATK += change;
    }

    public void setDEX(int change) {
        this.DEX += change;
    }

    public void setDEF(int change) {
        this.DEF += change;
    }

    public void setSTA(int change) {
        this.STA += change;
    }
    
	// Method to view the character's current status in a GUI window
    public void viewStatus() {
        StringBuilder status = new StringBuilder();
        status.append("<html>Name: " + name + "<br/>");
        status.append("HP: " + HP + "<br/>");
        status.append("Intelligence (INT): " + INT + "<br/>");
        status.append("Strength (ATK): " + ATK + "<br/>");
        status.append("Dexterity (DEX): " + DEX + "<br/>");
        status.append("Defense (DEF): " + DEF + "<br/>");
        status.append("Stamina (STA): " + STA + "<br/></html>");
        status.append("Skill:" + SKILL  + "<br/></html>");

        JOptionPane.showMessageDialog(null, status.toString(), "Character Status", JOptionPane.INFORMATION_MESSAGE);
    }
}
