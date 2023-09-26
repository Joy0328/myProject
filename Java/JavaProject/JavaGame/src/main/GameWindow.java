package main;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.WindowEvent;
import java.awt.event.WindowFocusListener;

import javax.swing.JFrame;

public class GameWindow {
	
	private JFrame jframe;
	
	public GameWindow(GamePanel gamePanel) {
		
		jframe=new JFrame();
		
		jframe.setTitle("HOME");
		jframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		jframe.add(gamePanel);
		jframe.setLocationRelativeTo(null);
		jframe.setResizable(false);
		jframe.pack();
		jframe.setVisible(true);
		jframe.addWindowFocusListener(new WindowFocusListener() {
		
			@Override
			public void windowGainedFocus(WindowEvent e) {
				gamePanel.getGame().windowFocusLost();
				
			}

			@Override
			public void windowLostFocus(WindowEvent e) {

				
			}
	});
		 Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		 int screenWidth = screenSize.width;
	     int screenHeight = screenSize.height;

	     int windowWidth = jframe.getWidth();
	     int windowHeight = jframe.getHeight();

	     int centerX = (screenWidth - windowWidth) / 2;
	     int centerY = (screenHeight - windowHeight) / 2;

	     jframe.setLocation(centerX, centerY);
		
		
		
	}

}