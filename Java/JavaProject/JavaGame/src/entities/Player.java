package entities;

import static utilz.HelpMethods.*;
import static utilz.Constants.PlayerConstants.*;
import static utilz.Constants.Environment.*;
import utilz.LoadSave;
import java.awt.geom.Ellipse2D;
import audio.AudioPlayer;
import gamestates.Playing;
import main.Game;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;


public class Player extends Entity{
	
	private BufferedImage[][]animations;
	private int aniTick,aniIndex,aniSpeed=20;
	private int playerAction=IDLE;
	private boolean moving=false, attacking = false;
	private boolean left,right,jump;
	private float playerSpeed=1.0f*Game.SCALE;
	private int[][] mapData;
	private float xDrawOffset=21*Game.SCALE;
	private float yDrawOffset=4*Game.SCALE;
	

	private float airSpeed=0f;
	private float gravity=0.04f*Game.SCALE;
	private float jumpSpeed=-2.25f*Game.SCALE;;
	private float fallSpeedAfterCollision=0.5f*Game.SCALE;
	private boolean inAir=false;
	

	private BufferedImage lifeBarImg;
	private BufferedImage lifeBarImg2;//died
	private int statusBarWidth = (int) (192 * Game.SCALE);
	private int statusBarHeight = (int) (24 * Game.SCALE);
	private int statusBarX = (int) (10 * Game.SCALE);
	private int statusBarY = (int) (10 * Game.SCALE);

	private int healthBarWidth = (int) (170 * Game.SCALE);
	private int healthBarHeight = (int) (15 * Game.SCALE);
	private int healthBarXStart = (int) (2 * Game.SCALE);
	private int healthBarYStart = (int) (5 * Game.SCALE);


	private int currentHealth;
	int lastCurrentHealth;
	private int healthWidth = healthBarWidth;
	
	private int flipX = 0;
	private int flipW = 1;

	private boolean attackChecked;
	private Playing playing;
	

	
	
	public Player(float x, float y, int width, int height, Playing playing) {
		super(x, y, width, height,100);
		
		currentHealth = maxHealth;
		lastCurrentHealth=currentHealth;
		
		this.playing = playing;
		loadAnimations();
		initHitbox(x, y, (int)(20*Game.SCALE), (int)(27*Game.SCALE));
		initAttackBox(x, y, (int) (30 * Game.SCALE), (int) (30 * Game.SCALE));
	}
	

	public void update(int mapOffsetX) {
		
		
		if(checkFallInSpike(mapOffsetX)||currentHealth <= 0) {
			setDead();
			return;
		}
		
		if(checkWin(mapOffsetX)) {
			playing.setGameWin(true);
			if(playing.getAntsManager().getScoreEqualZero()) 
				playing.setBonus();
				
			if(!playing.isMuted()) {			
				if(playing.getAntsManager().getScoreEqualZero()) 
					playing.getGame().getAudioPlayer().playSong(AudioPlayer.BONUS);
				else
					playing.getGame().getAudioPlayer().playSong(AudioPlayer.WIN);
				
			}
				
			return;
		}
			
		
		updateHealthBar();

		
		updateAttackBox(0);

		updatePos();
		if (attacking)
			checkAttack();
		updateAnimationTick();
		setAnimation();
		
		
	}
	
	
	private void setDead() {
		updateHealthBar();
		playerAction = DEAD;
		aniTick = 0;
		aniIndex = 0;
		playing.setGameOver(true);
		if(!playing.isMuted())
			playing.getGame().getAudioPlayer().playSong(AudioPlayer.GAMEOVER);
		
		
	}
	
	private boolean checkWin(int mapOffsetX) {
		
		
		
		Rectangle2D.Float hitboxHome;
		hitboxHome=new Rectangle2D.Float((int)(80*Game.SCALE)-mapOffsetX-25, (int)(315*Game.SCALE),House_WIDTH, House_HEIGHT);

		if (hitbox.intersects(hitboxHome)) 				
			return true;
			
		
		return false;
		
		
	}
	
