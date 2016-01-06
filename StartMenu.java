import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class StartMenu implements ActionListener {
	public static JFrame startFrame;
	private static JButton start = new JButton(), quit = new JButton(), fifteen = new JButton(), nine = new JButton();
	private static JLabel title = new JLabel();
	
	public void createStartMenu() {
		startFrame = new JFrame();
		startFrame.setSize(300, 225);
		startFrame.setLocationRelativeTo(null);
		startFrame.setResizable(false);
		startFrame.setDefaultCloseOperation(startFrame.EXIT_ON_CLOSE);
		startFrame.setVisible(true);
		//startFrame.setLayout(new BoxLayout(startFrame.getContentPane(), BoxLayout.LINE_AXIS));
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
		
		quit.setEnabled(false);
		quit.setVisible(false);
		
		start.setEnabled(false);
		start.setVisible(false);
		
		nine.setSize(size2);
		nine.setText("Easy - 3x3");
		nine.setVisible(true);
		nine.addActionListener(this);
		addComponent(nine, 50, 75);
		
		fifteen.setSize(size2);
		fifteen.setText("Medium - 4x4");
		fifteen.setVisible(true);
		fifteen.addActionListener(this);
		addComponent(fifteen, 50, 130);
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
		else if (src == nine)
			System.exit(0);
		else if (src == fifteen)
			System.exit(0);
	}
}
