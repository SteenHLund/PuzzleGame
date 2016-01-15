import java.awt.Component;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;

import javax.swing.Timer;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

public class Game extends Info implements ActionListener, MouseListener, KeyListener {
	private static final long serialVersionUID = 1L;
	public static GamePanel gamePanel;
	public static Timer timer;
	public static JButton quit = new JButton(), boardSelect = new JButton(), useless = new JButton();
	public static JLabel gameTime = new JLabel(), title = new JLabel();
	public static JTextField name = new JTextField();
	
	public Game(int gameBoardType) {
		boardType = gameBoardType;
		if(boardType == 3)
			boardSize = new Dimension(420, 330);
		else if(boardType == 4)
			boardSize = new Dimension(480, 390);
		else if(boardType == 5)
			boardSize = new Dimension(540, 450);
		
		gameFrame = new JFrame();
		gamePanel = new GamePanel();
		gamePanel.setLayout(null);
		gameFrame.getContentPane().add(gamePanel);

		gameFrame.setSize(boardSize);
		gameFrame.setTitle("Sliding Puzzle Ultra");
		gameFrame.setLocationRelativeTo(null);
		gameFrame.setDefaultCloseOperation(gameFrame.EXIT_ON_CLOSE);
		gameFrame.setResizable(false);
		gameFrame.setVisible(true);
		gameFrame.addMouseListener(this);
		gameFrame.addKeyListener(this);
		
		setUpLabels();
		
		timer = new Timer(TIMER_SPEED, this);
		timer.setInitialDelay(TIMER_DELAY);
		
		numbers.add(new Block(blockSize, 2, new Point(50, 50)));
		numbers.add(new Block(blockSize, 3, new Point(110, 50)));
		numbers.add(new Block(blockSize, 1, new Point(170, 50)));
		
		int counter = 1;
		for (int i = 0; i < boardType; i++) {
			for(int q = 0; q < boardType; q++) {
				if(counter != boardType*boardType && counter > 3)
					numbers.add(new Block(blockSize, counter, new Point(50 + q*60, 50 + i*60)));
				counter++;
			}
		}
		numbers.add(new Block(blockSize, 0, new Point(50 + (boardType - 1)*60, 50 + (boardType - 1)*60)));
		
		try {
			if(boardType == 3)
				fileName = "3x3High";
			else if(boardType == 4)
				fileName = "4x4High";
			else
				fileName = "5x5High";
			
			input = new BufferedReader(new FileReader(fileName));
			
			String currentLine = "";
			while ((currentLine = input.readLine()) != null) {
				highscores.add(currentLine);
			}
			
			input.close();
		} catch(Exception Error) {
			System.out.println("File not found");
		}
	}
	
	public int zeroPos() {
		int posOfZero = 0;
		for(int i = 0; i < numbers.size(); i++)
			if(numbers.get(i).getValue() == 0)
				posOfZero = i;
		return posOfZero;
	}
	
	public boolean nextToZero(int pos) {
		boolean isNeighbor = false;
		int zero = zeroPos();
		
		if(pos - boardType == zero || pos + boardType == zero || (pos - 1 == zero && pos % boardType != 0) || (pos + 1 == zero && pos % boardType != boardType - 1))
			isNeighbor = true;
		else
			System.out.println(pos % boardType);
		
		return isNeighbor;
	}
	
	public void setUpLabels() {
		Dimension size = new Dimension(100, 50);
		Dimension titleSize = new Dimension(150, 20);
		
		title.setSize(titleSize);
		title.setText("Sliding Puzzle Ultra!");
		title.setVisible(true);
		addComponent(title, 65 + (boardType - 3) * 30, 20, titleSize);
		
		gameTime.setSize(titleSize);
		gameTime.setText("Time: " + 0.0);
		gameTime.setVisible(true);
		addComponent(gameTime, 45, gameFrame.getHeight() - 75, titleSize);
		
		quit.setSize(size);
		quit.setText("Quit");
		quit.setVisible(true);
		quit.addActionListener(this);
		quit.setFocusable(false);
		addComponent(quit, gameFrame.getWidth() - 290, gameFrame.getHeight() - 90, size);
		
		useless.setVisible(false);
		addComponent(useless, 0, 0, size);
	}

	private static void addComponent(Component c, int x, int y, Dimension size){
		c.setBounds(new Rectangle(x, y, size.width, size.height));
		gamePanel.add(c);
	}

