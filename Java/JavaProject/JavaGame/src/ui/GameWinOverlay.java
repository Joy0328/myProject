package ui;


import static utilz.Constants.UI.URMButtons.URM_SIZE;

import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

import audio.AudioPlayer;
import gamestates.Gamestate;
import gamestates.Playing;
import main.Game;
import utilz.LoadSave;

public class GameWinOverlay {
	private Playing playing;
	private ButtonBackground buttonBackground;
	private UrmButton menuB,replayB;
	private BufferedImage[]start;
	private int index=0,aniTick=0,aniSpeed=45,aniIndex;
	public GameWinOverlay(Playing playing) {
		this.playing = playing;
		
		
		buttonBackground=new ButtonBackground(LoadSave.WIN_BACKGROUND,LoadSave.WIN_BACKGROUND2);
		createURMButtons();
		loadStartImg();
	}
	private void loadStartImg() {
		start =new BufferedImage[4];
		for(int i=0;i<4;i++) 
			
			start[i] = LoadSave.GetSpriteAtlas(LoadSave.WIN_START[i]);

		
	}

	
	private void createURMButtons() {
		int menuX=(int)(330*Game.SCALE);
		int replayX=(int)(442*Game.SCALE);
		int bY=(int)(200*Game.SCALE);
		
		menuB=new UrmButton(menuX,bY,URM_SIZE,URM_SIZE,2);
		replayB=new UrmButton(replayX,bY,URM_SIZE,URM_SIZE,1);	
	}

	public void update() {

		setIndex();
		menuB.update();
		replayB.update();
		updateAnimationTick();

	}
	protected void updateAnimationTick() {
		aniTick++;
		if (aniTick >= aniSpeed) {
			aniTick = 0;
			aniIndex++;
			if (aniIndex>index) 
				aniIndex = index;

					
				
			}
		
	}
	
	public void draw(Graphics g) {
		
		buttonBackground.draw(g);
		menuB.draw(g);
		replayB.draw(g);
		
		g.drawImage(start[aniIndex], (int)(355*Game.SCALE), (int)(250*Game.SCALE), (int)(128*Game.SCALE), (int)(66*Game.SCALE), null);


	}	
	public void setIndex() {
		
		if(playing.getScore()<5)
			index=0;
		else if(playing.getScore()<8)
			index=1;
		
		else if(playing.getScore()<10)
			index=2;
		else 
			index=3;
		
	}	
	


	public void mousePressed(MouseEvent e) {

		if(menuB.isIn(e))
			menuB.setMousePressed(true);
		if(replayB.isIn(e))
			replayB.setMousePressed(true);

	}


	
	public void mouseReleased(MouseEvent e) {
		
		
		if(menuB.isIn(e))
			if(menuB.isMousePressed()) {
				playing.getGame().getAudioPlayer().playSong(AudioPlayer.MENU);
				Gamestate.state=Gamestate.MENU;
				playing.unpauseGame();
				playing.resetAll();
				playing.resetMuted();
			}
		
		if(replayB.isIn(e))
			if(replayB.isMousePressed())
			{
				if(!playing.isMuted())
					playing.getGame().getAudioPlayer().playSong(AudioPlayer.MAP);
				playing.resetAll();
				playing.unpauseGame();
				playing.resetAll();
				
			}

		menuB.resetBools();
		replayB.resetBools();
		
	}


	public void mouseMoved(MouseEvent e) {

		menuB.setMouseOver(false);
		replayB.setMouseOver(false);
		
		if(menuB.isIn(e))
			menuB.setMouseOver(true);
		if(replayB.isIn(e))
			replayB.setMouseOver(true);
		
		
	}
	

	
	
}
