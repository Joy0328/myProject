package gamestates;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.geom.Rectangle2D;


import entities.SnakesManager;
import entities.AntsManager;
import entities.BigRock;
import entities.Player;
import map.Map;
import main.Game;
import ui.DanceOverlay;
import ui.EnemyInforOverlay;
import ui.GameOverOverlay;
import ui.GameWinOverlay;
import ui.PauseOverlay;

import utilz.LoadSave;


public class Playing extends State implements Statemethods {
	
	private Player player;

	private Map map;

	private SnakesManager snakesManager;
	private AntsManager antsManager;
	private BigRock bigRock;
	private PauseOverlay pauseOverlay;
	private GameOverOverlay gameOverOverlay;
	private GameWinOverlay gameWinOverlay;
	private EnemyInforOverlay enemyInforOverlay;
	private DanceOverlay danceOverlay;
	
	
	private boolean paused = false;

	private int xMapOffset;

	private int leftBorder = (int) (0.2 * Game.GAME_WIDTH);
	private int rightBorder = (int) (0.8 * Game.GAME_WIDTH);
	private int maxTilesWide = LoadSave.GetMap()[0].length;
	private int maxTilesOffset = maxTilesWide - Game.TILES_IN_WIDTH;
	private int maxMapOffsetX = maxTilesOffset * Game.TILES_SIZE;


	private boolean gameWin;
	private boolean gameOver;
	private boolean monsterInfor;
	private boolean bonus;
	
	public Playing(Game game) {
		super(game);
		initClasses();
		monsterInfor=true;
		bonus=false;
	}

	private void initClasses() {
		map = new Map(game);
		snakesManager = new SnakesManager(this);
		antsManager= new AntsManager(this);
		player = new Player(100, 0, (int) (64 * Game.SCALE), (int) (40 * Game.SCALE), this);
		bigRock=new BigRock();
		player.loadMapData(map.getMapData());
		pauseOverlay = new PauseOverlay(this);
		gameOverOverlay = new GameOverOverlay(this);
		gameWinOverlay= new GameWinOverlay(this);
		enemyInforOverlay= new EnemyInforOverlay();
		danceOverlay= new DanceOverlay(this);
		
	}

	@Override
	public void update() {

		
		if(snakesManager.checkspeciaDied()) 
			player.recoverHealth();
		
		
		if (!paused && !gameOver &&!gameWin) {
			player.update(xMapOffset);
			snakesManager.update(map.getMapData(), player);
			antsManager.update(map.getMapData(), player);
			checkCloseToBorder();
			
		} 
		else if(gameOver) 
			gameOverOverlay.update();
		else if(gameWin&&!bonus) 
			gameWinOverlay.update();
		else if(paused) 
			pauseOverlay.update();


		

	}

	private void checkCloseToBorder() {
		int playerX = (int) player.getHitbox().x;

		int diff = playerX - xMapOffset;

		if (diff > rightBorder)
			xMapOffset += diff - rightBorder;
		else if (diff < leftBorder)
			xMapOffset += diff - leftBorder;

		if (xMapOffset > maxMapOffsetX)
			xMapOffset = maxMapOffsetX;
		else if (xMapOffset < 0)
			xMapOffset = 0;
	}

	@Override
	public void draw(Graphics g) {
		
		
		
		map.draw(g, xMapOffset);
		player.draw(g, xMapOffset);
		snakesManager.draw(g, xMapOffset);
		antsManager.draw(g, xMapOffset);
		bigRock.draw(g, xMapOffset);
		if(monsterInfor) {
			g.setColor(new Color(0, 0, 0, 150));
			g.fillRect(0, 0, Game.GAME_WIDTH, Game.GAME_HEIGHT);
			enemyInforOverlay.draw(g);
			//danceOverlay.draw(g);	
			//gameWinOverlay.draw(g);
		}
		else if (paused) {
			g.setColor(new Color(0, 0, 0, 150));
			g.fillRect(0, 0, Game.GAME_WIDTH, Game.GAME_HEIGHT);
			pauseOverlay.draw(g);
		} 
		else if (gameOver) {
			g.setColor(new Color(0, 0, 0, 150));
			g.fillRect(0, 0, Game.GAME_WIDTH, Game.GAME_HEIGHT);
			gameOverOverlay.draw(g);
		}
			
		else if (gameWin){
			g.setColor(new Color(0, 0, 0, 150));
			g.fillRect(0, 0, Game.GAME_WIDTH, Game.GAME_HEIGHT);
			
			if(bonus) 
				danceOverlay.draw(g);	
			else 
				gameWinOverlay.draw(g);
			
		}
		
			
			
		
	}

	
	public void resetAll() {
		gameWin = false;
		gameOver = false;
		paused = false;
		bonus=false;
		player.resetAll();
		snakesManager.resetAllSnakes();
		antsManager.resetAllAnts();
		bigRock.reset();

	}