	private boolean checkFallInSpike(int mapOffsetX) {
		
		Rectangle2D.Float hitboxSpike;
		hitboxSpike=new Rectangle2D.Float((int)(545*Game.SCALE)-mapOffsetX, (int)(222*Game.SCALE),20*SPIKE_WIDTH, SPIKE_HEIGHT);
		if (hitbox.intersects(hitboxSpike)) 		
		{
			currentHealth=0;
			updateHealthBar();
			return true;

		}
		return false;
		
}
	
	
	
	private void checkAttack() {
		if (attackChecked || aniIndex != 1)
			return;
		attackChecked = true;
		playing.checkEnemySnakeHit(attackBox);
		if(!playing.isMuted())
			playing.getGame().getAudioPlayer().playEffect(AudioPlayer.ATTACK);
	}
	

	private void updateHealthBar() {
		healthWidth = (int) ((currentHealth / (float) maxHealth) * healthBarWidth);
	}
	
	
	public void draw(Graphics g, int mapOffset) {
		
		g.drawImage(animations[playerAction][aniIndex], (int) (hitbox.x - xDrawOffset) - mapOffset + flipX,
				(int) (hitbox.y - yDrawOffset), width * flipW, height, null);
		drawUI(g);
		

	}
	


	private void drawUI(Graphics g) {
		
		
		
		g.setColor(Color.red);
		
		g.fillRect(healthBarXStart + statusBarX, healthBarYStart + statusBarY, healthWidth, healthBarHeight);
	

		if(currentHealth!=0)
			g.drawImage(lifeBarImg, statusBarX, statusBarY, statusBarWidth, statusBarHeight, null);
		else
			g.drawImage(lifeBarImg2, statusBarX, statusBarY, statusBarWidth, statusBarHeight, null);
	}
	
	private void loadAnimations() {

			animations =new BufferedImage[6][8];
		
			int idleCnt=GetSpriteAmount(IDLE);
			for(int i=0;i<idleCnt;i++) 
				animations[IDLE][i] = LoadSave.GetSpriteAtlas(LoadSave.PLAYER_IDLE[i]);
			
			
			int jumpCnt=GetSpriteAmount(JUMP);
			for(int i=0;i<jumpCnt;i++) 
				animations[JUMP][i] = LoadSave.GetSpriteAtlas(LoadSave.PLAYER_JUMP[i]);
			
			
			int attackCnt=GetSpriteAmount(ATTACK);
			for(int i=0;i<attackCnt;i++) 
				animations[ATTACK][i] = LoadSave.GetSpriteAtlas(LoadSave.PLAYER_JUMP[i]);
			
			int runCnt=GetSpriteAmount(RUNNING);
			for(int i=0;i<runCnt;i++) 
				animations[RUNNING][i] = LoadSave.GetSpriteAtlas(LoadSave.PLAYER_RUN[i]);
			
			
			int hitCnt=GetSpriteAmount(HIT);
			for(int i=0;i<hitCnt;i++) 
				animations[HIT][i] = LoadSave.GetSpriteAtlas(LoadSave.PLAYER_HIT[i]);
			

			animations[DEAD][0] = LoadSave.GetSpriteAtlas(LoadSave.PLAYER_DEAD);
	
			lifeBarImg = LoadSave.GetSpriteAtlas(LoadSave.LIFEBAR_BAR);
			lifeBarImg2 = LoadSave.GetSpriteAtlas(LoadSave.LIFEBAR2_BAR);
	}
	
	public void loadMapData(int[][] mapData) {
		
		this.mapData=mapData;
		if(!IsEntityOnFloor(hitbox,mapData))
			inAir=true;
		
	}
	

	
	private void updateAnimationTick() {
		aniTick++;
		if (aniTick >= aniSpeed) {
			aniTick = 0;
			aniIndex++;
			if (aniIndex >= GetSpriteAmount(playerAction)) {
				aniIndex = 0;
				attacking = false;
				attackChecked = false;
			}

		}

	}
	
