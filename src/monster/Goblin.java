package monster;

public class Goblin extends Monster {

    public Goblin() {
        super("哥布林", 30.0, 0.0, 15, 10.0);
    }

    @Override
    public int hit() {
        System.out.println("平A!");
        int aa=getAttack() + (int) (Math.random() * 10);
        aa-=10;
        System.out.println("输出伤害为"+aa);
        return aa;
    }
}
