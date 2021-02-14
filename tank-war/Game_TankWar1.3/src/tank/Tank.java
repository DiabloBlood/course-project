package tank;

import java.applet.Applet;
import java.applet.AudioClip;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import battle.BattleMediator;
import missile.Missile;
import tank.Tank.Direction;
import util.ImageLoader;
import util.SoundLoader;

public class Tank {
	class BloodBar {
		public void draw(Graphics g) {
			if(bossFlag == true) {
				g.setColor(Color.BLACK);
				g.drawRect(tankLocation_X - 10, tankLocation_Y - 8, 120, 8);
				int w = 120 * blood/bloodBase;
				if ( w >= 80 && w <= 120) {
					g.setColor(Color.GREEN);
				} else if (w > 40 && w < 80) {
					g.setColor(Color.ORANGE);
				}
				else {
					g.setColor(Color.RED);
				}
				g.fillRect(tankLocation_X - 9, tankLocation_Y - 7, w, 6);
			}
			else {
				g.setColor(Color.BLACK);
				g.drawRect(tankLocation_X + 3, tankLocation_Y - 8, 42, 8);
				int w = 40 * blood/bloodBase;
				if ( w >= 30 && w <= 40) {
					g.setColor(Color.GREEN);
				} else if (w > 10 && w < 30) {
					g.setColor(Color.ORANGE);
				}
				else {
					g.setColor(Color.RED);
				}
				g.fillRect(tankLocation_X + 4, tankLocation_Y - 7, w, 6);
			}
			
		}
	}
	
	public static final int TANK_SPEED = 5;
	public static final int MISSILE_ATTACK = 20;
	protected boolean bossFlag = false;
	//Tank在图中的位置
	protected int tankLocation_X;
	protected int tankLocation_Y;
	//调用stay方法的位置
	protected int stopX;
	protected int stopY;
	//生死
	protected boolean isLive = true;
	protected int blood;
	protected int bloodBase;
	protected BloodBar b = new BloodBar();
	//判断是否为敌方坦克,true表示是敌法坦克，false表示是玩家
	protected boolean isEnemy;
	//按键按下时候显示类
	protected boolean bUp = false, bDown = false, bLeft = false, bRight = false;
	//坦克停住的时候的重画方向
	protected boolean bU = true, bD = false, bL = false, bR = false;
	public enum Direction {UP, DOWN, LEFT, RIGHT, STOP, LU, RU, LD, RD}
	
	protected Direction dir = Direction.STOP;
	//坦克停下来时所指的方向，保证停下来图片画的方向朝着这个方向
	protected Direction ptrDirection = Direction.UP;
	//坦克必须持有WarField的引用才能将开火以后的子弹返回给WarField中的子弹成员变量
	
	protected Direction[] directions = Direction.values();
	
	protected BattleMediator bm;
	
	protected static Random r = new Random();
	
	public static AudioClip startClip[] = new AudioClip[] {
			SoundLoader.loadAudio( "start.wav" )
	};
	public static Image[] playerImages = new Image[] {
					ImageLoader.loadImageIcon("tank/player/U.gif").getImage(),
					ImageLoader.loadImageIcon("tank/player/D.gif").getImage(),
					ImageLoader.loadImageIcon("tank/player/L.gif").getImage(),
					ImageLoader.loadImageIcon("tank/player/R.gif").getImage()
	};
	
	public static Image[] enemyImages = new Image[] {
			ImageLoader.loadImageIcon("tank/enemy/U.png").getImage(),
			ImageLoader.loadImageIcon("tank/enemy/D.png").getImage(),
			ImageLoader.loadImageIcon("tank/enemy/L.png").getImage(),
			ImageLoader.loadImageIcon("tank/enemy/R.png").getImage()
	};
	
	public static Image[] bossImages = new Image[] {
			ImageLoader.loadImageIcon("tank/boss/UU.png").getImage(),
			ImageLoader.loadImageIcon("tank/boss/DD.png").getImage(),
			ImageLoader.loadImageIcon("tank/boss/LL.png").getImage(),
			ImageLoader.loadImageIcon("tank/boss/RR.png").getImage()
	};
	
