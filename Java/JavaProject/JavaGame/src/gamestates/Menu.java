package gamestates;


import static utilz.Constants.UI.SoundButtons.*;
import static utilz.Constants.UI.MenuButtons.*;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

import audio.AudioPlayer;
import ui.MenuButton;
import ui.SoundButton;
import utilz.LoadSave;
import main.Game;

public class Menu extends State implements Statemethods{
	
	private MenuButton[] buttons=new MenuButton[3];
	SoundButton musicButton;
	
	private BufferedImage backgroundImg,backgroundImg2;
	private int menuX,menuY,menuWidth,menuHeight;
	private int menuX2,menuY2,menuWidth2,menuHeight2;
	
	public Menu(Game game) {
		
		super(game);
		loadButtons();
		loadBackground();

	}
	


	private void loadBackground() {
		

		backgroundImg=LoadSave.GetSpriteAtlas(LoadSave.MENU_BACKGROUND);
		menuWidth=(int)(backgroundImg.getWidth()*Game.SCALE);
		menuHeight=(int)(backgroundImg.getHeight()*Game.SCALE);
		menuX=0;
		menuY=0;
		
		backgroundImg2=LoadSave.GetSpriteAtlas(LoadSave.MENU_BACKGROUND2);
		menuWidth2=(int)(backgroundImg2.getWidth()*Game.SCALE);
		menuHeight2=(int)(backgroundImg2.getHeight()*Game.SCALE);
		menuX2=Game.GAME_WIDTH/2-menuWidth2/2;
		menuY2=(int)(45*Game.SCALE);
		
	}


	private void loadButtons() {
		buttons[0]=new MenuButton(Game.GAME_WIDTH/2,(int)(150*Game.SCALE),Gamestate.PLAYING,LoadSave.MENU_BUTTONS_PLAY,MENU_B_WIDTH,MENU_B_HEIGHT);
		buttons[1]=new MenuButton(Game.GAME_WIDTH/2,(int)(220*Game.SCALE),Gamestate.TIPS,LoadSave.MENU_BUTTONS_TIPS,MENU_B_WIDTH,MENU_B_HEIGHT);
		buttons[2]=new MenuButton(Game.GAME_WIDTH/2,(int)(290*Game.SCALE),Gamestate.QUIT,LoadSave.MENU_BUTTONS_QUIT,MENU_B_WIDTH,MENU_B_HEIGHT);
		musicButton=new SoundButton((int)((Game.GAME_WIDTH/2)-120*Game.SCALE),(int)(350*Game.SCALE),SOUND_B_SIZE,SOUND_B_SIZE);
	}


	public void update() {
		for(MenuButton mb:buttons)
			mb.update();
		musicButton.update();
	}

	public void draw(Graphics g) {
		g.drawImage(backgroundImg, menuX, menuY, menuWidth, menuHeight,null);
		g.drawImage(backgroundImg2, menuX2, menuY2, menuWidth2, menuHeight2,null);
		
		for(MenuButton mb:buttons)
			mb.draw(g);
		
		musicButton.draw(g);
	}

	public void mouseClicked(MouseEvent e) {

		
	}

	public void mousePressed(MouseEvent e) {

		for(MenuButton mb:buttons)
			if(isIn(e,mb)) {
				mb.setMousePressed(true);
				break;
				
		}
		
		if(musicButton.isIn(e))
			musicButton.setMousePressed(true);
			
		
		
	}

	public void mouseReleased(MouseEvent e) {
		for(MenuButton mb:buttons)
			if(isIn(e,mb)) {
				if(mb.isMousePressed())
					mb.applyGamestate();
				if (mb.getState() == Gamestate.PLAYING)
					game.getAudioPlayer().playSong(AudioPlayer.MAP);
				break;
				
		}
		
		if(musicButton.isIn(e)) 
			if(musicButton.isMousePressed())
			{
				musicButton.setMuted(!musicButton.isMuted());
				if(musicButton.isMuted())
					game.getAudioPlayer().stopSong();
				else 
					game.getAudioPlayer().playSong(AudioPlayer.MENU);
				
			}
		
		
		resetButtons();
	}

	private void resetButtons() {
		for(MenuButton mb:buttons)
			mb.resetBools();
		musicButton.resetBools();
	}


	public void mouseMoved(MouseEvent e) {
		for(MenuButton mb:buttons)
			mb.setMouseOver(false);
		
		musicButton.setMouseOver(false);
		
		for(MenuButton mb:buttons)
			if(isIn(e,mb)) {
				mb.setMouseOver(true);
				break;
			}
		if(musicButton.isIn(e))
			musicButton.setMouseOver(true);
	}

	public void keyPressed(KeyEvent e) {
		
	
	}


	public void keyReleased(KeyEvent e) {

		
	}

	
	
}
