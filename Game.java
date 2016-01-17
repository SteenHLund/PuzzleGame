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
import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.io.PrintWriter;

import javax.swing.Timer;

import javazoom.jl.player.Player;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

public class Game extends Info implements ActionListener, MouseListener, KeyListener {
	private static final long serialVersionUID = 1L;
	public static GamePanel gamePanel;
	public static Timer timer;
	public static JButton quit = new JButton(), boardSelect = new JButton(), useless = new JButton(), mute = new JButton(), undo = new JButton();
	public static JLabel gameTime = new JLabel(), title = new JLabel(), moves = new JLabel();
	public static JTextField name = new JTextField();
	//Player is the music player we use.  It is not a natural java class but one provided for free online that must be downloaded
	private Player player;
	
	//gameBoardType is the size of the board.  i.e. it will have a value 3 when the board is 3x3, etc
	public Game(int gameBoardType) {
		boardType = gameBoardType;
		//As the board has more blocks, we give it a proportionally large jframe to exist in
		if(boardType == 3)
			boardSize = new Dimension(420, 330);
		else if(boardType == 4)
			boardSize = new Dimension(480, 390);
		else if(boardType == 5)
			boardSize = new Dimension(540, 450);
		
		//Create the JFrame and JPanel to have all the graphics, etc.
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
		
		//Adds all the various labels and jbuttons to the game frame
		setUpLabels();
		
		//Create the timer used that calls actionPerformed and records the game time. We do not start it immediately
		timer = new Timer(TIMER_SPEED, this);
		timer.setInitialDelay(TIMER_DELAY);
		
		//We create the array of blocks here.  It starts off as requested: 2, 3, 1, then n^2 - 1
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
		//This is for the "0" or "null" block.  The empty one
		numbers.add(new Block(blockSize, 0, new Point(50 + (boardType - 1)*60, 50 + (boardType - 1)*60)));
		
		//Try catch for the high score boards.  3 different high score boards for the different board sizes.
		if(boardType == 3)
			fileName = "3x3High.txt";
		else if(boardType == 4)
			fileName = "4x4High.txt";
		else
			fileName = "5x5High.txt";
		try {
			input = new BufferedReader(new FileReader(fileName));
//			input = new BufferedReader(new InputStreamReader(Game.class.getResourceAsStream(fileName)));
			
			//Writes all of the lines in the high score list on to an arraylist
			String currentLine = "";
			while ((currentLine = input.readLine()) != null) {
				highscores.add(currentLine);
			}
			
			input.close();
		} catch(Exception Error) {
			System.out.println("File not found");
		}
		
		//Starts the music after everything is created and set up
		play();
	}
	
	public void play() {
		try {
			//Used to read the music file and create a player out of it.
			BufferedInputStream bis = new BufferedInputStream(this.getClass().getResourceAsStream(musicName));
			player = new Player(bis);
		} catch (Exception error) {
			System.out.println("Player not working");
			return;
		}

		//Create a thread for the music player to run in
		new Thread(){
			public void run() {
				try {
					player.play();
					//The player will play until it ends and then it will move on to close.
					player.close();
				} catch (Exception error) {
					System.out.println("File could not be played");
				}
			}
		}.start();
	}
	
	//A simple method used to find the position of the zero block in the arraylist
	public int zeroPos() {
		int posOfZero = 0;
		for(int i = 0; i < numbers.size(); i++)
			if(numbers.get(i).getValue() == 0)
				posOfZero = i;
		return posOfZero;
	}
	
	//Will return true if the passed position is located adjacent to the zero block; false otherwise.
	public boolean nextToZero(int pos) {
		boolean isNeighbor = false;
		int zero = zeroPos();
		//This is used to see if it is immediately adjacent.  Important to note that all this is required because
		//if you just say -1, +1, -boardSize, +boardSize it will not detect if it jumps from one side of the board to the other
		if(pos - boardType == zero || pos + boardType == zero || (pos - 1 == zero && pos % boardType != 0) || (pos + 1 == zero && pos % boardType != boardType - 1))
			isNeighbor = true;
		
		return isNeighbor;
	}
	
	//Sets up all the labels and jbuttons
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
		addComponent(gameTime, 45, gameFrame.getHeight() - 90, titleSize);
		
		moves.setSize(titleSize);
		moves.setText("Moves: " + numMoves);
		moves.setVisible(true);
		addComponent(moves, 45, gameFrame.getHeight() - 70, titleSize);
		
		quit.setSize(size);
		quit.setText("Quit");
		quit.setVisible(true);
		quit.addActionListener(this);
		quit.setFocusable(false);
		addComponent(quit, gameFrame.getWidth() - 290, gameFrame.getHeight() - 90, size);
		
		mute.setSize(size);
		mute.setText("Stop Music");
		mute.setVisible(true);
		mute.addActionListener(this);
		mute.setFocusable(false);
		addComponent(mute, gameFrame.getWidth() - 110, 10, new Dimension(100, 20));
		
		undo.setSize(size);
		undo.setText("Undo");
		undo.setVisible(true);
		undo.addActionListener(this);
		undo.setFocusable(false);
		addComponent(undo, gameFrame.getWidth() - 220, 10, new Dimension(100, 20));
		
