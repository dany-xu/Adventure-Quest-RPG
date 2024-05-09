package map;

import event.AbstractEvent;
import event.RoleEvent;
import role.Knight;

public class Cell {

	private int x;

	private int y;

	private AbstractEvent event;

	private String eventFlag;

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

	public void setEvent(AbstractEvent event) {
		this.event = event;
	}

	public void setEventFlag(String eventFlag) {
		this.eventFlag = eventFlag;
	}

	public AbstractEvent getEvent() {
		return event;
	}

	public String getEventFlag() {
		return this.eventFlag;
	}

	public static void main(String[] args) {
		Knight role = new Knight("Dummy", 100.0);
//		System.out.println(role.viewStatus());
		// RoleEvent roleEvent = new RoleEvent(role.getRoleName() + "_event");
		Cell cell = new Cell(1, 1);
		cell.setEvent(new RoleEvent(role.getRoleName() + "_event"));
		cell.getEvent().getRandomEvent();
		role.meetEvent(cell);

		// System.out.println(role.viewStatus());
	}

}
