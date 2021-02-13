package plants;

import environment.Environment;
import time.Clock;

public class Sunflower extends Plant {

	public Sunflower(Environment env, Clock clock) {
		super(env, clock);
	}

	@Override
	public void setPlant() {
		//初始高度30厘米,浮点数
		height = 20;
		//生命15天
		lifeTime = 6;
	}

	@Override
	public int growth() {
		if( clock.hourSum()!=0 && clock.hourSum()%144 == 0 ){
			height = 20;
		}
		else {
			height = 20 + clock.hourSum()%144;
		}
		return height;
	}
}
