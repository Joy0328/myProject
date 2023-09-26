package entities;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import static utilz.Constants.Environment.*;
import main.Game;
import utilz.LoadSave;


public class BigRock {

	private int x,y,width, height;
	private Rectangle bounds;
	private BufferedImage imgs;
	boolean isDragging=false;
	
	public BigRock() {
		x = (int)(220*Game.SCALE);
		y = (int)(276*Game.SCALE);
		width = (int)(118*Game.SCALE);
		height = (int)(113*Game.SCALE);
		

		bounds=new Rectangle(x,y,width,height);
		loadImg();
	}
	
	public void loadImg() {
		imgs = LoadSave.GetSpriteAtlas(LoadSave.BIGROCK_IMG);	
		
	}
	
	public void draw(Graphics g,int mapOffset) {

		bounds=new Rectangle(x-mapOffset,y,width,height);
		g.drawImage(imgs, x-mapOffset, y, BIGROCK_WIDTH_DEFAULT, BIGROCK_HEIGHT_DEFAULT, null);
	
	}

	
	
	public Rectangle getBounds() {
		return bounds;
	}

	 public void mousePressed(MouseEvent e) {
		 if(isIn(e))
			{
				isDragging=true;
				
			}
     }

     public void mouseReleased(MouseEvent e) {
       
         isDragging = false;
        
     }
	
	public void mouseDragged(MouseEvent e) {
		if(isDragging)
		{
				x = e.getX();
				y = e.getY();
				
		}

    }
	public boolean isIn(MouseEvent e) {
		
		return getBounds().contains(e.getX(),e.getY());

		
	}
	public int getX() {	
		return x;

	}
	public int getY() {
		
		return y;

		
	}
	public void reset() {

		x = (int)(220*Game.SCALE);
		y = (int)(276*Game.SCALE);



		bounds=new Rectangle(x,y,width,height);
	}
	
	
}
