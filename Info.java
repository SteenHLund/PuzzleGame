import java.awt.Dimension;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class Info extends JPanel {
	final int TIMER_SPEED = 100;
	final int TIMER_DELAY = 0;

	public static int time = 0, boardType;

	public static JFrame gameFrame;
	
	public static Dimension boardSize;
	
	public static boolean gameOver = false, started = false;
	
	public static ArrayList<Integer> numbers = new ArrayList<Integer>();
}