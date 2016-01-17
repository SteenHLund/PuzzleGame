import java.awt.Dimension;
import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import javax.swing.JFrame;
import javax.swing.JPanel;

//A class used entirely for the purpose of sharing variables between the classes
public class Info extends JPanel {
	private static final long serialVersionUID = 1L;
	
	final int TIMER_SPEED = 100;
	final int TIMER_DELAY = 0;
	
	public final Dimension blockSize = new Dimension(57, 57);

	public static int time = 0, boardType, previous = 8, numMoves = 0;
	
	public BufferedReader input;
	public FileWriter output;
	public PrintWriter printOut;

	public static JFrame gameFrame;
	
	public static Dimension boardSize;
	
	public static boolean gameOver = false, started = false;
	
	public static ArrayList<Block> numbers = new ArrayList<Block>();
	public static ArrayList<String> highscores = new ArrayList<String>();
	
	//The song below is not owned by us and is used simply as a proof of concept that it works.  It would never
	//be included in anything not used for educational purposes in a classroom
	public static String fileName = "", musicName = "Was Not Was - Walk The Dinosaur.mp3";
}
