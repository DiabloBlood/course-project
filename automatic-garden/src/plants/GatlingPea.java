package plants;

import environment.Environment;
import time.Clock;

public class GatlingPea extends Plant {
	
	public GatlingPea(Environment env, Clock clock) {
		super(env, clock);
	}

	@Override
	public void setPlant() {
		//初始高度30厘米,浮点数
		height = 30;
		//生命15天
		lifeTime = 10;
	}

	@Override
	public int growth() {
		if( clock.hourSum()!=0 && clock.hourSum()%240 == 0 ){
			height = 30;
		}
		else {
			height = 30 + (clock.hourSum()%240)/3;
		}
		return height;
	}
}
