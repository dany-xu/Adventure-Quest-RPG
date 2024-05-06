package map;

import event.NoEvent;

public class FinalCell extends Cell {

	public FinalCell(int x, int y) {
		super(x, y);
		this.setEvent(new NoEvent());
		this.getEvent()
				.setContent("Congratulations! You have reached the treasure burying place! You are wealthy now!");
		this.getEvent().setKeyword("Destination");
	}

}