	public void actionPerformed(ActionEvent e) {
		Object src = e.getSource();
		
		boolean stillGood = true;
		for(int i = 0; i < numbers.size() - 1; i++)
			if(numbers.get(i).getValue() != i + 1)
				stillGood = false;
		
		if(stillGood) {
			gameOver = true;
			gameFrame.repaint();
		}
		
		if(src == quit)
			System.exit(0);
		else if(src == name) {
			boolean notAdded = false;
			for(int i = 1; i < highscores.size(); i += 2) {
				if(!notAdded) {
					name.setEnabled(false);
					double currentTime = Double.parseDouble((time / 1000) + "." + (time / 100 % 10));
					double currentVal = Double.parseDouble(highscores.get(i));
					if (currentVal > currentTime) {
						highscores.add(i - 1, name.getText());
						highscores.add(i, "" + currentTime);
						highscores.remove(10);
						highscores.remove(10);
						gameFrame.repaint();
						notAdded = true;
					}
				}
			}
			try {
				output = new FileWriter(fileName);
				printOut = new PrintWriter(output);
				printOut.write("");
				
				for (int q = 0; q < highscores.size(); q++) {
					output.write(highscores.get(q) + "\n");
				}
				
				output.close();
				printOut.flush();
				printOut.close();
			} catch (Exception Error) {
				System.out.println("File not found");
			}
		}
		
		if(gameOver == false  && started) {
			time += TIMER_SPEED;
			gameTime.setText("Time: " + (time / 1000) + "." + (time / 100 % 10));
		} else if (gameOver) {
			timer.stop();
			
			name.setVisible(true);
			name.setText("Enter your name");
			name.addActionListener(this);
			addComponent(name, 80 + boardType * 60, gameFrame.getHeight() - 90, new Dimension(150, 25));
		}
	}

	public void mouseClicked(MouseEvent e) {
		
	}

	public void mousePressed(MouseEvent e) {
		
	}

	public void mouseReleased(MouseEvent e) {
		started = true;
		if (!timer.isRunning())
			timer.start();
		
		Point mouseLoc = gameFrame.getMousePosition();
		boolean clickedAlready = false;
		
		if (!gameOver) {
			for (int i = 0; i < numbers.size(); i++) {
				if (clickedAlready == false) {
					if (mouseLoc.getX() < numbers.get(i).blockInfo().x + numbers.get(i).blockInfo().getWidth()
							&& mouseLoc.getX() > numbers.get(i).blockInfo().x) {
						if (mouseLoc.getY() - 25 < numbers.get(i).blockInfo().y + numbers.get(i).blockInfo().getHeight()
								&& mouseLoc.getY() - 25 > numbers.get(i).blockInfo().y) {
							if (nextToZero(i)) {
								swap(i);
								clickedAlready = true;
							}
						}
					}
				}
			}
		}
		gamePanel.repaint();
	}

	public void mouseEntered(MouseEvent e) {
	
	}

	public void mouseExited(MouseEvent e) {
		
	}

	public void keyTyped(KeyEvent e) {
	
	}

	public void keyPressed(KeyEvent e) {
		started = true;
		if (!timer.isRunning())
			timer.start();
		
		int key = e.getKeyCode();
		int zero = zeroPos();
		
		if(!gameOver) {
			if (key == 37) {
				if (nextToZero(zero - 1))
					swap(zero - 1);
			} else if (key == 38) {
				if (nextToZero(zero - boardType))
					swap(zero - boardType);
			} else if (key == 39) {
				if (nextToZero(zero + 1))
					swap(zero + 1);
			} else if (key == 40) {
				if (nextToZero(zero + boardType))
					swap(zero + boardType);
			}
		}
		
		gameFrame.repaint();
	}
	
	public void swap(int pos) {
		if (pos >= 0 && pos < numbers.size()) {
			int tempZeroSpace = zeroPos();
			Block tempZeroBlock = numbers.get(tempZeroSpace);
			Block tempSwappingBlock = numbers.get(pos);
			Point temp = tempSwappingBlock.getPos();
			tempSwappingBlock.setPos(tempZeroBlock.getPos());

			tempZeroBlock.setPos(temp);

			numbers.set(tempZeroSpace, numbers.get(pos));
			numbers.set(pos, tempZeroBlock);
		}
	}

	public void keyReleased(KeyEvent e) {
		
	}
}
