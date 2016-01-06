import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class StartMenu implements ActionListener {
	public static JFrame startFrame;
	private static JButton start = new JButton(), quit = new JButton();
	private static JLabel title = new JLabel();
	
	public void createStartMenu() {
		startFrame = new JFrame();
		startFrame.setSize(300, 225);
		startFrame.setLocationRelativeTo(null);
		startFrame.setResizable(false);
		startFrame.setDefaultCloseOperation(startFrame.EXIT_ON_CLOSE);
		startFrame.setVisible(true);
		addStartButtons();
	}
	
	private void addStartButtons() {
		Dimension size = new Dimension(200, 50);
		
		title.setText("Slider Puzzle Ultra!");
		title.setSize(size);
		title.setVisible(true);
		title.setLocation(90, 10);
		startFrame.add(title);
		
		start.setSize(size);
		start.setText("Start");
		start.setVisible(true);
		start.setLocation(50, 75);
		start.addActionListener(this);
		startFrame.add(start);
		
		quit.setSize(size);
		quit.setText("Quit");
		quit.setVisible(true);
		quit.setLocation(50, 130);
		quit.addActionListener(this);
		startFrame.add(quit);
	}
	
	private void switchToSizeButtons() {
		
	}

	public void actionPerformed(ActionEvent e) {
		Object src = e.getSource();
		
		if(src == start) {
			switchToSizeButtons();
		} else if (src == quit) {
			System.exit(0);
		}
	}
}
