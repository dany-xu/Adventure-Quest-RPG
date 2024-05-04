package role;

public abstract class Role {
    protected String name;
    protected Double hp;
    protected Double magic;
    protected int power;

    public abstract int hit();

    public Role() {

    }

    public Role(String name, Double hp, Double magic, int power) {
        this.name = name;
        this.hp = hp;
        this.magic = magic;
        this.power = power;
    }

    public void print() {
        System.out.println("角色信息");
        System.out.println("昵称:" + name);
        System.out.println("血量:" + hp);
        System.out.println("力量:" + power);
        System.out.println("法术:" + magic);
    }

    public String getName() {
        return name;
    }

    public double getHp() {
        return hp;
    }

    public double getMp() {
        return magic;
    }

    public int getAttack() {
        return power;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setHp(Double hp) {
        this.hp = hp;
    }

    public void setMp(Double magic) {
        this.magic = magic;
    }

    public void setAttack(int power) {
        this.power = power;
    }

    public Double getMagic() {
        return magic;
    }

    public void setMagic(Double magic) {
        this.magic = magic;
    }

    public int getPower() {
        return power;
    }

    public void setPower(int power) {
        this.power = power;
    }


}
