package plants;

import environment.Environment;
import time.Clock;

public class Orchid extends Plant {
	
	public Orchid(Environment env, Clock clock) {
		super(env, clock);
	}

	@Override
	public void setPlant() {
		//��ʼ�߶�30����,������
		height = 10;
		//����15��
		lifeTime = 6;
	}

	@Override
	public int growth() {
		if( clock.hourSum()!=0 && clock.hourSum()%144 == 0 ){
			height = 10;
		}
		else {
			height = 10 + (clock.hourSum()%144)/2;
		}
		return height;
	}
}
