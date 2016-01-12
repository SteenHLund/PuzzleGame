import java.awt.Component;
import java.awt.Dimension;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.PointerInfo;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.Timer;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class Game extends Info implements ActionListener, MouseListener, KeyListener {
	public static GamePanel gamePanel;
	public static Timer timer;
	public static JButton quit = new JButton(), boardSelect = new JButton(), useless = new JButton();
	public static JLabel gameTime = new JLabel(), title = new JLabel();
	
	public Game(int gameBoardType) {
		boardType = gameBoardType;
		if(boardType == 3)
			boardSize = new Dimension(275, 330);
		else if(boardType == 4)
			boardSize = new Dimension(335, 390);
		else if(boardType == 5)
			boardSize = new Dimension(395, 450);
		
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
		
		setUpLabels();
		
		timer = new Timer(TIMER_SPEED, this);
		timer.setInitialDelay(TIMER_DELAY);
		
		int counter = 1;
		for (int i = 0; i < boardType; i++) {
			for(int q = 0; q < boardType; q++) {
				if(counter != boardType*boardType)
					numbers.add(new Block(blockSize, counter, new Point(50 + q*60, 50 + i*60)));
				counter++;
			}
		}
		numbers.add(new Block(blockSize, 0, new Point(50 + (boardType - 1)*60, 50 + (boardType - 1)*60)));
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
		addComponent(quit, gameFrame.getWidth() - 140, gameFrame.getHeight() - 90, size);
		
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
		
		if(gameOver == false  && started) {
			time += TIMER_SPEED;
			gameTime.setText("Time: " + (time / 1000) + "." + (time / 100 % 10));
		} else if (gameOver)
			timer.stop();
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
								int tempZeroSpace = zeroPos();
								Block tempZeroBlock = numbers.get(tempZeroSpace);
								Block tempSwappingBlock = numbers.get(i);
								Point temp = tempSwappingBlock.getPos();
								tempSwappingBlock.setPos(tempZeroBlock.getPos());

								tempZeroBlock.setPos(temp);

								numbers.set(tempZeroSpace, numbers.get(i));
								numbers.set(i, tempZeroBlock);

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
		
	}

	public void keyReleased(KeyEvent e) {
		
	}
}
