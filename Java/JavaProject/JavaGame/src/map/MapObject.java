package map;


import static utilz.Constants.Environment.*;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import main.Game;
import utilz.LoadSave;

public class MapObject {

	
	private BufferedImage houseImg,backgroundImg,treeImg,rockImg,caveImg,spikeImg,grassImg,grass2Img,grass3Img;
	
	MapObject(){
		
		backgroundImg = LoadSave.GetSpriteAtlas(LoadSave.PLAYING_BG_IMG);	
		treeImg=LoadSave.GetSpriteAtlas(LoadSave.TREE_IMG);
		houseImg=LoadSave.GetSpriteAtlas(LoadSave.HOUSE_IMG);
		rockImg=LoadSave.GetSpriteAtlas(LoadSave.ROCK_IMG);
		caveImg=LoadSave.GetSpriteAtlas(LoadSave.CAVE_IMG);
		spikeImg=LoadSave.GetSpriteAtlas(LoadSave.SPIKE_IMG);
		grassImg=LoadSave.GetSpriteAtlas(LoadSave.GRASS_IMG);
		grass2Img=LoadSave.GetSpriteAtlas(LoadSave.GRASS2_IMG);
		grass3Img=LoadSave.GetSpriteAtlas(LoadSave.GRASS3_IMG);
	}
	
public void draw(Graphics g, int mapOffset) {
		
		g.drawImage(backgroundImg, 0, 0, Game.GAME_WIDTH, Game.GAME_HEIGHT, null);
		g.drawImage(caveImg, 0, Game.GAME_HEIGHT/2+25, Game.GAME_WIDTH, Game.GAME_HEIGHT, null);
		g.drawImage(houseImg, (int)(80*Game.SCALE)-mapOffset, (int)(315*Game.SCALE), House_WIDTH, House_HEIGHT, null);
		
		g.drawImage(rockImg, (int)(2500*Game.SCALE)-mapOffset, (int)(113*Game.SCALE), ROCK_WIDTH, ROCK_HEIGHT, null);
		
		
		g.drawImage(treeImg, (int)(2450*Game.SCALE)-mapOffset, (int)(35*Game.SCALE), TREE_WIDTH,TREE_HEIGHT, null);
		
		
		for(int i=0;i<9;i++) 
			g.drawImage(spikeImg, (int)(545*Game.SCALE)-mapOffset+i*SPIKE_WIDTH, (int)(180*Game.SCALE), SPIKE_WIDTH,SPIKE_HEIGHT, null);
			
		
		
		
		
		for(int i=0;i<30;i++) 
			g.drawImage(grassImg, (int)(445*Game.SCALE)-mapOffset+i*(GRASS_WIDTH-15), (int)(390*Game.SCALE), GRASS_WIDTH, GRASS_HEIGHT, null);
			
		
		for(int i=0;i<20;i++) 
			g.drawImage(grass2Img, (int)(0*Game.SCALE)-mapOffset+i*(GRASS2_WIDTH-30), (int)(200*Game.SCALE), GRASS2_WIDTH, GRASS2_HEIGHT, null);
		
		
		for(int i=0;i<20;i++) 
			g.drawImage(grass3Img, (int)(0*Game.SCALE)-mapOffset+i*(GRASS3_WIDTH), (int)(200*Game.SCALE), GRASS3_WIDTH, GRASS3_HEIGHT, null);
		
		
		for(int i=0;i<25;i++) 
			g.drawImage(rockImg, (int)(445*Game.SCALE)-mapOffset+i*(GRASS_WIDTH*6), (int)(400*Game.SCALE), ROCK_WIDTH, ROCK_HEIGHT, null);
		
		
	}
	
	
}



