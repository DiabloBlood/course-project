package tank;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;

import battle.BattleMediator;
import missile.EnemyMissile;

public class EnemyTank extends Tank {
	
	private int randomStep = r.nextInt(15) + 5;
	private int shotGap = 0;
	

	public EnemyTank(boolean isEnemy, int tankLocation_X, int tankLocation_Y, BattleMediator bm, int blood) {
		super(isEnemy, tankLocation_X, tankLocation_Y, bm, blood);
	}
	
	public EnemyTank(boolean isEnemy, int tankLocation_X, int tankLocation_Y, 
			BattleMediator bm, int blood, Direction dir) {
		super(isEnemy, tankLocation_X, tankLocation_Y, bm, blood);
		this.dir = dir;
	}

	@Override
	public void draw(Graphics g) {
		if(this.isLive()) {
			b.draw(g);
			//super.draw(g); //放super的话会把玩家坦克画上来
			switch( ptrDirection ) {
				case UP :
					g.drawImage(enemyImages[0], tankLocation_X, tankLocation_Y, null);
					break;
				case DOWN :
					g.drawImage(enemyImages[1], tankLocation_X, tankLocation_Y, null);
					break;			
				case LEFT :
					g.drawImage(enemyImages[2], tankLocation_X, tankLocation_Y, null);
					break;
				case RIGHT :
					g.drawImage(enemyImages[3], tankLocation_X, tankLocation_Y, null);
					break;
				case STOP:
					break;
				}
			
			switch( dir ) {
				case UP :
					g.drawImage(enemyImages[0], tankLocation_X, tankLocation_Y, null);
					break;
				case DOWN :
					g.drawImage(enemyImages[1], tankLocation_X, tankLocation_Y, null);
					break;			
				case LEFT :
					g.drawImage(enemyImages[2], tankLocation_X, tankLocation_Y, null);
					break;
				case RIGHT :
					g.drawImage(enemyImages[3], tankLocation_X, tankLocation_Y, null);
					break;		
				case STOP :
					break;
			}
			//每次重画都调用一次移动
			tankMove();
		}
		else {
			bm.enemyTanks.remove(this);
		}
	}

	@Override
	public void tankMove() {
		super.tankMove();
		//AI坦克的随机行走过程
		if( randomStep == 0) {
			//每次换方向的时候都STOP一下
			//dir = Direction.STOP;
			//clearCurrentDirection();
			randomStep = r.nextInt(30) + 15;
			//direction.lenth - 1是为了防止STOP 出现，AI坦克没有STOP
			int randomNum = r.nextInt(directions.length - 4);
			//int randomNum = 4;
			if( randomNum == 4) {
				dir = directions[randomNum];
				enemyTankChangeDirection( dir );				
			}
			else {
				clearCurrentDirection();
				clearCurrentPtrDirection();
				dir = directions[randomNum];
				enemyTankChangeDirection( dir );
			}

		}		
		randomStep--;
		shotGap++;
		if( shotGap == 15) {
			shotGap = 0;
			this.shot();
		}		
	}
	
	

	@Override
	public void shot() {
		EnemyMissile em = new EnemyMissile(true, tankLocation_X + 18, tankLocation_Y + 18, dir, bm);
		bm.addEnemyMissile(em);
	}
	
}
	
	

