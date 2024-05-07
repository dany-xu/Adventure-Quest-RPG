import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.net.URL;
import java.util.Arrays;
import java.util.Locale;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;

import event.Fight;
import map.Cell;
import map.FinalCell;
import map.WorldMap;
import monster.Goblin;
import role.Knight;
import role.Ranger;
import role.Role;
import role.Soldier;
import role.Thief;
import role.Witch;

public class UI extends JFrame implements KeyListener {
	private Role role;
	private int width = 10;
	private int height = 10;
	private Cell currCell;
	private WorldMap worldMap;
	private JPanel mapPanel;
	private JTextArea infoArea;
	private JTextArea eventArea;
	private ImageIcon playerIcon;
	private JLabel roleAttributesLabel; // Label to display role attributes

	private ImageIcon resizeIcon(ImageIcon icon, int width, int height) {
		Image img = icon.getImage();
		Image resizedImage = img.getScaledInstance(width, height, Image.SCALE_SMOOTH);
		return new ImageIcon(resizedImage);
	}

	public UI() {
		loadImages(); // add player tag
		initializeUI();
		createR(); // init role
	}

	private void loadImages() {
		URL imageUrl = UI.class.getResource("player.png");
		if (imageUrl != null) {
			ImageIcon originalIcon = new ImageIcon(imageUrl);
			playerIcon = resizeIcon(originalIcon, 40, 40);
		}
		// 不知道问什么窗口无法更新status，位置显示也有问题
		// 还差回合制显示打怪（还有你之前说的玩家和怪物选action对比？还实现吗）

		// 不确定下面的thread有没有用
		/*
		 * // Start a new thread to continuously update the role attributes Thread
		 * statusUpdateThread = new Thread(() -> { while (true) { try { // Sleep for a
		 * certain interval (e.g., 1 second) Thread.sleep(1000); // Call
		 * role.viewStatus() to get the updated role attributes String status =
		 * role.viewStatus(); // Update the role attributes label
		 * SwingUtilities.invokeLater(() -> roleAttributesLabel.setText(status)); }
		 * catch (InterruptedException e) { e.printStackTrace(); } } });
		 * statusUpdateThread.start();
		 */
	}

	private void updateRoleAttributes() {
		// Create and start a new thread to update the role attributes
		Thread statusUpdateThread = new Thread(new Runnable() {
			public void run() {
				// Call role.viewStatus() to get the updated role attributes
				String status = role.viewStatus();
				// Update the role attributes label
				roleAttributesLabel.setText(status);
			}
		});
		statusUpdateThread.start();
	}

	private void initializeUI() {
		setTitle("Adventure Game");
		setSize(800, 600);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		setLayout(new BorderLayout());

		// Create map
		mapPanel = new JPanel(new GridLayout(height, width));
		mapPanel.setPreferredSize(new Dimension(400, 400));
		add(mapPanel, BorderLayout.CENTER);

		// Create info panel to display role attributes
		JPanel infoPanel = new JPanel(new BorderLayout());
		roleAttributesLabel = new JLabel();
		infoPanel.add(roleAttributesLabel, BorderLayout.CENTER);
		add(infoPanel, BorderLayout.SOUTH);

		// Create info area to display additional information
		infoArea = new JTextArea(5, 30);
		infoArea.setEditable(false);
		infoPanel.add(new JScrollPane(infoArea), BorderLayout.NORTH);

		// Show events
		eventArea = new JTextArea(10, 30);
		eventArea.setEditable(false);
		eventArea.setLineWrap(true);
		eventArea.setWrapStyleWord(true);
		add(new JScrollPane(eventArea), BorderLayout.EAST);

		// Register key listeners
		addKeyListener(this);
		mapPanel.addKeyListener(this);
		infoArea.addKeyListener(this); // Ensure infoArea is initialized before adding the key listener
		eventArea.addKeyListener(this);

		// Pack and display the window
		pack();
		setVisible(true);
	}

