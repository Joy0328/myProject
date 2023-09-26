package entities;

import static utilz.Constants.Directions.*;
import static utilz.Constants.SnakeConstants.*;
import static utilz.HelpMethods.*;

import java.awt.geom.Rectangle2D;

import main.Game;

public class Snake extends Entity {


	private int attackBoxOffsetX=(int) (Game.SCALE * 15);


	protected int aniIndex, state;
	protected int aniTick, aniSpeed = 25;
	protected boolean firstUpdate = true;
	protected boolean inAir;
	protected float fallSpeed;
	protected float gravity = 0.04f * Game.SCALE;
	protected float walkSpeed = 0.35f * Game.SCALE;
	protected int walkDir = LEFT;
	protected int tileY;
	protected float attackDistance = Game.TILES_SIZE;
	


	protected int currentHealth;
	protected boolean active = true;
	protected boolean attackChecked;
	
	

	
	public Snake(float x, float y,int maxHealth) {
		super(x, y, SNAKE_WIDTH, SNAKE_HEIGHT,maxHealth);
		currentHealth= maxHealth;
		initHitbox(x, y, (int) (22 * Game.SCALE), (int) (19 * Game.SCALE));
		initAttackBox(x, y, (int) (48 * Game.SCALE), (int) (19 * Game.SCALE));

	}

	protected void firstUpdateCheck(int[][] mapData) {
		if (!IsEntityOnFloor(hitbox, mapData))
			inAir = true;
		firstUpdate = false;
	}
	

	protected void updateInAir(int[][] mapData) {
		if (CanMoveHere(hitbox.x, hitbox.y + fallSpeed, hitbox.width, hitbox.height, mapData)) {
			hitbox.y += fallSpeed;
			fallSpeed += gravity;
		}
		else {
			inAir = false;
			hitbox.y = GetEntityYPosUnderRoofOrAboveFloor(hitbox, fallSpeed);
			tileY = (int) (hitbox.y / Game.TILES_SIZE);
		}
	}
	
	
	public void update(int[][] mapData, Player player) {
		updateBehavior(mapData, player);
		updateAnimationTick();
		updateAttackBox(attackBoxOffsetX);

	}

	

	private void updateBehavior(int[][] mapData, Player player) {
		if (firstUpdate)
			firstUpdateCheck(mapData);

		if (inAir)
			updateInAir(mapData);
		else {
			switch (state) {
			case WALKING:
				if (canSeePlayer(mapData, player)) {
					turnTowardsPlayer(player);
					if (isPlayerCloseForAttack(player))
						newState(ATTACK);
				}
				move(mapData);
				break;
			case ATTACK:
				if (aniIndex == 0)
					attackChecked = false;

				if (aniIndex == 2 && !attackChecked)
					checkPlayerHit(attackBox, player);
				break;
			case HIT:
				break;

			}
		}

	}
	protected void move(int[][] mapData) {
		float xSpeed = 0;

		if (walkDir == LEFT)
			xSpeed = -walkSpeed;
		else
			xSpeed = walkSpeed;


		if (CanMoveHere(hitbox.x + xSpeed, hitbox.y, hitbox.width, hitbox.height, mapData))
			if (IsFloor(hitbox, xSpeed, mapData)) {
				hitbox.x += xSpeed;
				return;
			}

		changeWalkDir();
	}

	protected void turnTowardsPlayer(Player player) {
		
		if (player.hitbox.x > hitbox.x) 
			walkDir = RIGHT;
		else 
			walkDir = LEFT;
		
	}

	protected boolean canSeePlayer(int[][] mapData, Player player) {
		int playerTileY = (int) (player.getHitbox().y / Game.TILES_SIZE);
		if (playerTileY == tileY)
			if (isPlayerInRange(player)) {
				if (IsSightClear(mapData, hitbox, player.hitbox, tileY))
					return true;
			}

		return false;
	}

	protected boolean isPlayerInRange(Player player) {
		int absValue = (int) Math.abs(player.hitbox.x - hitbox.x);
		return absValue <= attackDistance * 5;
	}
					
	protected boolean isPlayerCloseForAttack(Player player) {
		int absValue = (int) Math.abs(player.hitbox.x - hitbox.x);
		return absValue <= attackDistance;
	}

	protected void newState(int State) {
		this.state = State;
		aniTick = 0;
		aniIndex = 0;
	}
	
	public void hurt(int amount) {
		currentHealth -= amount;
		if (currentHealth <= 0)
			newState(DEAD);
		else
			newState(HIT);
	}
	

	protected void checkPlayerHit(Rectangle2D.Float attackBox, Player player) {
		if (attackBox.intersects(player.hitbox))
			player.changeHealth(SNAKE_DMG);
		attackChecked = true;

	}
	
	

	protected void updateAnimationTick() {
		aniTick++;
		if (aniTick >= aniSpeed) {
			aniTick = 0;
			aniIndex++;
			if (aniIndex >= GetSnakeSpriteAmount(state)) {
				aniIndex = 0;
				switch (state) {
				case ATTACK, HIT -> state = WALKING;
				case DEAD -> active = false;
					
				}
			}
		}
	}
	protected void changeWalkDir() {
		if (walkDir == LEFT) 
			walkDir = RIGHT;
		else 
			walkDir = LEFT;
		

	}

	public void reset() {
		hitbox.x = x;
		hitbox.y = y;
		firstUpdate = true;
		currentHealth = maxHealth;
		newState(WALKING);
		active = true;
		fallSpeed = 0;
	}

	public int getAniIndex() {
		return aniIndex;
	}

	public int getSnakeState() {
		return state;
	}
	
	public boolean isActive() {
		return active;
	}


	
	
	public int flipX() {
		if (walkDir == RIGHT)
			return width;
		else
			return 0;
	}

	public int flipW() {
		if (walkDir == RIGHT)
			return -1;
		else
			return 1;

	}
	

}