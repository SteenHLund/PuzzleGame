import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class StartMenu implements ActionListener {
	public static JFrame startFrame;
	private static JButton start = new JButton(), quit = new JButton(), sq4 = new JButton(), sq3 = new JButton(), sq5 = new JButton(), test = new JButton();
	private static JLabel title = new JLabel();
	
	public StartMenu() {
		startFrame = new JFrame();
		startFrame.setSize(300, 225);
		startFrame.setLocationRelativeTo(null);
		startFrame.setResizable(false);
		startFrame.setDefaultCloseOperation(startFrame.EXIT_ON_CLOSE);
		startFrame.setVisible(true);
		startFrame.setTitle("Sliding Puzzle Ultra");
		addStartButtons();
	}
	
	private void addStartButtons() {
		Dimension size = new Dimension(200, 50);
		
		title.setText("Slider Puzzle Ultra!");
		title.setSize(size);
		title.setVisible(true);
		addComponent(title, 90, 10);
		
		start.setSize(size);
		start.setText("Start");
		start.setVisible(true);
		start.addActionListener(this);
		addComponent(start, 50, 75);
		
		quit.setSize(size);
		quit.setText("Quit");
		quit.setVisible(true);
		quit.addActionListener(this);
		addComponent(quit, 50, 130);
	}
	
	private void switchToSizeButtons() {
		Dimension size2 = new Dimension(200, 50);
		startFrame.setSize(300, 275);
		
		quit.setEnabled(false);
		quit.setVisible(false);
		
		start.setEnabled(false);
		start.setVisible(false);
		
		sq3.setSize(size2);
		sq3.setText("Easy - 3x3");
		sq3.setVisible(true);
		sq3.addActionListener(this);
		addComponent(sq3, 50, 75);
		
		sq4.setSize(size2);
		sq4.setText("Medium - 4x4");
		sq4.setVisible(true);
		sq4.addActionListener(this);
		addComponent(sq4, 50, 130);
		
		sq5.setSize(size2);
		sq5.setText("Hard - 5x5");
		sq5.setVisible(true);
		sq5.addActionListener(this);
		addComponent(sq5, 50, 190);
		
		test.setVisible(false);
		addComponent(test, 0, 0);
	}
	
	private static void addComponent(Component c, int x, int y){
		c.setLocation(x, y);
		startFrame.add(c);
	}

	public void actionPerformed(ActionEvent e) {
		Object src = e.getSource();
		
		if(src == start)
			switchToSizeButtons();
		else if (src == quit)
			System.exit(0);
		else if (src == sq3) {
			startFrame.dispose();
			new Game(3);
		}
		else if (src == sq4) {
			startFrame.dispose();
			new Game(4);
		}
		else if (src == sq5) {
			startFrame.dispose();
			new Game(5);
		}
	}
}
