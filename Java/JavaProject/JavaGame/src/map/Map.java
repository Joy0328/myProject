package map;

import java.awt.image.BufferedImage;

import main.Game;
import java.awt.Graphics;
import utilz.LoadSave;


public class Map {

	private int[][] mapData;
	
	private Game game;
	private BufferedImage[] mapSprite;
	private MapObject mapObject;
	
	
	public Map(Game game) {
		
		this.game = game;
		this.mapData= LoadSave.GetMap();
		mapObject=new MapObject();
		
		importOutsideSprites();

		
		
		
	}

	private void importOutsideSprites() {
		BufferedImage img = LoadSave.GetSpriteAtlas(LoadSave.MAP_ATLAS);
		mapSprite = new BufferedImage[48];
		for (int j = 0; j < 4; j++)
			for (int i = 0; i < 12; i++) {
				int index = j * 12 + i;
				mapSprite[index] = img.getSubimage(i * 32, j * 32, 32, 32);
			}
	}
	
	public void draw(Graphics g, int mapOffset) {
		
		
		mapObject.draw(g, mapOffset);
		
		for (int j = 0; j < Game.TILES_IN_HEIGHT; j++)
			for (int i = 0; i < mapData[0].length; i++) {
				int index = mapData[j][i];
				g.drawImage(mapSprite[index], Game.TILES_SIZE * i - mapOffset, Game.TILES_SIZE * j, Game.TILES_SIZE, Game.TILES_SIZE, null);
					
			}

		
		
	}
	
	

	
	public int[][] getMapData() {
		
		return mapData;

	}	
	
	
	
}
