package ui;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import static utilz.Constants.UI.URMButtons.*;
import utilz.LoadSave;

public class UrmButton extends Button {
	
	private BufferedImage[] imgs;
	private int rowIndex,index;
	private boolean mouseOver,mousePressed;
	
	public UrmButton(int x, int y, int width, int height,int rowIndex) {
		super(x, y, width, height);
		this.rowIndex=rowIndex;
		loadImgs();
	}
	
	private void loadImgs() {
		imgs=new BufferedImage[2];
		
		String[] path=new String[2];
		
		if(rowIndex==0) {
			path=LoadSave.PLAY_BUTTONS;
			
		}
		else if(rowIndex==1) {
			path=LoadSave.RESTART_BUTTONS;
			
		}
		else if(rowIndex==2) {
			path=LoadSave.MENU_BUTTONS;
			
		}
		

		for(int i=0;i<imgs.length;i++)
			imgs[i]=LoadSave.GetSpriteAtlas(path[i]);
		
		
	
	}



	public void update() {
		
		index=0;
		if(mouseOver||mousePressed)
			index=1;

		
	}
	public void draw(Graphics g) {
		g.drawImage(imgs[index], x, y, URM_SIZE, URM_SIZE, null);
		
	}
	public void resetBools() {
		
		mouseOver=false;
		mousePressed=false;
		
	}



	public boolean isMouseOver() {
		return mouseOver;
	}



	public void setMouseOver(boolean mouseOver) {
		this.mouseOver = mouseOver;
	}



	public boolean isMousePressed() {
		return mousePressed;
	}



	public void setMousePressed(boolean mousePressed) {
		this.mousePressed = mousePressed;
	}
	
	
}
