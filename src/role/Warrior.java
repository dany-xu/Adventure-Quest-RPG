package role;

public class Warrior extends Role {
    //战士类
    public Warrior(String name) {
        super(name,300.0,50.0,30);
        super.print();
    }
    public int hit() {
        System.out.println("平A!");
        int aa=getAttack() + (int) (Math.random() * 10);
        aa-=10;
        System.out.println("输出伤害为"+aa);
        return aa;

    }
}
