
package entities;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import gamestates.Playing;
import main.Game;
import utilz.LoadSave;
import static utilz.Constants.SnakeConstants.*;

public class SnakesManager {

	private BufferedImage[][] SnakeArr= new BufferedImage[4][7];
	private BufferedImage[][] SpeSnakeArr= new BufferedImage[4][7];
	
	private ArrayList<Snake> Snakes = new ArrayList<>();

	private ArrayList<SpecialSnake> SpecialSnakes = new ArrayList<>();
	
	private int score=0;
	private int shakesNum=0;
	
	public SnakesManager(Playing playing) {
		
		loadSnakesImgs(SnakeArr,LoadSave.SNAKE_WALK_SPRITE,LoadSave.SNAKE_HURT_SPRITE);
		loadSnakesImgs(SpeSnakeArr,LoadSave.SPECIAL_SNAKE_WALK_SPRITE,LoadSave.SPECIAL_SNAKE_HURT_SPRITE);
		addSnakes();
	}

	
	private void addSnakes() {
		Snakes = LoadSave.GetSnakes();
		SpecialSnakes = LoadSave.GetSpecialSnakes();
		
	}

	public void update(int[][] mapData, Player player) {
		for (Snake s : Snakes)
			if (s.isActive()) {
				shakesNum++;
				s.update(mapData, player);
			}
		
		for (SpecialSnake s : SpecialSnakes)
			if (s.isActive()) {
				shakesNum++;
				s.update(mapData, player);
			}
		
		
		if(Snakes.size()+SpecialSnakes.size()!=shakesNum) 
			score=Snakes.size()+SpecialSnakes.size()-shakesNum;
		
		shakesNum=0;
	}

	public void draw(Graphics g, int xMapOffset) {
		drawSnakes(g, xMapOffset);
		g.setColor(Color.white);
        g.setFont(new Font("Arial", Font.BOLD, (int)(24*Game.SCALE)));
        if(score==0)
        	g.drawImage(SnakeArr[0][0], (int)(-5*Game.SCALE), (int)(0*Game.SCALE), SNAKE_WIDTH,SNAKE_HEIGHT, null);
        else
        	g.drawImage(SnakeArr[2][0], (int)(-5*Game.SCALE), (int)(0*Game.SCALE), SNAKE_WIDTH,SNAKE_HEIGHT, null);
        	
        g.drawString(": " + score, (int)(52*Game.SCALE),(int)(60*Game.SCALE));
	}

	private void drawSnakes(Graphics g, int xMapOffset) {
		for (Snake s : Snakes) 
			if (s.isActive())
				g.drawImage(SnakeArr[s.getSnakeState()][s.getAniIndex()], (int) s.getHitbox().x - xMapOffset - SNAKE_DRAWOFFSET_X+s.flipX(), (int) s.getHitbox().y+2 - SNAKE_DRAWOFFSET_Y, SNAKE_WIDTH*s.flipW(),
						SNAKE_HEIGHT, null);

		
		for (SpecialSnake s : SpecialSnakes) 
			if (s.isActive())
				g.drawImage(SpeSnakeArr[s.getSnakeState()][s.getAniIndex()], (int) s.getHitbox().x - xMapOffset - SNAKE_DRAWOFFSET_X+s.flipX(), (int) s.getHitbox().y+2 - SNAKE_DRAWOFFSET_Y, SNAKE_WIDTH*s.flipW(),
						SNAKE_HEIGHT, null);


	}

	public void checkSnakeHit(Rectangle2D.Float attackBox) {
		for (Snake s : Snakes)
			if (s.isActive())
				if (attackBox.intersects(s.getHitbox())) {
					s.hurt(5);
					return;
				}
		
		for (SpecialSnake s : SpecialSnakes)
			if (s.isActive())
				if (attackBox.intersects(s.getHitbox())) {
					s.hurt(5);
					return;
				}
		
		
	}
	
	private void loadSnakesImgs(BufferedImage[][] arr,String walk,String hurt) {
		
		
		BufferedImage temp = LoadSave.GetSpriteAtlas(walk);
		
		int walkCnt=GetSnakeSpriteAmount(WALKING);
		for (int i = 0; i < walkCnt; i++)
			arr[WALKING][i] = temp.getSubimage(i * SNAKE_WIDTH_DEFAULT, 0, SNAKE_WIDTH_DEFAULT, SNAKE_HEIGHT_DEFAULT);
				
		
		int attackCnt=GetSnakeSpriteAmount(ATTACK);
		for (int i = 0; i < attackCnt; i++)
			arr[ATTACK][i] = temp.getSubimage((i+3) * SNAKE_WIDTH_DEFAULT, 0, SNAKE_WIDTH_DEFAULT, SNAKE_HEIGHT_DEFAULT);
		
		temp = LoadSave.GetSpriteAtlas(hurt);
		
		int hitCnt=GetSnakeSpriteAmount(HIT);	
		for (int i = 0; i < hitCnt; i++)
			arr[HIT][i] = temp.getSubimage(i * SNAKE_WIDTH_DEFAULT, 0, SNAKE_WIDTH_DEFAULT, SNAKE_HEIGHT_DEFAULT);
	

		int deadCnt=GetSnakeSpriteAmount(DEAD);
		for (int i = 0; i < deadCnt; i++)
			arr[DEAD][i]= LoadSave.GetSpriteAtlas(LoadSave.ENEMY_DEATH_SPRITE[i]);
			
		
	}

	public void resetAllSnakes() {
		for (Snake s : Snakes)
			s.reset();
		for (SpecialSnake s : SpecialSnakes)
			s.reset();
		score=0;
	}
	public boolean checkspeciaDied() {
		
		for (SpecialSnake s : SpecialSnakes)
			if(s.active==false&&s.getUsed()==false) {
				s.setUsed();
				return true;
			}
		
		return false;
	}
	
	public int getScore() {
		return score;
	}
}
