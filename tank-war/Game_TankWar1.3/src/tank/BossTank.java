package tank;

import java.awt.Graphics;

import battle.BattleMediator;
import missile.EnemyMissile;

public class BossTank extends EnemyTank {

	public BossTank(boolean isEnemy, int tankLocation_X, int tankLocation_Y, BattleMediator bm, int blood) {
		super(isEnemy, tankLocation_X, tankLocation_Y, bm, blood);
		setBossFlag();
	}

	@Override
	public void draw(Graphics g) {
		if(this.isLive()) {
			b.draw(g);
			//super.draw(g); //放super的话会把玩家坦克画上来
			switch( ptrDirection ) {
				case UP :
					g.drawImage(bossImages[0], tankLocation_X, tankLocation_Y, null);
					break;
				case DOWN :
					g.drawImage(bossImages[1], tankLocation_X, tankLocation_Y, null);
					break;			
				case LEFT :
					g.drawImage(bossImages[2], tankLocation_X, tankLocation_Y, null);
					break;
				case RIGHT :
					g.drawImage(bossImages[3], tankLocation_X, tankLocation_Y, null);
					break;
				case STOP:
					break;
				}
			
			switch( dir ) {
				case UP :
					g.drawImage(bossImages[0], tankLocation_X, tankLocation_Y, null);
					break;
				case DOWN :
					g.drawImage(bossImages[1], tankLocation_X, tankLocation_Y, null);
					break;			
				case LEFT :
					g.drawImage(bossImages[2], tankLocation_X, tankLocation_Y, null);
					break;
				case RIGHT :
					g.drawImage(bossImages[3], tankLocation_X, tankLocation_Y, null);
					break;		
				case STOP :
					break;
			}
			//每次重画都调用一次移动
			tankMove();
		}
	}
	
	@Override
	public void shot() {
		EnemyMissile em1 = new EnemyMissile(true, tankLocation_X + 50, tankLocation_Y + 50, Direction.LU, bm);
		EnemyMissile em2 = new EnemyMissile(true, tankLocation_X + 50, tankLocation_Y + 50, Direction.RU, bm);
		EnemyMissile em3 = new EnemyMissile(true, tankLocation_X + 50, tankLocation_Y + 50, Direction.LD, bm);		
		EnemyMissile em4 = new EnemyMissile(true, tankLocation_X + 50, tankLocation_Y + 50, Direction.RD, bm);
		EnemyMissile em5 = new EnemyMissile(true, tankLocation_X + 50, tankLocation_Y + 50, Direction.UP, bm);
		EnemyMissile em6 = new EnemyMissile(true, tankLocation_X + 50, tankLocation_Y + 50, Direction.DOWN, bm);
		EnemyMissile em7 = new EnemyMissile(true, tankLocation_X + 50, tankLocation_Y + 50, Direction.LEFT, bm);		
		EnemyMissile em8 = new EnemyMissile(true, tankLocation_X + 50, tankLocation_Y + 50, Direction.RIGHT, bm);
		bm.addEnemyMissile(em1);
		bm.addEnemyMissile(em2);
		bm.addEnemyMissile(em3);
		bm.addEnemyMissile(em4);
		bm.addEnemyMissile(em5);
		bm.addEnemyMissile(em6);
		bm.addEnemyMissile(em7);
		bm.addEnemyMissile(em8);
	}

	@Override
	public void setBossFlag() {
		bossFlag = true;
	}
	
	
}
