package event;

public class NoEvent extends AbstractEvent {
	public NoEvent() {
		super();
		setKeyword("Be relief");
		setContent("There is nothing happening here.");
		this.eventFlag = "N";
	}

	@Override
	public void getRandomEvent() {
		setStrength(0);
		setIntelligence(0);
		setAgility(0);
		setStability(0);
		setDefense(0);
	}
}
