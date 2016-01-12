import java.awt.Dimension;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class Info extends JPanel {
	final int TIMER_SPEED = 100;
	final int TIMER_DELAY = 0;
	
	public final Dimension blockSize = new Dimension(57, 57);

	public static int time = 0, boardType;

	public static JFrame gameFrame;
	
	public static Dimension boardSize;
	
	public static boolean gameOver = false, started = false;
	
	public static ArrayList<Block> numbers = new ArrayList<Block>();
	
	public static String high3 = "3x3High", high4 = "4x4High", high5 = "5x5High";
}
