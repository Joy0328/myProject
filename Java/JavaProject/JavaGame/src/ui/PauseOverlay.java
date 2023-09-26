package ui;


import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

import audio.AudioPlayer;
import gamestates.Gamestate;
import gamestates.Playing;
import main.Game;
import utilz.LoadSave;
import static utilz.Constants.UI.SoundButtons.*;
import static utilz.Constants.UI.URMButtons.*;


public class PauseOverlay {
	
	private Playing playing;
	private ButtonBackground buttonBackground;
	private BufferedImage backgroundText;
	private SoundButton musicButton;
	private UrmButton menuB,replayB,unpauseB;

	
	public  PauseOverlay(Playing playing){
		this.playing=playing;
		
		buttonBackground=new ButtonBackground(LoadSave.PAUSE_BACKGROUND,LoadSave.PAUSE_BACKGROUND2);
		createSoundButtons();
		createURMButtons();
		backgroundText= LoadSave.GetSpriteAtlas(LoadSave.PAUSE_TEXT);	
		
	}
	
	private void createURMButtons() {
		int menuX=(int)(313*Game.SCALE);
		int replayX=(int)(387*Game.SCALE);
		int unpauseX=(int)(462*Game.SCALE);
		int bY=(int)(180*Game.SCALE);
		
		menuB=new UrmButton(menuX,bY,URM_SIZE,URM_SIZE,2);
		replayB=new UrmButton(replayX,bY,URM_SIZE,URM_SIZE,1);
		unpauseB=new UrmButton(unpauseX,bY,URM_SIZE,URM_SIZE,0);
		
		
	}


	private void createSoundButtons() {
		int soundX=(int)(490*Game.SCALE);
		int soundY=(int)(260*Game.SCALE);
		musicButton=new SoundButton(soundX,soundY,SOUND_B_SIZE,SOUND_B_SIZE);
	
	}


	public void update() {
		musicButton.update();
		menuB.update();
		replayB.update();
		unpauseB.update();

	}
	
	public void draw(Graphics g) {
		buttonBackground.draw(g);
		musicButton.draw(g);
		menuB.draw(g);
		replayB.draw(g);
		unpauseB.draw(g);
		g.drawImage(backgroundText, (int)(320*Game.SCALE), (int)(250*Game.SCALE),(int)(128*Game.SCALE),(int)(46*Game.SCALE),null );
	}
	
	
	public void mousePressed(MouseEvent e) {
		if(musicButton.isIn(e))
			musicButton.setMousePressed(true);
		if(menuB.isIn(e))
			menuB.setMousePressed(true);
		if(replayB.isIn(e))
			replayB.setMousePressed(true);
		if(unpauseB.isIn(e))
			unpauseB.setMousePressed(true);
	}


	public void mouseReleased(MouseEvent e) {
		
		if(musicButton.isIn(e)) 
			if(musicButton.isMousePressed())
			{
				musicButton.setMuted(!musicButton.isMuted());
				if(musicButton.isMuted())
					playing.getGame().getAudioPlayer().stopSong();
				else 
					playing.getGame().getAudioPlayer().playSong(AudioPlayer.MAP);
				
			}

		
		if(menuB.isIn(e))
			if(menuB.isMousePressed()) {
				
				Gamestate.state=Gamestate.MENU;
				playing.getGame().getAudioPlayer().playSong(AudioPlayer.MENU);
				musicButton.setMuted(false);		
				playing.resetAll();
				
			}
		
		if(replayB.isIn(e))
			if(replayB.isMousePressed())
			{
				if(!musicButton.isMuted())
					playing.getGame().getAudioPlayer().playSong(AudioPlayer.MAP);
				playing.resetAll();
		
			}

		if(unpauseB.isIn(e))
			if(unpauseB.isMousePressed())
				playing.unpauseGame();
		
		
		
		musicButton.resetBools();
		menuB.resetBools();
		replayB.resetBools();
		unpauseB.resetBools();
		
	}


	public void mouseMoved(MouseEvent e) {
		musicButton.setMouseOver(false);

		menuB.setMouseOver(false);
		replayB.setMouseOver(false);
		unpauseB.setMouseOver(false);

		
		if(musicButton.isIn(e))
			musicButton.setMouseOver(true);

		
		if(menuB.isIn(e))
			menuB.setMouseOver(true);
		if(replayB.isIn(e))
			replayB.setMouseOver(true);
		if(unpauseB.isIn(e))
			unpauseB.setMouseOver(true);
		
	}
	

	public SoundButton getSoundButton() {
		
		return musicButton;
	}

	
}
