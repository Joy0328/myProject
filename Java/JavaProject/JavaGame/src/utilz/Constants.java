package utilz;

import main.Game;

public class Constants {
	

	public static class AntConstants {


		public static final int WALKING = 0;

		public static final int DEAD = 1;
		

		
		public static final int ANT_WIDTH_DEFAULT = 64;
		public static final int ANT_HEIGHT_DEFAULT = 64;
		
		public static final int ANT_WIDTH = (int) (ANT_WIDTH_DEFAULT * Game.SCALE);
		public static final int ANT_HEIGHT = (int) (ANT_HEIGHT_DEFAULT * Game.SCALE);

		public static final int ANT_DRAWOFFSET_X = (int) (26 * Game.SCALE);
		public static final int ANT_DRAWOFFSET_Y = (int) (45 * Game.SCALE);


		
		public static int GetSnakeSpriteAmount(int state) {

			
			switch (state) {		
			case WALKING:
				return 8;

			case DEAD:
				return 2;
			}

			return 0;

		}
		

	}
	
	
	public static class SnakeConstants {


		public static final int WALKING = 0;
		public static final int ATTACK = 1;
		public static final int HIT = 2;
		public static final int DEAD = 3;
		
		public static final int SNAKE_WIDTH_DEFAULT = 64;
		public static final int SNAKE_HEIGHT_DEFAULT = 64;

		public static final int SNAKE_WIDTH = (int) (SNAKE_WIDTH_DEFAULT * Game.SCALE);
		public static final int SNAKE_HEIGHT = (int) (SNAKE_HEIGHT_DEFAULT * Game.SCALE);

		public static final int SNAKE_DRAWOFFSET_X = (int) (26 * Game.SCALE);
		public static final int SNAKE_DRAWOFFSET_Y = (int) (45 * Game.SCALE);

		public static final int SNAKE_DMG = -15;
		public static final int SSNAKE_DMG = -20;
		
		public static int GetSnakeSpriteAmount(int state) {

			
			switch (state) {		
			case WALKING:
				return 7;
			case ATTACK:
				return 4;
			case HIT:
				return 2;
			case DEAD:
				return 2;
			}

			return 0;

		}
		

	}

	
	

	public static class Environment{

			public static final int ROCK_WIDTH_DEFAULT=28;
			public static final int ROCK_HEIGHT_DEFAULT=15;
			public static final int ROCK_WIDTH=(int)(ROCK_WIDTH_DEFAULT*Game.SCALE);
			public static final int ROCK_HEIGHT=(int)(ROCK_HEIGHT_DEFAULT*Game.SCALE);
			
		
		
			public static final int TREE_WIDTH_DEFAULT=105;
			public static final int TREE_HEIGHT_DEFAULT=93;
			public static final int TREE_WIDTH=(int)(TREE_WIDTH_DEFAULT*Game.SCALE);
			public static final int TREE_HEIGHT=(int)(TREE_HEIGHT_DEFAULT*Game.SCALE);
			
		
		

			public static final int House_WIDTH_DEFAULT=100;
			public static final int House_HEIGHT_DEFAULT=78;
			public static final int House_WIDTH=(int)(House_WIDTH_DEFAULT*Game.SCALE);
			public static final int House_HEIGHT=(int)(House_HEIGHT_DEFAULT*Game.SCALE);
		
		
			public static final int SPIKE_WIDTH_DEFAULT=50;
			public static final int SPIKE_HEIGHT_DEFAULT=48;
			public static final int SPIKE_WIDTH=(int)(SPIKE_WIDTH_DEFAULT*Game.SCALE);
			public static final int SPIKE_HEIGHT=(int)(SPIKE_HEIGHT_DEFAULT*Game.SCALE);
			
			public static final int GRASS_WIDTH_DEFAULT=116;
			public static final int GRASS_HEIGHT_DEFAULT=25;
			public static final int GRASS_WIDTH=(int)(GRASS_WIDTH_DEFAULT*Game.SCALE);
			public static final int GRASS_HEIGHT=(int)(GRASS_HEIGHT_DEFAULT*Game.SCALE);
			
			public static final int GRASS2_WIDTH_DEFAULT=140;
			public static final int GRASS2_HEIGHT_DEFAULT=36;
			public static final int GRASS2_WIDTH=(int)(GRASS2_WIDTH_DEFAULT*Game.SCALE);
			public static final int GRASS2_HEIGHT=(int)(GRASS2_HEIGHT_DEFAULT*Game.SCALE);
		
			public static final int GRASS3_WIDTH_DEFAULT=104;
			public static final int GRASS3_HEIGHT_DEFAULT=38;
			public static final int GRASS3_WIDTH=(int)(GRASS3_WIDTH_DEFAULT*Game.SCALE);
			public static final int GRASS3_HEIGHT=(int)(GRASS3_HEIGHT_DEFAULT*Game.SCALE);
			
			public static final int BIGROCK_WIDTH_DEFAULT=128;
			public static final int BIGROCK_HEIGHT_DEFAULT=113;
			public static final int BIGROCK_WIDTH=(int)(BIGROCK_WIDTH_DEFAULT*Game.SCALE);
			public static final int BIGROCK_HEIGHT=(int)(BIGROCK_HEIGHT_DEFAULT*Game.SCALE);
		
	}
	
	
	public static class UI{
		public static class MenuButtons{
			public static final int MENU_B_WIDTH_DEFAULT=140;
			public static final int MENU_B_HEIGHT_DEFAULT=60;
			public static final int MENU_B_WIDTH=(int)(MENU_B_WIDTH_DEFAULT*Game.SCALE);
			public static final int MENU_B_HEIGHT=(int)(MENU_B_HEIGHT_DEFAULT*Game.SCALE);
			
		}
		
		public static class SoundButtons{
			public static final int SOUND_B_SIZE_DEFAULT=42;
			public static final int SOUND_B_SIZE=(int)(SOUND_B_SIZE_DEFAULT*Game.SCALE);

			
		}
		
		public static class URMButtons{
			public static final int URM_SIZE_DEFAULT=56;
			public static final int URM_SIZE=(int)(URM_SIZE_DEFAULT*Game.SCALE);

		
		}
		

		
	}
	
	public static class Directions{
		public static final int LEFT=0;
		public static final int UP=1;
		public static final int RIGHT=2;
		public static final int DOWN=3;
	}
	
	public static class PlayerConstants{
		public static final int IDLE=0;
		public static final int JUMP=1;
		public static final int ATTACK=2;
		public static final int RUNNING=3;
		public static final int HIT=4;
		public static final int DEAD=5;

		

		
	
		public static int GetSpriteAmount(int player_action)
		{
			switch(player_action){
			
			case IDLE:
				return 8;
			case RUNNING:
				return 6;
			case JUMP:
			case ATTACK:
				return 4;
			case HIT:
				return 2;
			case DEAD:
				return 1;
			default:
				return 1;
			}
			
			
			
		}
	}
	
	
}
