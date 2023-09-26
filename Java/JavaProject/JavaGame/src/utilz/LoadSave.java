package utilz;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.awt.Color;
import javax.imageio.ImageIO;

import entities.Ant;
import entities.Snake;
import entities.SpecialSnake;


import main.Game;

public class LoadSave {

	public static final String PLAYER_IDLE1="player-idle-1.png";
	
	public static final String[] PLAYER_IDLE= {"player-idle-1.png","player-idle-2.png","player-idle-3.png","player-idle-4.png","player-idle-5.png","player-idle-6.png","player-idle-7.png","player-idle-8.png"};
	public static final String[] PLAYER_JUMP= {"player-jump-1.png","player-idle-2.png","player-jump-3.png","player-jump-4.png"};	
	public static final String[] PLAYER_RUN= {"player-run-1.png","player-run-2.png","player-run-3.png","player-run-4.png","player-run-5.png","player-run-6.png"};
	public static final String[] PLAYER_HIT= {"player-hurt-1.png","player-hurt-2.png"};
	public static final String PLAYER_DEAD= "dead.png";
	public static final String[] PLAYER_DANCE= {"player-dance-1.png","player-dance-2.png","player-dance-3.png","player-dance-4.png","player-dance-5.png"};
	public static final String PLAYER_ACORN= "acorn.png";
	public static final String PRESS_ENTER= "press-enter.png";
	
	
	public static final String[] MENU_BUTTONS_PLAY= {"PlayBtn.png","PlayClick.png"};
	public static final String[] MENU_BUTTONS_TIPS= {"tipsBtn.png","tipsClick.png"};
	public static final String[] MENU_BUTTONS_QUIT= {"ExitBtn.png","ExitClick.png"};
	
	public static final String[] TIPS_BUTTONS_BACK= {"backBtn.png","backClick.png"};
	
	
	public static final String MAP_ATLAS = "outside_sprites.png";
	public static final String MAP_ONE_DATA = "map.png";
	
	public static final String TIPS_INFOR = "tips.png";
	public static final String MENU_BACKGROUND="menuBG.png";
	public static final String MENU_BACKGROUND2="bg.png";
	
	public static final String PAUSE_BACKGROUND="f.png";
	public static final String PAUSE_BACKGROUND2="header.png";
	public static final String PAUSE_TEXT="text.png";

	public static final String LOSE_BACKGROUND="f.png";
	public static final String LOSE_BACKGROUND2="loseheader.png";
	
	public static final String WIN_BACKGROUND="f.png";
	public static final String WIN_BACKGROUND2="win.png";
	public static final String[] WIN_START={"star1.png","star2.png","star3.png","star4.png"};
	

	public static final String ENEMY_INFOR_BACKGROUND="table.png";
	
	public static final String[] SOUND_BUTTONS={"sound.png","sound2.png"};
	public static final String[] SOUND_OFF_BUTTONS={"sound_off.png","sound_off2.png"};
	

	
	public static final String[] PLAY_BUTTONS={"play.png","play2.png"};
	public static final String[] RESTART_BUTTONS={"restart.png","restart2.png"};
	public static final String[] MENU_BUTTONS={"menu.png","menu2.png"};
	
	

	
	
	public static final String SNAKE_WALK_SPRITE="snake_walk.png";
	public static final String SNAKE_HURT_SPRITE="snake_hurt.png";
	public static final String SPECIAL_SNAKE_WALK_SPRITE="specialSnake_walk.png";
	public static final String SPECIAL_SNAKE_HURT_SPRITE="specialSnake_hurt.png";
	
	public static final String[] ENEMY_DEATH_SPRITE={"enemy-death-2.png","enemy-death-3.png"};
	
	public static final String[] ANT_WALK_SPRITE={"ant-1.png","ant-2.png","ant-3.png","ant-4.png","ant-5.png","ant-6.png","ant-7.png","ant-8.png"};

	public static final String BIGROCK_IMG="bigRock.png";


	
	
	public static final String LIFEBAR_BAR = "lifeBar.png";
	public static final String LIFEBAR2_BAR = "lifeBar2.png";
	public static final String PLAYING_BG_IMG = "forest.png";

	public static final String CAVE_IMG = "cave.png";
	public static final String HOUSE_IMG = "house.png";
	public static final String TREE_IMG = "tree.png";
	public static final String ROCK_IMG = "rock.png";
	public static final String SPIKE_IMG = "spike.png";
	public static final String GRASS_IMG = "grass.png";
	public static final String GRASS2_IMG = "grass2.png";
	public static final String GRASS3_IMG = "grass3.png";
	
	
	public static BufferedImage GetSpriteAtlas(String fileName) {
		BufferedImage img=null;
		InputStream is=LoadSave.class.getResourceAsStream("/"+fileName);
		
		try {
			img=ImageIO.read(is);
			
			
		} catch (IOException e) {
			e.printStackTrace();
		}finally {
			try {
				is.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			
		}
		return img;
		
	}
	

	public static ArrayList<Ant> GetAnts() {
		BufferedImage img = GetSpriteAtlas(MAP_ONE_DATA);
		ArrayList<Ant> list = new ArrayList<>();
		for (int j = 0; j < img.getHeight(); j++)
			for (int i = 0; i < img.getWidth(); i++) {
				Color color = new Color(img.getRGB(i, j));
				int value = color.getGreen();
				if (value ==2)
					list.add(new Ant(i * Game.TILES_SIZE, j * Game.TILES_SIZE,5));
			}
		return list;

	}
	
	public static ArrayList<Snake> GetSnakes() {
		BufferedImage img = GetSpriteAtlas(MAP_ONE_DATA);
		ArrayList<Snake> list = new ArrayList<>();
		for (int j = 0; j < img.getHeight(); j++)
			for (int i = 0; i < img.getWidth(); i++) {
				Color color = new Color(img.getRGB(i, j));
				int value = color.getGreen();
				if (value ==0)
					list.add(new Snake(i * Game.TILES_SIZE, j * Game.TILES_SIZE,10));
			}
		return list;

	}
	
	public static ArrayList<SpecialSnake> GetSpecialSnakes() {
		BufferedImage img = GetSpriteAtlas(MAP_ONE_DATA);
		ArrayList<SpecialSnake> list = new ArrayList<>();
		for (int j = 0; j < img.getHeight(); j++)
			for (int i = 0; i < img.getWidth(); i++) {
				Color color = new Color(img.getRGB(i, j));
				int value = color.getGreen();
				if (value ==1)
					list.add(new SpecialSnake(i * Game.TILES_SIZE, j * Game.TILES_SIZE));
			}
		return list;

	}
	
	
	
	public static int[][] GetMap(){
		BufferedImage img = GetSpriteAtlas(MAP_ONE_DATA);
		int[][] mapData = new int[img.getHeight()][img.getWidth()];
		
		for(int j=0;j<img.getHeight();j++) {
			for(int i=0;i<img.getWidth();i++) {
				Color color=new Color(img.getRGB(i, j));
				int value=color.getRed();
				if(value>=48) {
					value=0;
				}
				mapData[j][i]=value;
			}
			
		}
		return mapData;
	}
	
}
