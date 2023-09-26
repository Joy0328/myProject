package main;

import java.awt.Graphics;
import audio.AudioPlayer;
import gamestates.Gamestate;
import gamestates.Menu;
import gamestates.Playing;
import gamestates.Tips;

public class Game implements Runnable{
	
	private GameWindow gamewindow;
	private GamePanel gamePanel;
	private Thread gameThread;
	private final int FPS_SET=120;
	private final int UPS_SET=190;

	private Playing playing;
	private Menu menu;
	private Tips tips;
	private AudioPlayer audioPlayer;
	
	public final static int TILES_DEFAULT_SIZE=32;
	public final static float SCALE=1.0f;
	
	public final static int TILES_IN_WIDTH=26;
	public final static int TILES_IN_HEIGHT=14;
	
	public final static int TILES_SIZE=(int)(TILES_DEFAULT_SIZE*SCALE);
	public final static int GAME_WIDTH=TILES_SIZE*TILES_IN_WIDTH;
	public final static int GAME_HEIGHT=TILES_SIZE*TILES_IN_HEIGHT;
	
	
	public Game(){
		
		initclasses();

		gamePanel=new GamePanel(this);
		gamewindow=new GameWindow(gamePanel);
		
		
		gamePanel.setFocusable(true);
		gamePanel.requestFocus();
		
		startGameLoop();
	}
	private void initclasses() {
		audioPlayer = new AudioPlayer();
		menu=new Menu(this);
		playing=new Playing(this);
		tips=new Tips(this);
	}
	public void startGameLoop() {
		gameThread=new Thread(this);
		gameThread.start();
		
	}
	
	private void update() {
		
		switch(Gamestate.state) {
		case MENU:
			menu.update();
			break;
		case PLAYING:
			playing.update();
			break;
		case TIPS:
			tips.update();
			break;
		case QUIT:
		default:
			System.exit(0);
			break;
		}
		
	}
	
	public void render(Graphics g) {

		switch(Gamestate.state) {
		case MENU:
			menu.draw(g);
			break;
		case PLAYING:
			playing.draw(g);
			break;
		case TIPS:
			tips.draw(g);
			break;	
		default:
			break;
		}
		
		
	}
	
	
	public void run() {
		
		double timePerFrame=1000000000.0/FPS_SET;
		double timePerUpdate=1000000000.0/UPS_SET;
		
		long previousTime=System.nanoTime();

		long lastCheck=System.currentTimeMillis();
		 
		double deltaU=0;
		double deltaF=0;
		
		while(true) {

			long currentTime=System.nanoTime();
			
			deltaU+=(currentTime-previousTime)/timePerUpdate;
			deltaF+=(currentTime-previousTime)/timePerFrame;
			previousTime=currentTime;
		
			if(deltaU>=1) {
				update();
				deltaU--;
			}
			
			
			if(deltaF>=1) {
				gamePanel.repaint();
				deltaF--;
				
			}
					
			if(System.currentTimeMillis()-lastCheck>=1000) {
				lastCheck=System.currentTimeMillis();

			}
			
		}
		
	}
	

	public void windowFocusLost() {
		if(Gamestate.state==Gamestate.PLAYING)
			playing.getPlayer().resetDirBooleans();
		
	}
	public Menu getMenu() {
		return menu;
	}
	
	public Playing getPlaying() {
		return playing;
	}
	public Tips getTips() {
		return tips;
	}
	public AudioPlayer getAudioPlayer() {
		return audioPlayer;
	}

}
