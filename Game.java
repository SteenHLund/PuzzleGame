import java.awt.Component;
import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.Timer;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class Game extends Info implements ActionListener, KeyListener {
	public static GamePanel gamePanel;
	public static Timer timer;
	public static JButton quit = new JButton(), start = new JButton(), useless = new JButton();
	public static JLabel gameTime = new JLabel(), title = new JLabel();
	
	public Game(int gameBoardType) {
		boardType = gameBoardType;
		if(boardType == 3)
			boardSize = new Dimension(500, 300);
		else if(boardType == 4)
			boardSize = new Dimension(550, 370);
		else if(boardType == 5)
			boardSize = new Dimension(600, 440);
		
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
		gameFrame.addKeyListener(this);
		
		setUpLabels();
		
		timer = new Timer(TIMER_SPEED, this);
		timer.setInitialDelay(TIMER_DELAY);
		
		for(int i = 1; i < boardType*boardType; i++)
			numbers.add(i);
		numbers.add(0);
		
		for(int i = 0; i < numbers.size(); i++)
			System.out.print(numbers.get(i) + ", ");
	}
	
	public void setUpLabels() {
		Dimension size = new Dimension(100, 50);
		Dimension titleSize = new Dimension(150, 20);
		
		title.setSize(titleSize);
		title.setText("Sliding Puzzle Ultra!");
		title.setVisible(true);
		addComponent(title, boardSize.width - title.getWidth() - 10, title.getHeight() + 30, titleSize);
		
		gameTime.setSize(titleSize);
		gameTime.setText("Time: " + 0.0);
		gameTime.setVisible(true);
		addComponent(gameTime, boardSize.width - gameTime.getWidth() + 24, quit.getHeight() + 205, titleSize);
		
		quit.setSize(size);
		quit.setText("Quit");
		quit.setVisible(true);
		quit.addActionListener(this);
		addComponent(quit, boardSize.width - quit.getWidth() - 48, quit.getHeight() + 90, size);
		
		start.setSize(size);
		start.setText("Start");
		start.setVisible(true);
		start.addActionListener(this);
		addComponent(start, boardSize.width - start.getWidth() - 48, start.getHeight() + 30, size);
		
		useless.setVisible(false);
		addComponent(useless, 0, 0, size);
	}

	private static void addComponent(Component c, int x, int y, Dimension size){
		c.setBounds(new Rectangle(x, y, size.width, size.height));
		gamePanel.add(c);
	}
	
	public void keyTyped(KeyEvent e) {
		
	}

	public void keyPressed(KeyEvent e) {
		
	}

	public void keyReleased(KeyEvent e) {
		
	}

	public void actionPerformed(ActionEvent e) {
		Object src = e.getSource();
		
		if(src == start) {
			started = true;
			timer.start();
		}
		else if(src == quit)
			System.exit(0);
		
		if(gameOver == false  && started) {
			time += TIMER_SPEED;
			gameTime.setText("Time: " + (time / 1000) + "." + (time / 100 % 10));
			repaint();
		}
	}
}
