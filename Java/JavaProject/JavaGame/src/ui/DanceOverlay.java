package ui;





import java.awt.Graphics;
import java.awt.image.BufferedImage;
import gamestates.Playing;
import main.Game;
import utilz.LoadSave;

public class DanceOverlay {
	
	private BufferedImage[]animations;
	private BufferedImage enter;
	private int aniTick,aniIndex=0,aniSpeed=3;
	
	public DanceOverlay(Playing playing) {
		loadAnimations();
		
	
	}
	private void loadAnimations() {
		animations=new BufferedImage[5];
		
		for(int i=0;i<5;i++) 
			animations[i] = LoadSave.GetSpriteAtlas(LoadSave.PLAYER_DANCE[i]);
		
		enter= LoadSave.GetSpriteAtlas(LoadSave.PRESS_ENTER);
	
	}
	public void draw(Graphics g) {
		int x=(int)(250*Game.SCALE);
		int y=(int)(120*Game.SCALE);
		int w=(int)(400*Game.SCALE);
		int h=(int)(258*Game.SCALE);
		
		int x2=(int)(700*Game.SCALE);
		int y2=(int)(420*Game.SCALE);
		int w2=(int)(76*Game.SCALE);
		int h2=(int)(12*Game.SCALE);
		
		g.drawImage(animations[aniIndex],x , y, w, h, null);
		g.drawImage(enter,x2 , y2, w2, h2, null);
		updateAnimationTick();
		
	}	
	private void updateAnimationTick() {
		aniTick++;
		if (aniTick >= aniSpeed) {
			aniTick = 0;
			aniIndex++;
			if (aniIndex >= 5) {
				aniIndex = 0;

			}

		}

	}
	
	
	
}
