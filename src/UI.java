import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.net.URL;
import java.util.Arrays;
import java.util.Locale;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
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
import monster.Monster;
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
	private JTextArea statusArea; // Dedicated area for role status
	private ImageIcon playerIcon;

	private JLabel imageLabel;

	private ImageIcon resizeIcon(ImageIcon icon, int width, int height) {
		Image img = icon.getImage();
		Image resizedImage = img.getScaledInstance(width, height, Image.SCALE_SMOOTH);
		return new ImageIcon(resizedImage);
	}

	public UI() {
//        loadImages("player.png");
		initializeUI();
		createRole();
		String currentStatus = role.viewStatus();
		statusArea.setText(currentStatus);
		startStatusUpdateThread();
	}

	private ImageIcon loadImages(String path) {
		URL imageUrl = UI.class.getResource(path);
		if (imageUrl != null) {
			ImageIcon originalIcon = new ImageIcon(imageUrl);
			return resizeIcon(originalIcon, 40, 40);
		} else {
			System.out.println("Image file not found: " + path);
			return null;
		}

	}

	private void initializeUI() {
		setTitle("Adventure Game");
		setSize(800, 600);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLayout(new BorderLayout());

		imageLabel = new JLabel(); // create jlabel obj
		updateImageDisplay("normal.png");
		add(imageLabel, BorderLayout.WEST); // add JLabelto the west of the window

		mapPanel = new JPanel(new GridLayout(height, width));
		mapPanel.setPreferredSize(new Dimension(400, 400));
		add(mapPanel, BorderLayout.CENTER);

		// character status area
		JPanel infoPanel = new JPanel(new BorderLayout());
		statusArea = new JTextArea(10, 18);
		statusArea.setEditable(false);
		statusArea.setPreferredSize(new Dimension(800, 100)); // set preferred size to avoid extra space
		statusArea.setFont(new Font("Arial", Font.PLAIN, 17)); // set font and size
		infoPanel.add(new JScrollPane(statusArea), BorderLayout.CENTER);
		add(infoPanel, BorderLayout.SOUTH);

		JButton skillButton = new JButton("Use Skill");
		skillButton.addActionListener(e -> {
			role.useSkill();

		});
		infoPanel.add(skillButton, BorderLayout.SOUTH); // add button to the lowest

		infoArea = new JTextArea(8, 8);
		infoArea.setEditable(false);
		// infoPanel.add(new JScrollPane(infoArea), BorderLayout.NORTH);

		eventArea = new JTextArea(10, 25);
		eventArea.setEditable(false);
		eventArea.setLineWrap(true);
		eventArea.setWrapStyleWord(true);
		eventArea.setFont(new Font("Arial", Font.PLAIN, 17));
		add(new JScrollPane(eventArea), BorderLayout.EAST);

		addKeyListener(this);
		mapPanel.addKeyListener(this);
		infoArea.addKeyListener(this);
		eventArea.addKeyListener(this);
		statusArea.addKeyListener(this);

		pack();
		setVisible(true);
	}

	public void updateImageDisplay(String imagePath) {
		URL imgUrl = UI.class.getResource(imagePath);
		if (imgUrl != null) {
			ImageIcon icon = new ImageIcon(imgUrl);
			Image img = icon.getImage();
			Image resizedImg = img.getScaledInstance(480, 270, Image.SCALE_SMOOTH);
			icon.setImage(resizedImg);
			imageLabel.setIcon(icon);
			imageLabel.revalidate(); 
			imageLabel.repaint(); 
		} else {
			System.out.println("Image file not found: " + imagePath);
		}
	}

	private void createRole() {
//        String name = (String) JOptionPane.showInputDialog(this, "Please input your name:", "Name Selection",
//                JOptionPane.PLAIN_MESSAGE, null, null, "player");
//        if (name == null || name.trim().isEmpty()) {
//            name = "player";
//            JOptionPane.showMessageDialog(this, "Warning: Your name is set to 'player'.");
//        }
//
//        String[] options = {"Knight", "Ranger", "Thief", "Witch", "Soldier"};
//        String selectedRole = (String) JOptionPane.showInputDialog(this, "Please choose your character class",
//                "Character Selection", JOptionPane.PLAIN_MESSAGE, null, options, options[0]);
//        if (selectedRole == null) {
//            System.exit(0);
//        }
//
//        int roleType = Arrays.asList(options).indexOf(selectedRole);
//        switch (roleType) {
//            case 0:
//                this.role = new Knight(name, 100.0);
//                loadImages("Knight.png");
//                break;
//            case 1:
//                this.role = new Ranger(name, 75.0);
//                loadImages("Ranger.png");
//                break;
//            case 2:
//                this.role = new Thief(name, 80.0);
//                loadImages("Thief.png");
//                break;
//            case 3:
//                this.role = new Witch(name, 70.0);
//                loadImages("Witch.png");
//                break;
//            case 4:
//                this.role = new Soldier(name, 90.0);
//                loadImages("Soldier.png");
//                break;
//            default:
//                break;
//        }
		String name = (String) JOptionPane.showInputDialog(this, "Please input your name:", "Name Selection",
				JOptionPane.PLAIN_MESSAGE, null, null, "player");
		if (name == null || name.trim().isEmpty()) {
			name = "player";
			JOptionPane.showMessageDialog(this, "Warning: Your name is set to 'player'.");
		}

		String[] options = { "Knight", "Ranger", "Thief", "Witch", "Soldier" };
		String selectedRole = (String) JOptionPane.showInputDialog(this, "Please choose your character class",
				"Character Selection", JOptionPane.PLAIN_MESSAGE, null, options, options[0]);
		// check if the user canceled or closed the dialog
		if (selectedRole == null) {
			JOptionPane.showMessageDialog(this, "You did not select a character. Defaulting to Knight.");
			this.role = new Knight(name, 100.0);
			playerIcon = loadImages("Knight.png");
		} else {
			int roleType = Arrays.asList(options).indexOf(selectedRole);
			switch (roleType) {
			case 0:
				this.role = new Knight(name, 100.0);
				playerIcon = loadImages("Knight.png");
				break;
			case 1:
				this.role = new Ranger(name, 75.0);
				playerIcon = loadImages("Ranger.png");
				break;
			case 2:
				this.role = new Thief(name, 80.0);
				playerIcon = loadImages("Thief.png");
				break;
			case 3:
				this.role = new Witch(name, 70.0);
				playerIcon = loadImages("Witch.png");
				break;
			case 4:
				this.role = new Soldier(name, 90.0);
				playerIcon = loadImages("Soldier.png");
				break;
			default:
				playerIcon = loadImages("default.png"); // Default image if no match
				break;
			}
		}
		this.worldMap = new WorldMap(width, height, role);
		currCell = worldMap.getBirthCell();
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
				} else if (!cell.isExplored()) {
					cellLabel.setBackground(Color.GRAY);
					cellLabel.setOpaque(true);
				} else {
					if (cell instanceof FinalCell) {
						// JOptionPane.showMessageDialog(this, "Congratulations!");
						// role.viewStatus();
						int result = JOptionPane.showConfirmDialog(this,
								"You found the hidden treasure! Do you want to exit the game?", "Congratulations!",
								JOptionPane.OK_CANCEL_OPTION);
						if (result == JOptionPane.OK_OPTION) {
							System.exit(0); // Exit the game if user clicks OK
						}
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
	
	// Multithreading part: keep refresh the status information
	private void startStatusUpdateThread() {
		Thread statusUpdateThread = new Thread(() -> {
			while (true) {
				try {
					Thread.sleep(1000);
					String status = role.viewStatus();
					SwingUtilities.invokeLater(() -> statusArea.setText(status));
				} catch (InterruptedException e) {
					Thread.currentThread().interrupt();
					return;
				}
			}
		});
		statusUpdateThread.start();
	}

	@Override
	public void keyTyped(KeyEvent e) {
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
			// Display current status in the status area
			String currentStatus = role.viewStatus();
			statusArea.setText(currentStatus);
			break;
		case KeyEvent.VK_0:
			showMap();
			break;
		}
	}

	private void checkEvent() {
		updateImageDisplay("normal.png");
		if (currCell.getEvent() != null && !currCell.isExplored()) {
			currCell.setExplored(true);// set cell visited
			currCell.getEvent().getRandomEvent(); // get attribute values
			String eventKeyword = currCell.getEvent().getKeyword();
			String eventContent = currCell.getEvent().getContent();
			eventArea.setText(eventKeyword + ": " + eventContent); // + role.viewStatus());
			// System.out.println("event atk" + currCell.getEvent().getStrength());

			if (currCell.getEvent().getEventFlag().equals("C") || currCell.getEvent().getEventFlag().equals("R")) {
				// System.out.println("before meet" + role.viewStatus());
				updateImageDisplay("event.png");
				role.meetEvent(currCell); // change role attribute values
				// System.out.println("after meet" + role.viewStatus());
			} else if (currCell.getEvent().getEventFlag().equals("M")) {
				updateImageDisplay("monster.png");
				currCell.getEvent().getRandomEvent();
				Monster a = new Monster(currCell.getEvent().name, 50.0, (int) currCell.getEvent().getIntelligence(),
						(int) currCell.getEvent().getStrength(), (int) currCell.getEvent().getAgility(),
						(int) currCell.getEvent().getDefense(), (int) currCell.getEvent().getStability(),
						"Trash Monster");
				Fight fight = new Fight(a, role);
				String eventMessage = currCell.getEvent().getContent() + "\n" + fight.startCombat();
				eventArea.setText(eventMessage);
			}
		}
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

	@Override
	public void keyReleased(KeyEvent e) {
	}

	public static void main(String[] args) {
		Locale.setDefault(Locale.ENGLISH);
		SwingUtilities.invokeLater(UI::new);
	}
}
