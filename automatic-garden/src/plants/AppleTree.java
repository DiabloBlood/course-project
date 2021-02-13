package plants;

import environment.Environment;
import time.Clock;

public class AppleTree extends Plant {
	
	public AppleTree(Environment env, Clock clock) {
		super(env, clock);
	}

	@Override
	public void setPlant() {
		//��ʼ�߶�30����,������
		height = 100;
		//����15��
		lifeTime = 15;
	}

	@Override
	public int growth() {
		if( clock.hourSum()!=0 && clock.hourSum()%360 == 0 ){
			height = 100;
		}
		else {
			height = 100 + clock.hourSum()%360;
		}
		return height;
	}
}
