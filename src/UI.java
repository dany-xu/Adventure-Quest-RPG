import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;

import map.Cell;
import map.FinalCell;
import map.WorldMap;
import role.Master;
import role.Role;

class UI extends JFrame implements KeyListener {
	private Role role;
	private int width = 10;
	private int height = 10;
	private Cell currCell;
	private WorldMap worldMap = new WorldMap(width, height);
	private JTextArea mapArea;
	private JTextArea infoArea;
	private JTextArea eventArea;

	public UI() {
		initializeUI();
		createR(); // 初始化角色
	}

	private void initializeUI() {
		setTitle("探险游戏");
		setSize(800, 600);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLayout(new BorderLayout());

		// 增加地图区域的可视性
		mapArea = new JTextArea(20, 20); // 调整文本区的大小
		mapArea.setFont(new Font("Monospaced", Font.PLAIN, 12)); // 设置字体，保证地图对齐
		mapArea.setEditable(false);
		add(new JScrollPane(mapArea), BorderLayout.CENTER);

		// 信息显示区
		infoArea = new JTextArea(5, 30);
		infoArea.setEditable(false);
		add(new JScrollPane(infoArea), BorderLayout.SOUTH);

		// 事件显示区
		eventArea = new JTextArea(10, 30);
		eventArea.setEditable(false);
		add(new JScrollPane(eventArea), BorderLayout.EAST);

		// 添加键盘事件监听器
		addKeyListener(this);
		mapArea.addKeyListener(this);
		infoArea.addKeyListener(this);
		eventArea.addKeyListener(this);

		pack();
		setVisible(true);
	}

	public void createR() {
		String name = JOptionPane.showInputDialog(this, "请输入角色姓名:");
		String[] options = { "战士", "法师" };
		int roleType = JOptionPane.showOptionDialog(this, "请选择职业", "角色选择", JOptionPane.DEFAULT_OPTION,
				JOptionPane.INFORMATION_MESSAGE, null, options, options[0]);
		if (roleType == 0) {
			// role = new Soldier(name);
		} else {
			role = new Master(name);
		}
		currCell = worldMap.randBirthCell();
		showMap();
	}

	private void showMap() {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < height; i++) {
			for (int j = 0; j < width; j++) {
				Cell cell = worldMap.getCell(j, i);
				if (cell == currCell) {
					sb.append(" P ");
				} else if (!cell.isExplored()) {
					sb.append(" ? ");
				} else if (cell instanceof FinalCell) {
					sb.append(" $ ");
				} else if (cell.getEvent() != null) {
					sb.append(" E ");
				} else {
					sb.append(" _ ");
				}
			}
			sb.append("\n");
		}
		mapArea.setText(sb.toString());
	}

	private void forward(char direction) {
		int dx = 0, dy = 0;
		switch (direction) {
		case 'w':
			dy = -1;
			break;
		case 's':
			dy = 1;
			break;
		case 'a':
			dx = -1;
			break;
		case 'd':
			dx = 1;
			break;
		default:
			return; // ignore invalid input
		}
		Cell nextCell = worldMap.action(currCell.getX() + dx, currCell.getY() + dy);
		if (nextCell != null) {
			currCell = nextCell;
			showMap();
			checkEvent();
		}
	}

	private void checkEvent() {
		if (currCell.getEvent() != null && !currCell.getEvent().isTriggered()) {
			String eventMessage = "发生了事件！";
			eventArea.setText(eventMessage);
			currCell.getEvent().trigger(role);
		}
	}

	private void showRoleInfo() {
		String info = String.format("昵称：%s\n血量：%d\n力量：%d\n法术：%d", role.getName(), role.getHp(), role.getPower(),
				role.getMagic());
		infoArea.setText(info);
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// 不需要实现
	}

	@Override
	public void keyPressed(KeyEvent e) {
		int keyCode = e.getKeyCode();
		switch (keyCode) {
		case KeyEvent.VK_W:
			forward('w');
			break;
		case KeyEvent.VK_S:
			forward('s');
			break;
		case KeyEvent.VK_A:
			forward('a');
			break;
		case KeyEvent.VK_D:
			forward('d');
			break;
		case KeyEvent.VK_N:
			showRoleInfo();
			break;
		case KeyEvent.VK_0:
			showMap();
			break;
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// 不需要实现
	}

	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				new UI();
			}
		});
	}
}