package missile;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.List;

import battle.BattleMediator;
import battle.Explosion;
import tank.EnemyTank;
import tank.Tank;
import tank.Tank.Direction;
import util.ImageLoader;

public class Missile {
	
	public static final int MISSILE_SPEED = 10;
	
	protected int missileLocation_X;
	protected int missileLocation_Y;
	
	protected boolean missileInside = true;
	protected boolean isEnemyShot = true; 
	
	protected BattleMediator bm;

	Tank.Direction ptrDir;
	
	public static Image normalMissile = 
			ImageLoader.loadImageIcon("missile/normalMissile.png").getImage();
	public static Image enemyMissile = 
			ImageLoader.loadImageIcon("missile/enemyMissile.png").getImage();

	public Missile( boolean isEnemyShot, int missileLocation_X, int missileLocation_Y, 
			Tank.Direction ptrDir, BattleMediator bm ) {
		this.isEnemyShot = isEnemyShot;
		this.missileLocation_X = missileLocation_X;
		this.missileLocation_Y = missileLocation_Y;
		this.ptrDir = ptrDir;
		this.bm = bm;
	}
	
	public void draw(Graphics g) {
		if ( missileInside ) {
			g.drawImage(Missile.normalMissile, missileLocation_X, missileLocation_Y, null);
			missileMove();
		}		
		
	}

	@SuppressWarnings("incomplete-switch")
	public void missileMove() {
		switch(ptrDir) {
		case UP :
			missileLocation_Y -= MISSILE_SPEED;
			break;
		case DOWN :
			missileLocation_Y += MISSILE_SPEED;
			break;			
		case LEFT :
			missileLocation_X -= MISSILE_SPEED;
			break;
		case RIGHT :
			missileLocation_X += MISSILE_SPEED;
			break;
		case LU:
			missileLocation_X -= MISSILE_SPEED;
			missileLocation_Y -= MISSILE_SPEED;
			break;
		case RU:
			missileLocation_X += MISSILE_SPEED;
			missileLocation_Y -= MISSILE_SPEED;
			break;
		case LD:
			missileLocation_X -= MISSILE_SPEED;
			missileLocation_Y += MISSILE_SPEED;
			break;
		case RD:
			missileLocation_X += MISSILE_SPEED;
			missileLocation_Y += MISSILE_SPEED;;
			break;
		case STOP :
			missileInside = false;
			break;
		}
		if( missileLocation_X < 0 || missileLocation_Y < 0 || 
				missileLocation_X > BattleMediator.CANVAS_FIELD_WIDTH || 
				missileLocation_Y > BattleMediator.CANVAS_FIELD_HEIGHT ) {
			missileInside = false;
		}
	}
		
	public void bossMissileMove() {
		switch(ptrDir) {
		case UP :
			missileLocation_Y -= MISSILE_SPEED;
			break;
		case DOWN :
			missileLocation_Y += MISSILE_SPEED;
			break;			
		case LEFT :
			missileLocation_X -= MISSILE_SPEED;
			break;
		case RIGHT :
			missileLocation_X += MISSILE_SPEED;
			break;
		case STOP :
			missileInside = false;
			break;
		}
		if( missileLocation_X < 0 || missileLocation_Y < 0 || 
				missileLocation_X > BattleMediator.CANVAS_FIELD_WIDTH || 
				missileLocation_Y > BattleMediator.CANVAS_FIELD_HEIGHT ) {
			missileInside = false;
		}
	}
	
	public boolean isMissileInside() {
		return missileInside;
	}
	//后两个图片为子弹的长和宽
	private Rectangle getRect() {
		return new Rectangle(missileLocation_X, missileLocation_Y, 5, 5);
	}
	//需要判断是不同阵营
	public void hitTank(Tank t) {
		if( this.missileInside && this.getRect().intersects(t.getRect() ) && 
				t.isLive() && (t.isEnemy() != this.isEnemyShot)) {
			t.setBlood();
			if( t.getBlood() <= 0) {
				t.setLive(false);
			}
			this.missileInside = false;
			Explosion e = new Explosion( missileLocation_X, missileLocation_Y, bm );
			bm.explosions.add(e);
		}	
	}
	
	//把敌方坦克整个数组传进来，让this Missile对象挨个敌对坦克进行判断，看是否击中了
	public void hitTanks(ArrayList<EnemyTank> enemyTanks) {
		for(int i = 0; i < enemyTanks.size(); i++) {
			hitTank( enemyTanks.get(i) );			
		}
	}
}




















