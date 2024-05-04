package monster;

import role.Role;

public class Monster extends Role {
    private double ex;
    public  Monster(String name, Double hp, Double magic, int power, double ex) {
        super(name, hp, magic, power);
        this.setEx(ex);
    }

    @Override
    public int hit() {
        System.out.println("平A!");
        int aa=getAttack() + (int) (Math.random() * 10);
        aa-=10;
        System.out.println("输出伤害为"+aa);
        return aa;

    }

	public double getEx() {
		return ex;
	}

	public void setEx(double ex) {
		this.ex = ex;
	}
}