	public void createR() {
		String name = (String) JOptionPane.showInputDialog(this, "Please input your name:", "Name Selection",
				JOptionPane.PLAIN_MESSAGE, null, null, "player");
		if (name == null || name.trim().isEmpty()) {
			name = "player";
			JOptionPane.showMessageDialog(this, "Warning: Your name is set to 'player'.");
		}

		String[] options = { "Knight", "Ranger", "Thief", "Witch", "Soldier" };
		String selectedRole = (String) JOptionPane.showInputDialog(this, "Please choose your character class",
				"Character Selection", JOptionPane.PLAIN_MESSAGE, null, options, options[0]);

		if (selectedRole == null) {
			System.exit(0);
		}

		int roleType = Arrays.asList(options).indexOf(selectedRole);
		// setup role
		switch (roleType) {
		case 0:
			this.role = new Knight(name, 100.0);
			break;
		case 1:
			this.role = new Ranger(name, 100.0);
			break;
		case 2:
			this.role = new Thief(name, 100.0);
			break;
		case 3:
			this.role = new Witch(name, 100.0);
			break;
		case 4:
			this.role = new Soldier(name, 100.0);
			break;
		default:
			break;
		}

		// Update role attributes label
		updateRoleAttributes();

		this.worldMap = new WorldMap(width, height, role);
		currCell = worldMap.randBirthCell();
		showMap();
	}

	private void showMap() {
		mapPanel.removeAll();
		for (int i = 0; i < height; i++) {
			for (int j = 0; j < width; j++) {
				JLabel cellLabel = new JLabel();
				cellLabel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
				Cell cell = worldMap.getCell(j, i);
				if (cell == currCell) {
					cellLabel.setIcon(playerIcon);
				} else if (!cell.isExplored()) { // Unexplored cell
					cellLabel.setBackground(Color.GRAY);
					cellLabel.setOpaque(true);
				} else { // Explored cell
					if (cell instanceof FinalCell) {
						JOptionPane.showMessageDialog(this, "Congratulations!");
						role.viewStatus();
						removeKeyListener(this);
					} else if (cell.getEvent().getEventFlag().equals("M")) {
						cellLabel.setBackground(Color.RED);
					} else if (cell.getEvent().getEventFlag().equals("C")
							|| cell.getEvent().getEventFlag().equals("R")) {
						cellLabel.setBackground(Color.BLUE);
					} else {
						cellLabel.setBackground(Color.WHITE);
					}
					cellLabel.setOpaque(true);
				}
				mapPanel.add(cellLabel);
			}
		}
		mapPanel.revalidate();
		mapPanel.repaint();
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
		if (currCell.getEvent() != null && !currCell.isExplored()) {
			currCell.setExplored(true);// set cell visited
			currCell.getEvent().getRandomEvent(); // get attribute values
			String eventKeyword = currCell.getEvent().getKeyword();
			String eventContent = currCell.getEvent().getContent();
			eventArea.setText(eventKeyword + ": " + eventContent); // + role.viewStatus());
			// System.out.println("event atk" + currCell.getEvent().getStrength());
			if (currCell.getEvent().getEventFlag().equals("C") || currCell.getEvent().getEventFlag().equals("R")) {
				// System.out.println("before meet" + role.viewStatus());
				role.meetEvent(currCell); // change role attribute values
				// System.out.println("after meet" + role.viewStatus());
			} else if (currCell.getEvent().getEventFlag().equals("M")) {
				Goblin a = new Goblin();
				Fight fight = new Fight(a, role);
				String eventMessage = fight.startCombat();
				eventArea.setText(eventMessage);
			}
		}
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// Not implemented
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
			// Assuming this should show role info
			// role.viewStatus(); // Assume this method exists
			String status = role.viewStatus();
			// Update the role attributes label
			roleAttributesLabel.setText(status);
			break;
		case KeyEvent.VK_0:
			showMap();
			break;
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// Not implemented
	}

	public static void main(String[] args) {
		Locale.setDefault(Locale.ENGLISH);
		SwingUtilities.invokeLater(UI::new);
	}
}
