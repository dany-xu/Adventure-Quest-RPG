package event;

import role.Role;

import java.util.Random;


public class BlessingEvent extends AbstractEvent {

	@Override
	public void trigger(Role role) {
		Random r = new Random();
		int power = r.nextInt(20) + 10;
		int magic = r.nextInt(10) + 10;
		role.setPower(role.getPower() + power);
		role.setMagic(role.getMagic() + magic);

		System.out.println(".______     ______   ____    __    ____  _______ .______          __    __  .______   ");
		System.out.println("|   _  \\   /  __  \\  \\   \\  /  \\  /   / |   ____||   _  \\        |  |  |  | |   _  \\  ");
		System.out.println("|  |_)  | |  |  |  |  \\   \\/    \\/   /  |  |__   |  |_)  |       |  |  |  | |  |_)  | ");
		System.out.println("|   ___/  |  |  |  |   \\            /   |   __|  |      /        |  |  |  | |   ___/  ");
		System.out.println("|  |      |  `--'  |    \\    /\\    /    |  |____ |  |\\  \\----.   |  `--'  | |  |     ");
		System.out.println("| _|       \\______/      \\__/  \\__/     |_______|| _| `._____|    \\______/  | _|    ");
		System.out.println();
		System.out.printf("山中仙人对你进行了祝福，力量+%s、法术+%s\n%n", power, magic);
		super.setTriggered(true);
	}



	@Override
	public String flag() {
		return "B";
	}

	@Override
	public boolean isTriggered() {
		return false;
	}

}
