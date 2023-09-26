package ui;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;


import main.Game;
import utilz.LoadSave;

public class EnemyInforOverlay {
	
	private BufferedImage backgroundImg;
	private int menuX,menuY,menuWidth,menuHeight;
	public EnemyInforOverlay() {
	
		
		loadBackground();
	}
	private void loadBackground() {
		

		backgroundImg=LoadSave.GetSpriteAtlas(LoadSave.ENEMY_INFOR_BACKGROUND);
		menuWidth=(int)(backgroundImg.getWidth()*Game.SCALE);
		menuHeight=(int)(backgroundImg.getHeight()*Game.SCALE);
		menuX=(int)(Game.GAME_WIDTH/2-200*Game.SCALE);
		menuY=(int)(45*Game.SCALE+50*Game.SCALE);
	}

	public void draw(Graphics g) {
		
		g.drawImage(backgroundImg, menuX, menuY, menuWidth, menuHeight,null);
		g.setColor(Color.black);
		Font chineseFont = new Font("宋体", Font.PLAIN, 18);
		g.setFont(chineseFont);
		g.drawString("一般蛇，無特殊功能", (int)( Game.GAME_WIDTH / 2-120*Game.SCALE), (int)(165*Game.SCALE));
		g.drawString("特殊蛇，打死後回血，攻擊較高", (int)( Game.GAME_WIDTH / 2-120*Game.SCALE), (int)(223*Game.SCALE));
		g.drawString("螞蟻，不會攻擊玩家但清除完有彩蛋", (int)( Game.GAME_WIDTH / 2-120*Game.SCALE), (int)(280*Game.SCALE));
		g.drawString("按enter繼續遊戲，再次查看按I", (int)( Game.GAME_WIDTH / 2-160*Game.SCALE), (int)(310*Game.SCALE));
		
	}
	

	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
