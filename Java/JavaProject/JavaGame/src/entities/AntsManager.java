package entities;

import static utilz.Constants.AntConstants.*;
import static utilz.Constants.SnakeConstants.SNAKE_HEIGHT;
import static utilz.Constants.SnakeConstants.SNAKE_WIDTH;

import java.awt.Graphics;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import gamestates.Playing;
import main.Game;
import utilz.LoadSave;

public class AntsManager {

	private BufferedImage[][] AntsArr= new BufferedImage[2][8];
	private BufferedImage Acorn;
	private int score=0;
	private int antsNum=0;

	
	private ArrayList<Ant> Ants = new ArrayList<>();
	public AntsManager(Playing playing) {
		
		loadAntsImgs();
		
		addAnts();
	}

	
	private void addAnts() {
		Ants = LoadSave.GetAnts();
		
		
	}

	public void update(int[][] mapData, Player player) {
		for (Ant a : Ants)
			if (a.isActive()) {
				antsNum++;
				a.update(mapData, player);
			}
		score=antsNum;
		antsNum=0;
	}

	public void draw(Graphics g, int xMapOffset) {
		drawAnts(g, xMapOffset);
		if(score>0) {
			g.drawImage(AntsArr[0][0], (int)(-5*Game.SCALE), (int)(50*Game.SCALE), SNAKE_WIDTH-5,SNAKE_HEIGHT-5, null);
			g.drawString(": " + score, (int)(52*Game.SCALE),(int)(105*Game.SCALE));
		}
		else
			g.drawImage(Acorn, (int)(8*Game.SCALE), (int)(63*Game.SCALE), 32,40, null);

		
	}

	private void drawAnts(Graphics g, int xMapOffset) {
		for (Ant a : Ants)
			if (a.isActive())
				g.drawImage(AntsArr[a.getSnakeState()][a.getAniIndex()], (int) a.getHitbox().x - xMapOffset - ANT_DRAWOFFSET_X+a.flipX(), (int) a.getHitbox().y+2 - ANT_DRAWOFFSET_Y, ANT_WIDTH*a.flipW(),
						ANT_HEIGHT, null);

	}

	public void checkAntHit(Rectangle2D.Float attackBox) {
		for (Ant a : Ants)
			if (a.isActive())
				if (attackBox.intersects(a.getHitbox())) {
					a.hurt(5);
					return;
				}
	}
	
	private void loadAntsImgs() {
		
		
		int walkCnt=GetSnakeSpriteAmount(WALKING);
		for (int i = 0; i < walkCnt; i++)
			AntsArr[WALKING][i]= LoadSave.GetSpriteAtlas(LoadSave.ANT_WALK_SPRITE[i]);
			

		int deadCnt=GetSnakeSpriteAmount(DEAD);
		for (int i = 0; i < deadCnt; i++)
			AntsArr[DEAD][i]= LoadSave.GetSpriteAtlas(LoadSave.ENEMY_DEATH_SPRITE[i]);
		
		
		Acorn= LoadSave.GetSpriteAtlas(LoadSave.PLAYER_ACORN);
		
	}

	public void resetAllAnts() {
		for (Ant a : Ants)
			a.reset();


	}
	public boolean getScoreEqualZero() {
		return score==0;
	}
	
	
	
}
