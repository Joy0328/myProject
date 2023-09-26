package ui;

import java.awt.Rectangle;
import java.awt.event.MouseEvent;

public class Button {
	
	protected int x,y,width,height;
	
	protected Rectangle bounds;
	
	public Button(int x,int y,int width,int height) {
		
		this.x=x;
		this.y=y;
		this.width=width;
		this.height=height;
		createBounds();
		
	}

	private void createBounds() {
		bounds=new Rectangle(x,y,width,height);
	
		
	}

	public Rectangle getBounds() {
		return bounds;
	}


	
	public boolean isIn(MouseEvent e) {
		
		return getBounds().contains(e.getX(),e.getY());

		
	}
	
	
	
	
}