	//初始化的时候设置tank的位置
	public Tank(boolean isEnemy, int tankLocation_X, int tankLocation_Y, BattleMediator bm, int blood) {
		this.isEnemy = isEnemy;
		this.tankLocation_X = tankLocation_X;
		this.tankLocation_Y = tankLocation_Y;
		this.stopX = tankLocation_X;
		this.stopY = tankLocation_Y;
		this.bm = bm;
		this.blood = blood;
		this.bloodBase = blood;
	}
	
	public void draw(Graphics g) {
		b.draw(g);
		switch(ptrDirection) {
			case UP :
				g.drawImage(playerImages[0], tankLocation_X, tankLocation_Y, null);
				break;
			case DOWN :
				g.drawImage(playerImages[1], tankLocation_X, tankLocation_Y, null);
				break;			
			case LEFT :
				g.drawImage(playerImages[2], tankLocation_X, tankLocation_Y, null);
				break;
			case RIGHT :
				g.drawImage(playerImages[3], tankLocation_X, tankLocation_Y, null);
				break;
			case STOP:
				break;
		}
		
		switch(dir) {
			case UP :
				g.drawImage(playerImages[0], tankLocation_X, tankLocation_Y, null);
				break;
			case DOWN :
				g.drawImage(playerImages[1], tankLocation_X, tankLocation_Y, null);
				break;			
			case LEFT :
				g.drawImage(playerImages[2], tankLocation_X, tankLocation_Y, null);
				break;
			case RIGHT :
				g.drawImage(playerImages[3], tankLocation_X, tankLocation_Y, null);
				break;		
			case STOP :
				break;
		}
		//每次重画都调用一次移动
		tankMove();
	}
	//拿到键盘按钮
	public void pressKeyboard(KeyEvent e) {		
		int key = e.getKeyCode();
		switch (key) {
			case KeyEvent.VK_UP:
				clearCurrentPtrDirection();
				bU = true;
				bUp = true;
				break;
			case KeyEvent.VK_DOWN:
				clearCurrentPtrDirection();
				bD = true;
				bDown = true;
				break;
			case KeyEvent.VK_LEFT:
				clearCurrentPtrDirection();
				bL = true;
				bLeft = true;
				break;
			case KeyEvent.VK_RIGHT:
				clearCurrentPtrDirection();
				bR = true;
				bRight = true;
				break;			
		}
		moveDirection();
		ptrDirection();
	}
	
	//松开键盘按钮	
	public void releaseKeyboard(KeyEvent e) {		
		int key = e.getKeyCode();
		switch (key) {
			case KeyEvent.VK_H:
				startClip[0].play();
				break;
			case KeyEvent.VK_SPACE:
				shot();
				break;
			case KeyEvent.VK_UP:
				bUp = false;
				break;
			case KeyEvent.VK_DOWN:
				bDown = false;
				break;
			case KeyEvent.VK_LEFT:
				bLeft = false;
				break;
			case KeyEvent.VK_RIGHT:
				bRight = false;
				break;			
		}
		moveDirection();
	}
	
	public void shot() {
		Missile m = new Missile(false, tankLocation_X + 18, tankLocation_Y + 18, ptrDirection, bm);
		bm.addNormalMissile(m);
	}
	
	public void tankMove() {
		stopX = tankLocation_X;
		stopY = tankLocation_Y;
		switch(dir) {
			case UP :
				tankLocation_Y -= TANK_SPEED;
				break;
			case DOWN :
				tankLocation_Y += TANK_SPEED;
				break;			
			case LEFT :
				tankLocation_X -= TANK_SPEED;
				break;
			case RIGHT :
				tankLocation_X += TANK_SPEED;
				break;		
			case STOP :
				break;
		}
		if( bossFlag == true ) {
			if( tankLocation_X < 0 )
				tankLocation_X = 0;
			if( tankLocation_Y < 80)
				tankLocation_Y = 80;
			if( tankLocation_X > BattleMediator.CANVAS_FIELD_WIDTH - 100)
				tankLocation_X = BattleMediator.CANVAS_FIELD_WIDTH - 100;
			if( tankLocation_Y > BattleMediator.CANVAS_FIELD_HEIGHT - 100)
				tankLocation_Y = BattleMediator.CANVAS_FIELD_HEIGHT - 100;
		}
		else {
			if( tankLocation_X < 0 )
				tankLocation_X = 0;
			if( tankLocation_Y < 20 )
				tankLocation_Y = 20;
			if( tankLocation_X > BattleMediator.CANVAS_FIELD_WIDTH - 45)
				tankLocation_X = BattleMediator.CANVAS_FIELD_WIDTH - 45;
			if( tankLocation_Y > BattleMediator.CANVAS_FIELD_HEIGHT - 45)
				tankLocation_Y = BattleMediator.CANVAS_FIELD_HEIGHT - 45;
		}
	}
	
