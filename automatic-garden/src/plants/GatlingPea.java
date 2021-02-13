package plants;

import environment.Environment;
import time.Clock;

public class GatlingPea extends Plant {
	
	public GatlingPea(Environment env, Clock clock) {
		super(env, clock);
	}

	@Override
	public void setPlant() {
		//��ʼ�߶�30����,������
		height = 30;
		//����15��
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
