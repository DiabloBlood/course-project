package plants;

import environment.Environment;
import time.Clock;

public class Sunflower extends Plant {

	public Sunflower(Environment env, Clock clock) {
		super(env, clock);
	}

	@Override
	public void setPlant() {
		//��ʼ�߶�30����,������
		height = 20;
		//����15��
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
