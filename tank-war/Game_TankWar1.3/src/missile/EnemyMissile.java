package missile;

import java.awt.Graphics;

import battle.BattleMediator;
import tank.Tank.Direction;

public class EnemyMissile extends Missile {

	public EnemyMissile(boolean isEnemyShot, int missileLocation_X, int missileLocation_Y, 
			Direction ptrDir, BattleMediator bm) {
		super(isEnemyShot, missileLocation_X, missileLocation_Y, ptrDir, bm);
	}

	@Override
	public void draw(Graphics g) {
		if(missileInside) {
			g.drawImage(Missile.enemyMissile, missileLocation_X, missileLocation_Y, null);
			missileMove();
		}			
	}
	
}
