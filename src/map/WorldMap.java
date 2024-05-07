package map;

import java.util.Random;

import event.CommonEvent;
//import event.Block;
import event.MonsterEvent;
import event.NoEvent;
import event.RoleEvent;
import role.Role;

public class WorldMap {
	private int width; // map
	private int height;
	private int cellCount;
	private Cell[][] cells; // map init
	private Cell roleCell;
	private int birthx;
	private int birthy;

	public WorldMap(int width, int height, Role role) {
		this.width = width;
		this.height = height;
		this.cellCount = width * height;
		cells = new Cell[height][width];
		initMap(role);
	}

	private void initMap(Role role) {
		for (int i = 0; i < height; i++) {
			for (int j = 0; j < width; j++) {
				Cell cell = new Cell(j, i);
				cells[i][j] = cell;
				cell.setEvent(new NoEvent());
			}
		}

		Random r = new Random();
		// monster event
		int monsterCount = cellCount / 10;
		for (int i = 0; i < monsterCount; i++) {
			int x, y;
			do {
				x = r.nextInt(width);
				y = r.nextInt(height);
			} while (x == birthx && y == birthy); // Exclude birth cell coordinates
			cells[y][x].setEvent(new MonsterEvent());
		}

		// role events
		int roleCount = cellCount / 20;
		for (int i = 0; i < roleCount; i++) {
			int x, y;
			do {
				x = r.nextInt(width);
				y = r.nextInt(height);
			} while (x == birthx && y == birthy); // Exclude birth cell coordinates
			cells[y][x].setEvent(new RoleEvent(role.getRoleName() + "_event"));
		}

		// common events
		int commonCount = cellCount / 25;
		for (int i = 0; i < commonCount; i++) {
			int x, y;
			do {
				x = r.nextInt(width);
				y = r.nextInt(height);
			} while (x == birthx && y == birthy); // Exclude birth cell coordinates
			cells[y][x].setEvent(new CommonEvent());

		}
		randCreateFinalCell();
	}

	// random end cell
	private void randCreateFinalCell() {
		Random r = new Random();
		// make sure final cell not same as birth cell
		while (r.nextInt(width) == this.birthx && r.nextInt(height) == this.birthy) {
			r = new Random();
		}
		int x = r.nextInt(width);
		int y = r.nextInt(height);
		cells[y][x] = new FinalCell(x, y);
	}

	// random birth cell
	public Cell randBirthCell() {
		Random r = new Random();
		this.birthx = r.nextInt(width);
		this.birthy = r.nextInt(height);
		Cell cell = cells[this.birthy][this.birthx];
		cell.setExplored(true);
		return cell;
	}

	// action
	public Cell action(int x, int y) {
		if (x < 0) {
			x = 0;
		} else if (x >= width) {
			x = width - 1;
		} else if (y < 0) {
			y = 0;
		} else if (y >= height) {
			y = height - 1;
		}
		roleCell = cells[y][x];
		return roleCell;
	}

	public Cell getCell(int x, int y) {
		return cells[y][x];
	}
}
