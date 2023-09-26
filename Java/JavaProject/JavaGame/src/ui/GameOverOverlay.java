package ui;

import static utilz.Constants.UI.URMButtons.URM_SIZE;

import java.awt.Graphics;
import java.awt.event.MouseEvent;

import audio.AudioPlayer;
import gamestates.Gamestate;
import gamestates.Playing;
import main.Game;
import utilz.LoadSave;

public class GameOverOverlay {

	private Playing playing;
	private ButtonBackground buttonBackground;

	private UrmButton menuB,replayB;
	
	
	public GameOverOverlay(Playing playing) {
		this.playing = playing;
		
		
		buttonBackground=new ButtonBackground(LoadSave.LOSE_BACKGROUND,LoadSave.LOSE_BACKGROUND2);
		createURMButtons();
	}
	private void createURMButtons() {
		int menuX=(int)(330*Game.SCALE);
		int replayX=(int)(442*Game.SCALE);
		int bY=(int)(200*Game.SCALE);
		
		menuB=new UrmButton(menuX,bY,URM_SIZE,URM_SIZE,2);
		replayB=new UrmButton(replayX,bY,URM_SIZE,URM_SIZE,1);	
	}

	public void update() {
		
		menuB.update();
		replayB.update();
		

	}
	
	public void draw(Graphics g) {
		buttonBackground.draw(g);
		menuB.draw(g);
		replayB.draw(g);

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