	public void changeHealth(int value) {

		
		
		currentHealth += value;
		
		if (currentHealth <= 0)
			currentHealth = 0;
		else if (currentHealth >= maxHealth)
			currentHealth = maxHealth;
	}
	
	public void recoverHealth() {
	
		currentHealth =100;
	}
	

	private void setAnimation() {
		
		int startAni=playerAction;
		
		if(moving)
				playerAction=RUNNING;
		else
			playerAction=IDLE;
		
		if(inAir)
			playerAction=JUMP;
		
		if (attacking) {
			playerAction = ATTACK;
			if (startAni != ATTACK) {
				aniIndex = 1;
				aniTick = 0;
				return;
			}
		}
		if(lastCurrentHealth!=currentHealth)
		{
			playerAction = HIT;
			
		}
		if(playerAction==HIT&&aniIndex==1) 
		{
			playerAction=IDLE;
			lastCurrentHealth=currentHealth;
		}
		
		if(startAni!=playerAction)
			resetAniTick();		
		
	}
	
	private void resetAniTick() {
		aniTick=0;
		aniIndex=0;
	}

	private void updatePos() {
		moving = false;

		if (jump)
			jump();

		if (!inAir)
			if ((!left && !right) || (right && left))
				return;

		float xSpeed = 0;

		if (left) {
			xSpeed -= playerSpeed;
			flipX=width;
			flipW=-1;
			
		}
		if (right) {
			xSpeed += playerSpeed;
			flipX=0;
			flipW=1;
		}

		if (!inAir)
			if (!IsEntityOnFloor(hitbox, mapData))
				inAir = true;

		if (inAir) {
			if (CanMoveHere(hitbox.x, hitbox.y + airSpeed, hitbox.width, hitbox.height, mapData)) {
				hitbox.y += airSpeed;
				airSpeed += gravity;
				updateXPos(xSpeed);
			} else {
				hitbox.y = GetEntityYPosUnderRoofOrAboveFloor(hitbox, airSpeed);
				if (airSpeed > 0)
					resetInAir();
				else
					airSpeed = fallSpeedAfterCollision;
				updateXPos(xSpeed);
			}

		} 
		else
			updateXPos(xSpeed);
		
		moving = true;
	}

	private void jump() {
		if(inAir)
			return;
		if(!playing.isMuted())
			playing.getGame().getAudioPlayer().playEffect(AudioPlayer.JUMP);
		inAir=true;
		airSpeed=jumpSpeed;
		
	}

	private void resetInAir() {
		inAir=false;
		airSpeed=0;
	}

	private void updateXPos(float xSpeed) {
		
		
        Ellipse2D circle = new Ellipse2D.Double((int)(playing.getSnorlax().getX()*Game.SCALE), (int)(playing.getSnorlax().getY()*Game.SCALE),(int)(118*Game.SCALE), (int)(113*Game.SCALE));


      
        

		
		if(!hitbox.intersects(circle.getBounds2D())&&CanMoveHere(hitbox.x+xSpeed,hitbox.y,hitbox.width,hitbox.height,mapData)) {
			hitbox.x+=xSpeed;
		}
		else {
			hitbox.x=GetEntityXPosNextToWall(hitbox,xSpeed);
		}
		
	}
	
	
	public void setAttacking(boolean attacking) {
		
		this.attacking=attacking;
	}


	public void setLeft(boolean left) {
		this.left = left;
	}


	public void setRight(boolean right) {
		this.right = right;
	}


	public void setJump(boolean jump) {
		this.jump = jump;
	}
	
	public void resetAll() {
		resetDirBooleans();
		inAir = false;
		attacking = false;
		moving = false;
		playerAction = IDLE;
		currentHealth = maxHealth;

		hitbox.x = x;
		hitbox.y = y;

		if (!IsEntityOnFloor(hitbox, mapData))
			inAir = true;
	}
	
	
	public void resetDirBooleans() {
		left = false;
		right = false;

	}
	
}
