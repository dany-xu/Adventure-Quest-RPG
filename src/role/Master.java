package role;



public class Master extends Role {
    //法师类
    public Master(String name) {
        super(name,135.00,350.0,8);
        super.print();
    }

    public int hit() {
        System.out.println("平A!");
        int aa=getAttack() + ((int) (Math.random() * 10) + (int) (getMp() / 10));
        System.out.println("输出伤害为"+aa);
        return aa;
    }
}
