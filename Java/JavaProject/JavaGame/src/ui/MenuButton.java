package ui;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import gamestates.Gamestate;
import utilz.LoadSave;


public class MenuButton {
	private int xPos, yPos, index;
	private int xOffsetCenter ;
	private Gamestate state;
	private BufferedImage[] imgs;
	private boolean mouseOver, mousePressed;
	private Rectangle bounds;
	private int butW, butH;
	
	
	public MenuButton(int xPos, int yPos, Gamestate state,String[] strImg,int w,int h) {
		this.xPos = xPos;
		this.yPos = yPos;
		this.state = state;
		loadImgs(strImg);
		butW=w;
		butH=h;
		xOffsetCenter = butW / 2;
		initBounds();
	}

	private void initBounds() {
		bounds = new Rectangle(xPos - xOffsetCenter, yPos, butW, butH);

	}

	private void loadImgs(String[] strImg) {
		imgs = new BufferedImage[2];

		for (int i = 0; i < imgs.length; i++)
			imgs[i] = LoadSave.GetSpriteAtlas(strImg[i]);
		
		
		
	}

	public void draw(Graphics g) {
		g.drawImage(imgs[index], xPos - xOffsetCenter, yPos, butW, butH, null);
	}

	public void update() {
		index = 0;
		if (mouseOver||mousePressed)
			index = 1;

		
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

	public Rectangle getBounds() {
		return bounds;
	}

	public void applyGamestate() {
		Gamestate.state = state;
	}

	public void resetBools() {
		mouseOver = false;
		mousePressed = false;
	}
	public Gamestate getState() {
		return state;
		
		
	}

}
