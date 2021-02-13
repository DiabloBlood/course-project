package plants;

import environment.Environment;
import time.Clock;

public class Rose extends Plant {
	
	public Rose(Environment env, Clock clock) {
		super(env, clock);
	}

	@Override
	public void setPlant() {
		//初始高度30厘米,浮点数
		height = 5;
		//生命15天
		lifeTime = 6;
	}

	@Override
	public int growth() {
		if( clock.hourSum()!=0 && clock.hourSum()%144 == 0 ){
			height = 5;
		}
		else {
			height = (int) (5 + (clock.hourSum()%144)/1.5);
		}
		return height;
	}
}
