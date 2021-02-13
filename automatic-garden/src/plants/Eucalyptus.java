package plants;

import environment.Environment;
import time.Clock;

public class Eucalyptus extends Plant {
	
	public Eucalyptus(Environment env, Clock clock) {
		super(env, clock);
	}

	@Override
	public void setPlant() {
		//��ʼ�߶�1��,������
		height = 1;
		//����15��
		lifeTime = 30;
	}

	@Override
	public int growth() {
		if( clock.hourSum()!=0 && clock.hourSum()%720 == 0 ){
			height = 1;
		}
		else {
			height = 1 + (clock.hourSum()%720)/6;
		}
		return height;
	}	
}
