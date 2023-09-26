package ui;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import utilz.LoadSave;



public class SoundButton extends Button{
	
	private BufferedImage[][] soundImgs;
	private boolean mouseOver,mousePressed;
	private boolean muted;
	private int rowIndex,colIndex;
	
	public  SoundButton(int x,int y,int width,int height) {
		
		super(x,y,width,height);
		loadSoundImgs();
		
	}

	
	private void loadSoundImgs() {
		
		soundImgs =new BufferedImage[2][2];
		BufferedImage[] soundOpenImg = new BufferedImage[2];
		
		for(int i=0;i<soundOpenImg.length;i++) {
			soundOpenImg[i]=LoadSave.GetSpriteAtlas(LoadSave.SOUND_BUTTONS[i]);
			soundImgs[0][i] = soundOpenImg[i];
		}
		
		BufferedImage[] soundOffImg = new BufferedImage[2];
		
		for(int i=0;i<soundOffImg.length;i++) {
			soundOffImg[i]=LoadSave.GetSpriteAtlas(LoadSave.SOUND_OFF_BUTTONS[i]);
			soundImgs[1][i] = soundOffImg[i];
		}
	
	}
	public void update() {
		
		if(muted)
			rowIndex=1;
		else
			rowIndex=0;
		
		colIndex=0;
		if(mouseOver||mousePressed)
			colIndex=1;

		
		
		
	}
	public void resetBools() {
		
		mouseOver=false;
		mousePressed=false;
		
	}
	
	
	
	public void draw(Graphics g) {
		g.drawImage(soundImgs[rowIndex][colIndex], x, y, width,height,null);
		
		
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

	public boolean isMuted() {
		return muted;
	}

	public void setMuted(boolean muted) {
		this.muted = muted;
		
	}
	
	
	
}