		//We had a problem with the last jbutton added filled up the entire frame so we created a dummy button to solve this
		useless.setVisible(false);
		addComponent(useless, 0, 0, size);
	}

	//A simple method to add the component, set the size, and set the location.  Saves minimum 2 lines of code per button
	private static void addComponent(Component c, int x, int y, Dimension size){
		c.setBounds(new Rectangle(x, y, size.width, size.height));
		gamePanel.add(c);
	}
	
	//Method used to swap the position of the inputed value with the position of the null block
	public void swap(int pos) {
		if (pos >= 0 && pos < numbers.size()) {
			int tempZeroSpace = zeroPos();
			//Stores the move to be used later if "undo" is used
			previous = tempZeroSpace;
			Block tempZeroBlock = numbers.get(tempZeroSpace);
			Block tempSwappingBlock = numbers.get(pos);
			Point temp = tempSwappingBlock.getPos();
			tempSwappingBlock.setPos(tempZeroBlock.getPos());
			
			tempZeroBlock.setPos(temp);

			numbers.set(tempZeroSpace, numbers.get(pos));
			numbers.set(pos, tempZeroBlock);
		}
	}

	//Method is called whenever a button is clicked or the timer increments
	public void actionPerformed(ActionEvent e) {
		Object src = e.getSource();
		
		//Used to detect when the board is solved
		boolean stillGood = true;
		for(int i = 0; i < numbers.size() - 1; i++)
			if(numbers.get(i).getValue() != i + 1)
				stillGood = false;
		
		//When the board is solved it sets the game in to a gameOver state
		if(stillGood) {
			gameOver = true;
			gameFrame.repaint();
		}
		
		//Commands for each of the jbutton
		if(src == quit)
			System.exit(0);
		else if(src == mute)
			player.close();
		//As long as the game has started and the game has not ended you can undo
		else if(src == undo && started && !gameOver)
			swap(previous);
		//When the game is over the "name" jtextfield appears.  This section gets the name and writes it in to the
		//high score board if the score is faster than any of the previous ones.
		else if(src == name) {
			boolean notAdded = false;
			//Only reads the numbers as the names don't matter unless it is faster
			for(int i = 1; i < highscores.size(); i += 2) {
				if(!notAdded) {
					name.setEnabled(false);
					double currentTime = Double.parseDouble((time / 1000) + "." + (time / 100 % 10));
					double currentVal = Double.parseDouble(highscores.get(i));
					if (currentVal > currentTime) {
						//Writes the new score and name in to the high score board and then deletes the old ones
						highscores.add(i - 1, name.getText());
						highscores.add(i, "" + currentTime);
						highscores.remove(10);
						highscores.remove(10);
						gameFrame.repaint();
						notAdded = true;
					}
				}
			}
			//This is used to clear the previous high score list file and then write the new one
			try {
				output = new FileWriter(fileName);
				printOut = new PrintWriter(output);
				printOut.write("");
				
				for (int q = 0; q < highscores.size(); q++)
					output.write(highscores.get(q) + "\n");
				
				output.close();
				printOut.flush();
				printOut.close();
			} catch (Exception Error) {
				System.out.println("File not found");
			}
		}
		
		//Increments and prints the time if the game is still running
		if(gameOver == false  && started) {
			time += TIMER_SPEED;
			gameTime.setText("Time: " + (time / 1000) + "." + (time / 100 % 10));
		//Stops the timer and creates the name jtextfield
		} else if (gameOver) {
			timer.stop();
			
			name.setVisible(true);
			name.setText("Enter your name");
			name.addActionListener(this);
			addComponent(name, 80 + boardType * 60, gameFrame.getHeight() - 90, new Dimension(150, 25));
		}
		//Calls repaint in the GamePanel to draw everything
		gameFrame.repaint();
	}

	public void mouseClicked(MouseEvent e) {	
	}

	public void mousePressed(MouseEvent e) {	
	}

	//Used for mouse click detection
	public void mouseReleased(MouseEvent e) {
		//Starts the timer and game when the mouse clicks on a block for the first time
		started = true;
		if (!timer.isRunning())
			timer.start();
		//Gets the location of the mouse position
		Point mouseLoc = gameFrame.getMousePosition();
		boolean clickedAlready = false;
		//Detection and swapping of the blocks that are clicked on
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
								numMoves++;
								moves.setText("Moves: " + numMoves);
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
		//Starts the game is one of the arrow keys are pressed and the game hasnt started
		started = true;
		if (!timer.isRunning())
			timer.start();
		
		int key = e.getKeyCode();
		int zero = zeroPos();
		//Detects which arrow key is pressed then gets if it is neighboring the zero and then swaps it if it is
		if(!gameOver) {
			if (key == 37) {
				if (nextToZero(zero - 1)) {
					swap(zero - 1);
					numMoves++;
					moves.setText("Moves: " + numMoves);
				}
			} else if (key == 38) {
				if (nextToZero(zero - boardType)) {
					swap(zero - boardType);
					numMoves++;
					moves.setText("Moves: " + numMoves);
				}
			} else if (key == 39) {
				if (nextToZero(zero + 1)) {
					swap(zero + 1);
					numMoves++;
					moves.setText("Moves: " + numMoves);
				}
			} else if (key == 40) {
				if (nextToZero(zero + boardType)) {
					swap(zero + boardType);
					numMoves++;
					moves.setText("Moves: " + numMoves);
				}
			}
		}
		
		gameFrame.repaint();
	}

	public void keyReleased(KeyEvent e) {
	}
}
