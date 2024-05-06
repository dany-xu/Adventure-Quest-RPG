
package event;

import java.util.Random;

public class MonsterEvent extends AbstractEvent {

	public MonsterEvent() {
		super();
		setKeyword("Monster Encounter");
		setContent("You encounter a fearsome monster! Be ready to fight!");
		this.eventFlag = "M";
	}

	@Override
	public void getRandomEvent() {
		Random random = new Random();
		// between 0 and 20
		setStrength(random.nextDouble() * 20);
		setIntelligence(random.nextDouble() * 20);
		setAgility(random.nextDouble() * 20);
		setStability(random.nextDouble() * 20);
		setDefense(random.nextDouble() * 20);
	}

}