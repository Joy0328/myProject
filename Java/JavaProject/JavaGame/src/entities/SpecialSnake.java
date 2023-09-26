package entities;


import static utilz.Constants.SnakeConstants.*;
import java.awt.geom.Rectangle2D;

public class SpecialSnake extends Snake{
	
	private boolean Used;
	protected int currentHealth;
	
	public SpecialSnake(float x, float y) {
		super(x, y,30);
		currentHealth= maxHealth;
		this.Used=false;
	}
	
	public void setUsed() {
		Used=true;
	}
	public boolean getUsed() {
		return Used;
	}
	public void reset() {
		hitbox.x = x;
		hitbox.y = y;
		firstUpdate = true;
		currentHealth = maxHealth;
		newState(WALKING);
		active = true;
		fallSpeed = 0;
		this.Used=false;
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
			player.changeHealth(SSNAKE_DMG);
		attackChecked = true;

	}
	

}
