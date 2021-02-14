package battle;

import java.awt.Graphics;
import java.awt.Image;

import util.ImageLoader;

public class Explosion {			
	public static Image[] explosionDeath = new Image[] {
			ImageLoader.loadImageIcon("explosion/death/1.png").getImage(),
			ImageLoader.loadImageIcon("explosion/death/2.png").getImage(),
			ImageLoader.loadImageIcon("explosion/death/3.png").getImage(),
			ImageLoader.loadImageIcon("explosion/death/4.png").getImage(),
			ImageLoader.loadImageIcon("explosion/death/5.png").getImage(),
			ImageLoader.loadImageIcon("explosion/death/6.png").getImage(),
			ImageLoader.loadImageIcon("explosion/death/7.png").getImage(),
			ImageLoader.loadImageIcon("explosion/death/8.png").getImage(),
			ImageLoader.loadImageIcon("explosion/death/9.png").getImage(),
			ImageLoader.loadImageIcon("explosion/death/10.png").getImage(),
			ImageLoader.loadImageIcon("explosion/death/11.png").getImage(),
			ImageLoader.loadImageIcon("explosion/death/12.png").getImage(),
			ImageLoader.loadImageIcon("explosion/death/13.png").getImage(),
			ImageLoader.loadImageIcon("explosion/death/14.png").getImage(),
			ImageLoader.loadImageIcon("explosion/death/15.png").getImage(),
			ImageLoader.loadImageIcon("explosion/death/16.png").getImage(),
			ImageLoader.loadImageIcon("explosion/death/17.png").getImage(),
			ImageLoader.loadImageIcon("explosion/death/18.png").getImage(),
			ImageLoader.loadImageIcon("explosion/death/19.png").getImage(),
			ImageLoader.loadImageIcon("explosion/death/20.png").getImage(),
			ImageLoader.loadImageIcon("explosion/death/21.png").getImage(),
			ImageLoader.loadImageIcon("explosion/death/22.png").getImage(),
			ImageLoader.loadImageIcon("explosion/death/23.png").getImage(),
			ImageLoader.loadImageIcon("explosion/death/24.png").getImage(),
			ImageLoader.loadImageIcon("explosion/death/25.png").getImage(),
			ImageLoader.loadImageIcon("explosion/death/26.png").getImage(),
			ImageLoader.loadImageIcon("explosion/death/27.png").getImage(),
			ImageLoader.loadImageIcon("explosion/death/28.png").getImage(),
			ImageLoader.loadImageIcon("explosion/death/29.png").getImage()
	};
	
	public static Image[] explosionHit = new Image[] {
			ImageLoader.loadImageIcon("explosion/hit/1.png").getImage(),
			ImageLoader.loadImageIcon("explosion/hit/2.png").getImage(),
			ImageLoader.loadImageIcon("explosion/hit/3.png").getImage(),
			ImageLoader.loadImageIcon("explosion/hit/4.png").getImage(),
			ImageLoader.loadImageIcon("explosion/hit/5.png").getImage(),
			ImageLoader.loadImageIcon("explosion/hit/6.png").getImage(),		
	};
	
	private int explosion_X;
	private int explosion_Y;
	private boolean explosionImgExist = true;	
	private BattleMediator bm ;
	private int step = 0;
	
	public Explosion(int x, int y, BattleMediator bm) {
			this.explosion_X = x;
			this.explosion_Y = y;
			this.bm = bm;
	}
	
	public void draw(Graphics g) {
		//判断爆炸对象是否还存在
		if(!explosionImgExist) {
			bm.explosions.remove(this);
			return;
		}
		if(step == explosionDeath.length) {
			explosionImgExist = false;
			step = 0;
			return;
		}
		g.drawImage(explosionDeath[step], explosion_X-35, explosion_Y-35, null);
		step ++;
	}
}
