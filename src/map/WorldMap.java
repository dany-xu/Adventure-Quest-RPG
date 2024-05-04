package map;

import event.Block;
import event.BlessingEvent;
import event.ElvesEvent;
import event.MonsterEvent;

import java.util.Random;


public class WorldMap {
	// 地图宽度
	private int width;
	// 地图高度
	private int height;
	// 格子总数
	private int cellCount;
	// 初始化地图格子
	private Cell[][] cells;
	// 角色所在的格子
	private Cell roleCell;
	public Block[][] blocks = new Block[10][10];
	/**
	 * 构造方法
	 */
	public WorldMap(int width, int height) {
		this.width = width;
		this.height = height;
		this.cellCount = width * height;
		cells = new Cell[height][width];
		// 初始化地图
		initMap();
	}

	/**
	 * 初始化地图
	 */
	private void initMap() {
		for (int i = 0; i < height; i++) {
			for (int j = 0; j < width; j++) {
				Cell cell = new Cell(j, i);
				cells[i][j] = cell;
			}
		}

		Random r = new Random();
		// 初始化怪物
		int monsterCount = cellCount / 10;  //总地图的1/10为怪物
		for (int i = 0; i < monsterCount; i++) {
			cells[r.nextInt(height)][r.nextInt(width)].setEvent(new MonsterEvent());
		}

		// 初始化精灵
		int elvesCount = cellCount / 20;
		for (int i = 0; i < elvesCount; i++) {
			cells[r.nextInt(height)][r.nextInt(width)].setEvent(new ElvesEvent());
		}

		// 初始化祝福
		int blessingCount = cellCount / 25;
		for (int i = 0; i < blessingCount; i++) {
			cells[r.nextInt(height)][r.nextInt(width)].setEvent(new BlessingEvent());
		}

		// 设置结束格子
		randCreateFinalCell();
		randCreateFinalCell();
	}

	/**
	 * 随机结束cell
	 */
	private void randCreateFinalCell() {
		Random r = new Random();
		int x = r.nextInt(width);
		int y = r.nextInt(height);
		cells[y][x] = new FinalCell(x, y);
	}

	/**
	 * 随机出生地
	 */
	public Cell randBirthCell() {
		Random r = new Random();
		int x = r.nextInt(width);
		int y = r.nextInt(height);
		Cell cell = cells[y][x];
		cell.setExplored(true);
		return cell;
	}

	/**
	 * 行动
	 */
	public Cell action(int x, int y) {
		if(x < 0) {
			x = 0;
		} else if(x >= width) {
			x = width - 1;
		} else if(y < 0) {
			y = 0;
		} else if(y >= height) {
			y = height - 1;
		}
		roleCell = cells[y][x];
		return roleCell;
	}

	public Cell getCell(int x, int y) {
		return cells[y][x];
	}


	public WorldMap() {
		for (int j = 0; j < blocks.length; j++) {

			for (int i = 0; i < blocks[j].length; i++) {
				int retindex = (int) (Math.random() * 10);
				if (retindex >= 5) {
					retindex = 1;
				} else {
					retindex = 2;
				}
				switch (retindex) {
					case 1:
						blocks[j][i] = new Block(new BlessingEvent());
						break;

					case 2:
						blocks[j][i] = new Block(new MonsterEvent());
						break;

					// case 3:

					// break;

					// case 4:

					// break;

					// default:
					// blocks[j][i] = new event.Block(new event.CureEvent());
					// break;

				}
			}
		}
	}

	public Block[][] getBlocks() {
		return blocks;
	}

}
