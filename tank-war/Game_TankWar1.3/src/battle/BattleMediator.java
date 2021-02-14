package battle;

import java.awt.Color;
import java.awt.Font;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JFrame;

import missile.EnemyMissile;
import missile.Missile;
import tank.BossTank;
import tank.EnemyTank;
import tank.Tank;
import tank.Tank.Direction;
import util.ImageLoader;


public class BattleMediator extends Frame {
	
	public static final int CANVAS_FIELD_WIDTH = 800;
	public static final int CANVAS_FIELD_HEIGHT = 600;
	public static final int ENEMY_TANKS_NUMBER = 10;
	public static final int ENEMY_INITIAL_X = 50;
	public static final int ENEMY_INITIAL_Y = 50;
	public static final int ENEMY_INITIAL_GAP = 60;
	public static final int PLAYER_INITIAL_X = 380;
	public static final int PLAYER_INITIAL_Y = 550;
	public static final int BOSS_INITIAL_X = 380;
	public static final int BOSS_INITIAL_Y = 100;
	
	private int[] gameOverFonts = new int[40];
	private int gameOverBuffer = 0;
	
	private Tank playerTank = new Tank(false, PLAYER_INITIAL_X, PLAYER_INITIAL_Y, this, 2000);
	private BossTank bossTank = new BossTank(true, BOSS_INITIAL_X, BOSS_INITIAL_Y, this, 1000);
	private ArrayList<Missile> normalMissiles = new ArrayList<Missile>();
	private ArrayList<EnemyMissile> enemyMissiles = new ArrayList<EnemyMissile>();
	public ArrayList<Explosion> explosions = new ArrayList<Explosion>();
	public ArrayList<EnemyTank> enemyTanks = new ArrayList<EnemyTank>();
	
	Image avoidScreenTwinkle = null;
	
	Image canvas = ImageLoader.loadImageIcon("background.png").getImage();
	
	public BattleMediator() {
		this.setLocation(100, 50);
		this.setSize(CANVAS_FIELD_WIDTH, CANVAS_FIELD_HEIGHT);
		this.setBackground(Color.BLACK);
		this.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});
		this.setResizable(false);
		this.setVisible(true);	
		for(int i = 0; i < ENEMY_TANKS_NUMBER; i++) {
			enemyTanks.add(new EnemyTank(true, ENEMY_INITIAL_X + ENEMY_INITIAL_GAP*(i+1), 
					ENEMY_INITIAL_Y, this, 100, Direction.DOWN));
		}
		for(int i = 0; i < gameOverFonts.length; i++) {
			gameOverFonts[i] = i + 20;
		}
		new Thread(new PaintTread()).start();
		new Thread(new deleteGarbageTread()).start();
		this.addKeyListener(new KeyboardListener());
	}
	@Override
	public void update(Graphics g) {
		if(avoidScreenTwinkle == null) {
			avoidScreenTwinkle = this.createImage(CANVAS_FIELD_WIDTH, CANVAS_FIELD_HEIGHT);
		}
		Graphics avoidTwinkle  = avoidScreenTwinkle.getGraphics();
		Color c = avoidTwinkle.getColor();
		avoidTwinkle.setColor(Color.BLACK);
		avoidTwinkle.fillRect(0,0,CANVAS_FIELD_WIDTH, CANVAS_FIELD_HEIGHT);
		avoidTwinkle.setColor(c);
		paint(avoidTwinkle);
		g.drawImage(avoidScreenTwinkle,0,0,null);
	}
	
	@Override
	public void paint(Graphics g) {		
		//super.paint(g);
		g.setColor(Color.WHITE);
		g.drawString("Missiles: " + normalMissiles.size(), 10, 50);
		g.drawString("Explosions: " + explosions.size(), 10, 70);
		g.drawString("EnemyTanks: " + enemyTanks.size(), 10, 90);
		g.drawString("EnemyMissiles: " + enemyMissiles.size(), 10, 110);
        g.drawImage(canvas, 0, 0, null);
		//防止敌方坦克相撞
		for(int i = 0; i < enemyTanks.size(); i++) {
			Tank t = enemyTanks.get(i);
			t.crashTanks(enemyTanks);
			playerTank.playerCrashTank(t);
			bossTank.crashTanks(enemyTanks);
		}		
		//判断每个player发出的子弹有没有打到敌方坦克，如果有，在那个点加入一个爆炸
		for(int i = 0; i < normalMissiles.size(); i++) {
			Missile m = normalMissiles.get(i);
			m.hitTanks(enemyTanks);
			m.hitTank(bossTank);
			m.draw(g);
		}
		//判断敌方坦克有没有打player坦克，如果有，加一个爆炸
		for(int i = 0; i < enemyMissiles.size(); i++) {
			EnemyMissile em = enemyMissiles.get(i);
			em.hitTank(playerTank);
			em.draw(g);
		}
		//画出每一个爆炸
		for(int i = 0; i < explosions.size(); i++){
			Explosion e = explosions.get(i);
			e.draw(g);
		}
		//画出敌方坦克，死了就删除掉
		for(int i = 0; i < enemyTanks.size(); i++) {
			EnemyTank et = enemyTanks.get(i);
			et.draw(g);
		}
		bossTank.draw(g);
		//显示Game over
		//playerTank.draw(g);
		if(playerTank.isLive() == true) {
			playerTank.draw(g);
		}			
		else {
			g.setColor(Color.RED);
			g.setFont(new Font("Tahoma", Font.BOLD + Font.ITALIC , gameOverFonts[gameOverBuffer]));
			g.drawString("GAME OVER!!!", 200, 300);
			if ( gameOverBuffer < gameOverFonts.length - 1) {
				gameOverBuffer++;
			}
		}						
	}
	
	
	private class PaintTread implements Runnable {
		public void run() {
			while( true ) {
				repaint();
				try {
					Thread.sleep (30);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	private class deleteGarbageTread implements Runnable {

		@Override
		public void run() {
			while( true ) {
				for(int i = 0; i < normalMissiles.size(); i++) {
					Missile m = normalMissiles.get(i);
					if( m.isMissileInside() == false) 
						normalMissiles.remove(m);
				}
				for(int i = 0; i < enemyMissiles.size(); i++) {
					Missile m = enemyMissiles.get(i);
					if( m.isMissileInside() == false) 
						enemyMissiles.remove(m);
				}
				try {
					Thread.sleep (1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
		
	}
	private class KeyboardListener extends KeyAdapter {
		
		//持续按下
		@Override
		public void keyPressed(KeyEvent e) {
			playerTank.pressKeyboard(e);
		}
		//松开键盘按钮坦克停下
		@Override
		public void keyReleased(KeyEvent e) {
			playerTank.releaseKeyboard(e);
		}
		
	}
	
	public void addNormalMissile(Missile m) {
		normalMissiles.add(m);
	}
	public void addEnemyMissile(EnemyMissile em) {
		enemyMissiles.add(em);
	}
	public static void main(String[] args) {
		BattleMediator wf = new BattleMediator();
	}

}
