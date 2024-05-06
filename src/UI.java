import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.net.URL;
import java.util.Arrays;
import java.util.Locale;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JDialog;
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
// 假设WorldMap, Cell等类以及它们的方法已正确定义
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
	}

	private void initializeUI() {
		setTitle("Adventure Game");
		setSize(800, 600);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		// Add a WindowListener to prompt the user when closing the window
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				confirmExit();
			}
		});

		setLayout(new BorderLayout());

		// create map
		mapPanel = new JPanel(new GridLayout(height, width));
		mapPanel.setPreferredSize(new Dimension(400, 400));
		add(mapPanel, BorderLayout.CENTER);

		// show info
		infoArea = new JTextArea(5, 30);
		infoArea.setEditable(false);
		add(new JScrollPane(infoArea), BorderLayout.SOUTH);

		// show events
		eventArea = new JTextArea(10, 30);
		eventArea.setEditable(false);
		eventArea.setLineWrap(true);
		eventArea.setWrapStyleWord(true);
		add(new JScrollPane(eventArea), BorderLayout.EAST);

		addKeyListener(this);
		mapPanel.addKeyListener(this);
		infoArea.addKeyListener(this);
		eventArea.addKeyListener(this);

		pack();
		setVisible(true);
	}

	private void confirmExit() {
		int result = JOptionPane.showConfirmDialog(UI.this, "Are you sure you want to exit the game?", "Exit",
				JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
		if (result == JOptionPane.YES_OPTION) {
			System.exit(0);
		}
	}

	public void createR() {
		String name = (String) JOptionPane.showInputDialog(this, "Please input your name:", "Name Selection",
				JOptionPane.PLAIN_MESSAGE, null, null, "player");
		if (name == null || name.trim().isEmpty()) {
			name = "player";
			JOptionPane.showMessageDialog(this, "Warning: Your name is set to 'player'.");
		}

		String[] options = { "Knight", "Ranger", "Thief", "Witch", "Soldier" };
		JOptionPane pane = new JOptionPane("Please choose your character class", JOptionPane.PLAIN_MESSAGE,
				JOptionPane.DEFAULT_OPTION, null, options, options[0]);
		JDialog dialog = pane.createDialog(this, "Character Selection");
		dialog.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
		dialog.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				int result = JOptionPane.showConfirmDialog(UI.this,
						"You have to choose a role. Are you sure you want to exit?", "Exit", JOptionPane.YES_NO_OPTION,
						JOptionPane.QUESTION_MESSAGE);
				if (result == JOptionPane.YES_OPTION) {
					System.exit(0);
				}
			}
		});
		dialog.setVisible(true);
		Object selectedValue = pane.getValue();
		if (selectedValue == null || !(selectedValue instanceof String)) {
			JOptionPane.showMessageDialog(this, "You must choose a character class.");
			createR(); // Prompt again for character selection
			return;
		}
		String selectedRole = (String) selectedValue;
		int roleType = Arrays.asList(options).indexOf(selectedRole);
		// setup role
		if (roleType == 0) {
			this.role = new Knight(name, 100.0);
		} else if (roleType == 1) {
			this.role = new Ranger(name, 100.0);
		} else if (roleType == 2) {
			this.role = new Thief(name, 100.0);
		} else if (roleType == 3) {
			this.role = new Witch(name, 100.0);
		} else {
			this.role = new Soldier(name, 100.0);
		}
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
						// cellLabel.setBackground(Color.YELLOW);
						JOptionPane.showMessageDialog(this, "Congratulations!");
						role.viewStatus();
						removeKeyListener(this);

					} else if (cell.getEvent().getEventFlag() == "M") {
						cellLabel.setBackground(Color.RED);
					} else if (cell.getEvent().getEventFlag() == "C" || cell.getEvent().getEventFlag() == "R") {
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
			eventArea.setText(eventKeyword + ": " + eventContent);
			if (currCell.getEvent().getEventFlag() == "C" || currCell.getEvent().getEventFlag() == "R") {
				role.meetEvent(currCell); // change role attribute values
			} else if (currCell.getEvent().getEventFlag() == "M") {
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
			role.viewStatus(); // Assume this method exists
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
