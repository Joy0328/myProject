package gamestates;

import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import static utilz.Constants.UI.URMButtons.*;
import main.Game;
import ui.MenuButton;
import utilz.LoadSave;

public class Tips extends State implements Statemethods{
	
	MenuButton backButton;
	private BufferedImage backgroundImg,backgroundImg2;
	private int menuX,menuY,menuWidth,menuHeight;
	private int menuX2,menuY2,menuWidth2,menuHeight2;
	public Tips(Game game) {
		
		super(game);
		backButton=new MenuButton(Game.GAME_WIDTH/2,(int)(340*Game.SCALE),Gamestate.MENU,LoadSave.TIPS_BUTTONS_BACK,URM_SIZE,URM_SIZE);
		loadBackground();

	}
	private void loadBackground() {
	

	backgroundImg=LoadSave.GetSpriteAtlas(LoadSave.MENU_BACKGROUND);
	menuWidth=(int)(backgroundImg.getWidth()*Game.SCALE);
	menuHeight=(int)(backgroundImg.getHeight()*Game.SCALE);
	menuX=0;
	menuY=0;
	
	backgroundImg2=LoadSave.GetSpriteAtlas(LoadSave.TIPS_INFOR);
	menuWidth2=(int)(backgroundImg2.getWidth()*Game.SCALE);
	menuHeight2=(int)(backgroundImg2.getHeight()*Game.SCALE);
	menuX2=Game.GAME_WIDTH/2-menuWidth2/2;
	menuY2=(int)(45*Game.SCALE);
	
}
	@Override
	public void update() {
		backButton.update();
		
	}
	
	@Override
	public void draw(Graphics g) {
		g.drawImage(backgroundImg, menuX, menuY, menuWidth, menuHeight,null);
		g.drawImage(backgroundImg2, menuX2, menuY2, menuWidth2, menuHeight2,null);
		backButton.draw(g);
		
	}
	
		@Override
		public void mouseClicked(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}
		
		@Override
		public void mousePressed(MouseEvent e) {
			if(isIn(e,backButton)) 
				backButton.setMousePressed(true);
				
			
		}
		
		@Override
		public void mouseReleased(MouseEvent e) {
			if(isIn(e,backButton)) {
				if(backButton.isMousePressed())
					backButton.applyGamestate();
			
				
			}
			backButton.resetBools();
		}
		
		
		@Override
		public void mouseMoved(MouseEvent e) {
			backButton.setMouseOver(false);
	
			
			if(isIn(e,backButton))
				backButton.setMouseOver(true);
			
		}
		
		@Override
		public void keyPressed(KeyEvent e) {

			
		}
		
		@Override
		public void keyReleased(KeyEvent e) {
			// TODO Auto-generated method stub
			
		}
		
	
}
