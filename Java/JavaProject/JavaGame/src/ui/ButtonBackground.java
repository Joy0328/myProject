package ui;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import main.Game;
import utilz.LoadSave;

public class ButtonBackground {
	
	protected int bgX,bgY,bgW,bgH,bg2X,bg2Y,bg2W,bg2H;
	protected BufferedImage backgroundImg,background2Img;
	
	ButtonBackground(String background1,String background2){
		
		
		backgroundImg=LoadSave.GetSpriteAtlas(background1);
		bgW=(int)(backgroundImg.getWidth()*Game.SCALE);
		bgH=(int)(backgroundImg.getHeight()*Game.SCALE);
		bgX=Game.GAME_WIDTH/2-bgW/2;
		bgY=(int)(120*Game.SCALE);
		
		background2Img=LoadSave.GetSpriteAtlas(background2);
		bg2W=(int)(background2Img.getWidth()*Game.SCALE);
		bg2H=(int)(background2Img.getHeight()*Game.SCALE);
		bg2X=Game.GAME_WIDTH/2-bg2W/2;
		bg2Y=(int)(60*Game.SCALE);
		
		
		
		
	}
	public void draw(Graphics g) {
		g.drawImage(backgroundImg, bgX, bgY,bgW,bgH,null );
		g.drawImage(background2Img, bg2X, bg2Y,bg2W,bg2H,null );
		
	}	
	
	
}
