import java.awt.Dimension;
import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class Info extends JPanel {
	private static final long serialVersionUID = 1L;
	
	final int TIMER_SPEED = 100;
	final int TIMER_DELAY = 0;
	
	public final Dimension blockSize = new Dimension(57, 57);

	public static int time = 0, boardType;
	
	public BufferedReader input;
	public FileWriter output;
	public PrintWriter printOut;

	public static JFrame gameFrame;
	
	public static Dimension boardSize;
	
	public static boolean gameOver = false, started = false;
	
	public static ArrayList<Block> numbers = new ArrayList<Block>();
	public static ArrayList<String> highscores = new ArrayList<String>();
	
	public static String fileName = "";
}
