
package event;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MonsterEvent extends AbstractEvent {
	
	 List<String> monsters = new ArrayList<>();
	
	public MonsterEvent() {
		super();
		monsters.add("Grimalkin");
        monsters.add("Shadow Lurker");
        monsters.add("Ironclad Troll");
        monsters.add("Nether Wraith");
        monsters.add("Frost Giant");
        monsters.add("Blight Beast");
        monsters.add("Arcane Sorcerer");
        monsters.add("Fire Drake");
        monsters.add("Cave Serpent");
        monsters.add("Skybreaker Wyvern");
        monsters.add("Mystic Ent");
        monsters.add("Dire Wolf");
        monsters.add("Crystal Golem");
        monsters.add("Venomous Harpy");
        monsters.add("Stone Basilisk");
		setKeyword("Monster Encounter");
		setContent("You encounter a fearsome monster! Be ready to fight!");
		this.eventFlag = "M";
	}

	@Override
	public void getRandomEvent() {
		Random random = new Random();
		// between 0 and 20
		this.name = this.monsters.get((int) (random.nextDouble() * 14));
		setStrength(random.nextDouble() * 20);
		setIntelligence(random.nextDouble() * 20);
		setAgility(random.nextDouble() * 20);
		setStability(random.nextDouble() * 20);
		setDefense(random.nextDouble() * 20);
	}

}