	public void moveDirection() {
		if( bUp && !bDown && !bLeft && !bRight ) {
			dir = Direction.UP;
		} 
		else if( !bUp && bDown && !bLeft && !bRight ) {
			dir = Direction.DOWN;
		}			
		else if( !bUp && !bDown && bLeft && !bRight ) {
			dir = Direction.LEFT;
		}
		else if( !bUp && !bDown && !bLeft && bRight ) {
			dir = Direction.RIGHT;
		}			
		else dir = Direction.STOP;
	}
	
	public void ptrDirection() {
		if( bU && !bD && !bL && !bR ) {
			ptrDirection = Direction.UP;
		} 
		else if( !bU && bD && !bL && !bR ) {
			ptrDirection = Direction.DOWN;
		}			
		else if( !bU && !bD && bL && !bR ) {
			ptrDirection = Direction.LEFT;
		}
		else if( !bU && !bD && !bL && bR ) {
			ptrDirection = Direction.RIGHT;
		}			
	}
	
	public void clearCurrentPtrDirection() {
		bU = false;
		bD = false;
		bL = false;
		bR = false;
	}
	
	public void clearCurrentDirection() {
		bUp = false;
		bDown = false;
		bLeft = false;
		bRight = false;
	}
	
	public void enemyTankChangeDirection(Direction dir) {		
		switch (dir) {
			case UP :
				bU = true;
				bUp = true;
				break;
			case DOWN :
				bD = true;
				bDown = true;
				break;
			case LEFT :
				bL = true;
				bLeft = true;
				break;
			case RIGHT :
				bR = true;
				bRight = true;
				break;
			case STOP :
				clearCurrentDirection();
				break;
		}
		moveDirection();
		ptrDirection();
	}
	
	public Direction getPtrDirection() {
		return ptrDirection;
	}
	
	public Rectangle getRect() {
		if( bossFlag == true ) {
			return new Rectangle(tankLocation_X, tankLocation_Y, 105, 105);
		}
		else 
			return new Rectangle(tankLocation_X, tankLocation_Y, 35, 35);
	}
	
	public boolean isLive() {
		return isLive;
	}
	
	public void setLive(boolean b) {
		isLive = b;
	}
	
	public boolean isEnemy() {
		return isEnemy;
	}
	
	public int getBlood() {
		return blood;
	}
	
	public void setBlood() {
		blood -= Tank.MISSILE_ATTACK;
	}
	
	public void setBossFlag() {
		bossFlag = false;
	}
	public void stop() {
		tankLocation_X = stopX;
		tankLocation_Y = stopY;		
	}
	
	public void crashTank( Tank t ) {
		if(isLive && getRect().intersects(t.getRect())) {
			Direction dirBeforeStop = dir;
			stop(); clearCurrentDirection();			
			switch( dirBeforeStop ) {
				case UP :
					dir = Direction.DOWN;
					clearCurrentPtrDirection();
					enemyTankChangeDirection( dir );
					break;
				case DOWN :
					dir = Direction.UP;
					clearCurrentPtrDirection();
					enemyTankChangeDirection( dir );
					break;			
				case LEFT :
					dir = Direction.RIGHT;
					clearCurrentPtrDirection();
					enemyTankChangeDirection( dir );
					break;
				case RIGHT :
					dir = Direction.LEFT;
					clearCurrentPtrDirection();
					enemyTankChangeDirection( dir );
					break;		
				case STOP :
					//dir = Direction.UP;
					break;
			}
		}
	}
	
	public void playerCrashTank( Tank t ) {
		if(isLive && getRect().intersects(t.getRect())) {
			stop();
		}		
	}
	
	public void crashTanks( ArrayList<EnemyTank> enemyTanks) {
		for(int i = 0; i < enemyTanks.size(); i++) {
			Tank t = enemyTanks.get(i);
			if(this != t) {
				crashTank( t );
			}
		}
	}

}