	public void setGameOver(boolean gameOver) {
		this.gameOver = gameOver;
	}
	public void setGameWin(boolean gameWin) {
		this.gameWin = gameWin;
	}
	
	
	public void checkEnemySnakeHit(Rectangle2D.Float attackBox) {
		snakesManager.checkSnakeHit(attackBox);
		antsManager.checkAntHit(attackBox);
	}


	@Override
	public void mouseClicked(MouseEvent e) {
		if (!gameOver&&!gameWin)
			if (e.getButton() == MouseEvent.BUTTON1)
				player.setAttacking(true);
	}

	@Override
	public void keyPressed(KeyEvent e)
	{
			if(monsterInfor) {
				switch (e.getKeyCode()) {
					case KeyEvent.VK_ENTER:
						monsterInfor = false;
						break;		
				}
			}
			else {
				switch (e.getKeyCode()) {
				case KeyEvent.VK_A:
					player.setLeft(true);
					break;
				case KeyEvent.VK_D:
					player.setRight(true);
					break;
				case KeyEvent.VK_SPACE:
					player.setJump(true);
					break;
				case KeyEvent.VK_ESCAPE:
					paused = !paused;
					break;
				
				case KeyEvent.VK_I:
					monsterInfor = true;
					break;
				case KeyEvent.VK_ENTER:
					bonus = false;
					break;
					
				}
				
			}
	}

	@Override
	public void keyReleased(KeyEvent e) {
	
		if (!gameOver&&!gameWin)
			switch (e.getKeyCode()) {
			case KeyEvent.VK_A:
				player.setLeft(false);
				break;
			case KeyEvent.VK_D:
				player.setRight(false);
				break;
			case KeyEvent.VK_SPACE:
				player.setJump(false);
				break;
			}

	}
	


	@Override
	public void mousePressed(MouseEvent e) {
		if (gameOver)
			gameOverOverlay.mousePressed(e);
		else if (gameWin)
			gameWinOverlay.mousePressed(e);
		else if (paused)
			pauseOverlay.mousePressed(e);
		else
			bigRock.mousePressed(e);
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		if (gameOver)
			gameOverOverlay.mouseReleased(e);
		else if (gameWin)
			gameWinOverlay.mouseReleased(e);
		else if (paused)
				pauseOverlay.mouseReleased(e);
		else
			bigRock.mouseReleased(e);
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		
		
		if (gameOver)
			gameOverOverlay.mouseMoved(e);
		else if (gameWin)
			gameWinOverlay.mouseMoved(e);
		else if (paused)
			pauseOverlay.mouseMoved(e);
	}
	public void mouseDragged(MouseEvent e) {
		if (!paused&&!monsterInfor)
			bigRock.mouseDragged(e);


    }

	public int getScore() {
		
		return snakesManager.getScore();
	}
	public void unpauseGame() {
		paused = false;
	}


	public Player getPlayer() {
		return player;
	}


	public boolean isMuted() {
		return pauseOverlay.getSoundButton().isMuted();
	}
	public void resetMuted() {
		pauseOverlay.getSoundButton().setMuted(false);
	}

	public AntsManager getAntsManager() {
		
		return antsManager;
	}
	public BigRock getSnorlax() {
		
		return bigRock;
	}
	public void setBonus() {
		bonus=true;
	}
	
}