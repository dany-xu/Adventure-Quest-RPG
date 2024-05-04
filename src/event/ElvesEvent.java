package event;

import role.Role;

public class ElvesEvent extends AbstractEvent {
    @Override
    public void trigger(Role role) {
        double ss = role.getHp();
        double aa = (int) (Math.random() * 100);
        role.setHp(ss + aa);
        System.out.println("你发现了隐藏在森林中的隐秘泉水，泉水中的精灵帮你恢复了" + aa + "点hp");
        System.out.printf("您当前血量为:%s,泉水净化了您的身体,您的血量增加了:%s,您的总血量达到了:%s.", ss, aa, role.getHp());
    }

    @Override
    public String flag() {
        return "E";
    }

    @Override
    public boolean isTriggered() {
        return false;
    }
}
