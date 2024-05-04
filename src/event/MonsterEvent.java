package event;

import event.Event;
import monster.Goblin;
import role.Role;

public class MonsterEvent implements Event {

	@Override
	public void trigger(Role role) {
		System.out.println("您在深林迷失了方向,深林升起了大雾,这时暗处一只哥布林向您走来.");
		System.out.println("您别无选择,只能选择战斗,战斗吧,勇士!");
		Goblin goblin = new Goblin();
		System.out.println("您的战士和哥布林相遇!");
		System.out.println("已进入战斗!");
		for (;;) {
			System.out.println("****************************");
			System.out.println("您的战士对哥布林发动平A!");
			int aa = role.hit();
			System.out.println(goblin.getHp());
			double ff = goblin.getHp() - aa;
			goblin.setHp(ff);
			System.out.println("哥布林血量剩余:" + goblin.getHp());
			if (goblin.getHp() <= 0) {
				System.out.println("游戏结束!哥布林死亡,您的战士获胜!");
				break;
			}
			System.out.println("****************************");
			System.out.println("哥布林对您的战士发动平A!");
			aa = goblin.hit();
			ff = role.getHp() - aa;
			role.setHp(ff);
			System.out.println("您的战士血量剩余:" + role.getHp());
			if (role.getHp() <= 0) {
				System.out.println("游戏结束!您的战士死亡,哥布林获胜!");
				break;
			}
		}
	}


	@Override
	public String flag() {
		return "M";
	}

	@Override
	public boolean isTriggered() {
		return false;
	}

}
