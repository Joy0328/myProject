package entities;

import java.awt.geom.Rectangle2D;

public abstract class Entity {
	
	protected float x,y;
	protected int width, height;
	protected Rectangle2D.Float hitbox,attackBox;
	protected int maxHealth;
	
	public Entity(float x, float y, int width, int height,int maxHealth) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.maxHealth = maxHealth;
		
	}
	

	
	protected void initHitbox(float x, float y,int width, int height) {
		hitbox=new Rectangle2D.Float(x,y,width,height);
		
	}
	
	protected void initAttackBox(float x, float y,int width, int height) {
		attackBox=new Rectangle2D.Float(x,y,width,height);
		
	}
	
	protected void updateAttackBox(int attackBoxOffsetX) {
		attackBox.x = hitbox.x - attackBoxOffsetX;
		attackBox.y = hitbox.y;
		
	}
	
	

	public Rectangle2D.Float getHitbox() {
		
		return hitbox;
		
	}
	
	
}
