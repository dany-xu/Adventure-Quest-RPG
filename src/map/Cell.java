package map;

import event.Event;

public class Cell {

	private int x;
	
	private int y;

	private Event event;
	
	private boolean explored; 
	
	public Cell(int x, int y) {
		super();
		this.x = x;
		this.y = y;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public boolean isExplored() {
		return explored;
	}

	public void setExplored(boolean explored) {
		this.explored = explored;
	}

	public Event getEvent() {
		return event;
	}

	public void setEvent(Event event) {
		this.event = event;
	}
	
}
